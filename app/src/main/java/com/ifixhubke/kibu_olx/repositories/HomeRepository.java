package com.ifixhubke.kibu_olx.repositories;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ifixhubke.kibu_olx.data.Item;
import com.ifixhubke.kibu_olx.others.CheckInternet;

import java.util.ArrayList;

import timber.log.Timber;

public class HomeRepository{

    public MutableLiveData<ArrayList<Item>> itemsList = new MutableLiveData<>();
    public MutableLiveData<Boolean> timeout = new MutableLiveData<>();
    public MutableLiveData<Boolean> connectedToInternet = new MutableLiveData<>();

    private final Context context;

    public HomeRepository(Application application){
        context = application.getApplicationContext();
    }


    ArrayList<Item> items = new ArrayList<>();

    public void refresh(){
        fetchItems();
    }

    public MutableLiveData<ArrayList<Item>> fetchItems() {

        final boolean[] gotResult = new boolean[1];

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gotResult[0] = true;

                if (snapshot.exists()) {

                    for (DataSnapshot i : snapshot.getChildren()) {
                        Item item = i.getValue(Item.class);
                        items.add(item);
                        itemsList.setValue(items);
                    }
                } else {
                    Timber.d("snapshot not found");
                    items.clear();
                    itemsList.setValue(items);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                gotResult[0] = true;
                Timber.d(error.getMessage());
            }
        };

        if (CheckInternet.isConnected(context)) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("all_items");
            databaseReference.addListenerForSingleValueEvent(valueEventListener);

            Handler mHandler = new Handler();
            Runnable makeToast = () -> {
                if (!gotResult[0]) { //  Timeout
                    databaseReference.removeEventListener(valueEventListener);
                    timeout.setValue(true);
                    Timber.d("Timeout");
                }
            };
            mHandler.postDelayed(makeToast, 15000);
        } else {
            Timber.d("No Internet");
            timeout.setValue(true);
            connectedToInternet.setValue(false);
        }

        return itemsList;
    }
}

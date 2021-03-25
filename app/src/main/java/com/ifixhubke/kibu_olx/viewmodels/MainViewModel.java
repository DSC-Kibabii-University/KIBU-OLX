package com.ifixhubke.kibu_olx.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ifixhubke.kibu_olx.data.Item;
import com.ifixhubke.kibu_olx.repositories.MainRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private final MainRepository mainRepository;
    private final LiveData<List<Item>> allItems;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mainRepository = new MainRepository(application);
        allItems = mainRepository.getAllItems();
    }

    /**
     * wrapper methods for database operations, from the repository
     **/
    public void insert(Item item) {
        mainRepository.insert(item);
    }

    public void delete(Item item) {
        mainRepository.delete(item);
    }

    public LiveData<List<Item>> allItems() {
        return allItems;
    }

    public void updateSoldItem(Boolean isSoldOut, int id) {
        mainRepository.updateSoldItem(isSoldOut, id);
    }
}

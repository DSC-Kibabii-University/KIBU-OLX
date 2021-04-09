package com.ifixhubke.kibu_olx.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ifixhubke.kibu_olx.data.Item;
import com.ifixhubke.kibu_olx.repositories.HomeRepository;
import com.ifixhubke.kibu_olx.repositories.MainRepository;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private final MainRepository mainRepository;
    private final HomeRepository homeRepository;
    private final LiveData<List<Item>> allItems;
    public LiveData<Boolean> timeout;
    public LiveData<Boolean> connectedToInternet;
    public MutableLiveData<ArrayList<Item>> itemsList = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        mainRepository = new MainRepository(application);
        homeRepository = new HomeRepository(application);
        allItems = mainRepository.getAllItems();
        timeout = homeRepository.timeout;
        connectedToInternet = homeRepository.connectedToInternet;
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

    public LiveData<ArrayList<Item>> fetchItems() {
        return homeRepository.fetchItems();
    }

    public void refresh() {
        homeRepository.refresh();
    }
}

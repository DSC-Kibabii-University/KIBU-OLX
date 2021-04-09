package com.ifixhubke.kibu_olx.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ifixhubke.kibu_olx.data.Item;
import com.ifixhubke.kibu_olx.data.ItemsDao;
import com.ifixhubke.kibu_olx.data.ItemsDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainRepository {

    private final ItemsDao itemsDao;
    private final LiveData<List<Item>> allItems;
    MutableLiveData<ArrayList<Item>> itemsList = new MutableLiveData<>();

    //var fragment2EditText: String? = null
    public MainRepository(Application application) {
        ItemsDatabase database = ItemsDatabase.getInstance(application);
        itemsDao = database.itemsDao();
        allItems = itemsDao.getAllItems();
    }

    public void insert(Item item) {
        new InsertItemAsyncTask(itemsDao).execute(item);
    }

    public void delete(Item item) {
        new DeleteItemAsyncTask(itemsDao).execute(item);
    }

    public LiveData<List<Item>> getAllItems() {
        return allItems;
    }

    public void updateSoldItem(Boolean isSoldOut, int id) {
        new UpdateItemSoldOut(itemsDao, isSoldOut, id).execute();
    }


    //AsyncTasks to execute the code in the background thread because database operations should not be executed in the UI thread

    //Insert a item
    private static class InsertItemAsyncTask extends AsyncTask<Item, Void, Void> {

        private final ItemsDao itemsDao;

        private InsertItemAsyncTask(ItemsDao itemsDao) {
            this.itemsDao = itemsDao;
        }

        @Override
        protected Void doInBackground(Item... items) {

            itemsDao.insert(items[0]);
            return null;
        }
    }

    private static class UpdateItemSoldOut extends AsyncTask<Boolean, Void, Void> {

        int id;
        Boolean isSoldOut;
        private final ItemsDao itemsDao;

        private UpdateItemSoldOut(ItemsDao itemsDao, Boolean isSoldOut, int id) {
            this.itemsDao = itemsDao;
            this.id = id;
            this.isSoldOut = isSoldOut;
        }

        @Override
        protected Void doInBackground(Boolean... booleans) {
            itemsDao.updateItemSoldOut(isSoldOut, id);
            return null;
        }
    }

    //Delete a item
    private static class DeleteItemAsyncTask extends AsyncTask<Item, Void, Void> {

        private final ItemsDao itemsDao;

        private DeleteItemAsyncTask(ItemsDao itemsDao) {
            this.itemsDao = itemsDao;
        }

        @Override
        protected Void doInBackground(Item... items) {

            itemsDao.delete(items[0]);
            return null;
        }
    }
}

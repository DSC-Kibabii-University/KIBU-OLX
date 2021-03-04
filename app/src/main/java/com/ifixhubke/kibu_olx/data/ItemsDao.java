package com.ifixhubke.kibu_olx.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Item item);

    @Delete
    public void delete(Item item);

    @Query("SELECT *FROM posted_item_history ORDER BY datePosted DESC")
    public LiveData<List<Item>> getAllItems();
}

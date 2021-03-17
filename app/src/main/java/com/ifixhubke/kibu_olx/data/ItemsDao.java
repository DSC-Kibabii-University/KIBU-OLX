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
    void insert(Item item);

    @Delete
    void delete(Item item);

    @Query("SELECT *FROM posted_item_history ORDER BY datePosted DESC")
    LiveData<List<Item>> getAllItems();

    @Query("UPDATE posted_item_history SET isSoldOut = :soldOut WHERE id = :id")
    void updateItemSoldOut(Boolean soldOut, int id);
}

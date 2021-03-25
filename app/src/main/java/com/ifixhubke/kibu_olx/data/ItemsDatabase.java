package com.ifixhubke.kibu_olx.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Item.class, version = 4, exportSchema = false)
public abstract class ItemsDatabase extends RoomDatabase {

    /**
     * We make it static so that the class can be singleton,
     * means we cannot make multiple instance of the class
     * which means we use the same instance of the class everywhere
     **/
    private static ItemsDatabase instance;

    /**
     * Synchronized means that a single thread at time can access the class
     **/
    public static synchronized ItemsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ItemsDatabase.class, "items_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    /**
     * this method has no method body
     * this will be used to access the note_item DAO
     **/
    public abstract ItemsDao itemsDao();
}

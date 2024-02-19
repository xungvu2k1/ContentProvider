package com.paci.training.android.xungvv.contentprovider.localdata.checkeditemmodel;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CheckedItemDao {
    @Query("SELECT * FROM checked_item_table")
    LiveData<List<CheckedItem>> getAllCheckedItem();

    @Insert(onConflict = OnConflictStrategy.IGNORE)// bỏ qua fruit mới nếu nó đã có trong danh sách
    long insert(CheckedItem checkedItem);

    @Query("SELECT * FROM checked_item_table")
    Cursor findAll();

    @Update
    int update(CheckedItem checkedItem);

    @Delete
    int delete(CheckedItem checkedItem);

    @Query("DELETE FROM checked_item_table")
    int deleteAll();
}

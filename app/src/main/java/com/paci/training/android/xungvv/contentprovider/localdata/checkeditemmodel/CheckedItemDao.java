package com.paci.training.android.xungvv.contentprovider.localdata.checkeditemmodel;

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
    void insert(CheckedItem checkedItem);

    @Update
    void update(CheckedItem checkedItem);

    @Delete
    void delete(CheckedItem checkedItem);

    @Query("DELETE FROM checked_item_table")
    void deleteAll();
}

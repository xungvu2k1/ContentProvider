package com.paci.training.android.xungvv.contentprovider.localdata.fruitmodel;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.paci.training.android.xungvv.contentprovider.localdata.fruitmodel.Fruit;

import java.util.List;
//https://developer.android.com/codelabs/android-room-with-a-view#8
// data access object (DAO) xác thực mã SQL tại compile time và liên kết nó với 1 method.
// Room dùng DAO để tạo 1 clean API cho code.

//DAO phải là 1 interface hoặc abstract class. By default, all queries must be executed on a separate thread
@Dao
public interface FruitDao {
    @Query("SELECT * FROM fruit_table")
    LiveData<List<Fruit>> getAllFruits();

    @Insert(onConflict = OnConflictStrategy.IGNORE)// bỏ qua fruit mới nếu nó đã có trong danh sách
    void insertFruit(Fruit fruit);

    @Update
    void updateFruit(Fruit fruit);

    @Delete
    void deleteFruit(Fruit fruit);

    @Query("DELETE FROM fruit_table")
    void deleteAll();
}

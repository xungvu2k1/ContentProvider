package com.paci.training.android.xungvv.contentprovider.localdata.checkeditemmodel;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "checked_item_table")
public class CheckedItem {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "fruitId")
    private int fruitId;
    @ColumnInfo(name = "fruit_valid")
    private int fruitValid; // 0 hoặc 1

    public CheckedItem(int fruitId, int fruitValid) {
        this.fruitId = fruitId;
        this.fruitValid = fruitValid;
    }

    public CheckedItem(){

    }

    public int getFruitId() {
        return fruitId;
    }

    public void setFruitId(int fruitId) {
        this.fruitId = fruitId;
    }

    public int getFruitValid() {
        return fruitValid;
    }

    public void setFruitValid(int fruitValid) {
        this.fruitValid = fruitValid;
    }

}


package com.paci.training.android.xungvv.contentprovider.localdata.checkeditemmodel;

import com.paci.training.android.xungvv.contentprovider.localdata.fruitmodel.Fruit;

import java.util.ArrayList;
import java.util.List;

public class RawCheckedItemData {
    public static List<CheckedItem> getInitCheckedItems(){
        List<CheckedItem> list = new ArrayList<>();
        list.add(new CheckedItem(1, 0));
        list.add(new CheckedItem(2, 0));
        list.add(new CheckedItem(3, 0));
        list.add(new CheckedItem(4, 0));
        list.add(new CheckedItem(5, 0));
        list.add(new CheckedItem(6, 0));
        list.add(new CheckedItem(7, 0));
        list.add(new CheckedItem(8, 0));
        list.add(new CheckedItem(9, 0));
        list.add(new CheckedItem(10, 0));
        list.add(new CheckedItem(11, 0));
        list.add(new CheckedItem(12, 0));
        list.add(new CheckedItem(13, 0));
        list.add(new CheckedItem(14, 0));
        list.add(new CheckedItem(15, 0));
        return list;
    }
}

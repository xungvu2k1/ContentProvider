package com.paci.training.android.xungvv.contentprovider.localdata.repository;


import android.app.Application;

import androidx.lifecycle.LiveData;

import com.paci.training.android.xungvv.contentprovider.localdata.fruitmodel.Fruit;
import com.paci.training.android.xungvv.contentprovider.localdata.fruitmodel.FruitDao;
import com.paci.training.android.xungvv.contentprovider.localdata.fruitmodel.FruitDatabase;

import java.util.List;

/*
- repository trừu tượng hóa quyền truy cập vào nhiều data source
- ko phải là thành phần của Architecture component, nhưng là phương pháp để phần tách mã và kiến trúc
- quản lí các truy vấn và cho phép ta sử dụng nhiều chương trình phụ trợ: triển khai logic để tìm nạp dữ liệu từ nguồn nào

 */
public class FruitRepository {
    private FruitDao mFruitDao;
    private LiveData<List<Fruit>> mAllFruits;

    public FruitRepository(Application application){
        FruitDatabase db = FruitDatabase.getDatabase(application);
        mFruitDao = db.fruitDao();
        mAllFruits = mFruitDao.getAllFruits();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Fruit>> getAllFruits() {
        return mAllFruits;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Fruit fruit) {
        FruitDatabase.databaseWriteExecutor.execute(() -> {
            mFruitDao.insertFruit(fruit);
        });
    }
}

package com.paci.training.android.xungvv.contentprovider.localdata.checkeditemmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {CheckedItem.class}, version = 1, exportSchema = false)
public abstract class CheckedItemDatabase extends RoomDatabase{
    public abstract CheckedItemDao checkedItemDao();
    private static volatile CheckedItemDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static CheckedItemDatabase getDatabase(Context context) {
        if (INSTANCE == null){
            synchronized (com.paci.training.android.xungvv.contentprovider.localdata.fruitmodel.FruitDatabase.class){
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    CheckedItemDatabase.class, "checked_item_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                CheckedItemDao dao = INSTANCE.checkedItemDao();
                dao.deleteAll();

                List<CheckedItem> checkedItems = RawCheckedItemData.getInitCheckedItems();
                for (CheckedItem checkedItem : checkedItems){
                    dao.insert(checkedItem);
                }
            });
        }
    };
}

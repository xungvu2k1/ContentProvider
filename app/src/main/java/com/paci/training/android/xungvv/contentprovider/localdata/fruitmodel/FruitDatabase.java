package com.paci.training.android.xungvv.contentprovider.localdata.fruitmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//https://developer.android.com/codelabs/android-room-with-a-view#8
//Room database
// là 1 lớp csdl nằm trên csdl sqlite
// đảm nhiệm công việc đơn giản mà ta từng xử lý bằng sqliteOpenHelper
// room sd DAO để đưa ra các truy vấn tới csdl của mình
// để tránh hiệu suất người dùng kém, room ko cho phép đưa ra truy vấn trên luồng chính
// khi truy vấn room trả về livedata=> các truy vấn sẽ tự động chạy ko đồng bộ trên chuỗi nền
// cung cấp khả năng kiểm tra thời gian biên dịch các câu lệnh sqlite
@Database(entities = {Fruit.class}, version = 1, exportSchema = false)
public abstract class FruitDatabase extends RoomDatabase {
    public abstract FruitDao fruitDao();

    /*
        volatile đồng bộ hóa truy cập của nhiều luồng
        giúp khi 1 luồng cập nhật data thì các luồng khác cũng thấy được giá trị mới nhất => tránh đọc giá trị lỗi
     */
    private static volatile FruitDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS); // use to run database operations asynchronously on a background thread.

    public static FruitDatabase getDatabase(Context context){
        if (INSTANCE == null){
            synchronized (FruitDatabase.class){
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    FruitDatabase.class, "fruit_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){

        /*
        Because you cannot do Room database operations on the UI thread,
        onCreate() uses the previously defined databaseWriteExecutor to execute a lambda on a background thread.
        The lambda deletes the contents of the database, then populates it with the contents.
         */
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                FruitDao dao = INSTANCE.fruitDao();
                dao.deleteAll();

                List<Fruit> fruits = RawFruitData.getInitFruits();
                for (Fruit fruit : fruits){
                    dao.insertFruit(fruit);
                }

//                Fruit fruit = new Fruit(0, "Vietnam", "This is Vietnam");
//                dao.insertFruit(fruit);
//                fruit = new Fruit(-1, "Vietnam-1", "This is Vietnam-1");
//                dao.insertFruit(fruit);
//                fruit = new Fruit(1, "Vietnam2", "This is Vietnam3");
//                dao.insertFruit(fruit);
            });
        }
    };
}

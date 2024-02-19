package com.paci.training.android.xungvv.contentprovider.localdata.repository;

import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.paci.training.android.xungvv.contentprovider.localdata.checkeditemmodel.CheckedItem;
import com.paci.training.android.xungvv.contentprovider.localdata.checkeditemmodel.CheckedItemDao;
import com.paci.training.android.xungvv.contentprovider.localdata.checkeditemmodel.CheckedItemDatabase;

import java.util.Objects;

public class MyContentProvider extends ContentProvider {
    private CheckedItemDao checkedItemDao;
    private static final int CHECKED_ITEMS = 1;
    public static final String authority = "com.example.myapp.provider";
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(authority, "checked_item_table", CHECKED_ITEMS);
    }

    @Override
    public boolean onCreate() {
        CheckedItemDatabase checkedItemDatabase = CheckedItemDatabase.getDatabase(getContext());
        checkedItemDao = checkedItemDatabase.checkedItemDao();
        return true;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        switch (uriMatcher.match(uri)) {
            case CHECKED_ITEMS:

                long id = checkedItemDao.insert(convertContentValuesToCheckedItem(values));
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }



    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case CHECKED_ITEMS:
                // Triển khai logic xóa dữ liệu từ Room Database ở đây
                int rowsDeleted = checkedItemDao.deleteAll();
                getContext().getContentResolver().notifyChange(uri, null);
                return rowsDeleted;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case CHECKED_ITEMS:
                // Triển khai logic truy vấn Room Database ở đây
                // Sử dụng checkedItemDao để truy vấn dữ liệu
                return checkedItemDao.findAll();
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,
                      @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case CHECKED_ITEMS:
                // Triển khai logic cập nhật dữ liệu trong Room Database ở đây
                // Sử dụng checkedItemDao để cập nhật giá trị của fruit_valid
                int rowsUpdated = checkedItemDao.update(convertContentValuesToCheckedItem(values));
                getContext().getContentResolver().notifyChange(uri, null);
                return rowsUpdated;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    private CheckedItem convertContentValuesToCheckedItem(ContentValues values) {
        CheckedItem checkedItem = new CheckedItem();
        if (values.containsKey("fruitId")) {
            checkedItem.setFruitId(values.getAsInteger("fruitId"));
        }
        if (values.containsKey("fruit_valid")) {
            checkedItem.setFruitValid(values.getAsInteger("fruit_valid"));
        }
        // Nếu có thêm các trường khác, hãy thêm vào đây
        return checkedItem;
    }
}
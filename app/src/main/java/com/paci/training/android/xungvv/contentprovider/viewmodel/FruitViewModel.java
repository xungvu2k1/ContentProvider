package com.paci.training.android.xungvv.contentprovider.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.paci.training.android.xungvv.contentprovider.localdata.fruitmodel.Fruit;
import com.paci.training.android.xungvv.contentprovider.localdata.repository.FruitRepository;

import java.util.List;

/*
- vai trò: cung cấp dữ liệu cho giao diện người dùng và lưu trữ dữ liệu UI khi thay đổi cấu hình
- hoạt động như trung tâm liên lạc giữa repository và UI, chia sẻ dữ liệu giữa các fragment
- tách biệt app's data khỏi activity và fragment giúp tuân thủ single reponsibility principle trong solid: activity chỉ hiển thị dữ liệu lên
    màn hình, còn viewmodel sẽ hold and process all data need for UI.
- Note:
    - Viewmodel ko call database 1 cách trực tiếp mà sẽ thông qua repository => giúp code dễ test hơn.
    - ko giữ tham chiếu đến context có lifecycle ngắn hơn viewmodel như fragment, activity, view,...( tức ko khai báo nó ở file này). Việc giữ một tham chiếu có thể gây rò rỉ bộ nhớ,
        ví dụ: ViewModel có tham chiếu đến một Hoạt động bị phá hủy! Tất cả các đối tượng này có thể bị hệ điều hành hủy bỏ và được tạo lại khi có thay đổi về cấu hình. Điều này có thể xảy ra nhiều lần trong vòng đời của ViewModel.
 */
public class FruitViewModel extends AndroidViewModel {
    private final FruitRepository mRepository;
    private final LiveData<List<Fruit>> mAllFruits;

    public FruitViewModel(Application application) {
        super(application);
        mRepository = new FruitRepository(application);
        mAllFruits = mRepository.getAllFruits();
    }

    public LiveData<List<Fruit>> getAllFruits() { return mAllFruits; }
    public void insert(Fruit fruit) {
        mRepository.insert(fruit);
    }
}

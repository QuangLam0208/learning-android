package com.persy.learnandroid.demo.controls;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.persy.learnandroid.R;
import com.persy.learnandroid.adapter.ButtonAdapter;
import com.persy.learnandroid.databinding.ActivityControlButtonBinding;
import com.persy.learnandroid.model.ButtonItem;

import java.util.ArrayList;
import java.util.List;

public class ButtonDemoActivity extends AppCompatActivity {

    private ActivityControlButtonBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_control_button);
        binding.setActivity(this);
        binding.setLifecycleOwner(this);

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.ime());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    
        setupToolBar();
        setupRecyclerView();
        binding.setBtnItemList(createListButton());
    }

    private void setupToolBar() {
        setSupportActionBar(binding.includeToolbar.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Button Variations");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        binding.includeToolbar.toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupRecyclerView() {
        binding.rvButtons.setLayoutManager(new LinearLayoutManager(this));
        binding.rvButtons.setAdapter(new ButtonAdapter());
    }

    public void scrollToBottom() {
        binding.nestedScrollView.post(() -> binding.nestedScrollView.fullScroll(View.FOCUS_DOWN));
    }

    private List<ButtonItem> createListButton() {
        List<ButtonItem> list = new ArrayList<>();

        list.add(new ButtonItem(
                "Standard Button",
                "Nút cơ bản của Android, hiển thị nền và chữ, thường dùng để thực hiện các hành động như Đăng nhập, Tiếp tục hoặc Lưu."
        ));

        list.add(new ButtonItem(
                "Outlined Button",
                "Nút Material chỉ có đường viền và không có nền màu nổi bật, thường dùng cho các hành động phụ như Hủy, Bỏ qua hoặc Quay lại."
        ));

        list.add(new ButtonItem(
                "Text Button",
                "Nút Material chỉ hiển thị nội dung dạng văn bản, không có nền hoặc đường viền nổi bật, thường dùng cho các hành động ít quan trọng."
        ));

        list.add(new ButtonItem(
                "Image Button",
                "Nút hiển thị hình ảnh hoặc biểu tượng thay vì văn bản, thường dùng cho các thao tác quen thuộc như Tìm kiếm, Chỉnh sửa, Xóa hoặc Thêm."
        ));

        list.add(new ButtonItem(
                "Floating Action Button (FAB)",
                "Nút tròn nổi đặc trưng của Material Design, thường chứa một biểu tượng và được dùng cho hành động chính của màn hình như Thêm hoặc Tạo mới."
        ));

        list.add(new ButtonItem(
                "Switch",
                "Control dùng để bật hoặc tắt một tùy chọn, luôn có hai trạng thái ON/OFF rõ ràng, thường dùng cho Wi-Fi, Dark Mode hoặc thông báo."
        ));

        list.add(new ButtonItem(
                "Toggle Button",
                "Control có thể chuyển đổi giữa các trạng thái hoặc lựa chọn. Khác Button thông thường ở chỗ nó duy trì trạng thái được chọn sau khi người dùng nhấn."
        ));

        list.add(new ButtonItem(
                "CheckBox",
                "Control cho phép người dùng chọn hoặc bỏ chọn một tùy chọn độc lập. Có thể chọn nhiều CheckBox cùng lúc, thường dùng cho danh sách tùy chọn."
        ));

        list.add(new ButtonItem(
                "Radio Button",
                "Control dùng để chọn một tùy chọn trong một nhóm. Các Radio Button cùng nhóm thường chỉ cho phép người dùng chọn một lựa chọn tại một thời điểm."
        ));

        return list;
    }
}
package com.persy.learnandroid.data;

import com.persy.learnandroid.model.Topic;
import com.persy.learnandroid.model.ETopicCategory;

import java.util.ArrayList;
import java.util.List;

public class TopicRepository {
    public static List<Topic> getMainTopics() {
        List<Topic> topics = new ArrayList<>();

        topics.add(new Topic(ETopicCategory.LAYOUTS.name(), "Layouts", "LinearLayout, ConstraintLayout, RelativeLayout...", null, true));
        topics.add(new Topic(ETopicCategory.CONTROLS.name(), "Controls", "Button, TextView, EditText, CheckBox...", null, true));
        topics.add(new Topic(ETopicCategory.INTENT_BUNDLE.name(), "Intent & Bundle", "Truyền nhận dữ liệu giữa các Activity", null, true));
        topics.add(new Topic(ETopicCategory.ROOM_DATABASE.name(), "SQLite & Room Database", "CRUD với Room Database", null, true));
        topics.add(new Topic(ETopicCategory.RETROFIT.name(), "Retrofit", "REST Client type-safe cho Android", null, true));
        //topics.add(new Topic(ETopicCategory.RECYCLERVIEW.name(), "RecyclerView", "Xử lý danh sách nâng cao với Adapter", null, true));

        return topics;
    }
    public static List<Topic> getTopicsForCategory(ETopicCategory category) {
        List<Topic> topics = new ArrayList<>();

        switch (category) {
            case LAYOUTS:
                topics.add(new Topic("L1", "LinearLayout", "Sắp xếp view theo hàng ngang hoặc dọc", "KEY_LINEAR_LAYOUT", false));
                topics.add(new Topic("L2", "RelativeLayout", "Sắp xếp view tương đối với nhau", "KEY_RELATIVE_LAYOUT", false));
                topics.add(new Topic("L3", "FrameLayout", "Xếp chồng các view lên nhau", "KEY_FRAME_LAYOUT", false));
                topics.add(new Topic("L4", "ConstraintLayout", "Sắp xếp view linh hoạt bằng constraint", "KEY_CONSTRAINT_LAYOUT", false));
                topics.add(new Topic("L5", "TableLayout", "Giao diện dạng bảng (row/col)", "KEY_TABLE_LAYOUT", false));
                topics.add(new Topic("L6", "GridLayout", "Giao diện dạng lưới", "KEY_GRID_LAYOUT", false));
                break;

            case CONTROLS:
                topics.add(new Topic("C1", "TextView", "Hiển thị đoạn text", "KEY_TEXT_VIEW", false));
                topics.add(new Topic("C2", "EditText", "Trường nhập dữ liệu văn bản", "KEY_EDIT_TEXT", false));
                topics.add(new Topic("C3", "Button", "Các loại Button", "KEY_BUTTON", false));
                topics.add(new Topic("C4", "Spinner", "Danh sách xổ xuống (Dropdown)", "KEY_SPINNER", false));
                topics.add(new Topic("C5", "ProgressBar", "Thanh trạng thái tải dữ liệu", "KEY_PROGRESS_BAR", false));
                topics.add(new Topic("C6", "SeekBar", "Thanh trượt chọn giá trị", "KEY_SEEK_BAR", false));
                break;

            case INTENT_BUNDLE:
                topics.add(new Topic("I1", "Put Extra", "Truyền dữ liệu kiểu cơ bản: Int, Float, Char, Double, Boolean, String", "KEY_SEND_EXTRA", false));
                topics.add(new Topic("I2", "Put Extras & Bundle", "Truyền nhiều giá trị bằng Bundle", "KEY_SEND_BUNDLE", false));
                topics.add(new Topic("I3", "Send Object", "Truyền Object (Serializable/Parcelable)", "KEY_SEND_OBJECT", false));
                topics.add(new Topic("I4", "Return Data", "Lấy dữ liệu trả về (ActivityResultLauncher)", "KEY_RETURN_DATA", false));
                break;

            case ROOM_DATABASE:
                topics.add(new Topic("R1", "Overview", "SQLite Database và Room", "KEY_ROOM_OVERVIEW", false));
                topics.add(new Topic("R2", "Room Entity", "Các lớp dữ liệu đại diện cho các bảng trong database", "KEY_ROOM_ENTITY", false));
                topics.add(new Topic("R3", "Room DAO", "Cung cấp các hàm để ứng dụng thực hiện các thao tác truy vấn, cập nhật, thêm và xóa dữ liệu trong database.", "KEY_ROOM_DAO", false));
                topics.add(new Topic("R4", "Todo Demo", "Basic Todo App Demo CRUD", "KEY_ROOM_DEMO", false));
                break;

            case RETROFIT:
                topics.add(new Topic("RF1", "Overview", "Retrofit là gì, kiến trúc tổng quan", "KEY_RETROFIT_OVERVIEW", false));
                topics.add(new Topic("RF2", "POJO & Converter", "Model đại diện JSON và Gson converter", "KEY_RETROFIT_POJO", false));
                topics.add(new Topic("RF3", "API Interface", "Khai báo endpoint bằng annotation", "KEY_RETROFIT_API_INTERFACE", false));
                topics.add(new Topic("RF4", "Retrofit Client", "Cấu hình Base URL, Converter, OkHttpClient", "KEY_RETROFIT_CLIENT", false));
                topics.add(new Topic("RF5", "Authentication", "Xác thực bằng Header hoặc OkHttp Interceptor", "KEY_RETROFIT_AUTH", false));
                topics.add(new Topic("RF6", "Todo Demo", "Todo sử dụng Retrofit + Room", "KEY_RETROFIT_DEMO", false));
                break;

//            case RECYCLERVIEW:
//                topics.add(new Topic("R1", "LinearLayoutManager", "Danh sách dọc/ngang cơ bản", "KEY_LINEAR_MANAGER", false));
//                topics.add(new Topic("R2", "GridLayoutManager", "Danh sách dạng lưới", "KEY_GRID_MANAGER", false));
//                topics.add(new Topic("R3", "CardView", "Trang trí item với bóng đổ và bo góc", "KEY_CARD_VIEW", false));
//                topics.add(new Topic("R4", "ViewHolder", "Kỹ thuật tối ưu bộ nhớ", "KEY_VIEW_HOLDER", false));
//                topics.add(new Topic("R5", "Adapter", "Cầu nối giữa data và RecyclerView", "KEY_ADAPTER", false));
//                topics.add(new Topic("R6", "Item Click", "Bắt sự kiện click vào item trong list", "KEY_ITEM_CLICK", false));
//                break;
        }

        return topics;
    }
}
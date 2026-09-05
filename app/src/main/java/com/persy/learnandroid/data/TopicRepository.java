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
        topics.add(new Topic(ETopicCategory.DATA_BINDING.name(), "Data Binding", "Kết nối dữ liệu trực tiếp với View trong layout XML", null, true));
        topics.add(new Topic(ETopicCategory.DAGGER.name(), "Dagger", "Dependency Injection với Dagger 2", null, true));

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
                topics.add(new Topic("RF7", "Login Demo", "Basic Auth login, lưu JWT, gọi API kèm Bearer Token", "KEY_RETROFIT_LOGIN", false));
                break;

            case DATA_BINDING:
                topics.add(new Topic("DB1", "Data Binding", "Liên kết dữ liệu giữa View và đối tượng trong XML", "KEY_BINDING_OVERVIEW", false));
                topics.add(new Topic("DB2", "Two-way Data Binding", "Đồng bộ dữ liệu hai chiều giữa View và đối tượng", "KEY_BINDING_TWO", false));
                topics.add(new Topic("DB3", "Demo", "Áp dụng Data Binding và Two-way Data Binding vào ứng dụng", "KEY_BINDING_DEMO", false));
                break;

            case DAGGER:
                topics.add(new Topic("DG1", "Overview", "Dagger là gì, vấn đề nó giải quyết, khi nào nên dùng", "KEY_DAGGER_OVERVIEW", false));
                topics.add(new Topic("DG2", "Injection Types", "Constructor, Field, Method Injection", "KEY_DAGGER_INJECTION", false));
                topics.add(new Topic("DG3", "Scope", "@Singleton và tự định nghĩa Scope", "KEY_DAGGER_SCOPE", false));
                topics.add(new Topic("DG4", "Component", "Subcomponent vs Component Dependencies", "KEY_DAGGER_COMPONENT", false));
                topics.add(new Topic("DG5", "Triển khai", "Quy trình triển khai Dagger từng bước", "KEY_DAGGER_IMPLEMENTATION", false));
                break;
        }

        return topics;
    }
}
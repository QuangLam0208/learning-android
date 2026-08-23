package com.persy.learnandroid.data;

import com.persy.learnandroid.model.Topic;
import com.persy.learnandroid.model.TopicCategory;

import java.util.ArrayList;
import java.util.List;

public class TopicRepository {

    /**
     * Lấy danh sách 4 danh mục chính (Layouts, Controls, Intent, RecyclerView)
     * Các item này có hasChildren = true và targetActivityKey = null (vì click vào sẽ mở list con)
     */
    public static List<Topic> getMainTopics() {
        List<Topic> topics = new ArrayList<>();

        // Dùng Enum name làm ID luôn để dễ map sang bước 2
        topics.add(new Topic(TopicCategory.LAYOUTS.name(), "Layouts", "LinearLayout, ConstraintLayout, RelativeLayout...", null, true));
        topics.add(new Topic(TopicCategory.CONTROLS.name(), "Controls", "Button, TextView, EditText, CheckBox...", null, true));
        topics.add(new Topic(TopicCategory.INTENT_BUNDLE.name(), "Intent & Bundle", "Truyền nhận dữ liệu giữa các Activity", null, true));
        topics.add(new Topic(TopicCategory.RECYCLERVIEW.name(), "RecyclerView", "Xử lý danh sách nâng cao với Adapter", null, true));

        return topics;
    }

    /**
     * Lấy danh sách bài học con tương ứng với từng Category
     * Các item này có hasChildren = false và chứa targetActivityKey để mở Activity tương ứng
     */
    public static List<Topic> getTopicsForCategory(TopicCategory category) {
        List<Topic> topics = new ArrayList<>();

        switch (category) {
            case LAYOUTS:
                topics.add(new Topic("L1", "LinearLayout", "Sắp xếp view theo hàng ngang hoặc dọc", "KEY_LINEAR_LAYOUT", false));
                topics.add(new Topic("L2", "ConstraintLayout", "Sắp xếp view linh hoạt bằng constraint", "KEY_CONSTRAINT_LAYOUT", false));
                topics.add(new Topic("L3", "RelativeLayout", "Sắp xếp view tương đối với nhau", "KEY_RELATIVE_LAYOUT", false));
                topics.add(new Topic("L4", "FrameLayout", "Xếp chồng các view lên nhau", "KEY_FRAME_LAYOUT", false));
                topics.add(new Topic("L5", "TableLayout", "Giao diện dạng bảng (row/col)", "KEY_TABLE_LAYOUT", false));
                topics.add(new Topic("L6", "GridLayout", "Giao diện dạng lưới", "KEY_GRID_LAYOUT", false));
                break;

            case CONTROLS:
                topics.add(new Topic("C1", "Button", "Nút bấm cơ bản", "KEY_BUTTON", false));
                topics.add(new Topic("C2", "EditText", "Trường nhập dữ liệu văn bản", "KEY_EDIT_TEXT", false));
                topics.add(new Topic("C3", "TextView", "Hiển thị đoạn text", "KEY_TEXT_VIEW", false));
                topics.add(new Topic("C4", "CheckBox", "Hộp kiểm chọn nhiều lựa chọn", "KEY_CHECK_BOX", false));
                topics.add(new Topic("C5", "RadioButton", "Nút chọn một lựa chọn duy nhất", "KEY_RADIO_BUTTON", false));
                topics.add(new Topic("C6", "Switch", "Nút bật/tắt (On/Off)", "KEY_SWITCH", false));
                topics.add(new Topic("C7", "Spinner", "Danh sách xổ xuống (Dropdown)", "KEY_SPINNER", false));
                topics.add(new Topic("C8", "SeekBar", "Thanh trượt chọn giá trị", "KEY_SEEK_BAR", false));
                topics.add(new Topic("C9", "ProgressBar", "Thanh trạng thái tải dữ liệu", "KEY_PROGRESS_BAR", false));
                break;

            case INTENT_BUNDLE:
                topics.add(new Topic("I1", "Send String", "Truyền một chuỗi cơ bản", "KEY_SEND_STRING", false));
                topics.add(new Topic("I2", "Send Multiple Values", "Truyền nhiều giá trị bằng Bundle", "KEY_SEND_MULTIPLE", false));
                topics.add(new Topic("I3", "Send Student Object", "Truyền Object (Serializable/Parcelable)", "KEY_SEND_OBJECT", false));
                topics.add(new Topic("I4", "Return Data", "Lấy dữ liệu trả về (startActivityForResult/ActivityResultLauncher)", "KEY_RETURN_DATA", false));
                break;

            case RECYCLERVIEW:
                topics.add(new Topic("R1", "LinearLayoutManager", "Danh sách dọc/ngang cơ bản", "KEY_LINEAR_MANAGER", false));
                topics.add(new Topic("R2", "GridLayoutManager", "Danh sách dạng lưới", "KEY_GRID_MANAGER", false));
                topics.add(new Topic("R3", "CardView", "Trang trí item với bóng đổ và bo góc", "KEY_CARD_VIEW", false));
                topics.add(new Topic("R4", "ViewHolder", "Kỹ thuật tối ưu bộ nhớ", "KEY_VIEW_HOLDER", false));
                topics.add(new Topic("R5", "Adapter", "Cầu nối giữa data và RecyclerView", "KEY_ADAPTER", false));
                topics.add(new Topic("R6", "Item Click", "Bắt sự kiện click vào item trong list", "KEY_ITEM_CLICK", false));
                break;
        }

        return topics;
    }
}
    package com.duyth10.intentservice1;


    import android.content.ComponentName;
    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Button;
    import android.widget.ImageButton;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;

    import com.duyth10.intentservice1.R;
    import com.duyth10.intentservice1.AmountEntryModel;

    public class AmountEntryFragment extends Fragment {

        private AmountEntryModel model;
        private String displayText = "0"; // Giá trị hiển thị mặc định
        private TextView tvDisplay; // Tham chiếu đến TextView

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_amount_entry, container, false);

            model = new AmountEntryModel();
            tvDisplay = view.findViewById(R.id.tv_display); // Tham chiếu đến TextView
            updateDisplay(); // Cập nhật ban đầu cho TextView

            ImageButton btnBack = view.findViewById(R.id.btn_back);
            btnBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

            Button btnConfirm = view.findViewById(R.id.btn_confirm);
            btnConfirm.setOnClickListener(v -> {
                if (!displayText.equals("0")) {

                    for(int i = 0 ;i <100 ;i++) {

                        Intent intent = new Intent();
                        intent.setComponent(new ComponentName("com.duyth10.intentservice", "com.duyth10.intentservice.MainActivity"));
//                        intent.putExtra("data", displayText); // Gửi giá trị displayText sang activity khác
//                        Log.d("AmountEntryFrg",displayText);

                        intent.putExtra("data", displayText+""+i); // Gửi giá trị displayText sang activity khác
                        Log.d("AmountEntryFrg",displayText+""+i);

                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        requireActivity().startActivity(intent);
}
                } else {
                    Toast.makeText(getContext(), "Số tiền không thể bằng 0!", Toast.LENGTH_SHORT).show();
                }
            });

            // Xử lý các nút nhập số
            view.findViewById(R.id.btn_1).setOnClickListener(v -> onNumberButtonClicked("1"));
            view.findViewById(R.id.btn_2).  setOnClickListener(v -> onNumberButtonClicked("2"));
            view.findViewById(R.id.btn_3).setOnClickListener(v -> onNumberButtonClicked("3"));
            view.findViewById(R.id.btn_4).setOnClickListener(v -> onNumberButtonClicked("4"));
            view.findViewById(R.id.btn_5).setOnClickListener(v -> onNumberButtonClicked("5"));
            view.findViewById(R.id.btn_6).setOnClickListener(v -> onNumberButtonClicked("6"));
            view.findViewById(R.id.btn_7).setOnClickListener(v -> onNumberButtonClicked("7"));
            view.findViewById(R.id.btn_8).setOnClickListener(v -> onNumberButtonClicked("8"));
            view.findViewById(R.id.btn_9).setOnClickListener(v -> onNumberButtonClicked("9"));
            view.findViewById(R.id.btn_0).setOnClickListener(v -> onNumberButtonClicked("0"));
            view.findViewById(R.id.btn_clean).setOnClickListener(v -> onNumberButtonClicked("clean"));
            view.findViewById(R.id.btn_delete).setOnClickListener(v -> onNumberButtonClicked("del"));

            return view;
        }

        private void onNumberButtonClicked(String number) {
            switch (number) {
                case "clean":
                    model.clear();
                    break;
                case "del":
                    model.deleteLast();
                    break;
                default:
                    model.appendNumber(number);
                    break;
            }
            updateDisplay(); // Cập nhật TextView sau khi model thay đổi
        }

        // Hàm này cập nhật TextView hiển thị số
        private void updateDisplay() {
            displayText = model.formatNumber(); // Lấy giá trị định dạng từ model
            tvDisplay.setText(displayText); // Cập nhật giá trị hiển thị trên TextView
            Log.d("AmountEntryFragment", "Updated displayText: " + displayText); // Debugging
        }
    }

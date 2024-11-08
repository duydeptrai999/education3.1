package com.duyth10.intentservice1;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.Fragment;

import com.duyth10.intentservice1.R;
import com.google.android.material.navigation.NavigationView;

public class HomeFragment extends Fragment {

    private DrawerLayout drawerLayout;
    private String qrData;
    private String amountEntry;
    private View dialogView;
    private TextView text1;
    private ImageView img1;
    private boolean isDialogShown = false;
    private boolean hasShownTransactionDialog = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout truyền thống thay vì dùng DataBinding
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        dialogView = inflater.inflate(R.layout.progress_dialog, null);

        // Khởi tạo các thành phần trong layout
        drawerLayout = view.findViewById(R.id.drawer_layout);
        img1 = view.findViewById(R.id.img1);
        text1 = view.findViewById(R.id.text1);

        // Cập nhật giao diện mặc định
        img1.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.coin));
        text1.setText("Default Text");

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imgQR = view.findViewById(R.id.imgQR);
        ImageView imgMoney = view.findViewById(R.id.imgMoney);
        ImageView imgCredit = view.findViewById(R.id.imgCredit);

        // Lắng nghe sự kiện click cho các icon
        imgCredit.setOnClickListener(v -> handleImageViewClick("Feature not yet integrated"));
        imgMoney.setOnClickListener(v -> handleImageViewClick("Feature not yet integrated"));
        imgQR.setOnClickListener(v -> navigateToNextFragment());

        // Cấu hình cho NavigationView và xử lý chọn menu item
        NavigationView navigationView = view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            drawerLayout.closeDrawer(GravityCompat.END);
            return true;
        });

        // Cấu hình nút mở menu
        view.findViewById(R.id.custom_menu_icon).setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.END);
            } else {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        // Lấy dữ liệu từ bundle và hiển thị dialog nếu cần
        Bundle args = getArguments();
        if (args != null) {
            qrData = args.getString("qrData");
            amountEntry = args.getString("amountEntry");

            if (qrData != null || amountEntry != null) {
                dialogCompleteTransaction(qrData, amountEntry);
                new Handler().postDelayed(() -> showDialog("Do you want to print the bill?"), 5000);
                hasShownTransactionDialog = true;

                args.clear();
            }
        }

        Log.d("HomeFragment", "Received qrData: " + qrData);
        Log.d("HomeFragment", "Received amountEntry: " + amountEntry);
    }

    @Override
    public void onResume() {
        super.onResume();
        isDialogShown = false;
    }

    public void showDialog(String message) {
        if (!isDialogShown) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog);
            builder.setTitle("Warning")
                    .setMessage(message)
                    .setPositiveButton("OK", (dialog, which) -> {
                        dialog.dismiss();
                        isDialogShown = false;
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> {
                        dialog.dismiss();
                        isDialogShown = false;
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
            isDialogShown = true;
        }
    }

    public void handleImageViewClick(String message) {
        showDialog(message);
    }

    private void dialogCompleteTransaction(String qrData, String amountEntry) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Notification")
                .setMessage("Printing " + qrData + " complete transaction " + amountEntry)
                .setView(dialogView)
                .setCancelable(false);
        AlertDialog progressDialog = builder.create();
        progressDialog.show();

        new Handler().postDelayed(() -> {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }, 5000);
    }

    private void navigateToNextFragment() {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new AmountEntryFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public void updateData(String qrData, String amountEntry) {
        this.qrData = qrData;
        this.amountEntry = amountEntry;

    }
}

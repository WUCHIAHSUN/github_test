package com.example.a365test.frameWork;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import com.example.a365test.R;


public class BaseActivity extends AppCompatActivity {
    public ProgressBar mProgressBar;
    public RelativeLayout progressLayout;
    private ActivityListener.onBackPressedListener mBackListener;
    private RelativeLayout baseLayout;
    private RelativeLayout titleLayout;
    private TextView titleName;
    private ImageView back;
    private ImageView rightIcon;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        baseLayout = findViewById(R.id.base_layout);
        progressLayout = findViewById(R.id.progress_layout);
        mProgressBar = findViewById(R.id.progress);
        titleLayout = findViewById(R.id.title_layout);
        titleName = findViewById(R.id.title_name);
        back = findViewById(R.id.back);
        rightIcon = findViewById(R.id.right_icon);

        if (back != null) {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goBackStack();
                }
            });
        }
    }

    public void setTitleName(String title) {
        if (back != null) {
            back.setVisibility(View.VISIBLE);
        }
        if (titleLayout != null) {
            titleLayout.setVisibility(View.VISIBLE);
        }
        if (titleName != null) {
            titleName.setText(title);
        }
    }

    public void hideBack() {
        if (back != null) {
            back.setVisibility(View.GONE);
        }
    }

    public void gotoFragment(int id) {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navHostFragment.getNavController().navigate(id);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void setBackPressedListener(ActivityListener.onBackPressedListener listener) {
        mBackListener = listener;
    }

    public void goBackStack() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null && !navHostFragment.getNavController().popBackStack()) {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (mBackListener != null) {
            goBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void onBackStackChanged() {
        // 需要实现具体逻辑
    }
}

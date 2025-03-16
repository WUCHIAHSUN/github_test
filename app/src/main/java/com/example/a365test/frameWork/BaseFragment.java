package com.example.a365test.frameWork;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class BaseFragment extends Fragment implements ActivityListener.onBackPressedListener {
    private static final String TAG = BaseFragment.class.getName();
    private boolean isProgress = false;

    private static BaseActivity mActivity = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity != mActivity && activity instanceof BaseActivity) {
            mActivity = (BaseActivity) activity;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mActivity != null) {
            mActivity.setBackPressedListener(this);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public BaseActivity getBaseActivity() {
        if (mActivity == null) {
            Log.d(TAG, "mActivity is null (getBaseActivity)");
        }
        return mActivity;
    }

    @Override
    public boolean onFragmentBackPressed() {
        return !isProgress;
    }

    public void showProgressView() {
        showProgressView(false);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void showProgressView(boolean hasGrayBG) {
        if (mActivity != null) {
            isProgress = true;
            if (mActivity.progressLayout != null) {
                mActivity.progressLayout.setVisibility(hasGrayBG ? View.VISIBLE : View.GONE);
            }
            if (mActivity.mProgressBar != null) {
                mActivity.mProgressBar.setVisibility(View.VISIBLE);
                mActivity.mProgressBar.setOnTouchListener((v, event) -> true);
            }
        }
    }

    public void dismissProgressView() {
        if (mActivity != null) {
            isProgress = false;
            if (mActivity.progressLayout != null) {
                mActivity.progressLayout.setVisibility(View.GONE);
            }
            if (mActivity.mProgressBar != null) {
                mActivity.mProgressBar.setVisibility(View.GONE);
            }
        }
    }
}

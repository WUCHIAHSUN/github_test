package com.example.a365test.View;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import com.example.a365test.databinding.UserLayoutBinding;
import com.example.a365test.frameWork.BaseFragment;
import com.example.a365test.viewModel.UserDetailViewModel;
import com.example.a365test.viewModel.ViewModelFactory;

public class UserDetailFragment extends BaseFragment {

    private UserDetailViewModel viewModel;
    private UserLayoutBinding dataBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (dataBinding == null) {
            dataBinding = UserLayoutBinding.inflate(inflater, container, false);
            viewModel = new ViewModelProvider(getBaseActivity(), new ViewModelFactory()).get(UserDetailViewModel.class);
            dataBinding.setViewModel(viewModel);
            dataBinding.setLifecycleOwner(getBaseActivity());
        }
        return dataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("requestKey", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                viewModel.getListApi(result.getInt("position"));
                getBaseActivity().setTitleName(result.getString("name"));
            }
        });
    }
}

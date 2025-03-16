package com.example.a365test.View;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.a365test.Adapter.UserListAdapter;
import com.example.a365test.Interface.AdapterListener;
import com.example.a365test.R;
import com.example.a365test.viewModel.HomeViewModel;
import com.example.a365test.viewModel.ViewModelFactory;
import com.example.a365test.databinding.HomeLayoutBinding;
import com.example.a365test.frameWork.BaseFragment;

import java.util.Arrays;

public class HomeFragment extends BaseFragment implements AdapterListener {

    public HomeViewModel viewModel;
    public HomeLayoutBinding dataBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        getBaseActivity().setTitleName("使用者列表");
        getBaseActivity().hideBack();

        if (dataBinding == null) {
            dataBinding = HomeLayoutBinding.inflate(inflater, container, false);
            viewModel = new ViewModelProvider(getBaseActivity(), new ViewModelFactory()).get(HomeViewModel.class);
            dataBinding.setViewModel(viewModel);
            dataBinding.setLifecycleOwner(getBaseActivity());

            dataBinding.userRv.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
            viewModel.isBottomListener(dataBinding.userRv);

            viewModel.since.setValue(0);
            viewModel.getListApi();
        }

        return dataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getUsersListData().observe(getViewLifecycleOwner(), userData -> {
            if (userData == null) return;
            if (dataBinding.userRv.getAdapter() == null){
                dataBinding.userRv.setAdapter(new UserListAdapter(HomeFragment.this));
            }
            UserListAdapter adapter = (UserListAdapter) dataBinding.userRv.getAdapter();
            adapter.notifyItemChanged(adapter.getItemCount()-21);
            adapter.submitList(userData.getData());
            viewModel.getIsBottom().setValue(false);
        });

        viewModel.getIsBottom().observe(getViewLifecycleOwner(), isBottom -> {
            if (isBottom == null) return;
            if (isBottom) {
                viewModel.since.setValue(viewModel.getUsersListData().getValue().getData().get(viewModel.getUsersListData().getValue().getData().size() - 1).getId() + 1);
                viewModel.getListApi();
            }
        });
    }

    @Override
    public void onPosClicked(int position) {
        Bundle result = new Bundle();
        result.putInt("position", viewModel.getUsersListData().getValue().getData().get(position).getId());
        result.putString("name", viewModel.getUsersListData().getValue().getData().get(position).getLogin());
        getParentFragmentManager().setFragmentResult("requestKey", result);
        getBaseActivity().gotoFragment(R.id.userDetailFragment);
    }
}

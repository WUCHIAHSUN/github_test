package com.example.a365test.viewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory implements ViewModelProvider.Factory {
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(BaseActivityModel.class)) {
            return (T) new BaseActivityModel();
        }else if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel();
        }else if (modelClass.isAssignableFrom(UserDetailViewModel.class)) {
            return (T) new UserDetailViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

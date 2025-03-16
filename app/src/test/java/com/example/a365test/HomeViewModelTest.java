package com.example.a365test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a365test.API.ApiAction;
import com.example.a365test.API.ApiManager;
import com.example.a365test.API.Data.ResultHttpData;
import com.example.a365test.API.Data.UsersListData;
import com.example.a365test.viewModel.HomeViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.LooperMode;

import java.util.ArrayList;

@RunWith(RobolectricTestRunner.class)
@LooperMode(LooperMode.Mode.PAUSED)
public class HomeViewModelTest {
    private HomeViewModel homeViewModel;

    @Mock
    private ResultHttpData mockResultHttpData;

    @Mock
    private UsersListData mockUsersListData;


    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        homeViewModel = new HomeViewModel();
    }

    @Test
    public void testGetList_withValidData() {
        // Arrange
        ArrayList<UsersListData.UserList> userLists = new ArrayList<>();
        userLists.add(new UsersListData.UserList());
        userLists.add(new UsersListData.UserList());
        when(mockResultHttpData.getRtnData()).thenReturn(mockUsersListData);
        when(mockUsersListData.getData()).thenReturn(userLists);

        // Act
        homeViewModel.getList(mockResultHttpData);

        // Assert
        MutableLiveData<UsersListData> liveData = homeViewModel.getUsersListData();
        liveData.observeForever(data -> {
            assertNotNull(data);
            assertEquals(2, data.getData().size());
        });
    }

    @Test
    public void testOnResult_withValidData() {
        // Arrange
        when(mockResultHttpData.getRtnData()).thenReturn(mockUsersListData);
        when(mockUsersListData.getData()).thenReturn(new UsersListData().getData());

        // Act
        homeViewModel.onResult(ApiAction.API_GET_USER_LIST, mockResultHttpData);

        // Assert
        MutableLiveData<UsersListData> liveData = homeViewModel.getUsersListData();
        liveData.observeForever(data -> {
            assertNotNull(data);
            assertEquals(1, data.getData().size()); // 檢查數據的數量
        });
    }

    @Test
    public void testIsBottomListener() {
        // Arrange
        RecyclerView mockRecyclerView = mock(RecyclerView.class);
        LinearLayoutManager mockLayoutManager = mock(LinearLayoutManager.class);
        when(mockRecyclerView.getLayoutManager()).thenReturn(mockLayoutManager);
        when(mockLayoutManager.getItemCount()).thenReturn(10);
        when(mockLayoutManager.findLastVisibleItemPosition()).thenReturn(9); // 最後一個可見項目
        ArgumentCaptor<RecyclerView.OnScrollListener> scrollListenerCaptor = ArgumentCaptor.forClass(RecyclerView.OnScrollListener.class);
        // Act
        homeViewModel.isBottomListener(mockRecyclerView);
        homeViewModel.getIsBottom().setValue(false); // 確保初始值為 false

        // Simulate scrolling
        verify(mockRecyclerView).addOnScrollListener(scrollListenerCaptor.capture());

        // Get the captured listener
        RecyclerView.OnScrollListener capturedListener = scrollListenerCaptor.getValue();

        // Simulate scrolling (call onScrolled on the captured listener)
        capturedListener.onScrolled(mockRecyclerView, 0, 1);
        // Assert
        assertTrue(homeViewModel.getIsBottom().getValue()); // 應該設置為 true
    }
}

package com.example.a365test.Adapter;
import android.widget.ImageView;
import androidx.databinding.BindingAdapter;

import coil.Coil;
import coil.request.ImageRequest;
import com.example.a365test.R;

public class BindingAdapters {
    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView imageView, String url) {
        if (url != null) {
            ImageRequest request = new ImageRequest.Builder(imageView.getContext())
                    .data(url) // 设置图片 URL
                    .placeholder(R.drawable.ic_download) // 预加载占位图
                    .error(R.drawable.ic_launcher_foreground) // 加载失败显示的图片
                    .target(imageView) // 绑定到目标 ImageView
                    .build();

            Coil.imageLoader(imageView.getContext()).enqueue(request);
        }
    }
}

package com.baizhong.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.baizhong.test.CustomToast;
import com.baizhong.test.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zfdang.multiple_images_selector.utilities.DraweeUtils;
import com.zfdang.multiple_images_selector.utilities.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/6/1.
 */
public class CustomRecyclerViewAdapter extends RecyclerView.Adapter {
    private List<String> files;
    private int len;
    CustomListener customListener;

    public interface CustomListener {
        void onItemClickListener(int position, int type, boolean isCamera);
    }

    public CustomRecyclerViewAdapter(Context context, List files) {
        customListener = (CustomListener) context;
        this.files = files;

        len = files.size();
        CustomToast.instance(context, "--------------" + len, Toast.LENGTH_LONG);
    }

    public void setFiles(List files) {
        this.files = files;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rview_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Uri uri;
        MyHolder myHolder;
        File file = null;
        if (position != files.size()) {
            file = new File(files.get(position));
            if (file.exists()) {
                uri = Uri.fromFile(file);
            } else {
                uri = FileUtils.getUriByResId(R.drawable.default_image);//设置默认图片
            }
            myHolder = (MyHolder) holder;
            myHolder.mIv_close.setVisibility(View.VISIBLE);
            myHolder.mIv_main.setVisibility(View.VISIBLE);
            myHolder.mIv_main.setOnClickListener(new android.view.View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    customListener.onItemClickListener(position, 1, false);
                }
            });
            myHolder.mIv_close.setOnClickListener(new android.view.View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    customListener.onItemClickListener(position, 0, false);
                }
            });
        } else {
            uri = FileUtils.getUriByResId(android.R.drawable.ic_menu_add);//设置默认图片
            myHolder = (MyHolder) holder;
            myHolder.mIv_close.setVisibility(View.GONE);
            myHolder.mIv_main.setVisibility(View.VISIBLE);
            myHolder.mIv_main.setOnClickListener(new android.view.View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    customListener.onItemClickListener(position, 1, true);
                }
            });
        }

        DraweeUtils.showThumb(uri, myHolder.mIv_main);

    }

    @Override
    public int getItemCount() {
        return files.size() + 1;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView mIv_main;
        public ImageView mIv_close;

        public MyHolder(View itemView) {
            super(itemView);
            mIv_main = (SimpleDraweeView) itemView.findViewById(R.id.rview_iv);
            mIv_close = (ImageView) itemView.findViewById(R.id.rview_iv_close);
        }
    }

    public void addData(int position, String path) {
        files.add(path);

        notifyItemInserted(files.size() - 2);

    }

    public void removeData(int position) {
//        files.remove(position);
        notifyItemRemoved(position-1);
        notifyItemRangeChanged(0,files.size());
    }
}

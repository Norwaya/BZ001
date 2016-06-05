package com.baizhong.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Xbel on 2016/5/31.
 */
public class ImageAdapter extends BaseAdapter {
    String[] files ;
    LayoutInflater inflater;
    Context context;
    public ImageAdapter(Context context,String[] files) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.files = files;
    }

    @Override
    public int getCount() {
        return files.length;
    }

    @Override
    public Object getItem(int position) {
        CustomToast.instance(context, files[position], Toast.LENGTH_LONG);
        return BitmapFactory.decodeFile(files[position]);
    }

    @Override
    public long getItemId(int position) {

        return files[position].hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView iv;
        if(convertView ==null){
            convertView = inflater.inflate(R.layout.item_image,parent,false);
            iv = (ImageView) convertView.findViewById(R.id.item_imageview);
            convertView.setTag(iv);
        }else {
            iv = (ImageView) convertView.getTag();

        }
        iv.setImageBitmap((Bitmap) getItem(position));
        return convertView;
    }
}

package com.baizhong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baizhong.entity.CustomFile;
import com.baizhong.test.R;

import java.util.List;

/**
 * Created by Administrator on 2016/5/30.
 */
public class CustomAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<CustomFile> list;

    public CustomAdapter(Context context, List<CustomFile> list) {
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    public List<CustomFile> getList() {
        return list;
    }

    public void setList(List<CustomFile> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.simple_text, parent, false);
            tv = (TextView) convertView.findViewById(R.id.simple_tv);
            convertView.setTag(tv);
        } else {
            tv = (TextView) convertView.getTag();
        }
        CustomFile file = (CustomFile) getItem(position);
        tv.setText(file.getName());
        return convertView;
    }
}

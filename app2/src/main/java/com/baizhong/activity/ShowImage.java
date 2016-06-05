package com.baizhong.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.baizhong.test.CustomToast;
import com.baizhong.test.ImageAdapter;
import com.baizhong.test.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/30.
 */
public class ShowImage extends Activity {
    String path;
    ListView lv;
    ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        path = getIntent().getStringExtra("path");
        CustomToast.instance(this, path, Toast.LENGTH_LONG);
//        lv = (ListView) findViewById(R.id.show_lv);
        vp = (ViewPager) findViewById(R.id.show_vp);
        init();
    }

    List<ImageView> list = new ArrayList<ImageView>();

    public void init() {
        String[] file = path.split(",");

        for (int i = 0; i < file.length; i++) {
//           final ImageView iv = (ImageView) getLayoutInflater().inflate(R.layout.item_image,null).findViewById(R.id.item_imageview);
            final ImageView iv = new ImageView(this);
            list.add(iv);
            new AsyncTask<String, Void, Bitmap>() {

                @Override
                protected Bitmap doInBackground(String... params) {
                    File file = new File(params[0]);
                    if (file.exists()){
                        BitmapFactory.Options opt = new  BitmapFactory.Options();

                        opt.inPreferredConfig =  Bitmap.Config.RGB_565;

                        return BitmapFactory.decodeFile(params[0],opt);
                    }
                    return BitmapFactory.decodeResource(getResources(), R.drawable.ic_arrow_back_white_24dp);
                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    iv.setImageBitmap(bitmap);
//                    if(bitmap!=null &&!bitmap.isRecycled()){
//                        bitmap.recycle();
//                    }
                }
            }.execute(file[i]);
        }
        vp.setAdapter(new MyViewPageAdapter(list));
    }

    class MyViewPageAdapter extends PagerAdapter {
        List<ImageView> list;

        public MyViewPageAdapter(List<ImageView> list) {
            this.list = list;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position));

        }

        public Bitmap getItem(int position) {
            return null;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position), 0);
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}

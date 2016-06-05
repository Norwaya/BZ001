package com.baizhong.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Xbel on 2016/5/31.
 */
public class FileTask extends AsyncTask<String, Void, String> {
    Context context;

    public FileTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... files) {
        Bitmap bitmap;
        FileOutputStream outputStream;

        try {
            bitmap = BitmapFactory.decodeFile(files[0]);
            outputStream = new FileOutputStream(files[1]);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "error";
        }


        return "suc";

    }

    @Override
    protected void onPostExecute(String s) {
        if (s.equals("suc"))
            CustomToast.instance(context, "suc", Toast.LENGTH_LONG);
        else
            CustomToast.instance(context, "error", Toast.LENGTH_LONG);
    }
}
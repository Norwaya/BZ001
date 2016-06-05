package com.baizhong.test;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/5/30.
 */
public class CustomToast {
    private static Toast toast;

    public static void instance(Context context, String text, int len) {
        if (toast == null)
            toast = Toast.makeText(context, text, len);
        else {
            toast.setText(text);
            toast.setDuration(len);
        }
        toast.show();
    }
}

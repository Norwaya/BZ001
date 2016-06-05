package com.baizhong.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baizhong.entity.CustomFile;
import com.baizhong.test.CustomToast;
import com.baizhong.test.FileTask;
import com.baizhong.test.R;
import com.zfdang.multiple_images_selector.ImagesSelectorActivity;
import com.zfdang.multiple_images_selector.SelectorSettings;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/5/30.
 */
public class AddActivity extends Activity implements View.OnClickListener {
    private TextView mTv;
    private TextView mAll;
    private Button mBtn;
    private Button mCommit;

    private ArrayList<String> mResults = new ArrayList<String>();
    private static final int REQUEST_CODE = 732;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        mTv = (TextView) findViewById(R.id.add_filename);
        mAll = (TextView) findViewById(R.id.all);
        mBtn = (Button) findViewById(R.id.add_btn);
        mCommit = (Button) findViewById(R.id.commit);
        mBtn.setOnClickListener(this);
        mCommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_btn:
                CustomToast.instance(this, "in___", Toast.LENGTH_SHORT);
                // start multiple photos selector
                Intent intent = new Intent(this, ImagesSelectorActivity.class);
                // max number of images to be selected
                intent.putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER, 5);
                // min size of image which will be shown; to filter tiny images (mainly icons)
                intent.putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE, 100000);
                // show camera or not
                intent.putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA, true);
                // pass current selected images as the initial value
                intent.putStringArrayListExtra(SelectorSettings.SELECTOR_INITIAL_SELECTED_LIST, mResults);
                // start the selector
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.commit:
                CustomToast.instance(this, "commit_operater", Toast.LENGTH_LONG);
                someOperation();
                break;
        }
    }

    //    String str = getApplicationContext().getFilesDir().getAbsolutePath()+"_image";
    List<String> list = new ArrayList<String>();

    private void someOperation() {
        //把文件放入本目录下的文件夹
        String text = mTv.getText().toString();
        if (TextUtils.isEmpty(text)) {
            CustomToast.instance(this, "请输入文件名", Toast.LENGTH_LONG);
        } else {
            if(!TextUtils.isEmpty(fileNames)){

                File rootFile = Environment.getExternalStorageDirectory();
                if (rootFile.exists()) {
                    String dirName = rootFile.getPath() + File.separator + "test" + File.separator;
                    File dirFile = new File(dirName);
                    if (!dirFile.exists())
                        dirFile.mkdirs();
                    String[] oldFile = fileNames.split(",");
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < oldFile.length; i++) {
                        String file = oldFile[i];
                        String sur_file = file.substring(file.lastIndexOf("."), file.length());
                        String newFile = dirName + new Date().getTime() + "_" + i + sur_file;
                        sb.append(newFile).append(",");
                        new FileTask(this).execute(file, newFile);
                    }

                    new CustomFile(text, sb.toString()).save();
                } else {
                    new CustomFile(text, fileNames).save();
                }
            }else{
                new CustomFile(text, "").save();
            }
//存储文件名到数据库 ，然后close 这个activity
            finish();
        }
    }

    String fileNames;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                mResults = data.getStringArrayListExtra(SelectorSettings.SELECTOR_RESULTS);
                assert mResults != null;

                // show results in textview
                StringBuilder sb = new StringBuilder();
                for (String result : mResults) {
                    sb.append(result).append(",");
                }
                fileNames = sb.toString();
                mAll.setText(fileNames);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

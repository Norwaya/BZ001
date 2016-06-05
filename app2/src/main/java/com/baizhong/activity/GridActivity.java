package com.baizhong.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.baizhong.adapter.CustomRecyclerViewAdapter;
import com.baizhong.entity.Cpath;
import com.baizhong.entity.CustomFile;
import com.baizhong.test.CustomToast;
import com.baizhong.test.FileTask;
import com.baizhong.test.R;
import com.zfdang.multiple_images_selector.ImagesSelectorActivity;
import com.zfdang.multiple_images_selector.SelectorSettings;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by admin on 2016/6/1.
 */
public class GridActivity extends Activity implements CustomRecyclerViewAdapter.CustomListener {
    private static final int REQUEST_CODE = 732;
    private RecyclerView rview;
    private List<String> files = null;
    private CustomFile currentFile;
    private List<Cpath> pathList;
    private CustomRecyclerViewAdapter adapter;
    private SharedPreferences share;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_grid);
        share = getSharedPreferences("path",MODE_PRIVATE);
        editor = share.edit();
        init();
    }

    private void init() {
        files = getFiles(getIntent().getStringExtra("id"));
        rview = (RecyclerView) findViewById(R.id.rv);
        rview.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new CustomRecyclerViewAdapter(GridActivity.this, files);
        rview.setAdapter(adapter);
        pathList = new ArrayList<>();
    }

    private List getFiles(String id) {
        List<String> list = new ArrayList<String>();
        if (id != null) {
            currentFile = CustomFile.load(CustomFile.class, Long.parseLong(id));
            if (currentFile != null) {
                String path = currentFile.getPath();
                if (!TextUtils.isEmpty(path)) {
                    String[] strarray = path.split(",");
                    if (pathList == null) {
                        pathList = new ArrayList<>();
                    }
                    for (int i = 0; i < strarray.length; i++) {

                        String str = strarray[i];
                        list.add(str);
                        Cpath c = new Cpath(true, str);
                        pathList.add(c);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_grid_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                CustomToast.instance(this, "save the file", Toast.LENGTH_SHORT);
                saveCurrentFile();
//                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveCurrentFile() {
        StringBuilder sb = new StringBuilder();
        int noid = 0;
        for (String str : files) {
            File file = new File(str);
                sb.append(str).append(",");
        }
        currentFile.setPath(sb.toString());
        currentFile.save();
        CustomToast.instance(this, "save the file_" + noid, Toast.LENGTH_SHORT);
    }


    @Override
    public void onItemClickListener(int position, int type, boolean isCamera) {
        if (isCamera) {
            //open fileSelector
            CustomToast.instance(this, "carema", Toast.LENGTH_LONG);
            Number = files.size();
            if (Number < 5)
                addImage();
            else
                CustomToast.instance(this, "have already 5 pics", Toast.LENGTH_LONG);
        } else {
            if (type == 0) {
                //del the image
                CustomToast.instance(this, "del file" + position, Toast.LENGTH_LONG);
                delFilePathAndFile(position);
            } else {
                CustomToast.instance(this, "see pics" + position, Toast.LENGTH_LONG);
                //open view show the views
            }
        }
    }

    private ArrayList<String> mResults = new ArrayList<String>();
    int Number;

    private void addImage() {
        mResults.clear();
        CustomToast.instance(this, "in___", Toast.LENGTH_SHORT);
        // start multiple photos selector
        Intent intent = new Intent(this, ImagesSelectorActivity.class);
        // max number of images to be selected
        intent.putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER, 5 - Number);
        // min size of image which will be shown; to filter tiny images (mainly icons)
        intent.putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE, 100000);
        // show camera or not
        intent.putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA, true);
        // pass current selected images as the initial value
        intent.putStringArrayListExtra(SelectorSettings.SELECTOR_INITIAL_SELECTED_LIST, mResults);
        // start the selector
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                mResults = data.getStringArrayListExtra(SelectorSettings.SELECTOR_RESULTS);
                assert mResults != null;
                for (String str : mResults) {
                    files.add(str);
//                    adapter.addData(str);
                }
                HashSet<String> set = new HashSet<>(files);
                files.clear();
                files.addAll(set);
                adapter.notifyItemRangeChanged(0, files.size());
                // show results in textview
//                StringBuilder sb = new StringBuilder();
//                for (String result : mResults) {
//                    sb.append(result).append(",");
//                }
//                fileNames = sb.toString();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void delFilePathAndFile(int position) {
        try {
            adapter.removeData(position);

            CustomToast.instance(this, files.size() + "", Toast.LENGTH_LONG);
        } catch (Exception e) {
            CustomToast.instance(this, e.getMessage() + files, Toast.LENGTH_LONG);
        }
    }
}

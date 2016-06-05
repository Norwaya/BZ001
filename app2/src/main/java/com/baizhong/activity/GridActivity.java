package com.baizhong.activity;

import android.app.Activity;
import android.content.Intent;
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
import com.baizhong.test.R;
import com.zfdang.multiple_images_selector.ImagesSelectorActivity;
import com.zfdang.multiple_images_selector.SelectorSettings;

import java.util.ArrayList;
import java.util.Date;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_grid);

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
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                CustomToast.instance(this, "save the file", Toast.LENGTH_SHORT);
                saveCurrentFile();
                finish();
                break;
            case R.id.settings:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveCurrentFile() {
    }


    @Override
    public void onItemClickListener(int position, int type, boolean isCamera) {
        if (isCamera) {
            if (position < 5) {
                //open fileSelector
                CustomToast.instance(this, "carema", Toast.LENGTH_LONG);
            } else {
                CustomToast.instance(this, "carema not add", Toast.LENGTH_LONG);
            }
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

    private void addImage() {
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
    }

    private void delFilePathAndFile(int position) {
//        pathList.remove(position);
        try {
            CustomToast.instance(this, "del pics" + position + "___" + files.get(position), Toast.LENGTH_LONG);
            files.remove(position);
//            pathList.remove(position);
            adapter.removeData(position);
        } catch (Exception e) {
            e.printStackTrace();
            CustomToast.instance(this, ""+e.getMessage()+"____size"+files.size(), Toast.LENGTH_LONG);
        }
//        StringBuilder sb = new StringBuilder();
//        String[] list2array = (String[]) files.toArray();
//       for (int i = 0;i<list2array.length;i++){
//            sb.append(list2array[i]).append(",");
//        }
//        currentFile.setPath(sb.toString());
//        currentFile.save();
    }
}

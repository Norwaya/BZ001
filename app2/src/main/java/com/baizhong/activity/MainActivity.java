package com.baizhong.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.baizhong.adapter.CustomAdapter;
import com.baizhong.entity.CustomFile;
import com.baizhong.test.CustomToast;
import com.baizhong.test.R;

import java.io.File;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView lv;
    List<CustomFile> list;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.lv);
        list = new Select().from(CustomFile.class).execute();
        adapter = new CustomAdapter(this, list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
        registerForContextMenu(lv);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CustomFile file = list.get(position);
//               Intent i = new Intent(MainActivity.this,ShowImage.class);
//               i.putExtra("path",file.getPath());
            Intent i = new Intent(MainActivity.this, GridActivity.class);
            i.putExtra("id", file.getId() + "");
            startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                CustomToast.instance(this, "add_a_file", Toast.LENGTH_SHORT);
                startActivityForResult(new Intent(this, AddActivity.class), 110);
                break;
            case R.id.settings:
                String file = getFilesDir().getPath() + "/" + new Date().getTime();
                CustomToast.instance(this, file, Toast.LENGTH_LONG);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        list = new Select().from(CustomFile.class).execute();
        CustomToast.instance(this, "result_ " + list.size(), Toast.LENGTH_LONG);
        adapter.setList(list);
        adapter.notifyDataSetChanged();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add("del this record");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int postion = menuInfo.position;
        CustomFile file = list.get(postion);
        file.delete();
        delFile(file.getPath());
        CustomToast.instance(this, item.getTitle().toString() + "_" + item.getItemId() + "__" + menuInfo.position + "_" + file.getName(), Toast.LENGTH_LONG);
        list = new Select().from(CustomFile.class).execute();
        adapter.setList(list);
        adapter.notifyDataSetChanged();
        return super.onContextItemSelected(item);
    }

    private void delFile(String paths) {
        if (!TextUtils.isEmpty(paths)) {
            String[] files = paths.split(",");
            for (int i = 0; i < files.length; i++) {
                File file = new File(files[i]);
                if (file.exists()) {
                    file.delete();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        CustomToast.instance(this, "onDestroy---", Toast.LENGTH_LONG);
        super.onDestroy();

    }


}

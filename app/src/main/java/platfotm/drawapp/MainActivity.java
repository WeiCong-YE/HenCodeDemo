package platfotm.drawapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import platfotm.drawapp.Activity.HenCode1;
import platfotm.drawapp.Adapter.ListMenuAdapter;


/**
 * @author Ly
 */
public class MainActivity extends AppCompatActivity {
    private ListMenuAdapter mListMenuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initRecyclerView();
        initData();
    }

    private void initData() {
        List<String> list=new ArrayList<>();
        list.add("自定义六角形");
        mListMenuAdapter.addData(list);
    }

    private void initRecyclerView() {
        RecyclerView listView = (RecyclerView) findViewById(R.id.lv_main_menu);
        listView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mListMenuAdapter = new ListMenuAdapter(MainActivity.this);
        listView.setAdapter(mListMenuAdapter);
        mListMenuAdapter.setItemClickListener(new ListMenuAdapter.ItemClickListener() {
            @Override
            public void onItemClickListener(String string) {
                switch (string){
                    case "自定义六角形":
                        startActivity(new Intent(MainActivity.this, HenCode1.class));
                        break;
                    default:break;
                }
            }
        });
    }
}

package com.zipcar.zipcarcodingtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.zipcar.zipcarcodingtest.adapters.ItemAdapter;
import com.zipcar.zipcarcodingtest.commons.StaticData;
import com.zipcar.zipcarcodingtest.models.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemsMainActivity extends AppCompatActivity {

    private ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_main);
        initData();

        LocalBroadcastManager.getInstance(this).registerReceiver(itemUpdateReceiver,
                new IntentFilter(StaticData.ITEM_CHANGED));
    }

    private BroadcastReceiver itemUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            int index = intent.getIntExtra(StaticData.ITEM_CHANGED_INDEX, 0);
            itemAdapter.getItemsList().set(index, new Item(ItemsMainActivity.this, index));
            itemAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(itemUpdateReceiver);
        super.onDestroy();
    }

    private void initData() {
        List<Item> list = new ArrayList<>();
        for(int i=0; i< StaticData.TOTAL_ITEMS; i++){
            list.add(new Item(this, i));
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.items_view);
        GridLayoutManager gridLayout = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayout);
        itemAdapter = new ItemAdapter(this, list);
        recyclerView.setAdapter(itemAdapter);
    }

}

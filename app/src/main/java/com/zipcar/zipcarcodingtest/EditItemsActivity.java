package com.zipcar.zipcarcodingtest;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.zipcar.zipcarcodingtest.commons.StaticData;
import com.zipcar.zipcarcodingtest.commons.Utilities;
import com.zipcar.zipcarcodingtest.models.Amount;
import com.zipcar.zipcarcodingtest.models.Item;

public class EditItemsActivity extends AppCompatActivity {

    private Item item;
    private EditText edit_amount;
    private EditText edit_count;
    private ImageView item_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item);
        initData();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        item = (Item) bundle.getSerializable(StaticData.ITEM_KEY);

        edit_amount = (EditText) findViewById(R.id.edit_amount);
        edit_count = (EditText) findViewById(R.id.edit_count);
        item_icon = (ImageView) findViewById(R.id.item_icon);
        edit_amount.setText(item.getAmount().toString());
        edit_count.setText("" + item.getCount());
        item_icon.setImageResource(item.getImageId());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = menuItem.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            boolean error = parseAndSaveData();
            if(!error){
                notifyItemUpdate();
                Utilities.showMessage(this, "Saved!");
            }

        }
        return super.onOptionsItemSelected(menuItem);
    }

    private boolean parseAndSaveData(){
        boolean error = false;
        int newCount = Integer.parseInt(edit_count.getText().toString());
        if (newCount < this.item.getCount()) {
            edit_count.setError("Restock value must not be less than " + item.getCount());
            error = true;
        }
        String newAmount = edit_amount.getText().toString();
        Amount amount = Utilities.parseAmountFromString(newAmount);
        if(amount.isZero()) {
            edit_amount.setError("Amount must not be empty/zero");
            error = true;
        }
        if(!error) {
            edit_amount.setText(amount.toString());
            this.item.setAmount(amount);
            this.item.setCount(newCount);
            this.item.save(this);
        }
        return error;
    }

    private void notifyItemUpdate(){
        Intent intent = new Intent(StaticData.ITEM_CHANGED);
        // You can also include some extra data.
        intent.putExtra(StaticData.ITEM_CHANGED_INDEX, item.getItemId());
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}

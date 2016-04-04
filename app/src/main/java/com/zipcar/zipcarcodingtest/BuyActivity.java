package com.zipcar.zipcarcodingtest;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zipcar.zipcarcodingtest.commons.StaticData;
import com.zipcar.zipcarcodingtest.commons.Utilities;
import com.zipcar.zipcarcodingtest.models.Amount;
import com.zipcar.zipcarcodingtest.models.Item;

public class BuyActivity extends AppCompatActivity {
    private Amount itemAmount;
    private EditText edit_amount;
    private TextView denomination;
    private ImageView item_icon;
    private Item item;
    private boolean isBought = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item);
        Bundle bundle = getIntent().getExtras();
        item = (Item) bundle.getSerializable(StaticData.ITEM_KEY);
        itemAmount = item.getAmount();
        denomination = (TextView) findViewById(R.id.denomination);
        edit_amount = (EditText) findViewById(R.id.edit_amount);
        EditText edit_count = (EditText) findViewById(R.id.edit_count);
        edit_count.setVisibility(View.GONE);
        item_icon = (ImageView) findViewById(R.id.item_icon);
        edit_amount.setText(itemAmount.toString());
        item_icon.setImageResource(item.getImageId());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_buy_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if(isBought){
            Utilities.showMessage(this, "Please return to main page.");
            return true;
        }
        int id = menuItem.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            parseAmount();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void parseAmount() {
        Amount amountPaid = Utilities.parseAmountFromString(edit_amount.getText().toString());
        edit_amount.setText(amountPaid.toString());
        if (amountPaid.equals(itemAmount) < 0) {
            Utilities.showMessage(this, "Please pay at least " + itemAmount.toString());
        } else {
            buyItem(amountPaid);
        }

    }

    private void buyItem(Amount amountPaid) {
        Amount change = amountPaid.subtract(itemAmount);
        showChange(amountPaid, change);
    }

    private void showChange(Amount amountPaid, Amount change) {
        Utilities.showMessage(this, change.toString());
        int dollars = change.getAmount_dollar();
        int cents = change.getAmount_cents();
        int FIVE_DOLLAR_COUNT = 0;
        int ONE_DOLLAR_COUNT = 0;
        int TWENTY_FIVE_CENT_COUNT = 0;
        int TEN_CENT_COUNT = 0;
        int FIVE_CENT_COUNT = 0;
        int ONE_CENT_COUNT = 0;

        if (dollars >= 5) {
            FIVE_DOLLAR_COUNT = dollars / 5;
            dollars = dollars % 5;
        }
        ONE_DOLLAR_COUNT = dollars;
        if (cents >= 25) {
            TWENTY_FIVE_CENT_COUNT = cents / 25;
            cents = cents % 25;
        }
        if (cents >= 10) {
            TEN_CENT_COUNT = cents / 10;
            cents = cents % 10;
        }
        if (cents >= 5) {
            FIVE_CENT_COUNT = cents / 5;
            cents = cents % 5;
        }
        ONE_CENT_COUNT = cents;
        denomination.setText("Paid:" +amountPaid.toString() + "\n"+
                        "Deducted:" +itemAmount.toString() + "\n"+
                        "Change: " + change.toString() + "\n\n" +
                        "$  5 = " + FIVE_DOLLAR_COUNT + "\n" +
                        "$  1 = " + ONE_DOLLAR_COUNT + "\n" +
                        "C 25 = " + TWENTY_FIVE_CENT_COUNT + "\n" +
                        "C 10 = " + TEN_CENT_COUNT + "\n" +
                        "C  5 = " + FIVE_CENT_COUNT + "\n" +
                        "C  1 = " + ONE_CENT_COUNT

        );
        denomination.setVisibility(View.VISIBLE);
        edit_amount.setVisibility(View.GONE);
        item.setCount(item.getCount()-1);
        item.save(this);
        isBought = true;
        notifyItemUpdate();
    }
    private void notifyItemUpdate(){
        Intent intent = new Intent(StaticData.ITEM_CHANGED);
        // You can also include some extra data.
        intent.putExtra(StaticData.ITEM_CHANGED_INDEX, item.getItemId());
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}

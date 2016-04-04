package com.zipcar.zipcarcodingtest.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zipcar.zipcarcodingtest.BuyActivity;
import com.zipcar.zipcarcodingtest.EditItemsActivity;
import com.zipcar.zipcarcodingtest.R;
import com.zipcar.zipcarcodingtest.commons.StaticData;
import com.zipcar.zipcarcodingtest.commons.Utilities;
import com.zipcar.zipcarcodingtest.models.Item;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter {

    private final Context context;
    private List<Item> itemsList;

    public ItemAdapter(Context context, List<Item> items) {
        this.context = context;
        itemsList = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.item_layout, viewGroup, false);
        return new ViewHolder(context, v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ((ViewHolder) viewHolder).bindData(itemsList.get(i));
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public List<Item> getItemsList() {
        return itemsList;
    }

    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Button buyButton;
        private Button editButton;
        private TextView amount;
        private TextView count;
        private ImageView itemIcon;
        private Context context;
        private Item item;

        public ViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            itemIcon = (ImageView) itemView.findViewById(R.id.item_image);
            buyButton = (Button) itemView.findViewById(R.id.buy_item);
            editButton = (Button) itemView.findViewById(R.id.edit_item);
            amount = (TextView) itemView.findViewById(R.id.item_amount);
            count = (TextView) itemView.findViewById(R.id.item_count);
            buyButton.setOnClickListener(this);
            editButton.setOnClickListener(this);
        }

        public void bindData(Item item) {
            this.item = item;
            itemIcon.setImageResource(this.item.getImageId());
            amount.setText(item.getAmount().toString());
            count.setText(this.item.getCount() + " left");
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.buy_item:
                    starBuyActivity();
                    break;
                case R.id.edit_item:
                    startEditActivity();
                    break;
            }
        }

        private void startEditActivity() {
            Intent intent = new Intent(this.context, EditItemsActivity.class);
            intent.putExtras(createBundle());
            this.context.startActivity(intent);
        }

        private Bundle createBundle(){
            Bundle bundle = new Bundle();
            bundle.putSerializable(StaticData.ITEM_KEY, this.item);
            return bundle;
        }
        private void starBuyActivity() {
            if(item.getCount() == 0){
                Utilities.showMessage(this.context, "Item out of Stock!");
            } else if(item.getAmount().getAmount_dollar() == 0 && item.getAmount().getAmount_cents() == 0){
                Utilities.showMessage(this.context, "Price for this item is not set.");
            } else {
                Intent intent = new Intent(this.context, BuyActivity.class);
                intent.putExtras(createBundle());
                this.context.startActivity(intent);
            }

        }
    }

}

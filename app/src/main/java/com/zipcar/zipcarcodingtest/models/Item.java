package com.zipcar.zipcarcodingtest.models;

import android.content.Context;

import com.zipcar.zipcarcodingtest.commons.SharedPrefsHandler;
import com.zipcar.zipcarcodingtest.commons.StaticData;

import java.io.Serializable;

public class Item implements Serializable {
    private int itemId;
    private int imageId;
    private int count;
    private Amount amount;

    public Item(Context context, int itemId) {
        this.itemId = itemId;
        imageId = StaticData.imgs[itemId];
        SharedPrefsHandler prefs = SharedPrefsHandler.getInstance(context);
        count = prefs.getInt(StaticData.COUNT_PREFIX + itemId);
        int amount_dollar = prefs.getInt(StaticData.DOLLARS_PREFIX + itemId);
        int amount_cents = prefs.getInt(StaticData.CENTS_PREFIX + itemId);
        amount = new Amount(amount_dollar, amount_cents);
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public void save(Context context) {
        SharedPrefsHandler prefs = SharedPrefsHandler.getInstance(context);
        prefs.putInt(StaticData.COUNT_PREFIX + itemId, count);
        prefs.putInt(StaticData.DOLLARS_PREFIX + itemId, amount.getAmount_dollar());
        prefs.putInt(StaticData.CENTS_PREFIX + itemId, amount.getAmount_cents());
    }
}

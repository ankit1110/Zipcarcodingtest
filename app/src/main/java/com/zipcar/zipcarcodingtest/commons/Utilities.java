package com.zipcar.zipcarcodingtest.commons;

import android.content.Context;
import android.widget.Toast;

import com.zipcar.zipcarcodingtest.models.Amount;

public class Utilities {

    public static void showMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static Amount parseAmountFromString(String newAmount) {
        Amount amount = new Amount(0, 0);
        newAmount = newAmount.replaceAll("\\$", "");
        if (!newAmount.isEmpty()) {
            double dAmount = Double.parseDouble(newAmount);
            if (dAmount <= 0) {
                return amount;
            }
        } else {
            return amount;
        }

        String[] temp = newAmount.split("\\.");
        String tempcents = "00";
        if (temp.length > 1) {
            amount.setAmount_dollar(Integer.parseInt(temp[0]));
            tempcents = temp[1];
            if (tempcents.length() == 0) {
                tempcents = "00";
            } else if (tempcents.length() == 1) {
                tempcents = tempcents + "0";
            } else {
                tempcents = tempcents.substring(0, 2);
            }

            amount.setAmount_cents(Integer.parseInt(tempcents));
        } else {
            amount.setAmount_dollar(Integer.parseInt(temp[0]));
            amount.setAmount_cents(Integer.parseInt(tempcents));
        }
        return amount;
    }
}

package com.zipcar.zipcarcodingtest.models;

import java.io.Serializable;

public class Amount implements Serializable {
    private int amount_dollar;
    private int amount_cents;

    public Amount(int amount_dollar, int amount_cents) {
        this.amount_dollar = amount_dollar;
        this.amount_cents = amount_cents;
    }

    public int getAmount_dollar() {
        return amount_dollar;
    }

    public void setAmount_dollar(int amount_dollar) {
        this.amount_dollar = amount_dollar;
    }

    public int getAmount_cents() {
        return amount_cents;
    }

    public void setAmount_cents(int amount_cents) {
        this.amount_cents = amount_cents;
    }

    public boolean isZero() {
        return amount_dollar == 0 && amount_cents == 0;
    }

    @Override
    public String toString() {
        String stringCents = amount_cents > 9 ? amount_cents + "" : "0" + amount_cents;
        return "$" + amount_dollar + "." + stringCents;
    }

    public int equals(Amount amount) {
        if (amount_dollar > amount.getAmount_dollar()) {
            return 1;
        } else if (amount_dollar < amount.getAmount_dollar()) {
            return -1;
        } else {
            if (amount_cents < amount.getAmount_cents()) {
                return -1;
            } else if (amount_cents > amount.getAmount_cents()) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    @Override
    public int hashCode() {
        int result = amount_dollar;
        result = 31 * result + amount_cents;
        return result;
    }

    public Amount subtract(Amount amount){
        if(amount.equals(this) > 0){
            return new Amount(0, 0);
        }

        int dollar = amount_dollar;
        int cents = amount_cents;
        int itemCents = amount.getAmount_cents();
        int itemDollars = amount.getAmount_dollar();

        if(cents < itemCents){
            dollar--;
            cents += 100;
        }
        return new Amount(dollar-itemDollars, cents-itemCents);
    }
}

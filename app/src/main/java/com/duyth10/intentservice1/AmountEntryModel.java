package com.duyth10.intentservice1;


import java.text.NumberFormat;
import java.util.Locale;

public class AmountEntryModel {
    private StringBuilder input = new StringBuilder();

    public String getInput() {
        return input.toString();
    }

    public void appendNumber(String number) {
        if (input.length() < 7) {
            input.append(number);
        }
    }

    public void deleteLast() {
        if (input.length() > 0) {
            input.deleteCharAt(input.length() - 1);
        }
    }

    public void clear() {
        input.setLength(0);
    }

    public String formatNumber() {
        if (input.length() == 0) {
            return "0";
        }

        long parsedNumber = Long.parseLong(input.toString());
        NumberFormat formatter = NumberFormat.getInstance(Locale.US);

        return formatter.format(parsedNumber);
    }
}


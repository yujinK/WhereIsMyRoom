package com.example.yujin.whereismyroom;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MultiSelectionSpinner extends AppCompatSpinner implements DialogInterface.OnMultiChoiceClickListener {
    String[] _items = null;
    boolean[] mSelection = null;

    ArrayAdapter<String> simpleAdapter;

    public MultiSelectionSpinner(Context context) {
        super(context);

        simpleAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item);
        super.setAdapter(simpleAdapter);
    }

    public MultiSelectionSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);

        simpleAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item);
        super.setAdapter(simpleAdapter);
    }

    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (mSelection != null && which < mSelection.length) {
            mSelection[which] = isChecked;

            simpleAdapter.clear();
            simpleAdapter.add(buildSelectedItemString());
        } else {
            throw new IllegalArgumentException("Argument 'which' is out of bounds.");
        }
    }

    @Override
    public boolean performClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMultiChoiceItems(_items, mSelection, this);
        builder.show();
        return true;
    }

    @Override
    public void setAdapter(SpinnerAdapter adapter) {
        throw new RuntimeException("setAdapter is not supported by MultiSelectSpinner.");
    }

    public void setItems(String[] items) {
        _items = items;
        mSelection = new boolean[_items.length];
        simpleAdapter.clear();
//        simpleAdapter.add(_items[0]);
        Arrays.fill(mSelection, false);
    }

    public void setItems(List<String> items) {
        _items = items.toArray(new String[items.size()]);
        mSelection = new boolean[_items.length];
        simpleAdapter.clear();
        simpleAdapter.add(_items[0]);
        Arrays.fill(mSelection, false);
    }

    public void setSelection(String[] selection) {
        for (String cell : selection) {
            for (int i = 0; i< _items.length; i++) {
                if (_items[i].equals(cell)) {
                    mSelection[i] = true;
                }
            }
        }
    }

    public void setSelection(List<String> selection) {
        for (int i=0; i<mSelection.length; i++) {
            mSelection[i] = false;
        }
        for (String sel : selection) {
            for (int j=0; j<_items.length; j++) {
                if (_items[j].equals(sel)) {
                    mSelection[j] = true;
                }
            }
        }

        simpleAdapter.clear();
        simpleAdapter.add(buildSelectedItemString());
    }

    public void setSelection(int index) {
        for (int i=0; i<mSelection.length; i++) {
            mSelection[i] = false;
        }
        if (index >= 0 && index < mSelection.length) {
            mSelection[index] = true;
        } else {
            throw new IllegalArgumentException("Index" + index + " is out of bounds.");
        }

        simpleAdapter.clear();
        simpleAdapter.add(buildSelectedItemString());
    }

    public List<String> getSelectedStrings() {
        List<String> selection = new LinkedList<>();

        for (int i=0; i<_items.length; i++) {
            if (mSelection[i]) {
                selection.add(_items[i]);
            }
        }

        return selection;
    }

    public List<Integer> getSelectedIndicies() {
        List<Integer> selection = new LinkedList<>();

        for (int i=0; i<_items.length; i++) {
            if (mSelection[i]) {
                selection.add(i);
            }
        }

        return selection;
    }

    private String buildSelectedItemString() {
        StringBuilder sb = new StringBuilder();
        boolean foundOne = false;

        for (int i=0; i<_items.length; i++) {
            if (mSelection[i]) {
                if (foundOne) {
                    sb.append(", ");
                }
                foundOne = true;
                sb.append(_items[i]);
            }
        }

        return sb.toString();
    }

    public String getSelectedItemsAsString() {
        StringBuilder sb = new StringBuilder();
        boolean foundOne = false;

        for (int i=0; i<_items.length; i++) {
            if (mSelection[i]) {
                if (foundOne) {
                    sb.append(", ");
                }
                foundOne = true;
                sb.append(mSelection[i]);
            }
        }

        return sb.toString();
    }
}

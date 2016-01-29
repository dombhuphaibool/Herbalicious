package com.bandonleon.herbalicious.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dombhuphaibool on 1/29/16.
 */
public class SpinnerHintAdapter<T> extends ArrayAdapter<T> {

    public SpinnerHintAdapter(Context context, @LayoutRes int resource, @NonNull T[] objects) {
        super(context, resource, objects);
    }

    public SpinnerHintAdapter(Context context, @LayoutRes int resource, @NonNull List<T> objects) {
        super(context, resource, objects);
    }

    @Override
    public boolean isEnabled(int position) {
        // First item will be use as a hint
        return position != 0;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
        textView.setTextColor(position == 0 ? Color.GRAY : Color.BLACK);
        return textView;
    }
}

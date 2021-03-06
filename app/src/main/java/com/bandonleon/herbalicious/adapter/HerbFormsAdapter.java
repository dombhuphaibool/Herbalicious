package com.bandonleon.herbalicious.adapter;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bandonleon.herbalicious.R;
import com.bandonleon.herbalicious.model.Herb;

import java.util.List;

/**
 * Created by dombhuphaibool on 1/23/16.
 */
public class HerbFormsAdapter extends RecyclerView.Adapter<HerbFormsAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView herbFormNameTxt;

        public ViewHolder(View rootView) {
            super(rootView);
            herbFormNameTxt = (TextView) rootView.findViewById(R.id.form_name_txt);
        }
    }

    private Resources mRes;
    private Herb mHerb;

    public HerbFormsAdapter(Resources res, Herb herb) {
        mRes = res;
        load(herb);
    }

    public void load(Herb herb) {
        mHerb = herb;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rootView = inflater.inflate(R.layout.herb_form_list_item, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.herbFormNameTxt.setText(mHerb.getForms().get(position).toString(mRes));
    }

    @Override
    public int getItemCount() {
        return mHerb != null ? mHerb.getForms().size() : 0;
    }
}

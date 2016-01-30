package com.bandonleon.herbalicious.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bandonleon.herbalicious.R;
import com.bandonleon.herbalicious.model.HerbCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dombhuphaibool on 1/21/16.
 */
public class HerbsListAdapter extends RecyclerView.Adapter<HerbsListAdapter.ViewHolder> {

    private HerbCollection mHerbCollection;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mHerbName;
        public ViewHolder(View rootView) {
            super(rootView);
            mHerbName = (TextView) rootView.findViewById(R.id.herb_name_txt);
        }
    }

    public HerbsListAdapter(HerbCollection herbCollection) {
        mHerbCollection = herbCollection;
    }

    @Override
    public HerbsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.herbs_list_item, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(HerbsListAdapter.ViewHolder holder, int position) {
        // holder.mHerbName.setText(mData.get(position) + ", herb #" + String.valueOf(mNumItems - position));
        holder.mHerbName.setText(mHerbCollection.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mHerbCollection.size();
    }
}

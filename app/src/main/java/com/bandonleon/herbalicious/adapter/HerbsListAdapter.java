package com.bandonleon.herbalicious.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bandonleon.herbalicious.R;
import com.bandonleon.herbalicious.model.Herb;
import com.bandonleon.herbalicious.model.HerbCollection;

/**
 * Created by dombhuphaibool on 1/21/16.
 */
public class HerbsListAdapter extends RecyclerView.Adapter<HerbsListAdapter.ViewHolder> {

    public interface HerbList {
        int size();
        Herb getAtPosition(int position);
    }

    public interface OnClickListener {
        void onItemClick(int position);
    }

    private HerbList mHerbList;
    private OnClickListener mListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mHerbName;
        int mPosition;

        public ViewHolder(View rootView) {
            super(rootView);
            mHerbName = (TextView) rootView.findViewById(R.id.herb_name_txt);
        }
    }

    public HerbsListAdapter(HerbList herbList) {
        mHerbList = herbList;
    }

    public void setOnClickListener(OnClickListener listener) {
        mListener = listener;
    }

    @Override
    public HerbsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.herbs_list_item, parent, false);
        final HerbsListAdapter.ViewHolder viewHolder = new ViewHolder(rootView);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyOnItemClick(viewHolder.mPosition);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HerbsListAdapter.ViewHolder holder, int position) {
        holder.mHerbName.setText(mHerbList.getAtPosition(position).getName() +
                ", herb #" + String.valueOf(mHerbList.size() - position));
        holder.mPosition = position;
    }

    @Override
    public int getItemCount() {
        return mHerbList.size();
    }

    private void notifyOnItemClick(int position) {
        if (mListener != null) {
            mListener.onItemClick(position);
        }
    }
}

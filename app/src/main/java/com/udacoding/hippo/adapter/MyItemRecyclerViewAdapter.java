package com.udacoding.hippo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacoding.hippo.R;
import com.udacoding.hippo.fragment.HomeFragment;
import com.udacoding.hippo.fragment.dummy.DataItem;
import com.udacoding.hippo.fragment.dummy.DummyContent.DummyItem;

import java.util.List;


public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<DataItem> mValues;
    private final HomeFragment.OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(List<DataItem> items, HomeFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mName.setText(mValues.get(position).getPlacename());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(mListener != null){


                    mListener.onListFragmentInteraction(mValues.get(position));
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mName;
        public final TextView mAddress;
        public final ImageView mImageView ;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mName = (TextView) view.findViewById(R.id.itemName);
            mAddress = (TextView) view.findViewById(R.id.itemAddress);
            mImageView = view.findViewById(R.id.itemImg);
        }


    }
}

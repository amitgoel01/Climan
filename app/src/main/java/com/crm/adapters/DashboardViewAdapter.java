package com.crm.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crm.R;
import com.crm.model.DataModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class DashboardViewAdapter extends RecyclerView.Adapter {

    ArrayList<DataModel> mValues;
    Context mContext;
    protected ItemListener mListener;

    public DashboardViewAdapter(Context context, ArrayList values, ItemListener itemListener) {
        mValues = values;
        mContext = context;
        mListener=itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textView;
        public ImageView imageView;
        public View card;
        DataModel item;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            textView = v.findViewById(R.id.textView);
            card = v.findViewById(R.id.cardView);

        }

        public void setData(DataModel item) {
            this.item = item;

            textView.setText(item.getText());
            textView.setCompoundDrawablesWithIntrinsicBounds(null, mContext.getResources().
                    getDrawable(item.getIcon(), null), null, null);
            card.setBackgroundColor(mContext.getResources().getColor(item.getColor()));

        }


        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }

    @Override
    public DashboardViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.dashboard_view_item, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder v = (ViewHolder) holder;
        ((ViewHolder) holder).setData(mValues.get(position));
    }

    /*@Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        (ViewHolder)holder.setData(mValues.get(position));
    }*/

    @Override
    public int getItemCount() {

        return mValues.size();
    }

    public interface ItemListener {
        void onItemClick(DataModel item);
    }
}
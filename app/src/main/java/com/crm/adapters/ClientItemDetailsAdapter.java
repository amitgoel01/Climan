package com.crm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.crm.R;
import com.crm.database.entity.ClientEntity;
import com.crm.database.entity.ClientPersonEntity;
import com.crm.databinding.ClientDetailItemViewBinding;
import com.crm.databinding.ClientListItemBinding;
import com.crm.model.Item;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ClientItemDetailsAdapter extends RecyclerView.Adapter<ClientItemDetailsAdapter.Holder> {

    private Context mContext;
    private ClientDetailItemViewBinding mBinding;
   /* ClientEntity mClientEntity;
    List<ClientPersonEntity> mClientPersonEntities;*/
   private ArrayList<Item> mItemList;
    public ClientItemDetailsAdapter(ArrayList<Item> list, Context context) {
      /*  mClientEntity = clientEntity;
        mClientPersonEntities = clientPersonEntities;*/
        mItemList = list;
        mContext = context;
    }

    @Override
    public void onBindViewHolder(@NonNull ClientItemDetailsAdapter.Holder holder, int position) {
        if(mItemList.get(position).getImageId() == R.drawable.back) {
            holder.binding.getRoot().setBackgroundColor(mContext.getResources().getColor(R.color.dashboard_menu_color, null));
            holder.binding.headerText.setText(mItemList.get(position).getHeader());
            holder.binding.content.setText(mItemList.get(position).getContent());
            holder.binding.headerText.setTextSize(18);
        }
        else {
            holder.binding.getRoot().setBackgroundColor(mContext.getResources().getColor(R.color.white, null));
            holder.binding.leftImg.setImageDrawable(mContext.getResources().getDrawable(mItemList.get(position).getImageId(), null));
            holder.binding.headerText.setText(mItemList.get(position).getHeader());
            holder.binding.content.setText(mItemList.get(position).getContent());
        }
    }

    @NonNull
    @Override
    public ClientItemDetailsAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        mBinding = ClientDetailItemViewBinding.inflate(layoutInflater, parent, false);
        return new Holder(mBinding);
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private final ClientDetailItemViewBinding binding;
        public Holder(ClientDetailItemViewBinding listItemBinding) {
            super(listItemBinding.getRoot());
            binding = listItemBinding;
        }
    }

    public void setList(ArrayList<Item> list) {
        mItemList = list;
    }

  /*  private ArrayList<String> addHeaderList() {
        ArrayList<String> headerList= new ArrayList<>();
        headerList.add(mContext.getResources().getString(R.string.group_id));
        headerList.add(mContext.getResources().getString(R.string.client_group_name));
        headerList.add(mContext.getResources().getString(R.string.client_Company_Name));
        headerList.add(mContext.getResources().getString(R.string.client_id));
        headerList.add(mContext.getResources().getString(R.string.client_address_first_line));
        headerList.add(mContext.getResources().getString(R.string.client_address_second_line));
        headerList.add(mContext.getResources().getString(R.string.email_id));
        headerList.add(mContext.getResources().getString(R.string.country));
        headerList.add(mContext.getResources().getString(R.string.city));
        headerList.add(mContext.getResources().getString(R.string.state));
        headerList.add(mContext.getResources().getString(R.string.pin_code));
        headerList.add(mContext.getResources().getString(R.string.client_source));
        return headerList;
    }*/

    private void addContentList() {
       /* if(employee.getEmpName() != null) {
            mContentList.add(employee.getEmpName());
            mContentList.add(employee.getEmailId());
            mContentList.add(employee.getBusinessUnit());
            mContentList.add(employee.getEmailId());
            mContentList.add(employee.getCountry());
            mContentList.add(employee.getCity());
            mContentList.add(employee.getState());
            mContentList.add(employee.getDesignation());
            mContentList.add(employee.getRpManager());
            mContentList.add(employee.getPhNumber());
            mContentList.add(employee.getDoj());
            mContentList.add(employee.getDob());
            mContentList.add(employee.getTenure());
        }*/
    }

}

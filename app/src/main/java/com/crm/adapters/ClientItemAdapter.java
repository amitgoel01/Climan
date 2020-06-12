package com.crm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crm.database.entity.ClientEntity;
import com.crm.databinding.ClientListItemBinding;
import com.crm.databinding.ListItemBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ClientItemAdapter extends RecyclerView.Adapter<ClientItemAdapter.Holder> {

    private Context mContext;
    private List<ClientEntity> mList;

    private ClientListItemBinding mListItemBinding;
    public ClientItemAdapter(List<ClientEntity> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public void onBindViewHolder(@NonNull ClientItemAdapter.Holder holder, int position) {
        holder.binding.clientId.setText("client id:  c" + Long.toString(mList.get(position).getClientId()));
        holder.binding.clientGroupName.setText("client group:  g" +mList.get(position).getClientGroup());
    }

    @NonNull
    @Override
    public ClientItemAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        mListItemBinding = ClientListItemBinding.inflate(layoutInflater, parent, false);
        return new Holder(mListItemBinding);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private final ClientListItemBinding binding;
        public Holder(ClientListItemBinding listItemBinding) {
            super(listItemBinding.getRoot());
            binding = listItemBinding;
        }
    }

    public void setList(List<ClientEntity> list) {
        mList = list;
    }

}

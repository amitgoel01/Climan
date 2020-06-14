package com.crm.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crm.R;
import com.crm.database.entity.ClientEntity;
import com.crm.databinding.ClientListItemBinding;
import com.crm.databinding.ListItemBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ClientItemAdapter extends RecyclerView.Adapter<ClientItemAdapter.Holder> {

    private Context mContext;
    private List<ClientEntity> mList;
    private IItemClickListener mListener;
    private ClientListItemBinding mListItemBinding;

    public ClientItemAdapter(List<ClientEntity> list, IItemClickListener activity, Context context) {
        mList = list;
        mContext = context;
        mListener = activity;
    }

    @Override
    public void onBindViewHolder(@NonNull ClientItemAdapter.Holder holder, int position) {
        if(position == 0) {
            holder.binding.clientGroupName.setText(mContext.getResources().getString(R.string.group_id));
            holder.binding.clientId.setText(mContext.getResources().getString(R.string.client_id));
            holder.binding.clientCompanyName.setText(mContext.getResources().getString(R.string.company_name));
            holder.binding.city.setText(mContext.getResources().getString(R.string.city));
        }
        if(position != 0) {
//            holder.binding.clientId.setText("client id:  c" + Long.toString(mList.get(position).getClientId()));
            holder.binding.clientGroupName.setText(mList.get(position).getClientGroup());
            holder.binding.clientId.setText(String.valueOf(mList.get(position).getClientId()));
            holder.binding.clientCompanyName.setText(mList.get(position).getClientCompanyName());
            holder.binding.city.setText(mList.get(position).getClientCity());

        }


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

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClickItem(getAdapterPosition(), mList.get(getAdapterPosition()).getClientId());
                }
            });
        }
    }

    public void setList(List<ClientEntity> list) {
        mList = list;
    }

    public interface IItemClickListener {
        public void onClickItem(int position, Long clientId);
    }

}

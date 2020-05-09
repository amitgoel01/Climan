package com.crm.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.crm.R;
import com.crm.database.entity.EmployeeEntity;
import com.crm.database.entity.EmployeeEntity;
import com.crm.model.Item;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.BeanHolder> {

//    private List<Employee> list;
    private EmployeeEntity employee;
    private Item item;
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<String> mHeaderList;
    private ArrayList<String> mContentList;


    public ItemAdapter(EmployeeEntity employee, Context context) {
        mHeaderList = new ArrayList<>();
        mContentList = new ArrayList<>();
        layoutInflater = LayoutInflater.from(context);
        this.employee = employee;
        this.context = context;
        addEmployeeContent();
        addEmployeeHeader();
        addImage();
    }

    private void addEmployeeHeader() {
        mHeaderList.add(context.getResources().getString(R.string.emp_name));
        mHeaderList.add(context.getResources().getString(R.string.emp_id));
        mHeaderList.add(context.getResources().getString(R.string.emp_bu));
        mHeaderList.add(context.getResources().getString(R.string.email_id));
        mHeaderList.add(context.getResources().getString(R.string.country));
        mHeaderList.add(context.getResources().getString(R.string.city));
        mHeaderList.add(context.getResources().getString(R.string.state));
        mHeaderList.add(context.getResources().getString(R.string.designation));
        mHeaderList.add(context.getResources().getString(R.string.reporting_manager));
        mHeaderList.add(context.getResources().getString(R.string.phone_number));
        mHeaderList.add(context.getResources().getString(R.string.doj));
        mHeaderList.add(context.getResources().getString(R.string.dob));
        mHeaderList.add(context.getResources().getString(R.string.tenure));


    }

    private void addEmployeeContent() {
        if(employee.getEmpName() != null) {
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
        }
    }

    private void addImage() {

    }


    @Override
    public BeanHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(mContentList == null ) {
            mContentList = new ArrayList<>();
            addEmployeeHeader();
        }
        if(mContentList.size() ==0) {
            addEmployeeContent();
        }


        mContentList = new ArrayList<>();
        View view = layoutInflater.inflate(R.layout.item_content_profile, parent, false);
        return new BeanHolder(view);
    }

    @Override
    public void onBindViewHolder(BeanHolder holder, int position) {
        if(mContentList.size() > 0) {
            Log.e("bind", "onBindViewHolder: " + position);
            holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_add_client));
            holder.header.setText(mHeaderList.get(position));
            holder.content.setText(mContentList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mHeaderList.size();
    }

    public class BeanHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private TextView header;
        private TextView content;
        public BeanHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.nameImageView);
            header = itemView.findViewById(R.id.header);
            content = itemView.findViewById(R.id.content);
        }

        @Override
        public void onClick(View v) {

        }
    }

}

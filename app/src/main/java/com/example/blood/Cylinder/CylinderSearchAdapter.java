package com.example.blood.Cylinder;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blood.Ambulance;
import com.example.blood.R;

import java.util.ArrayList;
import java.util.List;

public class CylinderSearchAdapter extends RecyclerView.Adapter<CylinderSearchAdapter.ViewHolder>  {

    private ArrayList<Cylinder> cylinderSearchList;
    private OnItemClickListener onItemClickListener;


    public interface OnItemClickListener{
        void onItemClick(int position);
        void onButtonClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }


    public CylinderSearchAdapter(ArrayList<Cylinder> ambulanceSearchList)
    {
        this.cylinderSearchList = ambulanceSearchList;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }







    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ambulance_list_item,parent,false);
        return new ViewHolder(view,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CylinderSearchAdapter.ViewHolder holder, int position) {
        String organization = cylinderSearchList.get(position).getOrganizationName();
        String contact = cylinderSearchList.get(position).getContactNo();
        String division = cylinderSearchList.get(position).getDivision().getDivision();
        String district = cylinderSearchList.get(position).getDistrict().getDistrict();
        String upazilla;
        if(cylinderSearchList.get(position).getUpazilla()!=null)
            upazilla = cylinderSearchList.get(position).getUpazilla().getUpazilla();
        else
        {
            upazilla = null;
        }
        String remarks = cylinderSearchList.get(position).getRemarks();
        holder.setData(organization,contact,division,district,upazilla,remarks);
        /*if(upazilla == null){
            holder.itemView.post(new Runnable() {
                View view = holder.itemView;
                @Override
                public void run() {
                    view.setLayoutParams(new RecyclerView.LayoutParams(view.getWidth(),view.getHeight()*5/6));
                }
            });

        }*/
    }

    @Override
    public int getItemCount() {
        return cylinderSearchList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView organizationNameTextView;
        private TextView contactTextView;
        private TextView divisionTextView;
        private TextView districtTextView;
        private TextView upazillaTextView;
        private TextView remarksTextView;
        private ImageButton imageButton;


        public ViewHolder(View itemView, OnItemClickListener listener)
        {
            super(itemView);
            organizationNameTextView = (TextView) itemView.findViewById(R.id.textView10);
            contactTextView = (TextView) itemView.findViewById(R.id.textView11);
            divisionTextView = (TextView) itemView.findViewById(R.id.textView13);
            districtTextView = (TextView) itemView.findViewById(R.id.textView12);
            upazillaTextView = (TextView) itemView.findViewById(R.id.textView51);
            remarksTextView = (TextView) itemView.findViewById(R.id.textView68);
            imageButton = (ImageButton) itemView.findViewById(R.id.button2);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null)
                    {
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null)
                    {
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.onButtonClick(position);
                        }
                    }
                }
            });

        }

        public void setData(String organization,String contact,String division,String district,String upazilla,String remarks)
        {

            organizationNameTextView.setText(organization);
            contactTextView.setText(contact);
            divisionTextView.setText(division);
            districtTextView.setText(district);
            if(upazilla == null)
            {
                upazillaTextView.setText("-");
                //TextView textView = itemView.findViewById(R.id.textView52);
                //textView.setVisibility(View.GONE);
                //itemView.setPadding(0,0,0,-10);

            }
            else{
                upazillaTextView.setText(upazilla);
            }
            remarksTextView.setText(remarks);
        }


    }
}

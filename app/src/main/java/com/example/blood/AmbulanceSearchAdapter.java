package com.example.blood;



import static androidx.core.app.ActivityCompat.requestPermissions;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AmbulanceSearchAdapter extends RecyclerView.Adapter<AmbulanceSearchAdapter.ViewHolder>  {

    private ArrayList<Ambulance> ambulanceSearchList;
    private OnItemClickListener onItemClickListener;
    public double heeight = -1;


    public interface OnItemClickListener{
        void onItemClick(int position);
        void onButtonClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }


    public AmbulanceSearchAdapter(ArrayList<Ambulance> ambulanceSearchList)
    {
        this.ambulanceSearchList = ambulanceSearchList;
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
    public void onBindViewHolder(@NonNull AmbulanceSearchAdapter.ViewHolder holder, int position) {
        String organization = ambulanceSearchList.get(position).getOrganizationName();
        String contact = ambulanceSearchList.get(position).getContactNo();
        String division = ambulanceSearchList.get(position).getDivision().getDivision();
        String district = ambulanceSearchList.get(position).getDistrict().getDistrict();
        String upazilla;
        if(ambulanceSearchList.get(position).getUpazilla()!=null)
            upazilla = ambulanceSearchList.get(position).getUpazilla().getUpazilla();
        else
        {
            upazilla = null;
        }
        String remarks = ambulanceSearchList.get(position).getRemarks();
        holder.setData(organization,contact,division,district,upazilla,remarks);
        /*{
            holder.constraintLayout.post(new Runnable() {
                ConstraintLayout view = holder.constraintLayout;

                @Override
                public void run() { //viewgroup.layout params
                    if(heeight == -1)
                    {
                        heeight = view.getLayoutParams().height;
                    }
                    if(upazilla == null)
                    {
                        view.getLayoutParams().height =(int) (heeight*6)/5;
                    }
                    else{
                        view.getLayoutParams().height = (int) heeight;
                    }

                //    view.setLayoutParams(new LinearLayout.LayoutParams(view.getWidth(),view.getHeight()*5/6));
                }
            });

        }*/

    }

    @Override
    public int getItemCount() {
        return ambulanceSearchList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView organizationNameTextView;
        private TextView contactTextView;
        private TextView divisionTextView;
        private TextView districtTextView;
        private TextView upazillaTextView;
        private TextView remarksTextView;
        private ImageButton imageButton;
        public ConstraintLayout constraintLayout;

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

            constraintLayout = itemView.findViewById(R.id.constraint_layout_1);


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
                //upazillaTextView.setVisibility(View.GONE);
                upazillaTextView.setText("-");
                /*TextView textView = itemView.findViewById(R.id.textView52);*/
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

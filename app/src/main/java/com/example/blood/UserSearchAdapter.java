package com.example.blood;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserSearchAdapter extends RecyclerView.Adapter<UserSearchAdapter.ViewHolder> {

    private ArrayList<UserSearch> userSearchList;
    private OnItemClickListener onItemClickListener;
    private Context context;

    public UserSearchAdapter(Context context,ArrayList<UserSearch> userSearchList) {
        this.userSearchList = userSearchList;
        this.context = context;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@Nullable ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchlistitem, parent,false);
        return new ViewHolder(view, onItemClickListener);

        /*LayoutInflater layoutInflater = context.getLayoutInflater();
        View listViewItem = layoutInflater.inflate(R.layout.searchlistitem, null, true);


        UserSearch userSearch = userSearchList.get(position);
        TextView textView;
        textView = (TextView) listViewItem.findViewById(R.id.textView10);
        textView.setText(userSearch.getFirstName());

        textView = (TextView) listViewItem.findViewById(R.id.textView11);
        textView.setText(userSearch.getLastName());

        textView = (TextView) listViewItem.findViewById(R.id.textView12);
        textView.setText(userSearch.getDistrict().getDistrict());

        textView = (TextView) listViewItem.findViewById(R.id.textView13);
        textView.setText(userSearch.getBloodGroup());

        textView = (TextView) listViewItem.findViewById(R.id.textView51);
        textView.setText(userSearch.getDivision().getDivision());


        textView = (TextView) listViewItem.findViewById(R.id.textView49);

        if (!userSearch.isCurrentStatus()) {
            GradientDrawable drawable = (GradientDrawable) context.getResources()
                    .getDrawable(R.drawable.edit_text_bg_green);
            drawable.mutate();
            drawable.setColor(Color.RED);
            textView.setBackground(drawable);
            textView.setText("UnAvailable");
        }



        return listViewItem;*/
    }

    @Override
    public void onBindViewHolder(@NonNull UserSearchAdapter.ViewHolder holder, int position) {
        String firstName = userSearchList.get(position).getFirstName();
        String lastName = userSearchList.get(position).getLastName();
        String bloodGroup = userSearchList.get(position).getBloodGroup();
        String division = userSearchList.get(position).getDivision().getDivision();
        String district = userSearchList.get(position).getDistrict().getDistrict();
        boolean status = userSearchList.get(position).isCurrentStatus();
        holder.setDate(context,firstName,lastName,bloodGroup,division,district,status);

    }

    @Override
    public int getItemCount() {
        return userSearchList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView firstNameTextView;
        private TextView lastNameTextView;
        private TextView divisionTextView;
        private TextView districtTextView;
        private TextView bloodGroupTextView;
        private TextView statusTextView;

        public ViewHolder(View itemView,OnItemClickListener listener)
        {
            super(itemView);
            firstNameTextView = (TextView) itemView.findViewById(R.id.textView10);
            lastNameTextView = (TextView) itemView.findViewById(R.id.textView11);
            bloodGroupTextView = (TextView) itemView.findViewById(R.id.textView13);
            divisionTextView = (TextView) itemView.findViewById(R.id.textView51);
            districtTextView = (TextView) itemView.findViewById(R.id.textView12);
            statusTextView = (TextView) itemView.findViewById(R.id.textView49);

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
        }

        public void setDate(Context context,String firstName,String lastName,String bloodGroup,String division,String district,boolean status)
        {
            firstNameTextView.setText(firstName);
            lastNameTextView.setText(lastName);
            bloodGroupTextView.setText(bloodGroup);
            divisionTextView.setText(division);
            districtTextView.setText(district);

            if(!status)
            {
               // GradientDrawable drawable = (GradientDrawable) context.getResources()
                 //       .getDrawable(R.drawable.edit_text_bg_green);
                //drawable.mutate();
               /// drawable.setColor(Color.RED);
              //  statusTextView.setBackground(drawable);
                statusTextView.setText("UnAvailable");
                statusTextView.setBackgroundResource(R.drawable.edit_text_bg_red);

            }
            else{
                statusTextView.setText("Available");
                statusTextView.setBackgroundResource(R.drawable.edit_text_bg_green);
            }
            //statusTextView.setText(status);
        }
    }
}

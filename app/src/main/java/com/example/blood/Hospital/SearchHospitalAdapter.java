package com.example.blood.Hospital;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blood.R;

import android.view.View;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class SearchHospitalAdapter extends RecyclerView.Adapter<SearchHospitalAdapter.ViewHolder> {

    private ArrayList<datum> hospitalsArrayList;
    private OnItemClickListener onItemClickListener;
    private String bed_type;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    public SearchHospitalAdapter(ArrayList<datum> hospitalsArrayList,String bed_type)
    {
        this.hospitalsArrayList = hospitalsArrayList;
        this.bed_type = bed_type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hospital_search_list_item,parent,false);
        return new ViewHolder(view,onItemClickListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull SearchHospitalAdapter.ViewHolder holder, int position) {
        String hospitalName = hospitalsArrayList.get(position).getName();
        String availableBed,totalBed;
        availableBed = "";
        totalBed = "";
        if(bed_type.equalsIgnoreCase("general_beds"))
        {
            availableBed = String.valueOf(hospitalsArrayList.get(position).getGeneral_beds_available());
            totalBed = String.valueOf(hospitalsArrayList.get(position).getTotal_beds());
        }
        else if(bed_type.equalsIgnoreCase("icu_beds"))
        {
            availableBed = String.valueOf(hospitalsArrayList.get(position).getIcu_beds_available());
            totalBed = String.valueOf(hospitalsArrayList.get(position).getIcu_beds());

        }
        else if(bed_type.equalsIgnoreCase("hfn_beds"))
        {
            availableBed = String.valueOf(hospitalsArrayList.get(position).getHfn_beds_available());
            totalBed = String.valueOf(hospitalsArrayList.get(position).getHfn_beds());
        }
        else if(bed_type.equalsIgnoreCase("hdu_beds"))
        {
            availableBed = String.valueOf(hospitalsArrayList.get(position).getHdu_beds_available());
            totalBed = String.valueOf(hospitalsArrayList.get(position).getHdu_beds());
        }

        else{
            availableBed = String.valueOf(hospitalsArrayList.get(position).getHdu_beds_available()+hospitalsArrayList.get(position).getHfn_beds_available()+hospitalsArrayList.get(position).getIcu_beds_available()
            +hospitalsArrayList.get(position).getGeneral_beds_available());
            totalBed = String.valueOf(hospitalsArrayList.get(position).getTotal_beds());
        }
        String lastUpdate = hospitalsArrayList.get(position).getDghs_update();

       // DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.ENGLISH);
        //DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS'Z'", Locale.ENGLISH);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        //LocalDateTime date = LocalDateTime.parse(lastUpadate, inputFormatter);

        //String formattedDate = outputFormatter.format(date);
        LocalDateTime date = LocalDateTime.parse(lastUpdate,outputFormatter);
        LocalDateTime today = LocalDateTime.now();
        Duration duration = Duration.between(date,today);
        if(duration.toDays()==0)
        {
            holder.setData(hospitalName,availableBed,totalBed,"Last Update about "+String.valueOf(duration.toHours()+" hours ago"));
        }
        else if(duration.toDays()==1)
            holder.setData(hospitalName,availableBed,totalBed,"Last update about "+String.valueOf(duration.toDays()+" day ago"));
        else
            holder.setData(hospitalName,availableBed,totalBed,"Last update about "+String.valueOf(duration.toDays()+" days ago"));
    }

    @Override
    public int getItemCount() {
        return hospitalsArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView hospitalTextView;
        private TextView availableBedTextView;
        private TextView totalBedTextView;
        private TextView lastUpdateTextView;

        public ViewHolder(View itemView,OnItemClickListener listener)
        {
            super(itemView);

            hospitalTextView = (TextView) itemView.findViewById(R.id.textView11);
            availableBedTextView = (TextView) itemView.findViewById(R.id.textView70);
            totalBedTextView = (TextView) itemView.findViewById(R.id.textView72);
            lastUpdateTextView = (TextView) itemView.findViewById(R.id.textView73);

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


        public void setData(String hospital,String available,String total,String lastUpdate)
        {
            hospitalTextView.setText(hospital);
            availableBedTextView.setText(available);
            totalBedTextView.setText(total);
            lastUpdateTextView.setText(lastUpdate);
        }

    }




}

package com.example.nwureddrops;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BloodRequestAdapter extends RecyclerView.Adapter<BloodRequestAdapter.ViewHolder> {

    private List<BloodRequests> bloodRequestList;

    public BloodRequestAdapter(List<BloodRequests> bloodRequestList) {
        this.bloodRequestList = bloodRequestList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blood_request_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BloodRequests bloodRequest = bloodRequestList.get(position);

        // Assuming you have a method to get the request time
        //String requestTime = getCurrentTime();
        holder.textRequestTime.setText("Request Time: " + bloodRequest.getRequestTime());
        holder.textBloodGroup.setText("Blood Group: " + bloodRequest.getBloodGroup());
        holder.textDistrict.setText("District: " + bloodRequest.getDistrict());
        holder.textDonationDate.setText("Donation Date: " + bloodRequest.getDonationDate());
        holder.textContact.setText("Contact: " + bloodRequest.getContact());
        holder.textHospitalDetails.setText("Hospital Details: " + bloodRequest.getHospitalDetails());
        holder.textPatientDetails.setText("Patient Details: " + bloodRequest.getPatientDetails());
    }

    @Override
    public int getItemCount() {
        return bloodRequestList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textRequestTime;
        TextView textBloodGroup;
        TextView textDistrict;
        TextView textDonationDate;
        TextView textContact;
        TextView textHospitalDetails;
        TextView textPatientDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textRequestTime = itemView.findViewById(R.id.textRequestTime);
            textBloodGroup = itemView.findViewById(R.id.textBloodGroup);
            textDistrict = itemView.findViewById(R.id.textDistrict);
            textDonationDate = itemView.findViewById(R.id.textDonationDate);
            textContact = itemView.findViewById(R.id.textContact);
            textHospitalDetails = itemView.findViewById(R.id.textHospitalDetails);
            textPatientDetails = itemView.findViewById(R.id.textPatientDetails);
        }
    }
}

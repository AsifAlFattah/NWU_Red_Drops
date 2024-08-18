package com.example.nwureddrops;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BloodBankAdapter extends RecyclerView.Adapter<BloodBankAdapter.BloodBankViewHolder> {
    private Context context;
    private ArrayList<BloodBank> bloodBanks;

    public BloodBankAdapter(Context context) {
        this.context = context;
        this.bloodBanks = new ArrayList<>();
    }

    public void setBloodBanks(ArrayList<BloodBank> bloodBanks) {
        this.bloodBanks = bloodBanks;
        notifyDataSetChanged(); // Notify the adapter that data has changed
    }

    @NonNull
    @Override
    public BloodBankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.blood_bank_item, parent, false);
        return new BloodBankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BloodBankViewHolder holder, int position) {
        BloodBank bloodBank = bloodBanks.get(position);
        holder.bind(bloodBank);
    }

    @Override
    public int getItemCount() {
        return bloodBanks.size();
    }

    public class BloodBankViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView addressTextView;
        private TextView contactTextView;

        public BloodBankViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            addressTextView = itemView.findViewById(R.id.addressTextView);
            contactTextView = itemView.findViewById(R.id.contactTextView);
        }

        public void bind(BloodBank bloodBank) {
            nameTextView.setText(bloodBank.getName());
            addressTextView.setText("Address: " + bloodBank.getAddress());
            contactTextView.setText("Contact: " + bloodBank.getContact());
        }
    }
}


package com.dicoding.pp_stokbaju.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.BarangViewHolder> {
    private List<Barang> barangList;

    public BarangAdapter(List<Barang> barangList) {
        this.barangList = barangList;
    }

    @NonNull
    @Override
    public BarangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new BarangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BarangViewHolder holder, int position) {
        Barang barang = barangList.get(position);
        holder.namaBarang.setText(barang.getNamaBarang());
        holder.jumlahBarang.setText(String.valueOf(barang.getJumlahBarang()));
    }

    @Override
    public int getItemCount() {
        return barangList.size();
    }

    public static class BarangViewHolder extends RecyclerView.ViewHolder {
        TextView namaBarang, jumlahBarang;

        public BarangViewHolder(@NonNull View itemView) {
            super(itemView);
            namaBarang = itemView.findViewById(android.R.id.text1);
            jumlahBarang = itemView.findViewById(android.R.id.text2);
        }
    }
}
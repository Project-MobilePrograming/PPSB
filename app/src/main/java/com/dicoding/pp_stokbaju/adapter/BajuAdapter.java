package com.dicoding.pp_stokbaju.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.dicoding.pp_stokbaju.R;
import com.dicoding.pp_stokbaju.model.Baju;
import java.util.List;

public class BajuAdapter extends RecyclerView.Adapter<BajuAdapter.BajuViewHolder> {
    private List<Baju> bajuList;
    private OnItemClickListener listener;

    // Interface untuk item click listener
    public interface OnItemClickListener {
        void onItemClick(Baju baju);
    }

    // Constructor dengan listener
    public BajuAdapter(List<Baju> bajuList, OnItemClickListener listener) {
        this.bajuList = bajuList;
        this.listener = listener;
    }

    // Membuat view holder untuk setiap item
    @NonNull
    @Override
    public BajuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang, parent, false);
        return new BajuViewHolder(view);
    }

    // Mengikat data ke view holder
    @Override
    public void onBindViewHolder(@NonNull BajuViewHolder holder, int position) {
        Baju baju = bajuList.get(position);

        // Set data ke view
        holder.tvNamaBaju.setText(baju.getNama_baju());
        holder.tvHarga.setText("Harga: Rp " + baju.getHarga());
        holder.tvStok.setText("Stok: " + baju.getStok());

        // Memuat gambar dari URL menggunakan Picasso
        if (baju.getGambar_url() != null && !baju.getGambar_url().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(baju.getGambar_url())
                    .placeholder(R.drawable.ic_launcher_background) // Gambar placeholder jika loading
                    .error(R.drawable.ic_launcher_foreground) // Gambar jika error
                    .into(holder.ivGambar);
        } else {
            holder.ivGambar.setImageResource(R.drawable.ic_launcher_background);
        }
    }

    // Jumlah item dalam daftar
    @Override
    public int getItemCount() {
        return bajuList.size();
    }

    // ViewHolder untuk menyimpan referensi view
    public static class BajuViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaBaju, tvHarga, tvStok;
        ImageView ivGambar;

        public BajuViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaBaju = itemView.findViewById(R.id.tvNamaBaju);
            tvHarga = itemView.findViewById(R.id.tvHarga);
            tvStok = itemView.findViewById(R.id.tvStok);
            ivGambar = itemView.findViewById(R.id.ivGambar);
        }
    }
}
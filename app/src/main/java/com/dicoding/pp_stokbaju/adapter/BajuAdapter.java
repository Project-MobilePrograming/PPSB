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
        holder.tvJenisBaju.setText("Jenis: " + baju.getNama_jenis_baju()); // Langsung ambil dari model
        holder.tvUkuranBaju.setText("Ukuran: " + baju.getUkuran_baju()); // Langsung ambil dari model
        holder.tvHarga.setText("Harga: Rp " + baju.getHarga());
        holder.tvStok.setText("Stok: " + baju.getStok());

        // Memuat gambar dari URL menggunakan Glide
        if (baju.getGambar_url() != null && !baju.getGambar_url().isEmpty()) {
            // Pastikan URL gambar lengkap (termasuk base URL jika diperlukan)
            String imageUrl = baju.getGambar_url().replace(" ", ""); // Hapus spasi jika ada

            Glide.with(holder.itemView.getContext())
                    .load(imageUrl) // Load gambar dari URL
                    .placeholder(R.drawable.ic_launcher_background) // Gambar placeholder saat loading
                    .error(R.drawable.ic_launcher_foreground) // Gambar jika error
                    .into(holder.ivGambar);
        } else {
            // Jika URL gambar kosong, tampilkan gambar default
            holder.ivGambar.setImageResource(R.drawable.ic_launcher_background);
        }

        // Handle item click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(baju);
            }
        });
    }

    // Jumlah item dalam daftar
    @Override
    public int getItemCount() {
        return bajuList.size();
    }

    // ViewHolder untuk menyimpan referensi view
    public static class BajuViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaBaju, tvJenisBaju, tvUkuranBaju, tvHarga, tvStok;
        ImageView ivGambar;

        public BajuViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaBaju = itemView.findViewById(R.id.tvNamaBaju);
            tvJenisBaju = itemView.findViewById(R.id.tvJenisBaju);
            tvUkuranBaju = itemView.findViewById(R.id.tvUkuranBaju);
            tvHarga = itemView.findViewById(R.id.tvHarga);
            tvStok = itemView.findViewById(R.id.tvStok);
            ivGambar = itemView.findViewById(R.id.ivGambar);
        }
    }
}
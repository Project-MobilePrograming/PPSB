package com.dicoding.pp_stokbaju.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.dicoding.pp_stokbaju.R;
import com.dicoding.pp_stokbaju.model.Baju;
import java.util.List;

public class BajuAdapter extends RecyclerView.Adapter<BajuAdapter.ViewHolder> {

    private List<Baju> bajuList;
    private OnItemClickListener onItemClickListener;
    private OnDeleteClickListener onDeleteClickListener;

    // Interface untuk item click listener
    public interface OnItemClickListener {
        void onItemClick(Baju baju);
    }

    // Interface untuk delete click listener
    public interface OnDeleteClickListener {
        void onDeleteClick(Baju baju);
    }

    // Constructor dengan listener
    public BajuAdapter(List<Baju> bajuList, OnItemClickListener itemClickListener) {
        this.bajuList = bajuList;
        this.onItemClickListener = itemClickListener;

    }

    public BajuAdapter(OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Baju baju = bajuList.get(position);

        // Set data ke view
        holder.tvNamaBaju.setText(baju.getNama_baju());
        holder.tvJenisBaju.setText("Jenis: " + baju.getNama_jenis_baju());
        holder.tvUkuranBaju.setText("Ukuran: " + baju.getUkuran_baju());
        holder.tvHarga.setText("Harga: Rp " + baju.getHarga());
        holder.tvStok.setText("Stok: " + baju.getStok());

        // Memuat gambar menggunakan Glide
        String imageUrl = baju.getGambar_url();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Log.d("BajuAdapter", "Memuat gambar dari URL: " + imageUrl);

            Glide.with(holder.itemView.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_launcher_background) // Gambar placeholder
                    .error(R.drawable.ic_edit) // Gambar jika gagal dimuat
                    .into(holder.ivGambar);
        } else {
            // Jika URL kosong atau null, tampilkan log dan gambar default
            holder.ivGambar.setImageResource(R.drawable.ic_delete);
            Log.e("BajuAdapter", "URL gambar kosong untuk item: " + baju.getNama_baju());
        }

        // Handle item click
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(baju));

        // Handle delete button click
        holder.buttonDelete.setOnClickListener(v -> onDeleteClickListener.onDeleteClick(baju));
    }

    @Override
    public int getItemCount() {
        return bajuList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaBaju, tvJenisBaju, tvUkuranBaju, tvHarga, tvStok;
        ImageView ivGambar;
        Button buttonDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaBaju = itemView.findViewById(R.id.tvNamaBaju);
            tvJenisBaju = itemView.findViewById(R.id.tvJenisBaju);
            tvUkuranBaju = itemView.findViewById(R.id.tvUkuranBaju);
            tvHarga = itemView.findViewById(R.id.tvHarga);
            tvStok = itemView.findViewById(R.id.tvStok);
            ivGambar = itemView.findViewById(R.id.ivGambar);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}

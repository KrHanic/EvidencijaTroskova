package com.example.evidencijatroskova.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evidencijatroskova.R;
import com.example.evidencijatroskova.model.entities.Trosak;

import java.util.ArrayList;
import java.util.List;

public class PrikazTroskovaAdapter extends RecyclerView.Adapter<PrikazTroskovaAdapter.TrosakHolder> {
    private List<Trosak> troskovi = new ArrayList<>();

    @NonNull
    @Override
    public TrosakHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View trosakHolder = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trosak_holder, parent, false);
        return new TrosakHolder(trosakHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull TrosakHolder holder, int position) {
        Trosak currentTrosak = troskovi.get(position);
        holder.tvDatum.setText((CharSequence) currentTrosak.getDatum());
        holder.tvNaziv.setText(currentTrosak.getNaziv());
        holder.tvIznos.setText(String.valueOf(currentTrosak.getIznos()));
        holder.tvOpis.setText(currentTrosak.getOpis());
    }

    @Override
    public int getItemCount() {
        return troskovi.size();
    }

    public void setTroskovi(List<Trosak> troskovi){
        this.troskovi = troskovi;
        notifyDataSetChanged();
    }

    class TrosakHolder extends RecyclerView.ViewHolder{
        private TextView tvDatum;
        private TextView tvNaziv;
        private TextView tvIznos;
        private TextView tvOpis;

        public TrosakHolder(@NonNull View itemView) {
            super(itemView);
            tvDatum = itemView.findViewById(R.id.tvDatumValue);
            tvNaziv = itemView.findViewById(R.id.tvNazivValue);
            tvIznos = itemView.findViewById(R.id.tvIznosValue);
            tvOpis = itemView.findViewById(R.id.tvOpisValue);
        }
    }
}

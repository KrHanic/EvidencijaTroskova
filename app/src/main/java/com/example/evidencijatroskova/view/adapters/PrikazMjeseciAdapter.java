package com.example.evidencijatroskova.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evidencijatroskova.R;
import com.example.evidencijatroskova.model.entities.Mjesec;
import com.google.android.material.textview.MaterialTextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class PrikazMjeseciAdapter extends RecyclerView.Adapter<PrikazMjeseciAdapter.MjesecHolder> {
    private List<Mjesec> mjeseci = new ArrayList<>();
    private PrikazMjeseciAdapter.OnItemClickListener listener;

    @NonNull
    @Override
    public MjesecHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mjesec_holder, parent, false);
        return new MjesecHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MjesecHolder holder, int position) {
        Mjesec currentMjesec = mjeseci.get(position);
        String mjesec = "0" +(currentMjesec.getDatum().getMonth() + 1) + ". " + (1900 + currentMjesec.getDatum().getYear())+ ".";
        holder.tvMjesec.setText(mjesec);
        if( currentMjesec.getPotroseno() != null) {
            DecimalFormat df = new DecimalFormat("0.00");
            String dostupno = df.format(currentMjesec.getIznos() - currentMjesec.getPotroseno()) + " kn";
            String potroseno = df.format(currentMjesec.getPotroseno()) + " kn";
            holder.tvDostupno.setText(dostupno);
            holder.tvPotroseno.setText(potroseno);
        }else{
            DecimalFormat df = new DecimalFormat("0.00");
            String iznos = df.format(currentMjesec.getIznos()) + " kn";
            holder.tvDostupno.setText(iznos);
            holder.tvPotroseno.setText("0 kn");
        }
    }

    @Override
    public int getItemCount() {
        return mjeseci.size();
    }

    public void setMjeseci(List<Mjesec> mjeseci){
        this.mjeseci = mjeseci;
        notifyDataSetChanged();
    }

    class MjesecHolder extends RecyclerView.ViewHolder{
        private MaterialTextView tvMjesec;
        private MaterialTextView tvDostupno;
        private MaterialTextView tvPotroseno;

        public MjesecHolder(@NonNull View itemView) {
            super(itemView);
            tvMjesec = itemView.findViewById(R.id.tvMjesec);
            tvDostupno = itemView.findViewById(R.id.tvDostupno);
            tvPotroseno = itemView.findViewById(R.id.tvPotroseno);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(mjeseci.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Mjesec mjesec);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}

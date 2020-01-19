package com.example.evidencijatroskova.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evidencijatroskova.R;
import com.example.evidencijatroskova.model.entities.Trosak;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PrikazTroskovaAdapter extends ListAdapter<Trosak,PrikazTroskovaAdapter.TrosakHolder> {
    private OnItemClickListener listener;

    public PrikazTroskovaAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Trosak> DIFF_CALLBACK = new DiffUtil.ItemCallback<Trosak>() {
        @Override
        public boolean areItemsTheSame(@NonNull Trosak oldItem, @NonNull Trosak newItem) {
            return oldItem.getIdTroska() == newItem.getIdTroska();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Trosak oldItem, @NonNull Trosak newItem) {
            boolean nazivIsSame = oldItem.getNaziv().equals(newItem.getNaziv());
            boolean iznosIsSame = oldItem.getIznos().equals(newItem.getIznos());
            boolean opisIsSame = oldItem.getOpis().equals(newItem.getOpis());
            return nazivIsSame && iznosIsSame && opisIsSame;
        }
    };

    @NonNull
    @Override
    public TrosakHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View trosakHolder = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trosak_holder, parent, false);
        return new TrosakHolder(trosakHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull TrosakHolder holder, int position) {
        Trosak currentTrosak = getItem(position);
        Date date = currentTrosak.getDatum();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy.");
        String strDate = dateFormat.format(date);
        DecimalFormat df = new DecimalFormat("0.00");
        String result = df.format(currentTrosak.getIznos()) + " kn";
        holder.tvDatum.setText(strDate);
        holder.tvNaziv.setText(currentTrosak.getNaziv());
        holder.tvIznos.setText(result);
        holder.tvOpis.setText(currentTrosak.getOpis());
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Trosak trosak);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}

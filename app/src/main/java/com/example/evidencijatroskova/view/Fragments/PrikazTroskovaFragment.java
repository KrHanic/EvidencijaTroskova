package com.example.evidencijatroskova.view.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evidencijatroskova.R;
import com.example.evidencijatroskova.model.entities.Trosak;
import com.example.evidencijatroskova.view.adapters.PrikazTroskovaAdapter;
import com.example.evidencijatroskova.viewModels.TrosakViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.List;

public class PrikazTroskovaFragment extends Fragment {
    private TrosakViewModel trosakViewModel;

    public static final String NAZIV = "naziv";
    public static final String IZNOS = "iznos";
    public static final String OPIS = "opis";

    private String naziv;
    private double iznos;
    private String opis;
    private Date datum;

    public static PrikazTroskovaFragment newInstance(String naziv, double iznos, String opis){
        PrikazTroskovaFragment fragment = new PrikazTroskovaFragment();
        Bundle trosakBundle = new Bundle();
        trosakBundle.putString(NAZIV, naziv);
        trosakBundle.putDouble(IZNOS, iznos);
        trosakBundle.putString(OPIS, opis);
        fragment.setArguments(trosakBundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if(getArguments() != null){
            naziv = getArguments().getString(NAZIV);
            iznos = getArguments().getDouble(IZNOS);
            opis = getArguments().getString(OPIS);
            datum = new Date();

            Trosak trosak = new Trosak(naziv, iznos, opis, datum);

            trosakViewModel = ViewModelProviders.of(this).get(TrosakViewModel.class);
            trosakViewModel.insert(trosak);
        }

        final View view = inflater.inflate(R.layout.fragment_prikaz_troskova, container, false);

        FloatingActionButton btnAddTrosak = view.findViewById(R.id.btnAddTrosak);
        btnAddTrosak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DodavanjeTroskaFragment nextFrag= new DodavanjeTroskaFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        RecyclerView rvTroskovi = view.findViewById(R.id.rvTroskovi);
        rvTroskovi.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvTroskovi.setHasFixedSize(true);

        final PrikazTroskovaAdapter prikazTroskovaAdapter = new PrikazTroskovaAdapter();
        rvTroskovi.setAdapter(prikazTroskovaAdapter);

        trosakViewModel = ViewModelProviders.of(this).get(TrosakViewModel.class);
        trosakViewModel.getAllTroskovi().observe(this, new Observer<List<Trosak>>() {
            @Override
            public void onChanged(List<Trosak> trosaks) {
                prikazTroskovaAdapter.setTroskovi(trosaks);
            }
        });

        return view;
    }
}

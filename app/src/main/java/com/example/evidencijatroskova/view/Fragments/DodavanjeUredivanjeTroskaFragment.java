package com.example.evidencijatroskova.view.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.evidencijatroskova.R;
import com.example.evidencijatroskova.view.adapters.PrikazTroskovaAdapter;
import com.google.android.material.textfield.TextInputEditText;

import static android.content.ContentValues.TAG;

public class DodavanjeUredivanjeTroskaFragment extends Fragment {
    public static final String ID = "id";
    public static final String NAZIV = "naziv";
    public static final String IZNOS = "iznos";
    public static final String OPIS = "opis";
    private TextInputEditText etNaziv;
    private TextInputEditText etIznos;
    private TextInputEditText etOpis;

    public static DodavanjeUredivanjeTroskaFragment newInstance(int id, String naziv, double iznos, String opis){
        DodavanjeUredivanjeTroskaFragment fragment = new DodavanjeUredivanjeTroskaFragment();
        Bundle trosakBundle = new Bundle();
        trosakBundle.putInt(ID, id);
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
        final View view = inflater.inflate(R.layout.fragment_dodavanje_troska, container, false);

        etNaziv = view.findViewById(R.id.etNaziv);
        etIznos = view.findViewById(R.id.etIznos);
        etOpis = view.findViewById(R.id.etOpis);


        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        if(getArguments() != null) {
            getActivity().setTitle("Uredi Trošak");
            etNaziv.setText(getArguments().getString(NAZIV));
            etIznos.setText(String.valueOf(getArguments().getDouble(IZNOS)));
            etOpis.setText(getArguments().getString(OPIS));
        }else{
            getActivity().setTitle("Dodaj Trošak");
        }

    }

    private void saveTrosak(){

        String naziv = "";
        double iznos = 0;
        String opis = "";

        if(etNaziv.getText().toString().trim().isEmpty()){
            Toast.makeText(getActivity(), "Naziv nije unesen.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(etIznos.getText().toString().trim().isEmpty()){
            Toast.makeText(getActivity(), "Iznos nije unesen.", Toast.LENGTH_SHORT).show();
            return;
        }

        naziv = etNaziv.getText().toString();
        iznos = Double.parseDouble(etIznos.getText().toString());
        if(!etOpis.getText().toString().trim().isEmpty()){
            opis = etOpis.getText().toString();
        }

        PrikazTroskovaFragment fragment = PrikazTroskovaFragment.newInstance(naziv, iznos, opis);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add_trosak, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveTrosak:
                Log.d(TAG, "onOptionsItemSelected: save()");
                saveTrosak();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}

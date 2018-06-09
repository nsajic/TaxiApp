package com.example.nsajic.testapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nsajic.testapp.Models.TaxiSluzba;
import com.example.nsajic.testapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FavouriteServiceAdapter extends ArrayAdapter<TaxiSluzba> {

    private ArrayList<TaxiSluzba> sluzbe;
    private DatabaseReference dataBaseReference;
    private FirebaseAuth firebaseAuth;
    Context mContext;

    private static class ViewHolder {
        TextView nazivSluzbe;
        TextView ocena;
        TextView cenaPoKilometru;
        TextView brojAutomobila;
        TextView brojTelefona;
    }

    public FavouriteServiceAdapter(ArrayList<TaxiSluzba> sluzbe, Context context){
        super(context, R.layout.favourite_services_item, sluzbe);

        this.sluzbe = sluzbe;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if(sluzbe != null){
            return sluzbe.size();
        }else{
            return 0;
        }
    }

    @Nullable
    @Override
    public TaxiSluzba getItem(int position) {
        return sluzbe.get(position);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        dataBaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        try{
            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                vi = inflater.inflate(R.layout.favourite_services_item, null);

                holder = new ViewHolder();

                holder.nazivSluzbe = (TextView) vi.findViewById(R.id.imeOmiljeneSluzbe);
                //holder.brojAutomobila = (TextView) vi.findViewById(R.id.brojAutomobilaView);
                //holder.cenaPoKilometru = (TextView) vi.findViewById(R.id.cenaPoKilometruLabel);

                vi.setTag(holder);
            }else{
                holder = (ViewHolder) vi.getTag();
            }

            //String ocenaStr = sluzbe.get(position).getOcena()+"";
            //String cenaStr = sluzbe.get(position).getCenaPoKilometru()+"";

            holder.nazivSluzbe.setText(sluzbe.get(position).getIme());
           // holder.ocena.setText(ocenaStr);
           // holder.cenaPoKilometru.setText(cenaStr);
            //holder.brojAutomobila.setText(sluzbe.get(position).getBrojAutomobila());

        }catch (Exception e){

        }

        return vi;
    }
}

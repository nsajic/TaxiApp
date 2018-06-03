package com.example.nsajic.testapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.nsajic.testapp.Models.TaxiSluzba;
import com.example.nsajic.testapp.R;

import java.util.ArrayList;

/**
 * Created by nsajic on 4/17/2018.
 */

public class TaxiSluzbaAdapter extends ArrayAdapter<TaxiSluzba>{

    private ArrayList<TaxiSluzba> sluzbe;
    Context mContext;

    private static class ViewHolder{
        TextView nazivSluzbe;
        TextView ocena;
        TextView cenaPoKilometru;
        TextView brojAutomobila;
        TextView brojTelefona;
        CheckBox favouriteChecked;
    }

    public TaxiSluzbaAdapter(ArrayList<TaxiSluzba> sluzbe, Context context){
        super(context, R.layout.sluzbe_item_layout, sluzbe);

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

        try{
            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                vi = inflater.inflate(R.layout.sluzbe_item_layout, null);

                holder = new ViewHolder();

                holder.nazivSluzbe = (TextView) vi.findViewById(R.id.imeSluzbe);
                //holder.ocena = (TextView) vi.findViewById(R.id.);
                holder.brojAutomobila = (TextView) vi.findViewById(R.id.brojAutomobilaView);
                holder.cenaPoKilometru = (TextView) vi.findViewById(R.id.cenaPoKilometruLabel);
                holder.favouriteChecked = (CheckBox) vi.findViewById(R.id.favouriteChecked);
                holder.favouriteChecked.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.print(sluzbe.get(position).getIme());
                    }
                });


                vi.setTag(holder);
            }else{
                vi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.print("aaa");
                    }
                });
                holder = (ViewHolder) vi.getTag();
            }

            String ocenaStr = sluzbe.get(position).getOcena()+"";
            String cenaStr = sluzbe.get(position).getCenaPoKilometru()+"";

            holder.nazivSluzbe.setText(sluzbe.get(position).getIme());
            holder.ocena.setText(ocenaStr);
            holder.cenaPoKilometru.setText(cenaStr);
            holder.brojAutomobila.setText(sluzbe.get(position).getBrojAutomobila());
            //holder.favouriteChecked.setChecked(sluzbe.get(position).getFavouriteChecked());
            /*holder.favouriteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    View v = view;
                }
            });*/

        }catch (Exception e){

        }

        return vi;
    }
}

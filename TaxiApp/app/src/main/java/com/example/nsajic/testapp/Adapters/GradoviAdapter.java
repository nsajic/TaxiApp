package com.example.nsajic.testapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nsajic.testapp.Models.Grad;
import com.example.nsajic.testapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nsajic on 4/16/2018.
 */

public class GradoviAdapter extends ArrayAdapter<Grad>{

    private ArrayList<Grad> gradovi;
    Context mContext;

    private static class ViewHolder{
        TextView imeGrada;
        TextView postanskiBroj;
    }

    public GradoviAdapter(ArrayList<Grad> gradovi, Context context){
        super(context, R.layout.gradovi_listview, gradovi);

        this.gradovi = gradovi;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if(gradovi != null){
            return gradovi.size();
        }else{
            return 0;
        }
    }

    @Nullable
    @Override
    public Grad getItem(int position) {
        return gradovi.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;

        try{
            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                vi = inflater.inflate(R.layout.gradovi_listview, null);

                holder = new ViewHolder();

                holder.imeGrada = (TextView) vi.findViewById(R.id.imeGrada);
                holder.postanskiBroj = (TextView) vi.findViewById(R.id.postanskiBroj);

                vi.setTag(holder);
            }else{
                holder = (ViewHolder) vi.getTag();
            }

            holder.imeGrada.setText(gradovi.get(position).getIme());
            holder.postanskiBroj.setText(gradovi.get(position).getPostanskiBroj());
        }catch(Exception e){

        }

        return vi;
    }
}

package com.example.nsajic.testapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.nsajic.testapp.Models.TaxiSluzba;
import com.example.nsajic.testapp.Models.UserRecension;
import com.example.nsajic.testapp.R;

import java.util.ArrayList;

public class RecensionsAdapter extends ArrayAdapter<UserRecension> {

    private ArrayList<UserRecension> recensions;
    Context mContext;

    private static class ViewHolder{
        TextView userEmail;
        TextView userRecension;
    }

    public RecensionsAdapter(ArrayList<UserRecension> recensions, Context context){
        super(context, R.layout.sluzbe_item_layout, recensions);

        this.recensions = recensions;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if(recensions != null){
            return recensions.size();
        }else{
            return 0;
        }
    }

    @Nullable
    @Override
    public UserRecension getItem(int position) {
        return recensions.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;

        try{
            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                vi = inflater.inflate(R.layout.recensions_item_layout, null);

                holder = new ViewHolder();

                holder.userEmail = (TextView) vi.findViewById(R.id.userEmail);
                holder.userRecension = (TextView) vi.findViewById(R.id.userRecension);

                vi.setTag(holder);
            }else{
                holder = (ViewHolder) vi.getTag();
            }

            holder.userEmail.setText(recensions.get(position).getUserEmail());
            holder.userRecension.setText(recensions.get(position).getContent());

        }catch (Exception e){

        }

        return vi;
    }
}

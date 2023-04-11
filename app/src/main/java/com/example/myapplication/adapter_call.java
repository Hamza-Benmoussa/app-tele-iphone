package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class adapter_call extends ArrayAdapter<appel> {
        private final Context mContext;
        private final int mResource;
        private final ArrayList<appel> listx;

        public adapter_call(Context mcontext, int resource, ArrayList<appel> list) {
                super(mcontext, resource, list);
                mContext = mcontext;
                mResource = resource;
                this.listx = list;

        }

                @SuppressLint("SetTextI18n")
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                        appel p = listx.get(position);
                        @SuppressLint("ViewHolder") View z = LayoutInflater.from(mContext).inflate(mResource, parent, false);
                        TextView nom = z.findViewById(R.id.user);
                        TextView date = z.findViewById(R.id.time);
if(p.getV() == 0){
    nom.setText((CharSequence) p.getNumber());

}else{

    nom.setText((CharSequence)p.getNom().toString() + " " + (CharSequence)p.getPrenom().toString());
}


                        date.setText(p.getDatex());

                        return z;
                }
        }





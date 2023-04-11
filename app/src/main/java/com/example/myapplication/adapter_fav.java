package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.myapplication.Favories;
import com.example.myapplication.R;
import com.example.myapplication.favor;

import basedonnées.bdd2;


import java.util.ArrayList;

public class adapter_fav extends ArrayAdapter<favor> {

    private final Context mContext;
    private final int mResource;
    private final ArrayList<favor> mFavList;

    public adapter_fav(Context context, int resource, ArrayList<favor> favList) {
        super(context, resource, favList);
        mContext = context;
        mResource = resource;
        mFavList = favList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        favor x = mFavList.get(position);

        View y = LayoutInflater.from(mContext).inflate(R.layout.list_fav, parent, false);
        TextView textv = y.findViewById(R.id.textView17);
        textv.setText(x.getNomf() + " " + x.getPrenomf());

        ImageView supp = y.findViewById(R.id.imageView4);
        supp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bdd2 db = new bdd2(mContext);
                db.deletefav(x.getIdf());
                Favories activity = (Favories) mContext;
                activity.onResume();
            }
        });

        return y;
    }
}

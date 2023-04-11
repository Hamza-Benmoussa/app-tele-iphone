package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class adapter extends ArrayAdapter<listcontact> {
    private Context mContext;
    private int mResource;
    private ArrayList<listcontact> mListContact;

    public adapter(@NonNull Context context, int resource, @NonNull ArrayList<listcontact> listContact) {
        super(context, resource, listContact);
        mContext = context;
        mResource = resource;
        mListContact = listContact;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        listcontact contact = mListContact.get(position);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView nom = convertView.findViewById(R.id.textView3);
        nom.setText((contact.nom + "  " + contact.prenom).trim());

        return convertView;
    }
}

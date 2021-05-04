package com.example.firebaserealtime_1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class listview_biodata extends ArrayAdapter {
    private Activity context;
    List<Biodata> list_biodata;
    public listview_biodata(Activity context,List<Biodata> biodataArray) {
        super(context, R.layout.layout_listview, biodataArray);
        this.context = context;
        this.list_biodata = biodataArray;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_listview,null,true);
            TextView textViewNama = listViewItem.findViewById(R.id.textViewNama);
            TextView textViewUmur = listViewItem.findViewById(R.id.textViewUmur);
            TextView textViewJK = listViewItem.findViewById(R.id.textViewJK);
            Biodata biodata = list_biodata.get(position);
            textViewNama.setText(biodata.getBiodata_Nama());
            textViewUmur.setText(biodata.getBiodata_Umur());
            textViewJK.setText(biodata.getBiodata_JK());
        return listViewItem;
    }
}

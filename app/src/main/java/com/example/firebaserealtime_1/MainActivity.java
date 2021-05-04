package com.example.firebaserealtime_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editText_nama,editText_umur;
    private Spinner spinner_jk;
    private DatabaseReference databaseBiodata;
    private ListView listViewBio;
    private List<Biodata> listBiodata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText_nama = findViewById(R.id.editTextNama);
        editText_umur = findViewById(R.id.editTextUmur);
        spinner_jk = findViewById(R.id.spinnerJK);
        databaseBiodata = FirebaseDatabase.getInstance().getReference("biodata");
        listViewBio = findViewById(R.id.listView_bio);
        listBiodata = new ArrayList<>();
        listViewBio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Biodata biodata = listBiodata.get(position);
                String biodataId = biodata.getBiodata_Id();
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                intent.putExtra("BiodataID",biodataId);
                startActivity(intent);
            }
        });

    }
    public void addBiodata(View view){
        String nama = editText_nama.getText().toString();
        String umur = editText_umur.getText().toString();
        String jk = spinner_jk.getSelectedItem().toString();

        if (!TextUtils.isEmpty(nama)&& !TextUtils.isEmpty(umur) && !TextUtils.isEmpty(jk)){
            String id = databaseBiodata.push().getKey();
            Biodata biodata = new Biodata(id, nama, umur, jk);
            databaseBiodata.child(id).setValue(biodata)
                    .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            editText_nama.setText(" ");
                            editText_umur.setText(" ");
                            spinner_jk.setSelection(0);
                            Toast.makeText(MainActivity.this,"Biodata berhasil ditambahkan", Toast.LENGTH_LONG).show();
                        }
                    });
        }else {
            Toast.makeText(this, "semua box harus diisi", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseBiodata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listBiodata.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    Biodata biodata = postSnapshot.getValue(Biodata.class);
                    listBiodata.add(biodata);
                }
                listview_biodata biodataList_adapter = new listview_biodata(MainActivity.this, listBiodata);
                listViewBio.setAdapter(biodataList_adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
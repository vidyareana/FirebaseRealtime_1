package com.example.firebaserealtime_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity2 extends AppCompatActivity {
    private EditText editText_nama, editText_umur;
    private Spinner spinner_jk;
    private DatabaseReference databaseBiodata;
    private String biodataId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        biodataId = getIntent().getStringExtra("BiodataID");
        editText_nama = findViewById(R.id.editText_updateNama);
        editText_umur = findViewById(R.id.editText_updateUmur);
        spinner_jk = findViewById(R.id.spinner_updateJk);
        databaseBiodata = FirebaseDatabase.getInstance().getReference("biodata").child(biodataId);

    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseBiodata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Biodata biodata = snapshot.getValue(Biodata.class);
                if (biodata != null){
                    String nama = biodata.getBiodata_Nama();
                    String umur = biodata.getBiodata_Umur();
                    String jk = biodata.getBiodata_JK();
                    editText_nama.setText(nama);
                    editText_umur.setText(umur);
                    if (jk.equals("Pria")){
                        spinner_jk.setSelection(0);
                    }
                    else{
                        spinner_jk.setSelection(1);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void buttonupdateBiodata(View view){
        String nama = editText_nama.getText().toString();
        String umur = editText_umur.getText().toString();
        String jk = spinner_jk.getSelectedItem().toString();

        if (!TextUtils.isEmpty(nama)&& !TextUtils.isEmpty(umur) && !TextUtils.isEmpty(jk)){
            Biodata biodata = new Biodata(biodataId, nama, umur, jk);
            databaseBiodata.setValue(biodata)
                    .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(MainActivity2.this,"Biodata updated", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
        }else {
            Toast.makeText(this, "semua box harus diisi", Toast.LENGTH_LONG).show();
        }
    }
    public void buttondeleteBiodata(View view){
        databaseBiodata.removeValue().addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(MainActivity2.this,"Biodata Deleted", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
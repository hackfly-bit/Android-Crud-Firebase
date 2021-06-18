package com.modul9.crudfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab_add;
    RecyclerAdapter recyclerAdapter;
    DatabaseReference database = FirebaseDatabase.getInstance("https://crud-firebase-9b4f9-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
    ArrayList<Mahasiswa> listMahasiswa;
    RecyclerView rv_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab_add = findViewById(R.id.fb_add);
        rv_view = findViewById(R.id.rv_view);

        RecyclerView.LayoutManager mLayout = new LinearLayoutManager(this);
        rv_view.setLayoutManager(mLayout);
        rv_view.setItemAnimator(new DefaultItemAnimator());

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogForm dialogForm = new DialogForm("","", "","", "", "Tambah");
                dialogForm.show(getSupportFragmentManager(), "Form");
            }
        });

        showData();
    }

    private void showData(){
        database.child("Data1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listMahasiswa = new ArrayList<>();
                for(DataSnapshot item : dataSnapshot.getChildren()){
                    Mahasiswa mhs = item.getValue(Mahasiswa.class);
                    mhs.setKey(item.getKey());
                    listMahasiswa.add(mhs);


                }

                recyclerAdapter = new RecyclerAdapter(listMahasiswa, MainActivity.this);
                rv_view.setAdapter(recyclerAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
package com.bhawani.bhawanitraders;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Edit extends AppCompatActivity {
    //DatabaseReference ref;
    AdapterEdit adapter;
    RecyclerView editview;
    EditText searching;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editview = findViewById(R.id.editrecycleview);
        searching= findViewById(R.id.editmasearch);

        editview.setLayoutManager(new LinearLayoutManager(this));

        final FirebaseRecyclerOptions<EditModel> options= new FirebaseRecyclerOptions.Builder<EditModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Details"),EditModel.class)
                .build();
        adapter=new AdapterEdit(options,this);
        editview.setAdapter(adapter);

        //------------------for searching-----------------------------
        searching.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String dataforsearch=searching.getText().toString();
                Searching(dataforsearch);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //------------------for searhing ends---------------------------
    }

    private void Searching(String data) {
        final FirebaseRecyclerOptions<EditModel> options= new FirebaseRecyclerOptions.Builder<EditModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Details").orderByChild("Item").startAt(data).endAt(data+"\uf8ff"),EditModel.class)
                .build();
        adapter=new AdapterEdit(options,this);
        adapter.startListening();
        editview.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
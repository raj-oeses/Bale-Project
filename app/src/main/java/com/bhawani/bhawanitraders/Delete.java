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

public class Delete extends AppCompatActivity{

    AdapterDelete adapter;
    EditText deletemasearch;
    RecyclerView deletemarecycleview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        deletemasearch=findViewById(R.id.deletemasearch);
        deletemarecycleview=findViewById(R.id.deleterecycleview);

        deletemarecycleview.setLayoutManager( new LinearLayoutManager(this));

        final FirebaseRecyclerOptions<DeleteModel> options= new FirebaseRecyclerOptions.Builder<DeleteModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Details"),DeleteModel.class)
                .build();
        adapter=new AdapterDelete(options,this);
        deletemarecycleview.setAdapter(adapter);

        deletemasearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String dataforsearch=deletemasearch.getText().toString();
                Searching(dataforsearch);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    private void Searching(String data) {
        final FirebaseRecyclerOptions<DeleteModel> options= new FirebaseRecyclerOptions.Builder<DeleteModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Details").orderByChild("Item").startAt(data).endAt(data+"\uf8ff"),DeleteModel.class)
                .build();
        adapter= new AdapterDelete(options,this);
        adapter.startListening();
        deletemarecycleview.setAdapter(adapter);
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
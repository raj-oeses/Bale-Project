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

public class Search extends AppCompatActivity {
    RecyclerView showlist;
    AdapterSearch adapter;
    EditText searchinginsearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycleview_for_search);

        showlist=findViewById(R.id.recycle_list);
        showlist.setLayoutManager(new LinearLayoutManager(this));
        searchinginsearch=findViewById(R.id.searchinginsearch);

        FirebaseRecyclerOptions<SearchModel> options =
                new FirebaseRecyclerOptions.Builder<SearchModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Details"), SearchModel.class)
                        .build();

        adapter=new AdapterSearch(options);
        showlist.setAdapter(adapter);
        searchinginsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String textSearching=searchinginsearch.getText().toString();
                Searching(textSearching);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
    private void Searching(String data) {
        FirebaseRecyclerOptions<SearchModel> options =
                new FirebaseRecyclerOptions.Builder<SearchModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference()
                                .child("Details").orderByChild("Item").startAt(data).endAt(data+"\uf8ff"), SearchModel.class)
                        .build();
        adapter=new AdapterSearch(options);
        adapter.startListening();
        showlist.setAdapter(adapter);

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
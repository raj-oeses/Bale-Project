package com.bhawani.bhawanitraders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AdapterSearch extends FirebaseRecyclerAdapter<SearchModel, AdapterSearch.RecycleViewHolder>  {

    public AdapterSearch(@NonNull FirebaseRecyclerOptions<SearchModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RecycleViewHolder holder, int position, @NonNull SearchModel model) {
        holder.item.setText(model.getItem());
        holder.cpperpiece.setText(model.getCPPerPiece());
        holder.spperpiece.setText(model.getSPPerPiece());
        holder.sppercarton.setText(model.getSPPerCarton());
        holder.barcode.setText(model.getBarCode());

    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_list_view,parent,false);
        return new RecycleViewHolder(view);
    }

    class RecycleViewHolder extends RecyclerView.ViewHolder{

        TextView item,cpperpiece,spperpiece,sppercarton,barcode;

        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            item=itemView.findViewById(R.id.item);
            cpperpiece=itemView.findViewById(R.id.cpperpiece);
            spperpiece=itemView.findViewById(R.id.spperpiece);
            sppercarton=itemView.findViewById(R.id.sppercarton);
            barcode=itemView.findViewById(R.id.barcode);
        }
    }

}

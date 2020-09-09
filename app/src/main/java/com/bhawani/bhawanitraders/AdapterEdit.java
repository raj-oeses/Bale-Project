package com.bhawani.bhawanitraders;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.zxing.integration.android.IntentIntegrator;
import com.squareup.picasso.Picasso;

public class AdapterEdit extends FirebaseRecyclerAdapter<EditModel,AdapterEdit.EditViewHolder> {
    Context context;
    public AdapterEdit(@NonNull FirebaseRecyclerOptions<EditModel> options,Context context) {
        super(options);
        this.context=context;

    }
    @Override
    protected void onBindViewHolder(@NonNull final EditViewHolder holder, final int position, @NonNull final EditModel model) {
        holder.item.setText(model.getItem());
        holder.spperpiece.setText(model.getSPPerPiece());
        holder.sppercarton.setText(model.getSPPerCarton());
        Picasso.get().load(model.getImageUrl()).into(holder.goodsimgae);
        /*========================================editing the details=======================*/

        holder.editmaedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent EditingTheDetail=new Intent(context,EditingActivity.class);
                EditingTheDetail.putExtra("key",getRef(position).getKey());
                EditingTheDetail.putExtra("ImageUrl",model.getImageUrl());
                context.startActivity(EditingTheDetail);
            }
        });
    }
    @NonNull
    @Override
    public EditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_recycle_view,parent,false);
        return new EditViewHolder(view);
    }
    public class EditViewHolder extends RecyclerView.ViewHolder {
        TextView item,cpperpiece,spperpiece,sppercarton,description,barcode;
        ImageView goodsimgae,editmaedit;

        public EditViewHolder(@NonNull View itemView) {
            super(itemView);
            item=itemView.findViewById(R.id.edittitle);
            spperpiece=itemView.findViewById(R.id.editspperpiece);
            sppercarton=itemView.findViewById(R.id.editsppercarton);
            goodsimgae=itemView.findViewById(R.id.goodsimgae);
            editmaedit=itemView.findViewById(R.id.editedit);

        }
    }
}

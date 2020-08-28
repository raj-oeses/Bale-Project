package com.bhawani.bhawanitraders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AdapterEdit extends FirebaseRecyclerAdapter<EditModel,AdapterEdit.EditViewHolder> {
    public AdapterEdit(@NonNull FirebaseRecyclerOptions<EditModel> options) {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull EditViewHolder holder, int position, @NonNull EditModel model) {
        holder.item.setText(model.getItem());
        holder.spperpiece.setText(model.getSPPerPiece());
        holder.sppercarton.setText(model.getSPPerCarton());
    }
    @NonNull
    @Override
    public EditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_recycle_view,parent,false);
        return new EditViewHolder(view);
    }
    public class EditViewHolder extends RecyclerView.ViewHolder {
        TextView item,cpperpiece,spperpiece,sppercarton,description,barcode;
        public EditViewHolder(@NonNull View itemView) {
            super(itemView);
            item=itemView.findViewById(R.id.edittitle);
            spperpiece=itemView.findViewById(R.id.editspperpiece);
            sppercarton=itemView.findViewById(R.id.editsppercarton);
        }
    }
}

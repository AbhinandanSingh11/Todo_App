package com.abhinandan.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class AdapterRecylerView extends RecyclerView.Adapter<AdapterRecylerView.ViewHolder> {
    private ArrayList<TODO> list = new ArrayList<>();
    private Context context;

    public AdapterRecylerView(ArrayList<TODO> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_todo,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        if(list.size()>0){

            MainActivity.text.setVisibility(View.GONE);
            if(list.get(position).isDone()){

                //holder.edit.setText("Completed");
                //holder.done.setText("Undo");
                holder.done.setText("Completed");
            }
            else{

                //holder.edit.setText("Yet to be Completed");
                holder.done.setText("Done");
            }

            if(list.get(position).isHighlighted()){

                if(!holder.item.isChecked()){
                    holder.item.setChecked(true);
                }
            }

            holder.item.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    if(!list.get(position).isHighlighted()){
                        list.get(position).setHighlighted(true);
                        Toast.makeText(context, "Marked", Toast.LENGTH_SHORT).show();
                        holder.item.setChecked(true);
                    }

                    else{
                        list.get(position).setHighlighted(false);
                        Toast.makeText(context, "un-Marked", Toast.LENGTH_SHORT).show();
                        holder.item.setChecked(false);
                    }
                    return false;
                }
            });


            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeCard(position);
                    Toast.makeText(context, "Removed", Toast.LENGTH_SHORT).show();
                }
            });


            holder.done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //removeCard(position);
                    if(!list.get(position).isDone()){
                        holder.done.setText("Completed");
                        //holder.edit.setVisibility(View.VISIBLE);
                        //holder.edit.setText("Completed");
                        list.get(position).setDone(true);
                    }

                    else{
                        holder.done.setText("done");
                        //holder.edit.setText("Yet to be Completed");
                        list.get(position).setDone(false);
                    }
                }
            });


            if(list.get(position).getTitle() == null){
                holder.title.setVisibility(View.GONE);
            }
            else{
                holder.title.setText(list.get(position).getTitle());
            }

            if(list.get(position).getSecondaryText() == null){
                holder.secondary.setVisibility(View.GONE);
            }
            else{
                holder.secondary.setText(list.get(position).getSecondaryText());
            }

            holder.primary.setText(list.get(position).getPrimaryText());
        }

    }

    @Override
    public int getItemCount() {

        if(list.size()>0){
            return list.size();
        }

        else{
            return -1;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private MaterialCardView item;
        private TextView title,secondary,primary;
        private MaterialButton done,delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item =  itemView.findViewById(R.id.item_card);
            title = itemView.findViewById(R.id.item_title);
            secondary = itemView.findViewById(R.id.item_secondarytext);
            primary = itemView.findViewById(R.id.item_primarytext);
            done = itemView.findViewById(R.id.item_done);
            delete = itemView.findViewById(R.id.item_delete);
            //edit = itemView.findViewById(R.id.item_edit);
        }
    }

    public void removeCard(int position){
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemChanged(position);


        if(list.isEmpty()){
            MainActivity.text.setVisibility(View.VISIBLE);
        }
    }
}

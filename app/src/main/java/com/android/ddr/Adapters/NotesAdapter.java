package com.android.ddr.Adapters;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.ddr.Models.Notesmodel;
import com.android.ddr.R;
import com.android.ddr.SingleNote;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyviewHolder> {

    Context context;
    List<Notesmodel> notesmodels;

    public NotesAdapter(Context context,List<Notesmodel> notesmodels) {
        this.notesmodels = notesmodels;
        this.context = context;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.homecardview, viewGroup,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder myviewHolder, int i) {
        myviewHolder.title.setText(notesmodels.get(i).getTitle());
        myviewHolder.sem.setText(notesmodels.get(i).getSemester());
        myviewHolder.sub.setText(notesmodels.get(i).getSubject());
        myviewHolder.desc.setText(notesmodels.get(i).getDescription());
        final int position = i;
        myviewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("title",notesmodels.get(position).getTitle());
                bundle.putString("sem",notesmodels.get(position).getSemester());
                bundle.putString("sub",notesmodels.get(position).getSubject());
                bundle.putString("desc",notesmodels.get(position).getDescription());
                bundle.putString("name",notesmodels.get(position).getName());
                Intent intent = new Intent(context, SingleNote.class);
                intent.putExtra("note",bundle);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return notesmodels.size();
    }

    public static class MyviewHolder extends RecyclerView.ViewHolder{

        TextView title,sem,sub,desc;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txttitle);
            sem = itemView.findViewById(R.id.txtsem);
            sub= itemView.findViewById(R.id.txtsubject);
            desc = itemView.findViewById(R.id.txtdesc);
        }
    }

}

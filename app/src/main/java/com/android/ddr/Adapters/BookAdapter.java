package com.android.ddr.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.ddr.Models.BookModel;
import com.android.ddr.R;
import com.android.ddr.SingleNote;
import com.android.ddr.Single_book;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyviewHolder> {

    Context context;
    List<BookModel> Bookmodel;

    public BookAdapter(Context context, List<BookModel> bookmodel) {
        this.context = context;
        Bookmodel = bookmodel;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.ebook_cardview, viewGroup,false);
        return new BookAdapter.MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder myviewHolder, int i) {
        myviewHolder.title.setText(Bookmodel.get(i).getTitle());
        myviewHolder.sem.setText(Bookmodel.get(i).getSemester());
        myviewHolder.sub.setText(Bookmodel.get(i).getSubject());
        myviewHolder.desc.setText(Bookmodel.get(i).getDescription());

        final int position = i;
        myviewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("title",Bookmodel.get(position).getTitle());
                bundle.putString("sem",Bookmodel.get(position).getSemester());
                bundle.putString("sub",Bookmodel.get(position).getSubject());
                bundle.putString("desc",Bookmodel.get(position).getDescription());
                bundle.putString("pdf",Bookmodel.get(position).getPdf());
                bundle.putString("aut",Bookmodel.get(position).getAuthor());
                Intent intent = new Intent(context, Single_book.class);
                intent.putExtra("book",bundle);
                context.startActivity(intent);

            }
        });
        
    }

    @Override
    public int getItemCount() {
        return Bookmodel.size();
    }


    public static class MyviewHolder extends RecyclerView.ViewHolder{

        TextView title,sem,sub,desc;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.booktxttitle);
            sem = itemView.findViewById(R.id.booktxtsem);
            sub= itemView.findViewById(R.id.booktxtsubject);
            desc = itemView.findViewById(R.id.booktxtdesc);
        }
    }
}

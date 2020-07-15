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
import android.widget.Toast;

import com.android.ddr.Models.ResourceModel;
import com.android.ddr.R;
import com.android.ddr.SingleNote;
import com.android.ddr.Single_Resource;

import java.util.List;

public class ResourceAdapter extends RecyclerView.Adapter<ResourceAdapter.MyviewHolder> {

    Context context;
    List<ResourceModel> resourceModels;

    public ResourceAdapter(Context context, List<ResourceModel> resourceModels) {
        this.context = context;
        this.resourceModels = resourceModels;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.resource_cardview, viewGroup,false);
        return new ResourceAdapter.MyviewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder myviewHolder, int i) {
        myviewHolder.title.setText(resourceModels.get(i).getTitle());
        myviewHolder.sem.setText(resourceModels.get(i).getSemester());
        myviewHolder.sub.setText(resourceModels.get(i).getSubject());
        myviewHolder.desc.setText(resourceModels.get(i).getDescription());
        final int position = i;
        myviewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("title",resourceModels.get(position).getTitle());
                bundle.putString("sem",resourceModels.get(position).getSemester());
                bundle.putString("sub",resourceModels.get(position).getSubject());
                bundle.putString("desc",resourceModels.get(position).getDescription());
                bundle.putString("feat",resourceModels.get(position).getFeature());
                bundle.putString("name",resourceModels.get(position).getName());
               // Toast.makeText(context,resourceModels.get(position).getFeature(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, Single_Resource.class);
                intent.putExtra("res",bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return resourceModels.size();
    }


    public static class MyviewHolder extends RecyclerView.ViewHolder{

        TextView title,sem,sub,desc;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.restxttitle);
            sem = itemView.findViewById(R.id.restxtsem);
            sub= itemView.findViewById(R.id.restxtsubject);
            desc = itemView.findViewById(R.id.restxtdesc);
        }
    }
}

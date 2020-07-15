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
import com.android.ddr.Models.Projectmodel;
import com.android.ddr.R;
import com.android.ddr.SingleNote;
import com.android.ddr.Single_Project;

import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.MyviewHolder> {

    Context context;
    List<Projectmodel> projectmodels;

    public ProjectAdapter(Context context,List<Projectmodel> projectmodel) {
        this.projectmodels = projectmodel;
        this.context = context;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.project_cardview, viewGroup,false);
        return new ProjectAdapter.MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder myviewHolder, int i) {
        myviewHolder.title.setText(projectmodels.get(i).getTitle());
        myviewHolder.sem.setText(projectmodels.get(i).getProjectSem());
        myviewHolder.tech.setText(projectmodels.get(i).getTechnology());
        myviewHolder.desc.setText(projectmodels.get(i).getDescription());
        final int position = i;
        myviewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("title",projectmodels.get(position).getTitle());
                bundle.putString("tech",projectmodels.get(position).getTechnology());
                bundle.putString("sem",projectmodels.get(position).getProjectSem());
                bundle.putString("desc",projectmodels.get(position).getDescription());
                bundle.putString("type",projectmodels.get(position).getProject_type());
                bundle.putString("name",projectmodels.get(position).getName());
                Intent intent = new Intent(context, Single_Project.class);
                intent.putExtra("project",bundle);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return projectmodels.size();
    }

    public static class MyviewHolder extends RecyclerView.ViewHolder{

        TextView title,sem,tech,desc;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.protxttitle);
            sem = itemView.findViewById(R.id.protxtsem);
            tech= itemView.findViewById(R.id.protxttech);
            desc = itemView.findViewById(R.id.protxtdesc);
        }
    }
}

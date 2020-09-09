package com.example.fpprogresstrack;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fpprogresstrack.db.Project;


import org.litepal.crud.DataSupport;

import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {
    private List<Project> mProjectList;


    public ProjectAdapter(List<Project> projectList){
        mProjectList=projectList;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvState;
        TextView tvDetail;
        TextView tvPercent;
        TextView tvLeader;
        TextView tvName;
        public ViewHolder(View itemView) {
            super(itemView);
            tvState=itemView.findViewById(R.id.text_state);
            tvDetail=itemView.findViewById(R.id.text_detail);
            tvPercent=itemView.findViewById(R.id.text_percent);
            tvName=itemView.findViewById(R.id.text_name);
            tvLeader=itemView.findViewById(R.id.text_leader);
        }
    }

    //创建时，实例化holder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //mContext=parent.getContext();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=holder.getAdapterPosition();
                Project project=mProjectList.get(position);
                Intent intent=new Intent(mContext,ProjectEditActivity.class);
                intent.putExtra("project",project);
                mContext.startActivity(intent);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int position=holder.getAdapterPosition();
                final Project project=mProjectList.get(position);
                //Toast.makeText(mContext,project.getName(),Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                builder.setTitle("确认删除");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DataSupport.delete(Project.class,project.getId());
                    }
                }).show();
                Toast.makeText(mContext,"删除成功",Toast.LENGTH_SHORT).show();
                return true;
            }
        });*/
        return holder;
    }

    public interface onItemClickListener{
        void onClick(int position);
    }

    private onItemClickListener listener;

    public void setOnItemClickListener(onItemClickListener listener){
        this.listener=listener;
    }


    public interface onLongItemClickListener{
        void onLongClick(int position);
    }

    private onLongItemClickListener longItemClickListener;

    public void setOnLongItemClickListener(onLongItemClickListener onLongItemClickListener){
        this.longItemClickListener=onLongItemClickListener;
    }

    //设置显示的内容
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Project project=mProjectList.get(position);
        holder.tvState.setText(project.getState());
        holder.tvDetail.setText(project.getDetail());
        holder.tvPercent.setText("0%");
        holder.tvLeader.setText(project.getLeader());
        holder.tvName.setText(project.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                longItemClickListener.onLongClick(position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProjectList.size();
    }


}

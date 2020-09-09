package com.example.fpprogresstrack;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.fpprogresstrack.db.Project;
import com.example.fpprogresstrack.utils.Utility;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Project> projectList;
    ProjectAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // Log.d("main","created");
        LitePal.initialize(this);
        projectList = new ArrayList<>();
        Utility.getProjectList(projectList);
        //Log.d("main",String.valueOf(projectList.size()));
        if (projectList != null) {
            recyclerView = findViewById(R.id.recycle_project);
            manager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(manager);
            adapter = new ProjectAdapter(projectList);
            adapter.setOnItemClickListener(new ProjectAdapter.onItemClickListener(){
                @Override
                public void onClick(int position) {
                    Project project=projectList.get(position);
                    Intent intent=new Intent(MainActivity.this,ProjectEditActivity.class);
                    intent.putExtra("project",project);
                    startActivity(intent);
                }
            });
            adapter.setOnLongItemClickListener(new ProjectAdapter.onLongItemClickListener() {
                @Override
                public void onLongClick(int position) {
                    final Project project=projectList.get(position);
                    AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("确认删除");
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DataSupport.delete(Project.class,project.getId());
                            reload();
                            Toast.makeText(MainActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                        }
                    }).show();
                }
            });


            recyclerView.setAdapter(adapter);
        }
        Button btnAdd=findViewById(R.id.button_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ProjectEditActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        reload();
        super.onResume();
    }

    private void reload(){
        Log.d("main","reloaded");
        Utility.getProjectList(projectList);
        adapter.notifyDataSetChanged();
    }


}
package com.example.fpprogresstrack;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.fpprogresstrack.db.Project;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ProjectEditActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private Project project;
    private String editType;
    String[] stateItems=new String[]{"进行中","已完结"};
    Calendar calendar=Calendar.getInstance();

    EditText edtProjectName;
    EditText edtProjectLeader;
    EditText edtProjectDetail;
    EditText edtProjectTime;
    //EditText edtProjectState;
    Spinner spProjectState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_edit);
        edtProjectName=findViewById(R.id.edit_project_name);
        edtProjectLeader=findViewById(R.id.edit_project_leader);
        edtProjectDetail=findViewById(R.id.edit_project_detail);
        edtProjectTime=findViewById(R.id.edit_project_time);
        //edtProjectState=findViewById(R.id.edit_project_state);
        spProjectState=findViewById(R.id.spinner_project_state);


        ArrayAdapter<String> stateAdapter=new ArrayAdapter<String>(ProjectEditActivity.this,android.R.layout.simple_spinner_dropdown_item,stateItems);
        spProjectState.setAdapter(stateAdapter);

        Intent intent=getIntent();
        project=(Project) intent.getSerializableExtra("project");
        editType=project==null?"ADD":"EDIT";
        if(project!=null){
            getProject(project);
        } else {
            project=new Project();
        }
        Button btnSubmit=findViewById(R.id.button_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result=false;
                setProject(project);
                if(editType.equals("ADD")){
                    result=project.save();
                } else if (editType.equals("EDIT")){
                    result=project.update(project.getId())==1;
                }
                if(result){
                    finish();
                }
            }
        });

        Button btnDatePicker=findViewById(R.id.button_date_picker);
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(ProjectEditActivity.this,ProjectEditActivity.this,
                                                                        calendar.get(calendar.YEAR),calendar.get(calendar.MONTH),calendar.get(calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });

    }

    private void setProject(Project curProject) {
        curProject.setName(edtProjectName.getText().toString());
        curProject.setLeader(edtProjectLeader.getText().toString());
        curProject.setDetail(edtProjectDetail.getText().toString());
        curProject.setEndTime(edtProjectTime.getText().toString());
        //curProject.setState(edtProjectState.getText().toString());
        curProject.setState(spProjectState.getSelectedItem().toString());
    }

    private void getProject(Project curProject){
        edtProjectName.setText(curProject.getName());
        edtProjectLeader.setText(curProject.getLeader());
        edtProjectDetail.setText(curProject.getDetail());
        try {
            String endTime=curProject.getEndTime();
            calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(endTime));
            edtProjectTime.setText(endTime);
        } catch (ParseException e){
            e.printStackTrace();
        }


        //edtProjectState.setText(curProject.getState());
        spProjectState.setSelection(curProject.getState().equals("进行中")?0:1,true);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        calendar.set(i, i1 , i2);
        SimpleDateFormat targetDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = targetDateFormat.format(calendar.getTime());
        edtProjectTime.setText(dateString);
    }
}
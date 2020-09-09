package com.example.fpprogresstrack.utils;

import com.example.fpprogresstrack.db.Project;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class Utility {
    public static void getProjectList(List<Project> projectList) {
        projectList.clear();
        List<Project> projects= DataSupport.findAll(Project.class);
        for(int i=0;i<projects.size();i++){
            projectList.add(projects.get(i));
        }
    }
}

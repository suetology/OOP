package com.lab4.University;

import java.util.ArrayList;

public class Course {
    private static Course instance;
    public static Course getInstance() {
        if (instance == null)
            instance = new Course();
        return instance;
    }

    private ArrayList<Group> groups = new ArrayList<>();

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void addGroup() {
        int id = groups.size() + 1;
        Group group = new Group(id);
        groups.add(group);
    }
}

package com.lab4.University;

import java.util.ArrayList;

public class Group {
    private int number;
    private ArrayList<Student> students;

    public Group(int n) {
        number = n;
        students = new ArrayList<>();
    }

    public int getNumber() {
        return number;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public int getStudentCount() {
        return students.size();
    }

    public void addStudent(Student student) {
        students.add(student);
    }
}

package com.lab4.University;

import com.lab4.Util.Printable;
import com.lab4.Util.Readable;
import javafx.scene.control.CheckBox;

import java.io.PrintWriter;

public class Student implements Printable, Readable {
    private String name;
    private String surname;
    private int groupNumber;
    private CheckBox[] attendance;

    public Student(String name, String surname, int groupNumber) {
        this.name = name;
        this.surname = surname;
        this.groupNumber = groupNumber;
        this.attendance = new CheckBox[Lectures.COUNT];
        for (int i = 0; i < Lectures.COUNT; i++) {
            CheckBox cb = new CheckBox();
            cb.setIndeterminate(false);
            attendance[i] = cb;
        }
    }

    public Student(String name, String surname, int groupNumber, boolean[] attendance) {
        this.name = name;
        this.surname = surname;
        this.groupNumber = groupNumber;
        this.attendance = new CheckBox[Lectures.COUNT];
        for (int i = 0; i < Lectures.COUNT && i < attendance.length; i++) {
            CheckBox cb = new CheckBox();
            cb.setIndeterminate(false);
            cb.setSelected(attendance[i]);
            this.attendance[i] = cb;
        }
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getFullName() {
        return name + " " + surname;
    }

    public CheckBox[] getAttendance() {
        return attendance;
    }

    @Override
    public void print(PrintWriter printWriter) {
        printWriter.printf("%s,%s,%d", this.getName(), this.getSurname(), this.getGroupNumber());
        for (CheckBox cb : this.getAttendance()) {
            char c = 'n';
            if (cb.isSelected())
                c = 'a';
            printWriter.printf(",%c", c);
        }
        printWriter.printf("\n");
    }

    @Override
    public void read(String str) {
        String[] data = str.split(",");
        this.name = data[0];
        this.surname = data[1];
        this.groupNumber = Integer.parseInt(data[2]);

        for (int i = 0; i < data.length - 3 && i < Lectures.COUNT; i++)
            attendance[i].setSelected(!data[i + 3].equals("n"));

        while (Course.getInstance().getGroups().size() < groupNumber) {
            Course.getInstance().addGroup();
        }
        Course.getInstance().getGroups().get(groupNumber - 1).addStudent(this);
    }
}

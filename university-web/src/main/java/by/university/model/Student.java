package by.university.model;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private int id;
    private String name;
    private String email;
    private int groupId;
    private List<LabWork> labWorks;

    public Student() {
        this.labWorks = new ArrayList<>();
    }

    public Student(int id, String name, String email, int groupId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.groupId = groupId;
        this.labWorks = new ArrayList<>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getGroupId() { return groupId; }
    public void setGroupId(int groupId) { this.groupId = groupId; }

    public List<LabWork> getLabWorks() { return labWorks; }
    public void setLabWorks(List<LabWork> labWorks) { this.labWorks = labWorks; }

    public void addLabWork(LabWork labWork) {
        this.labWorks.add(labWork);
    }
}
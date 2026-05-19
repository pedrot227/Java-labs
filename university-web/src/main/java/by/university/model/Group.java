package by.university.model;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private int id;
    private String groupName;
    private int facultyId;
    private List<Student> students;

    public Group() {
        this.students = new ArrayList<>();
    }

    public Group(int id, String groupName, int facultyId) {
        this.id = id;
        this.groupName = groupName;
        this.facultyId = facultyId;
        this.students = new ArrayList<>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }

    public int getFacultyId() { return facultyId; }
    public void setFacultyId(int facultyId) { this.facultyId = facultyId; }

    public List<Student> getStudents() { return students; }
    public void setStudents(List<Student> students) { this.students = students; }

    public void addStudent(Student student) {
        this.students.add(student);
    }
}
package by.university.model;

public class LabWork {
    private int id;
    private String title;
    private String description;
    private int studentId;

    public LabWork() {}

    public LabWork(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public LabWork(int id, String title, String description, int studentId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.studentId = studentId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
}
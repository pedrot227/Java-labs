package by.university.service;

import by.university.model.Faculty;
import by.university.model.Group;
import by.university.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FacultyService {

    private static List<Faculty> faculties = new ArrayList<>();
    private static List<Group> groups = new ArrayList<>();
    private static List<Student> students = new ArrayList<>();

    private static AtomicInteger facultyIdCounter = new AtomicInteger(1);
    private static AtomicInteger groupIdCounter = new AtomicInteger(1);
    private static AtomicInteger studentIdCounter = new AtomicInteger(1);

    // Начальные тестовые данные
    static {
        Faculty f1 = new Faculty(facultyIdCounter.getAndIncrement(), "Факультет информатики");
        Faculty f2 = new Faculty(facultyIdCounter.getAndIncrement(), "Факультет математики");
        faculties.add(f1);
        faculties.add(f2);

        Group g1 = new Group(groupIdCounter.getAndIncrement(), "ИТ-101", f1.getId());
        groups.add(g1);
        f1.addGroup(g1);

        Student s1 = new Student(studentIdCounter.getAndIncrement(), "Иван Иванов", "ivan@uni.by", g1.getId());
        Student s2 = new Student(studentIdCounter.getAndIncrement(), "Мария Петрова", "maria@uni.by", g1.getId());
        students.add(s1);
        students.add(s2);
        g1.addStudent(s1);
        g1.addStudent(s2);
    }

    public static List<Faculty> getAllFaculties() {
        return faculties;
    }

    public static Faculty getFacultyById(int id) {
        return faculties.stream()
                .filter(f -> f.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public static Group getGroupById(int id) {
        return groups.stream()
                .filter(g -> g.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public static List<Student> getStudentsByGroupId(int groupId) {
        List<Student> result = new ArrayList<>();
        for (Student s : students) {
            if (s.getGroupId() == groupId) {
                result.add(s);
            }
        }
        return result;
    }

    // ЗАДАНИЕ 1 - добавление группы в факультет
    public static Group addGroupToFaculty(String groupName, int facultyId,
                                          String[] studentNames, String[] studentEmails) {
        Faculty faculty = getFacultyById(facultyId);
        if (faculty == null) {
            throw new IllegalArgumentException("Факультет не найден!");
        }

        Group newGroup = new Group(groupIdCounter.getAndIncrement(), groupName, facultyId);

        if (studentNames != null) {
            int len = studentEmails != null
                    ? Math.min(studentNames.length, studentEmails.length)
                    : studentNames.length;

            for (int i = 0; i < len; i++) {
                String name = studentNames[i].trim();
                if (!name.isEmpty()) {
                    String email = (studentEmails != null && i < studentEmails.length)
                            ? studentEmails[i].trim() : "";
                    Student student = new Student(
                            studentIdCounter.getAndIncrement(),
                            name, email, newGroup.getId()
                    );
                    newGroup.addStudent(student);
                    students.add(student);
                }
            }
        }

        groups.add(newGroup);
        faculty.addGroup(newGroup);
        return newGroup;
    }
}
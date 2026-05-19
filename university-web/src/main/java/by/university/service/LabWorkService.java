package by.university.service;

import by.university.model.Group;
import by.university.model.LabWork;
import by.university.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LabWorkService {

    private static List<LabWork> labWorks = new ArrayList<>();
    private static AtomicInteger counter = new AtomicInteger(1);

    // Список доступных лабораторных работ (шаблоны)
    private static List<LabWork> availableLabWorks = new ArrayList<>();

    static {
        availableLabWorks.add(new LabWork(1, "Лаб. работа 1: Введение в Java", "Базовые конструкции"));
        availableLabWorks.add(new LabWork(2, "Лаб. работа 2: ООП", "Классы и наследование"));
        availableLabWorks.add(new LabWork(3, "Лаб. работа 3: Коллекции", "List, Map, Set"));
        availableLabWorks.add(new LabWork(4, "Лаб. работа 4: Исключения", "Try-catch"));
        availableLabWorks.add(new LabWork(5, "Лаб. работа 5: Многопоточность", "Thread, Runnable"));
    }

    public static List<LabWork> getAvailableLabWorks() {
        return availableLabWorks;
    }

    // ЗАДАНИЕ 2 - назначение лабораторных работ всем студентам группы
    public static void assignLabWorksToGroup(int groupId, int[] labWorkIds) {
        Group group = FacultyService.getGroupById(groupId);
        if (group == null) {
            throw new IllegalArgumentException("Группа не найдена!");
        }

        List<Student> students = FacultyService.getStudentsByGroupId(groupId);

        for (Student student : students) {
            // Очищаем старые назначения
            student.getLabWorks().clear();

            // Назначаем каждую выбранную лабораторную работу
            for (int labWorkId : labWorkIds) {
                LabWork template = availableLabWorks.stream()
                        .filter(lw -> lw.getId() == labWorkId)
                        .findFirst()
                        .orElse(null);

                if (template != null) {
                    LabWork assignment = new LabWork(
                            counter.getAndIncrement(),
                            template.getTitle(),
                            template.getDescription(),
                            student.getId()
                    );
                    student.addLabWork(assignment);
                    labWorks.add(assignment);
                }
            }
        }
    }
}
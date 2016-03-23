package fr.xebia.task;

import org.junit.gen5.api.Test;

import java.time.LocalDate;

import static org.junit.gen5.api.Assertions.assertEquals;

public interface IntrTodoListServiceTest {

    Task add1MonthToTask(Task newTask);

    @Test
    default void should_add_tasks_in_date_when_date_is_null() {
        Task task2add = new Task("task1", Task.Priority.NEW, LocalDate.of(2016, 3, 1));
        Task t = add1MonthToTask(task2add);
        assertEquals(t.getDate(), LocalDate.of(2016, 3, 31));
    }
}

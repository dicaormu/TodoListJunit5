package fr.xebia.task;


import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Test;
import org.junit.gen5.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;

import static java.util.Arrays.asList;
import static org.junit.gen5.api.Assertions.assertNotNull;
import static org.junit.gen5.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@DisplayName("Testing Todo List Service with mock")
@ExtendWith(MockitoExtension.class)
public class MockedTodoListServiceTest {

    @Mock
    Supplier<List<Task>> todoList;

    @Spy
    @InjectMocks
    TodoListService todoListService;


    @BeforeEach
    public void initTodoList() {
        todoListService = new TodoListService();
        when(todoList.get()).thenReturn(asList(new Task("task1", Task.Priority.NEW, LocalDate.now()), new Task("task2", Task.Priority.PROGRESS, LocalDate.now().plusMonths(1)), new Task("task3", Task.Priority.COMPLETE, LocalDate.now().plusMonths(2))));
    }


    @Test
    @DisplayName("find last task")
    public void
    should_find_last_task_to_execute() {
        Double resp = todoListService.getMaxTaskDelayInDays(todoList);
        assertNotNull(resp);
        assertTrue(resp == 2.0, "longest task must be " + resp);
    }


}
package fr.xebia.task;

import org.junit.gen5.api.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static org.junit.gen5.api.Assertions.*;

@Tag("firstTest")
@DisplayName("Testing Todo List Service")
public class TodoListServiceTest {

    Supplier<List<Task>> todoList;
    TodoListService todoListService;

    Supplier<String> errorsMessage;

    @BeforeEach
    public void initTodoList() {
        todoListService = new TodoListService();
        todoList = () -> Arrays.asList(new Task("task1", Task.Priority.NEW), new Task("task2", Task.Priority.PROGRESS), new Task("task3", Task.Priority.COMPLETE));
        errorsMessage = () -> "Test Failed, asserting not null";
    }

    @Test
    @DisplayName("First case: when changing my list")
    public void
    should_change_an_item() {
        List<Task> resp = todoListService.returnCompleteChangedList(todoList, Task.Priority.NEW);
        assertNotNull(resp, errorsMessage);
        assertTrue(resp.size() == 3, "Collection size mus be the same");

        assertAll("Assertions in the collection",
                () -> assertTrue(resp.contains(new Task("task1", Task.Priority.COMPLETE))),
                () -> assertTrue(resp.contains(new Task("task2", Task.Priority.PROGRESS))),
                () -> assertTrue(resp.contains(new Task("task3", Task.Priority.COMPLETE)))
        );
    }

    @Disabled
    @Test
    @DisplayName("second case: when changing my list and getting  completed collection. Not implemented yet")
    public void
    should_change_an_item_and_get_completed_list() {
        List<Task> resp = todoListService.completeListByPriority(todoList, Task.Priority.NEW);
        assertNotNull(resp, errorsMessage);
        assertTrue(resp.size() == 1, "Collection size mus be the same");

        assertAll("Assertions in the collection",
                () -> assertTrue(resp.contains(new Task("task1", Task.Priority.COMPLETE)))
        );
    }

    @Test
    @DisplayName("third case: when changing my list and getting collection of one item")
    public void
    should_change_an_item_and_get_one_item_list() {
        List<Task> resp = todoListService.completeListByPriority(todoList, Task.Priority.NEW);
        assertNotNull(resp, errorsMessage);
        assertTrue(resp.size() == 1, "Collection size mus be the same");

        assertTrue(resp.contains(new Task("task1", Task.Priority.COMPLETE)));
    }

    @Test
    @DisplayName("fourth case: getting task delay if possible")
    public void
    should_get_delay_exception_when_task_date_under_today() {
        assertThrows(IllegalArgumentException.class, () -> todoListService.getTaskDelayInDays(new Task("task1", Task.Priority.NEW, LocalDate.now().minusDays(1))));
    }
}
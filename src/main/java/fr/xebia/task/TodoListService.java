package fr.xebia.task;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class TodoListService {

    public Comparator<Period> periodComparator = new Comparator<Period>() {
        @Override
        public int compare(Period o1, Period o2) {
            return ((o1.toTotalMonths() + o1.getDays()) < (o2.toTotalMonths() + o2.getDays())) ? -1 : 1;
        }
    };

    public List<Task> completeListByPriority(Supplier<List<Task>> taskSupplier, Task.Priority priority) {
        return taskSupplier.get()
                .stream()
                .filter(task -> task.getPriority().equals(priority))
                .map(task -> new Task(task.getName(), Task.Priority.COMPLETE))
                .collect(Collectors.toList());
    }

    public List<Task> returnCompleteChangedList(Supplier<List<Task>> taskSupplier, Task.Priority priority) {
        return taskSupplier.get()
                .stream()
                .map(task -> new Task(task.getName(), task.getPriority().equals(priority) ? Task.Priority.COMPLETE : task.getPriority()))
                .collect(Collectors.toList());
    }

    public double getMaxTaskDelayInDays(Supplier<List<Task>> taskSupplier) {
        return taskSupplier.get()
                .stream()
                .map(Task::getDate)
                .map(date -> Period.between(LocalDate.now(), date))
                .max(periodComparator)
                .map(period -> ((Long) period.toTotalMonths()).doubleValue() + ((Integer) period.getDays()).doubleValue() / 30)
                .get();

    }

    public double getTaskDelayInDays(Task task) {
        if (task.getDate().isBefore(LocalDate.now()))
            throw new IllegalArgumentException("Not a valid task");
        Period period = Period.between(LocalDate.now(), task.getDate());
        return ((Long) period.toTotalMonths()).doubleValue() + ((Integer) period.getDays()).doubleValue() / 30;


    }

}

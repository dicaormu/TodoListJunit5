package fr.xebia.task;

import java.time.LocalDate;

class Task {
    private String name;
    private Priority priority;
    private LocalDate date;

    public Task(String name, Priority priority) {
        this.name = name;
        this.priority = priority;
        date = LocalDate.now().plusMonths(1);
    }

    public Task(String name, Priority priority, LocalDate date) {
        this.name = name;
        this.priority = priority;
        this.date=date;
    }

    public String getName() {
        return name;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority=priority;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (name != null ? !name.equals(task.name) : task.name != null) return false;
        return priority == task.priority;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (priority != null ? priority.hashCode() : 0);
        return result;
    }

    enum Priority {
        NEW, PROGRESS, COMPLETE
    }
}
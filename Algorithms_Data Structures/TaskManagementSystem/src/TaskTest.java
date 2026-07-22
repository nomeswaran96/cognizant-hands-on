public class TaskTest {
    
    // Singly Linked List Node
    private static class Node {
        Task task;
        Node next;

        Node(Task task) {
            this.task = task;
            this.next = null;
        }
    }

    private Node head;

    // Add Task (Appends at the end of the list)
    public void addTask(Task task) {
        Node newNode = new Node(task);
        if (head == null) {
            head = newNode;
        } else {
            Node temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
        System.out.println("Added Task: " + task);
    }

    // Search Task by ID
    public Task searchTask(String taskId) {
        Node temp = head;
        while (temp != null) {
            if (temp.task.getTaskId().equals(taskId)) {
                return temp.task;
            }
            temp = temp.next;
        }
        return null; // Not found
    }

    // Traverse and display tasks
    public void traverseTasks() {
        System.out.println("--- Task List (Singly Linked List) ---");
        Node temp = head;
        if (temp == null) {
            System.out.println("  No tasks found.");
        } else {
            while (temp != null) {
                System.out.println("  " + temp.task);
                temp = temp.next;
            }
        }
        System.out.println("-------------------------------------");
    }

    // Delete Task by ID
    public void deleteTask(String taskId) {
        if (head == null) {
            System.out.println("Error: Task list is empty.");
            return;
        }

        // Case 1: Delete head task
        if (head.task.getTaskId().equals(taskId)) {
            Task deleted = head.task;
            head = head.next;
            System.out.println("Deleted Task: " + deleted);
            return;
        }

        // Case 2: Delete inside or end task
        Node current = head;
        Node previous = null;

        while (current != null && !current.task.getTaskId().equals(taskId)) {
            previous = current;
            current = current.next;
        }

        if (current != null) {
            Task deleted = current.task;
            previous.next = current.next;
            System.out.println("Deleted Task: " + deleted);
        } else {
            System.out.println("Error: Task with ID " + taskId + " not found.");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Task Management System ===");
        TaskTest manager = new TaskTest();

        // 1. Add tasks
        manager.addTask(new Task("T001", "Design Database Schema", "Pending"));
        manager.addTask(new Task("T002", "Implement Authentication", "In Progress"));
        manager.addTask(new Task("T003", "Write API Documentation", "Pending"));
        manager.addTask(new Task("T004", "Configure CI/CD Pipelines", "Completed"));
        System.out.println();

        // Traverse tasks
        manager.traverseTasks();
        System.out.println();

        // 2. Search task
        System.out.println("--- Searching for Task T002 ---");
        Task searchResult = manager.searchTask("T002");
        System.out.println("Result: " + (searchResult != null ? searchResult : "Not Found"));
        System.out.println();

        // 3. Delete task (Head / Middle / End test cases)
        System.out.println("--- Deleting Task T001 (Design Database) ---");
        manager.deleteTask("T001");
        System.out.println();

        System.out.println("--- Deleting Task T003 (Write API Doc) ---");
        manager.deleteTask("T003");
        System.out.println();

        // Traverse remaining tasks
        manager.traverseTasks();
        System.out.println();

        System.out.println("SUCCESS: Task management Singly Linked List operations completed.");
    }
}

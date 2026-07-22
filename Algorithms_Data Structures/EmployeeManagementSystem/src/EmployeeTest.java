public class EmployeeTest {
    private final Employee[] employees;
    private int size;

    public EmployeeTest(int capacity) {
        employees = new Employee[capacity];
        size = 0;
    }

    // Add Employee
    public void addEmployee(Employee employee) {
        if (size >= employees.length) {
            System.out.println("Error: Employee array is full. Cannot add " + employee.getName());
            return;
        }
        employees[size] = employee;
        size++;
        System.out.println("Added Employee: " + employee);
    }

    // Search Employee (Linear Search by ID)
    public Employee searchEmployee(String employeeId) {
        for (int i = 0; i < size; i++) {
            if (employees[i].getEmployeeId().equals(employeeId)) {
                return employees[i];
            }
        }
        return null; // Not found
    }

    // Traverse Employees
    public void traverseEmployees() {
        System.out.println("--- Employee Directory (" + size + " Employees) ---");
        for (int i = 0; i < size; i++) {
            System.out.println("  Index " + i + ": " + employees[i]);
        }
        System.out.println("-------------------------------------------------");
    }

    // Delete Employee (Search and Shift elements left)
    public void deleteEmployee(String employeeId) {
        int indexToDelete = -1;
        for (int i = 0; i < size; i++) {
            if (employees[i].getEmployeeId().equals(employeeId)) {
                indexToDelete = i;
                break;
            }
        }

        if (indexToDelete == -1) {
            System.out.println("Error: Employee with ID " + employeeId + " not found.");
            return;
        }

        Employee deleted = employees[indexToDelete];
        // Shift remaining elements to the left to fill the gap
        for (int i = indexToDelete; i < size - 1; i++) {
            employees[i] = employees[i + 1];
        }
        employees[size - 1] = null; // Clear duplicate of last element
        size--;
        System.out.println("Deleted Employee: " + deleted);
    }

    public static void main(String[] args) {
        System.out.println("=== Employee Management System ===");
        EmployeeTest directory = new EmployeeTest(5);

        // 1. Add employees
        directory.addEmployee(new Employee("E001", "Alice", "Manager", 75000));
        directory.addEmployee(new Employee("E002", "Bob", "Developer", 60000));
        directory.addEmployee(new Employee("E003", "Charlie", "Designer", 55000));
        directory.addEmployee(new Employee("E004", "David", "QA Engineer", 50000));
        System.out.println();

        // Traverse records
        directory.traverseEmployees();
        System.out.println();

        // 2. Search employee
        System.out.println("--- Searching for Employee E003 ---");
        Employee searchResult = directory.searchEmployee("E003");
        System.out.println("Result: " + (searchResult != null ? searchResult : "Not Found"));
        System.out.println();

        // 3. Delete employee
        System.out.println("--- Deleting Employee E002 (Bob) ---");
        directory.deleteEmployee("E002");
        System.out.println();

        // Traverse records again to verify shifting
        directory.traverseEmployees();
        System.out.println();

        System.out.println("SUCCESS: Employee Management System operations completed.");
    }
}

package DataModels;

import java.util.ArrayList;
import java.util.List;


public class Employees {
    private List<Employee> employees = new ArrayList<>();

    public Employees() {
    }

    public Employees(List<Employee> employees) {
        this.employees = employees;
    }
    /**
     *
     * конструктор копирования возвращает копию списка сотрудников для обеспечения инкапсуляции
     */
    public Employees(Employees employees) {
        this(employees.getEmployees());
    }
    /**
     *
     * метод get создает копию исходного списка сотрудников для обеспечения инкапсуляции
     */
    public List<Employee> getEmployees() {
        List<Employee> employeeList = new ArrayList<>(this.employees.size());
        employees.forEach(employee -> employeeList.add(new Employee(
                employee.getName(), employee.getSurName())));
        return employeeList;
    }

    public void createNewEntry(int employeeId, Book book){
        System.out.println("Зашел2");
        employees.get(employeeId).createEntry(book);
    }
    public void updateEntry(int employeeId, Book book){
        employees.get(employeeId).updateEntry(book);
    }

    public Employee getEmployeeById(int employeeId){
        Employee employee = employees.get(employeeId);
        return new Employee(employee.getName(), employee.getSurName());
    }
}

package DataModels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Employees {
    private Map<String, Employee> registeredEmployees = new HashMap<>();

    public Employees() {
    }

    public Employees(Map<String, Employee> registeredEmployees) {
        this.registeredEmployees = registeredEmployees;
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
    public Map<String, Employee> getEmployees() {
        Map<String, Employee> employeesMap = new HashMap<>();
        registeredEmployees.forEach((k,v) -> employeesMap.put(k, new Employee(
                v.getName(),
                v.getSurName(),
                v.getEmail(),
                v.getPassword(),
                v.getEntries())));
        return employeesMap;
    }
    public void putNewRegisteredEmployee(Employee employee){
        registeredEmployees.put(employee.getEmail(), employee);
    }
    public boolean emailCheck(String email){
        return registeredEmployees.containsKey(email);
    }
    public boolean passwordCheck(String email, String password){
        if(emailCheck(email)){
            return registeredEmployees.get(email).getPassword().equals(password);
        }
        return false;
    }
    public Employee getEmployeeByEmail(String email){
        return registeredEmployees.get(email);
    }
    public void setEmployeeUserId(String email, String userId){
        registeredEmployees.get(email).setUserId(userId);
    }
    public boolean userIdCheck(String email, String userId){
        return registeredEmployees.get(email).userIdCheck(userId);
    }
    public boolean userHasTwoBooks(String email){
        return registeredEmployees.get(email).userHasTwoBook();
    }
    public void createEntry(Book book, String userEmail){
        registeredEmployees.get(userEmail).createEntry(book);
    }
    public void updateEntry(String book, String userEmail){
        registeredEmployees.get(userEmail).updateEntry(book);
    }
}

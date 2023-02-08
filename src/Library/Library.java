package Library;

import DataModels.Book;
import DataModels.Books;
import DataModels.Employee;
import DataModels.Employees;
import FileServise.FileService;

import java.util.*;

public class Library {
    private Books libraryBooks = new Books();
    private Employees employees = new Employees();
    public Library() {
    }

    /**
     *
     * вернет глубокую копию исходного объекта
     */
    public Books getLibraryBooks() {
        return new Books(libraryBooks);
    }
    /**
     *
     * вернет глубокую копию исходного объекта
     */
    public Employees getEmployees() {
        return new Employees(employees);
    }

    public void addNewRegisteredEmployee(Employee employee){
        employees.putNewRegisteredEmployee(employee);
    }

    public boolean emailCheck(String email){
        return employees.emailCheck(email);
    }
    public boolean passwordCheck(String email, String password){
        return employees.passwordCheck(email, password);
    }
    public Employee getEmployeeByEmail(String email){
        return employees.getEmployeeByEmail(email);
    }
    public Employee getPlugEmployee(){
        return new Employee("Имя", "Фамилия", "Email", "Пароль");
    }

}

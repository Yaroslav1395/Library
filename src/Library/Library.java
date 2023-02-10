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
    public void setEmployeeUserId(String email, String userId){
        employees.setEmployeeUserId(email, userId);
    }
    public boolean userIdCheck(String email, String userId){
        if(email == null){
            return false;
        }
        return employees.userIdCheck(email, userId);
    }
    public void setUserLogin(boolean bool){
        libraryBooks.setUserLogin(bool);
    }
    public void setLoginUserHasTwoBook(boolean bool){
        libraryBooks.setLoginUserHasTwoBook(bool);
    }
    public boolean userHasTwoBook(String userId){
        if(userId == null) return false;
        return employees.userHasTwoBooks(userId);
    }

}

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
    public void createNewEntryAtEmployee(int employeeId, Book book){
        if(book.isTaken()){
            System.out.println("Зашел1");
            libraryBooks.setTakenEmployee(book, employees.getEmployeeById(employeeId));
            employees.createNewEntry(employeeId, book);
        }
    }
    public void updateEntryAtEmployee(int employeeId, Book book){
        libraryBooks.removeTakenEmployee(book);
        employees.updateEntry(employeeId, book);
    }
}

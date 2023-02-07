
import DataModels.Book;
import DataModels.Books;
import DataModels.Employee;
import FileServise.FileService;
import Library.*;
import Server_44.Server44;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        /*try {
            new Server44("localhost", 9889).start();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        Library library = FileService.readJsonFile();
        Book book = library.getLibraryBooks().returnRandomBookFromBooksList(library.getEmployees().getEmployeeById(1));
        System.out.println("123");
        library.createNewEntryAtEmployee(1, book);
        System.out.println("123");
        library.updateEntryAtEmployee(1, book);
        book = library.getLibraryBooks().returnRandomBookFromBooksList(library.getEmployees().getEmployeeById(2));
        library.createNewEntryAtEmployee(2, book);
        library.updateEntryAtEmployee(2, book);
        book = library.getLibraryBooks().returnRandomBookFromBooksList(library.getEmployees().getEmployeeById(3));
        library.createNewEntryAtEmployee(3, book);
        library.updateEntryAtEmployee(3, book);
        book = library.getLibraryBooks().returnRandomBookFromBooksList(library.getEmployees().getEmployeeById(4));
        library.createNewEntryAtEmployee(4, book);
        library.updateEntryAtEmployee(4, book);
        book = library.getLibraryBooks().returnRandomBookFromBooksList(library.getEmployees().getEmployeeById(5));
        library.createNewEntryAtEmployee(5, book);
        library.updateEntryAtEmployee(5, book);
        book = library.getLibraryBooks().returnRandomBookFromBooksList(library.getEmployees().getEmployeeById(5));
        library.createNewEntryAtEmployee(5, book);
        library.updateEntryAtEmployee(5, book);
        FileService.writeJson(library);
    }
}
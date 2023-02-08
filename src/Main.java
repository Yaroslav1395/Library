
import DataModels.Book;
import DataModels.Books;
import DataModels.Employee;
import FileServise.FileService;
import Library.*;
import Server_44.Server44;
import Server_45.Server_45;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        try {
            new Server_45("localhost", 9889).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
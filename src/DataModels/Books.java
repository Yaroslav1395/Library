package DataModels;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Books {
    private List<Book> books = new ArrayList<>();

    public Books() {
    }

    public Books(List<Book> bookList) {
        books = bookList;
    }

    /**
     *
     * конструктор копирования возвращает копию списка книг для обеспечения инкапсуляции
     */
    public Books(Books books) {
        this(books.getBooks());
    }
    /**
     *
     * метод get создает копию исходного списка книг для обеспечения инкапсуляции
     */
    public List<Book> getBooks() {
        List<Book> bookListCopy = new ArrayList<>(books.size());
        books.forEach(book -> bookListCopy.add(new Book(
                book.getName(),
                book.getAuthorName(),
                book.getAuthorSurname(),
                book.getTakingEmployeeName(),
                book.getTakingEmployeeSurname()
        )));
        return bookListCopy;
    }
    /**
     *
     * метод создает копию случайной книги из списка и возвращает ее
     */
    public Book returnRandomBookFromBooksList(Employee employee){
        int random = new Random().nextInt(books.size() - 1);
        if(books.get(random).getTakingEmployeeName() == null){
            books.get(random).setTakingEmployeeName(employee.getName());
            books.get(random).setTakingEmployeeSurname(employee.getSurName());
            Book book = books.get(random);
            return new Book(
                    book.getName(),
                    book.getAuthorName(),
                    book.getAuthorSurname(),
                    book.getTakingEmployeeName(),
                    book.getTakingEmployeeSurname());
        }else {
            returnRandomBookFromBooksList(employee);
        }
        return null;
    }

    public void setTakenEmployee(Book book, Employee employee){
        for (Book bookFromList: books) {
            if(bookFromList == book){
                bookFromList.setTakingEmployeeName(employee.getName());
                bookFromList.setTakingEmployeeSurname(employee.getSurName());
                break;
            }
        }
    }
    public void removeTakenEmployee(Book book){
        for (Book bookFromList: books) {
            if(bookFromList == book){
                bookFromList.setTakingEmployeeName(null);
                bookFromList.setTakingEmployeeSurname(null);
                break;
            }
        }
    }

}
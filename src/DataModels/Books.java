package DataModels;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Books {
    private List<Book> books = new ArrayList<>();
    private transient boolean isUserLogin = false;
    private transient boolean isLoginUserHasTwoBook = false;

    public Books() {
    }

    public Books(List<Book> bookList, boolean isUserLogin, boolean isLoginUserHasTwoBook) {
        books = bookList;
        this.isUserLogin = isUserLogin;
        this.isLoginUserHasTwoBook = isLoginUserHasTwoBook;
    }

    /**
     *
     * конструктор копирования возвращает копию списка книг для обеспечения инкапсуляции
     */
    public Books(Books books) {
        this(books.getBooks(), books.isUserLogin(), books.isLoginUserHasTwoBook);
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

    public Book getBookByIndex(int index){
        return new Book(books.get(index));
    }

    public boolean isUserLogin() {
        return isUserLogin;
    }

    public void setUserLogin(boolean userLogin) {
        isUserLogin = userLogin;
    }

    public boolean isLoginUserHasTwoBook() {
        return isLoginUserHasTwoBook;
    }

    public void setLoginUserHasTwoBook(boolean loginUserHasTwoBook) {
        isLoginUserHasTwoBook = loginUserHasTwoBook;
    }
    public int getIndexOfBookByName(String name){
        for (Book book: books) {
            if(book.getName().equals(name)){
                return books.indexOf(book);
            }
        }
        return 0;
    }
    public void removeTakenEmployee(String bookName){
        for (Book book : books) {
            if(book.getName().equals(bookName)){
                book.removeTakenEmployee();
                isLoginUserHasTwoBook = false;
                break;
            }
        }
    }
    public void setTakenEmployee(Employee employee, int index){
        books.get(index).setTakenEmployee(employee);
    }
}

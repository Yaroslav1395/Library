package Library;

import DataModels.Book;


public class Entry {
    private String take;
    private String returned;
    private Book book;

    public Entry(String take, String returned, Book book) {
        this.take = take;
        this.returned = returned;
        this.book = book;
    }

    public String getReturned() {
        return returned;
    }

    public void setReturned(String returned) {
        this.returned = returned;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return book;
    }

}

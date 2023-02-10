package DataModels;

public class Book {
    private String name;
    private String authorName;
    private String authorSurname;
    private String takingEmployeeName;
    private String takingEmployeeSurname;


    public Book(String name, String authorName, String authorSurname, String takingEmployeeName, String takingEmployeeSurname) {
        this.name = name;
        this.authorName = authorName;
        this.authorSurname = authorSurname;
        this.takingEmployeeName = takingEmployeeName;
        this.takingEmployeeSurname = takingEmployeeSurname;
    }

    public Book(Book book) {
        name = book.getName();
        authorName = book.getAuthorName();
        authorSurname = book.getAuthorSurname();
        takingEmployeeName = book.getTakingEmployeeName();
        takingEmployeeSurname = book.getTakingEmployeeSurname();
    }

    public String getName() {
        return name;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorSurname() {
        return authorSurname;
    }

    public String getTakingEmployeeName() {
        return takingEmployeeName;
    }

    public String getTakingEmployeeSurname() {
        return takingEmployeeSurname;
    }

    public void setTakingEmployeeName(String takingEmployeeName) {
        this.takingEmployeeName = takingEmployeeName;
    }

    public void setTakingEmployeeSurname(String takingEmployeeSurname) {
        this.takingEmployeeSurname = takingEmployeeSurname;
    }

    @Override
    public String toString() {
        return "Название: " + name +
                "\nИмя автора: " + authorName +
                "\nФамилия автора: " + authorSurname +
                "\nИмя сотрудника: " + takingEmployeeName +
                "\nФамилия сотрудника: " + takingEmployeeSurname + "\n";
    }

    /**
     *
     * нужен для проверки при выборе книги клиентом
     */
    public boolean isTaken(){
        return takingEmployeeName != null;
    }
    public void removeTakenEmployee(){
        takingEmployeeName = null;
        takingEmployeeSurname = null;
    }
    public void setTakenEmployee(Employee employee){
        takingEmployeeName = employee.getName();
        takingEmployeeSurname = employee.getSurName();
    }
    public boolean isBookHasTakenEmployee(){
        return takingEmployeeName == null;
    }
}

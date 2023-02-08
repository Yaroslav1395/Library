package DataModels;

public class Employee {
    private String name;
    private String surName;
    private String email;
    private String password;
    private Entries entries;

    public Employee(String name, String surName, Entries entries) {
        this.name = name;
        this.surName = surName;
        this.entries = entries;
    }


    public Employee(String name, String surName, String email, String password) {
        this.name = name;
        this.surName = surName;
        this.email = email;
        this.password = password;
        this.entries = new Entries();
    }

    public Employee(String name, String surName, String email, String password, Entries entries) {
        this.name = name;
        this.surName = surName;
        this.email = email;
        this.password = password;
        this.entries = entries;
    }

    public String getName() {
        return name;
    }

    public String getSurName() {
        return surName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Entries getEntries() {
        return new Entries(entries);
    }

    @Override
    public String toString() {
        return "Имя: " + name + "\nФамилия: " + surName + '\n';
    }
    public void createEntry(Book book){
        entries.createEntry(book);
    }
    public void updateEntry(Book book){
        entries.updateEntry(book);
    }
}

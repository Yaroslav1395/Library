package DataModels;

public class Employee {
    private String name;
    private String surName;
    private Entries entries = new Entries();

    public Employee(String name, String surName, Entries entries) {
        this.name = name;
        this.surName = surName;
        this.entries = entries;
    }

    public String getName() {
        return name;
    }

    public String getSurName() {
        return surName;
    }

    public Entries getEntries() {
        return entries;
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

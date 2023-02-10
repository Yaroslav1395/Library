package DataModels;

public class Employee {
    private String name;
    private String surName;
    private String email;
    private String password;
    private Entries entries;
    private String userId;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
    public boolean userIdCheck(String userId){
        if(userId == null){
            return false;
        }
        System.out.println(userId.equals(this.userId));
        System.out.println(this.userId);
        return userId.equals(this.userId);
    }
    public boolean userHasTwoBook(){
        return entries.userHasTwoBook();
    }
}

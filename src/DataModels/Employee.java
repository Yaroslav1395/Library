package DataModels;

import Library.Entry;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Employee {
    private String name;
    private String surName;
    private List<Entry> entries = new ArrayList<>();

    public Employee(String name, String surName) {
        this.name = name;
        this.surName = surName;
    }

    public String getName() {
        return name;
    }

    public String getSurName() {
        return surName;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    @Override
    public String toString() {
        return "Имя: " + name + "\nФамилия: " + surName + '\n';
    }
    public void createEntry(Book book){
        if(entries == null){
            entries = new ArrayList<>();
        }
        entries.add(new Entry(LocalDateTime.now().toString(), null, book));

    }
    public void updateEntry(Book book){
        for (Entry entry: entries) {
            if(entry.getBook() == book && entry.getReturned() == null){
                entry.setReturned(LocalDateTime.now().toString());
                break;
            }
        }
    }
}

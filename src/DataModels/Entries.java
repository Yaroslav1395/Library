package DataModels;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Entries {
    private List<Entry> entries = new ArrayList<>();
    public List<Entry> getEntries() {
        List<Entry> entryList = new ArrayList<>(entries.size());
        entries.forEach(entry -> entryList.add(new Entry(entry.getTake(), entry.getReturned(), entry.getBook())));
        return entryList;
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

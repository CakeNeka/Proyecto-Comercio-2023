package org.cakeneka.utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SaveState implements Comparable<SaveState>{
    private int id;
    private LocalDateTime dateCreated;
    private List<String> fields;

    public SaveState(LocalDateTime dateCreated, int id, List<String> fields) {
        this.dateCreated = dateCreated;
        this.id = id;
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "SaveState " + id + " (" + getFormattedDateCreated() + ")";
    }
    
    
    
    @Override
    public int compareTo(SaveState o) {
        return dateCreated.compareTo(o.dateCreated);
    }
    
    public String getFormattedDateCreated(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dateCreated.format(dtf);
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public int getId() {
        return id;
    }

    public List<String> getFields() {
        return fields;
    }
    
    
}

package org.concomitant.conditions;


public class Event implements Condition {
    
    private final String name;

    public Event(String name) {
        if (name == null) throw new NullPointerException();
        this.name = name;
    }

    @Override
    public boolean satisfied() {
        return false;
    }
    
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Event) {
            return ((Event)obj).name.equals(this.name);
        } else {
            return false;
        }
    }
    
    

}

import java.util.ArrayList;
import java.util.List;

public class Event {
    private String eventId;
    private String title;
    private String date;
    private String location;
    private int capacity;
    private List<Member> registeredMembers;
    private List<Member> attendedMembers;

    public Event(String eventId, String title, String date, String location, int capacity) {
        this.eventId = eventId;
        this.title = title;
        this.date = date;
        this.location = location;
        this.capacity = capacity;
        this.registeredMembers = new ArrayList<>();
        this.attendedMembers = new ArrayList<>();
    }

    public String getEventId() { return eventId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public int getCapacity() { return capacity; }
    public List<Member> getRegisteredMembers() { return registeredMembers; }

    public boolean isFull() {
        return registeredMembers.size() >= capacity;
    }

    public void setCapacity(int newCapacity) {
        if (newCapacity < registeredMembers.size()) {
            throw new IllegalArgumentException("Capacity cannot be less than registered count (" + registeredMembers.size() + ")");
        }
        this.capacity = newCapacity;
    }

    public void registerMember(Member m) {
        if (registeredMembers.contains(m)) throw new IllegalArgumentException("Member already registered.");
        if (isFull()) throw new IllegalArgumentException("Event is full.");
        registeredMembers.add(m);
    }

    public void removeMember(Member m) {
        registeredMembers.remove(m);
        attendedMembers.remove(m);
    }

    public void markAttended(Member m) {
        if (!registeredMembers.contains(m)) throw new IllegalArgumentException("Member not registered.");
        if (!attendedMembers.contains(m)) attendedMembers.add(m);
    }
    
    public boolean hasAttended(Member m) {
        return attendedMembers.contains(m);
    }
}
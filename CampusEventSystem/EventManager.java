import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private List<Event> events;

    public EventManager() {
        this.events = new ArrayList<>();
        
    }

    public void createEvent(String id, String title, String date, String loc, int cap) {
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("ID required.");
        if (title == null || title.trim().isEmpty()) throw new IllegalArgumentException("Title required.");
        if (cap <= 0) throw new IllegalArgumentException("Capacity must be > 0.");
        for(Event e : events) {
            if(e.getEventId().equals(id)) throw new IllegalArgumentException("Event ID exists.");
        }
        events.add(new Event(id, title, date, loc, cap));
    }

    public void updateEvent(Event e, String title, String date, String loc, int cap) {
        if (title == null || title.trim().isEmpty()) throw new IllegalArgumentException("Title required.");
        e.setTitle(title);
        e.setDate(date);
        e.setLocation(loc);
        e.setCapacity(cap); 
    }

    public void registerMemberToEvent(Event e, String studentId, MemberManager memberManager) {
        if (e.isFull()) throw new IllegalArgumentException("Event is Full!");
        Member m = memberManager.getMemberOrThrow(studentId);
        e.registerMember(m);
    }

    public void removeMemberFromEvent(Event e, Member m) {
        if(m == null) throw new IllegalArgumentException("No member selected.");
        e.removeMember(m);
    }


    public void markAttendance(Event e, Member m) {
        if(m == null) throw new IllegalArgumentException("No member selected.");
        e.markAttended(m);
    }

    public void removeEvent(Event e) {
        events.remove(e);
    }

    public List<Event> getAllEvents() {
        return events;
    }
}
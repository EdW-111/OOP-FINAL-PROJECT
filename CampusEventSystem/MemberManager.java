import java.util.ArrayList;
import java.util.List;

public class MemberManager {
    private List<Member> members;

    public MemberManager() {
        this.members = new ArrayList<>();
    }

    public void createMember(String id, String name, String email) {
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("ID is required.");
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Name is required.");
        if (findMemberById(id) != null) throw new IllegalArgumentException("ID already exists.");
        members.add(new Member(id, name, email));
    }

    public void updateMember(Member m, String newName, String newEmail) {
        if (newName == null || newName.trim().isEmpty()) throw new IllegalArgumentException("Name cannot be empty.");
        m.setName(newName);
        m.setEmail(newEmail);
    }

    public Member getMemberOrThrow(String id) {
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("ID cannot be empty.");
        Member m = findMemberById(id);
        if (m == null) throw new IllegalArgumentException("Member ID '" + id + "' not found.");
        return m;
    }

    public void removeMember(Member m) {
        members.remove(m);
    }

    public List<Member> getAllMembers() {
        return members;
    }

    private Member findMemberById(String id) {
        for (Member m : members) {
            if (m.getStudentId().equals(id)) return m;
        }
        return null;
    }
}
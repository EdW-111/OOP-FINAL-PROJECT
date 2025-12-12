public class Member {
    private String studentId;
    private String name;
    private String email;

    public Member(String studentId, String name, String email) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
    }

    // --- Getters ---
    public String getStudentId() { return studentId; }
    public String getName() { return name; }
    
    // This was the missing method causing the error:
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return name + " (" + studentId + ")";
    }
    
    // Adding equals just in case we need it for comparisons later
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Member member = (Member) obj;
        return studentId.equals(member.studentId);
    }
public void setName(String name) { this.name = name; }
public void setEmail(String email) { this.email = email; }

}
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MainFrame extends JFrame {
    private MemberManager memberManager;
    private EventManager eventManager;
    private DefaultTableModel memberModel, eventModel;
    private JTable memberTable, eventTable;

    public MainFrame() {
        memberManager = new MemberManager();
        eventManager = new EventManager();

        setTitle("Campus Event Management System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Club Members", createMemberPanel());
        tabbedPane.addTab("Event List", createEventPanel());
        add(tabbedPane);
    }

    private DefaultTableModel createModel(String[] cols) {
        return new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
    }

    private JPanel createMemberPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        memberModel = createModel(new String[]{"ID", "Name", "Email"});
        memberTable = new JTable(memberModel);
        memberTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        refreshMemberTable();

        JPanel btns = new JPanel();
        JButton btnAdd = new JButton("Add Member");
        JButton btnEdit = new JButton("Edit");
        JButton btnDel = new JButton("Delete");

        btnAdd.addActionListener(e -> {
            JTextField id = new JTextField(), name = new JTextField(), email = new JTextField();
            JPanel p = new JPanel(new GridLayout(0, 2));
            p.add(new JLabel("ID:")); p.add(id); p.add(new JLabel("Name:")); p.add(name);
            p.add(new JLabel("Email:")); p.add(email);

            if (JOptionPane.showConfirmDialog(this, p, "Add Member", 2) == 0) {
                try {
                    memberManager.createMember(id.getText(), name.getText(), email.getText());
                    refreshMemberTable();
                } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); }
            }
        });

        btnEdit.addActionListener(e -> {
            int row = memberTable.getSelectedRow();
            if (row < 0) return;
            Member m = memberManager.getAllMembers().get(row);
            
            JTextField name = new JTextField(m.getName()), email = new JTextField(m.getEmail());
            JPanel p = new JPanel(new GridLayout(0, 2));
            p.add(new JLabel("ID: " + m.getStudentId())); p.add(new JLabel(""));
            p.add(new JLabel("Name:")); p.add(name); p.add(new JLabel("Email:")); p.add(email);

            if (JOptionPane.showConfirmDialog(this, p, "Edit Member", 2) == 0) {
                try {
                    memberManager.updateMember(m, name.getText(), email.getText());
                    refreshMemberTable();
                } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); }
            }
        });

        btnDel.addActionListener(e -> {
            int row = memberTable.getSelectedRow();
            if (row >= 0 && JOptionPane.showConfirmDialog(this, "Confirm Delete?", "Delete", 0) == 0) {
                memberManager.removeMember(memberManager.getAllMembers().get(row));
                refreshMemberTable();
            }
        });

        btns.add(btnAdd); btns.add(btnEdit); btns.add(btnDel);
        panel.add(new JScrollPane(memberTable), "Center");
        panel.add(btns, "South");
        return panel;
    }

    private JPanel createEventPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        eventModel = createModel(new String[]{"ID", "Title", "Date", "Loc", "Cap", "Reg"});
        eventTable = new JTable(eventModel);
        eventTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        refreshEventTable();

        JPanel btns = new JPanel();
        JButton btnAdd = new JButton("Create Event");
        JButton btnEdit = new JButton("Edit");
        JButton btnDel = new JButton("Delete");
        JButton btnManage = new JButton("Register/Attendance");

        btnAdd.addActionListener(e -> {
            JTextField id = new JTextField(), title = new JTextField(), date = new JTextField(), loc = new JTextField(), cap = new JTextField();
            JPanel p = new JPanel(new GridLayout(0, 2));
            p.add(new JLabel("ID:")); p.add(id); p.add(new JLabel("Title:")); p.add(title);
            p.add(new JLabel("Date:")); p.add(date); p.add(new JLabel("Loc:")); p.add(loc);
            p.add(new JLabel("Cap:")); p.add(cap);

            if (JOptionPane.showConfirmDialog(this, p, "New Event", 2) == 0) {
                try {
                    int c = Integer.parseInt(cap.getText());
                    eventManager.createEvent(id.getText(), title.getText(), date.getText(), loc.getText(), c);
                    refreshEventTable();
                } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage()); }
            }
        });

        btnEdit.addActionListener(e -> {
            int row = eventTable.getSelectedRow();
            if (row < 0) return;
            Event evt = eventManager.getAllEvents().get(row);
            
            JTextField title = new JTextField(evt.getTitle()), date = new JTextField(evt.getDate());
            JTextField loc = new JTextField(evt.getLocation()), cap = new JTextField(String.valueOf(evt.getCapacity()));
            JPanel p = new JPanel(new GridLayout(0, 2));
            p.add(new JLabel("ID: " + evt.getEventId())); p.add(new JLabel(""));
            p.add(new JLabel("Title:")); p.add(title); p.add(new JLabel("Date:")); p.add(date);
            p.add(new JLabel("Loc:")); p.add(loc); p.add(new JLabel("Cap:")); p.add(cap);

            if (JOptionPane.showConfirmDialog(this, p, "Edit Event", 2) == 0) {
                try {
                    eventManager.updateEvent(evt, title.getText(), date.getText(), loc.getText(), Integer.parseInt(cap.getText()));
                    refreshEventTable();
                } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); }
            }
        });

        btnDel.addActionListener(e -> {
            int row = eventTable.getSelectedRow();
            if (row >= 0 && JOptionPane.showConfirmDialog(this, "Confirm Delete?", "Delete", 0) == 0) {
                eventManager.removeEvent(eventManager.getAllEvents().get(row));
                refreshEventTable();
            }
        });

        btnManage.addActionListener(e -> {
            int row = eventTable.getSelectedRow();
            if (row >= 0) openAttendanceDialog(eventManager.getAllEvents().get(row));
        });

        btns.add(btnAdd); btns.add(btnEdit); btns.add(btnDel); btns.add(btnManage);
        panel.add(new JScrollPane(eventTable), "Center");
        panel.add(btns, "South");
        return panel;
    }
    private void openAttendanceDialog(Event event) {
        JDialog d = new JDialog(this, "Manage: " + event.getTitle(), true);
        d.setSize(600, 400); d.setLayout(new BorderLayout());
        
        DefaultTableModel model = createModel(new String[]{"ID", "Name", "Status"});
        JTable table = new JTable(model);
        Runnable refresh = () -> {
            model.setRowCount(0);
            for(Member m : event.getRegisteredMembers()) 
                model.addRow(new Object[]{m.getStudentId(), m.getName(), event.hasAttended(m)?"ATTENDED":"Registered"});
        };
        refresh.run();

        JPanel btns = new JPanel();
        JButton btnReg = new JButton("Register"), btnDel = new JButton("Cancel"), btnMark = new JButton("Mark Attend");

        btnReg.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Enter ID:");
            try {
                if(event.isFull()) throw new IllegalArgumentException("Event is Full!");
                
                eventManager.registerMemberToEvent(event, id, memberManager);
                refresh.run(); refreshEventTable();
            } catch (Exception ex) { JOptionPane.showMessageDialog(d, ex.getMessage()); }
        });

        btnDel.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0 && JOptionPane.showConfirmDialog(d, "Remove Member?", "Confirm", 0) == 0) {
                Member m = event.getRegisteredMembers().get(row);
                eventManager.removeMemberFromEvent(event, m);
                refresh.run(); refreshEventTable();
            }
        });

        btnMark.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                try {
                    Member m = event.getRegisteredMembers().get(row);
                    eventManager.markAttendance(event, m);
                    refresh.run();
                } catch(Exception ex) { JOptionPane.showMessageDialog(d, ex.getMessage()); }
            }
        });

        btns.add(btnReg); btns.add(btnDel); btns.add(btnMark);
        d.add(new JScrollPane(table), "Center"); d.add(btns, "South");
        d.setLocationRelativeTo(this); d.setVisible(true);
    }

    private void refreshMemberTable() {
        memberModel.setRowCount(0);
        for(Member m : memberManager.getAllMembers()) memberModel.addRow(new Object[]{m.getStudentId(), m.getName(), m.getEmail()});
    }

    private void refreshEventTable() {
        eventModel.setRowCount(0);
        for(Event e : eventManager.getAllEvents()) 
            eventModel.addRow(new Object[]{e.getEventId(), e.getTitle(), e.getDate(), e.getLocation(), e.getCapacity(), e.getRegisteredMembers().size()+"/"+e.getCapacity()});
    }

    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true)); }
}
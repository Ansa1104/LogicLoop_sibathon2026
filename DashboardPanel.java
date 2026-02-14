import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

public class DashboardPanel extends JPanel {

    private JLabel totalStudentsLabel;
    private DefaultTableModel studentModel;

    public DashboardPanel(DefaultTableModel studentModel, CardLayout cardLayout, JPanel contentPanel){

        this.studentModel = studentModel;

        setLayout(new BorderLayout());
        setBackground(new Color(240,244,250));
        setBorder(new EmptyBorder(30,40,30,40));

        // ===== TOP BAR =====
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT,15,0));
        topBar.setBackground(new Color(240,244,250));

        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(250,40));

        JButton searchBtn = createButton("Search", new Color(59,130,246));
        JButton addStudentBtn = createButton("Add New Student", new Color(37,99,235));

        topBar.add(new JLabel("Search by Student ID:"));
        topBar.add(searchField);
        topBar.add(searchBtn);
        topBar.add(Box.createHorizontalStrut(20));
        topBar.add(addStudentBtn);

        add(topBar, BorderLayout.NORTH);

        // ===== CENTER =====
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(240,244,250));
        centerPanel.setBorder(new EmptyBorder(30,0,0,0));

        JLabel welcome = new JLabel("Welcome, Administrator!");
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 32));
        welcome.setAlignmentX(Component.LEFT_ALIGNMENT);

        centerPanel.add(welcome);
        centerPanel.add(Box.createRigidArea(new Dimension(0,30)));

        // ===== STATS =====
        JPanel statsPanel = new JPanel(new GridLayout(1,4,20,0));
        statsPanel.setBackground(new Color(240,244,250));

        totalStudentsLabel = new JLabel("0");
        JLabel attendanceLabel = new JLabel("0%");
        JLabel feeLabel = new JLabel("0");
        JLabel gpaLabel = new JLabel("0.0");

        statsPanel.add(createCard("Total Students", totalStudentsLabel));
        statsPanel.add(createCard("Today's Attendance", attendanceLabel));
        statsPanel.add(createCard("Pending Fees", feeLabel));
        statsPanel.add(createCard("Overall GPA", gpaLabel));

        centerPanel.add(statsPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0,30)));

        // ===== QUICK ACTION BUTTONS =====
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,20,0));
        actionPanel.setBackground(new Color(240,244,250));

        JButton viewMarksBtn = createButton("View Marks", new Color(16,185,129));
        JButton viewAttendanceBtn = createButton("View Attendance", new Color(139,92,246));
        JButton feeStatusBtn = createButton("Fee Status", new Color(234,88,12));

        actionPanel.add(viewMarksBtn);
        actionPanel.add(viewAttendanceBtn);
        actionPanel.add(feeStatusBtn);

        centerPanel.add(actionPanel);

        add(centerPanel, BorderLayout.CENTER);

        // ===== Navigation =====
        addStudentBtn.addActionListener(e -> cardLayout.show(contentPanel,"Students"));
        viewMarksBtn.addActionListener(e -> cardLayout.show(contentPanel,"Marks & Grades"));
        viewAttendanceBtn.addActionListener(e -> cardLayout.show(contentPanel,"Attendance"));
        feeStatusBtn.addActionListener(e -> cardLayout.show(contentPanel,"Fees"));

        // ===== SEARCH FUNCTION =====
        searchBtn.addActionListener(e -> {

            String searchId = searchField.getText().trim();

            if(searchId.isEmpty()){
                JOptionPane.showMessageDialog(this,"Enter Student ID!");
                return;
            }

            boolean found = false;

            for(int i=0;i<studentModel.getRowCount();i++){
                if(studentModel.getValueAt(i,0).toString().equals(searchId)){
                    found = true;
                    JOptionPane.showMessageDialog(this,
                            "Student Found:\nName: "
                            + studentModel.getValueAt(i,1)
                            + "\nCourse: "
                            + studentModel.getValueAt(i,3));
                    break;
                }
            }

            if(!found){
                JOptionPane.showMessageDialog(this,"Student Not Found!");
            }
        });

        // ===== Auto Update Student Count =====
        studentModel.addTableModelListener(e ->
                totalStudentsLabel.setText(
                        String.valueOf(studentModel.getRowCount())
                ));
    }

    private JPanel createCard(String title, JLabel value){

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(
                new LineBorder(new Color(220,220,220)),
                new EmptyBorder(20,20,20,20)
        ));

        JLabel t = new JLabel(title);
        t.setForeground(Color.GRAY);
        t.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        value.setFont(new Font("Segoe UI", Font.BOLD, 28));
        value.setForeground(new Color(37,99,235));

        card.add(t, BorderLayout.NORTH);
        card.add(value, BorderLayout.CENTER);

        return card;
    }

    private JButton createButton(String text, Color color){
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBorder(new EmptyBorder(10,20,10,20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}

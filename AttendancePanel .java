import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

class AttendancePanel extends JPanel {

    private DefaultTableModel attendanceModel;

    public AttendancePanel(DefaultTableModel studentModel){

        setLayout(new BorderLayout());
        setBackground(new Color(245,247,250));
        setBorder(new EmptyBorder(30,40,30,40));

        // ===== Title =====
        JLabel title = new JLabel("Attendance Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(30,41,59));

        add(title, BorderLayout.NORTH);

        // ===== Main Container =====
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(Color.WHITE);
        container.setBorder(new CompoundBorder(
                new LineBorder(new Color(220,220,220)),
                new EmptyBorder(25,25,25,25)
        ));

        // ===== Top Section =====
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,15,10));
        topPanel.setBackground(Color.WHITE);

        JComboBox<String> studentBox = new JComboBox<>();
        studentBox.setPreferredSize(new Dimension(250,40));
        studentBox.setFont(new Font("Segoe UI", Font.PLAIN,14));

        JButton presentBtn = createColorButton("Mark Present", new Color(16,185,129));
        JButton absentBtn = createColorButton("Mark Absent", new Color(239,68,68));

        topPanel.add(new JLabel("Select Student:"));
        topPanel.add(studentBox);
        topPanel.add(presentBtn);
        topPanel.add(absentBtn);

        // ===== Table =====
        attendanceModel = new DefaultTableModel(
                new String[]{"Student","Status"},0);

        JTable table = new JTable(attendanceModel);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN,14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD,14));

        JScrollPane scrollPane = new JScrollPane(table);

        container.add(topPanel, BorderLayout.NORTH);
        container.add(scrollPane, BorderLayout.CENTER);

        add(container, BorderLayout.CENTER);

        // ===== Load Enrolled Students =====
        Runnable loadStudents = () -> {
            studentBox.removeAllItems();
            for(int i=0;i<studentModel.getRowCount();i++){
                studentBox.addItem(studentModel.getValueAt(i,0).toString());
            }
        };

        loadStudents.run();

        // Auto refresh when student added
        studentModel.addTableModelListener(e -> loadStudents.run());

        // ===== Present Button =====
        presentBtn.addActionListener(e -> {
            String student = (String) studentBox.getSelectedItem();
            if(student != null){
                attendanceModel.addRow(new Object[]{student,"Present"});
            }
        });

        // ===== Absent Button =====
        absentBtn.addActionListener(e -> {
            String student = (String) studentBox.getSelectedItem();
            if(student != null){
                attendanceModel.addRow(new Object[]{student,"Absent"});
            }
        });
    }

    private JButton createColorButton(String text, Color color){
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD,14));
        btn.setBorder(new EmptyBorder(10,20,10,20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}

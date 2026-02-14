import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StudentManagementSystem extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;

    public DefaultTableModel studentModel;

    public StudentManagementSystem() {

        setTitle("Student Management System");
        setSize(1300, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== Shared Student Model (ID Added) =====
        studentModel = new DefaultTableModel(
                new String[]{"Student ID","Name","Email","Course"},0);

        // ===== Sidebar =====
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(18, 24, 38));
        sidebar.setPreferredSize(new Dimension(250, getHeight()));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(40,20,20,20));

        String[] menuItems = {
                "Dashboard",
                "Students",
                "Attendance",
                "Marks & Grades",
                "Fees"
        };

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        for (String item : menuItems) {

            JButton btn = new JButton(item);
            btn.setMaximumSize(new Dimension(220, 45));
            btn.setAlignmentX(Component.LEFT_ALIGNMENT);
            btn.setBackground(new Color(37,99,235));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btn.setBorder(BorderFactory.createEmptyBorder(10,15,10,15));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btn.addActionListener(e -> cardLayout.show(contentPanel, item));

            sidebar.add(btn);
            sidebar.add(Box.createRigidArea(new Dimension(0, 20)));
        }

        add(sidebar, BorderLayout.WEST);

        // ===== Add Panels =====
        contentPanel.add(
                new DashboardPanel(studentModel, cardLayout, contentPanel),
                "Dashboard"
        );

        contentPanel.add(
                new StudentsPanel(studentModel),
                "Students"
        );

        contentPanel.add(
                new AttendancePanel(studentModel),
                "Attendance"
        );

        contentPanel.add(
                new MarksPanel(studentModel),
                "Marks & Grades"
        );

        contentPanel.add(
                new FeesPanel(studentModel),
                "Fees"
        );

        add(contentPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new StudentManagementSystem().setVisible(true));
    }
}

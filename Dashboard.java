import javax.swing.*;
import java.awt.*;

 class Dashboard extends JFrame {

    JPanel contentPanel;

    public Dashboard() {

        setTitle("Student Management System");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setBounds(0, 0, 200, 600);
        sidebar.setBackground(new Color(40, 40, 80));
        sidebar.setLayout(new GridLayout(10,1));
        add(sidebar);

        JButton homeBtn = new JButton("Dashboard");
        JButton addBtn = new JButton("Add Student");
        JButton viewBtn = new JButton("View Students");

        sidebar.add(homeBtn);
        sidebar.add(addBtn);
        sidebar.add(viewBtn);

        // Content Area
        contentPanel = new JPanel();
        contentPanel.setBounds(200, 0, 800, 600);
        contentPanel.setLayout(new BorderLayout());
        add(contentPanel);

        // Default Home
        contentPanel.add(new HomePanel());

        homeBtn.addActionListener(e -> switchPanel(new HomePanel()));
        addBtn.addActionListener(e -> switchPanel(new AddStudentPanel()));
        viewBtn.addActionListener(e -> switchPanel(new ViewStudentPanel()));

        setLocationRelativeTo(null);
        setVisible(true);
    }

    void switchPanel(JPanel panel){
        contentPanel.removeAll();
        contentPanel.add(panel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}

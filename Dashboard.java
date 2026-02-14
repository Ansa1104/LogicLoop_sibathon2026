import javax.swing.*;

public class Dashboard extends JFrame {

    public Dashboard() {
        setTitle("Dashboard");
        setSize(400, 300);
        setLayout(null);

        JButton studentBtn = new JButton("Manage Students");
        studentBtn.setBounds(100, 50, 200, 40);
        add(studentBtn);

        JButton attendanceBtn = new JButton("Manage Attendance");
        attendanceBtn.setBounds(100, 110, 200, 40);
        add(attendanceBtn);

        JButton marksBtn = new JButton("Manage Marks");
        marksBtn.setBounds(100, 170, 200, 40);
        add(marksBtn);

        studentBtn.addActionListener(e -> new Student());
        attendanceBtn.addActionListener(e -> new Attendance());
        marksBtn.addActionListener(e -> new Marks());

        setLocationRelativeTo(null);
        setVisible(true);
    }
}

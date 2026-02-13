import javax.swing.*;
import java.sql.*;

 class Attendance extends JFrame {

    JTextField studentIdField, totalField, attendedField;

    public Attendance() {
        setTitle("Add Attendance");
        setSize(350, 300);
        setLayout(null);

        studentIdField = createField("Student ID:", 30);
        totalField = createField("Total Classes:", 70);
        attendedField = createField("Attended:", 110);

        JButton addBtn = new JButton("Add Attendance");
        addBtn.setBounds(90, 160, 150, 30);
        add(addBtn);

        addBtn.addActionListener(e -> addAttendance());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JTextField createField(String label, int y) {
        JLabel l = new JLabel(label);
        l.setBounds(20, y, 120, 25);
        add(l);

        JTextField tf = new JTextField();
        tf.setBounds(140, y, 150, 25);
        add(tf);
        return tf;
    }

    private void addAttendance() {
        try {
            Connection con = DBConnection.getConnection();

            int studentId = Integer.parseInt(studentIdField.getText());
            int total = Integer.parseInt(totalField.getText());
            int attended = Integer.parseInt(attendedField.getText());

            double percentage = (attended * 100.0) / total;

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO attendance (student_id, total_classes, attended_classes, attendance_percentage) VALUES (?,?,?,?)"
            );

            ps.setInt(1, studentId);
            ps.setInt(2, total);
            ps.setInt(3, attended);
            ps.setDouble(4, percentage);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Attendance Added!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

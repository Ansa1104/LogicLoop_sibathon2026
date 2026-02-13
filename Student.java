public import javax.swing.*;
import java.sql.*;

public class Student extends JFrame {

    JTextField nameField, rollField, deptField, semField, emailField, phoneField;

    public Student() {
        setTitle("Add Student");
        setSize(400, 400);
        setLayout(null);

        nameField = createField("Name:", 20);
        rollField = createField("Roll No:", 60);
        deptField = createField("Department:", 100);
        semField = createField("Semester:", 140);
        emailField = createField("Email:", 180);
        phoneField = createField("Phone:", 220);

        JButton addBtn = new JButton("Add Student");
        addBtn.setBounds(120, 270, 150, 30);
        add(addBtn);

        addBtn.addActionListener(e -> addStudent());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JTextField createField(String label, int y) {
        JLabel l = new JLabel(label);
        l.setBounds(20, y, 100, 25);
        add(l);

        JTextField tf = new JTextField();
        tf.setBounds(130, y, 200, 25);
        add(tf);
        return tf;
    }

    private void addStudent() {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO students (name, roll_no, department, semester, email, phone) VALUES (?,?,?,?,?,?)"
            );

            ps.setString(1, nameField.getText());
            ps.setString(2, rollField.getText());
            ps.setString(3, deptField.getText());
            ps.setInt(4, Integer.parseInt(semField.getText()));
            ps.setString(5, emailField.getText());
            ps.setString(6, phoneField.getText());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Student Added Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} {
    
}

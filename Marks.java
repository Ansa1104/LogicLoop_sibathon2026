import javax.swing.*;
import java.sql.*;

public class Marks extends JFrame {

    JTextField studentIdField, subjectField, marksField, gradeField;

    public Marks() {

        setTitle("Add Marks");
        setSize(350, 320);
        setLayout(null);

        studentIdField = createField("Student ID:", 40);
        subjectField = createField("Subject:", 80);
        marksField = createField("Marks:", 120);
        gradeField = createField("Grade:", 160);

        JButton addBtn = new JButton("Add Marks");
        addBtn.setBounds(90, 210, 150, 30);
        add(addBtn);

        addBtn.addActionListener(e -> addMarks());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JTextField createField(String label, int y) {

        JLabel l = new JLabel(label);
        l.setBounds(20, y, 120, 25);
        add(l);

        JTextField tf = new JTextField();
        tf.setBounds(150, y, 150, 25);
        add(tf);

        return tf;
    }

    private void addMarks() {

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO marks (student_id, subject, marks_obtained, grade) VALUES (?,?,?,?)"
            );

            ps.setInt(1, Integer.parseInt(studentIdField.getText()));
            ps.setString(2, subjectField.getText());
            ps.setInt(3, Integer.parseInt(marksField.getText()));
            ps.setString(4, gradeField.getText());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Marks Added!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< Updated upstream
} {
    
=======
>>>>>>> 4e241991920506d666a7cbcf6130e8dcde99b75c
}
=======
}

>>>>>>> Stashed changes
=======
}
>>>>>>> 0c1e4aecfc2e92112b789300911c92e0a94e7d99

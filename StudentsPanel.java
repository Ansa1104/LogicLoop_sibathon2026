

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

class StudentsPanel extends JPanel {

    private DefaultTableModel model;
    private JTable table;

    public StudentsPanel(DefaultTableModel sharedModel){

        this.model = sharedModel;

        setLayout(new BorderLayout());
        setBackground(new Color(245,247,250));
        setBorder(new EmptyBorder(30,40,30,40));

        JLabel title = new JLabel("Student Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        add(title, BorderLayout.NORTH);

        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(Color.WHITE);
        container.setBorder(new CompoundBorder(
                new LineBorder(new Color(220,220,220)),
                new EmptyBorder(25,25,25,25)
        ));

        table = new JTable(model);
        table.setRowHeight(35);

        JScrollPane scrollPane = new JScrollPane(table);

        JTextField studentId = new JTextField();
        JTextField name = new JTextField();
        JTextField email = new JTextField();
        JTextField course = new JTextField();

        JButton add = createButton("Add", new Color(37,99,235));
        JButton update = createButton("Update", new Color(16,185,129));
        JButton delete = createButton("Delete", new Color(239,68,68));

        JPanel form = new JPanel(new GridLayout(2,5,15,15));
        form.setBackground(Color.WHITE);

        form.add(new JLabel("Student ID"));
        form.add(new JLabel("Name"));
        form.add(new JLabel("Email"));
        form.add(new JLabel("Course"));
        form.add(new JLabel(""));

        form.add(studentId);
        form.add(name);
        form.add(email);
        form.add(course);
        form.add(add);

        container.add(form, BorderLayout.NORTH);
        container.add(scrollPane, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.setBackground(Color.WHITE);
        bottom.add(update);
        bottom.add(delete);

        container.add(bottom, BorderLayout.SOUTH);
        add(container, BorderLayout.CENTER);

        // 🔥 Load existing data
        loadStudentsFromDatabase();

        // ================= ADD =================
        add.addActionListener(e -> {

            String idText = studentId.getText().trim();
            String nameText = name.getText().trim();
            String emailText = email.getText().trim();
            String courseText = course.getText().trim();

            if(idText.isEmpty()){
                JOptionPane.showMessageDialog(this,"Student ID Required!");
                return;
            }

            if(!emailText.contains("@")){
                JOptionPane.showMessageDialog(this,"Invalid Email!");
                return;
            }

            try {

                SQLConnection db = new SQLConnection();
                Connection con = db.getConnection();

                // 🔎 Proper Duplicate Check
                String checkQuery = "SELECT COUNT(*) FROM students WHERE id=?";
                PreparedStatement checkPs = con.prepareStatement(checkQuery);
                checkPs.setString(1, idText);

                ResultSet rs = checkPs.executeQuery();
                rs.next();
                int count = rs.getInt(1);

                if(count > 0){
                    JOptionPane.showMessageDialog(this,"Student ID Already Exists!");
                    return;
                }

                String query = "INSERT INTO students VALUES (?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(query);

                ps.setString(1, idText);
                ps.setString(2, nameText);
                ps.setString(3, emailText);
                ps.setString(4, courseText);

                ps.executeUpdate();

                model.addRow(new Object[]{idText, nameText, emailText, courseText});

                JOptionPane.showMessageDialog(this,"Student Added Successfully!");

                con.close();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,"Database Error!");
            }
        });

        // ================= UPDATE =================
        update.addActionListener(e -> {

            int row = table.getSelectedRow();

            if(row >= 0){

                try {
                    SQLConnection db = new SQLConnection();
                    Connection con = db.getConnection();

                    String query = "UPDATE students SET name=?, email=?, course=? WHERE id=?";
                    PreparedStatement ps = con.prepareStatement(query);

                    ps.setString(1, name.getText().trim());
                    ps.setString(2, email.getText().trim());
                    ps.setString(3, course.getText().trim());
                    ps.setString(4, studentId.getText().trim());

                    ps.executeUpdate();

                    model.setValueAt(name.getText(),row,1);
                    model.setValueAt(email.getText(),row,2);
                    model.setValueAt(course.getText(),row,3);

                    JOptionPane.showMessageDialog(this,"Student Updated!");

                    con.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // ================= DELETE =================
        delete.addActionListener(e -> {

            int row = table.getSelectedRow();

            if(row >= 0){

                String id = model.getValueAt(row,0).toString();

                try {
                    SQLConnection db = new SQLConnection();
                    Connection con = db.getConnection();

                    String query = "DELETE FROM students WHERE id=?";
                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setString(1, id);

                    ps.executeUpdate();
                    model.removeRow(row);

                    JOptionPane.showMessageDialog(this,"Student Deleted!");

                    con.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // ================= LOAD SELECTED ROW =================
        table.getSelectionModel().addListSelectionListener(e -> {

            int row = table.getSelectedRow();
            if(row >= 0){
                studentId.setText(model.getValueAt(row,0).toString());
                name.setText(model.getValueAt(row,1).toString());
                email.setText(model.getValueAt(row,2).toString());
                course.setText(model.getValueAt(row,3).toString());
            }
        });
    }

    // 🔥 Load From DB (Fixed)
    private void loadStudentsFromDatabase(){

        try {

            model.setRowCount(0);  // clear old data

            SQLConnection db = new SQLConnection();
            Connection con = db.getConnection();

            String query = "SELECT * FROM students";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                model.addRow(new Object[]{
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("course")
                });
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JButton createButton(String text, Color color){
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

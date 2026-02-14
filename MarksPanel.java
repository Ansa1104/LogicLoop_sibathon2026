import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

class MarksPanel extends JPanel {

    private DefaultTableModel marksModel;

    public MarksPanel(DefaultTableModel studentModel){

        setLayout(new BorderLayout());
        setBackground(new Color(245,247,250));
        setBorder(new EmptyBorder(30,40,30,40));

        JLabel title = new JLabel("Marks & Grades Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(30,41,59));

        add(title, BorderLayout.NORTH);

        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(Color.WHITE);
        container.setBorder(new CompoundBorder(
                new LineBorder(new Color(220,220,220)),
                new EmptyBorder(25,25,25,25)
        ));

        // ===== TABLE =====
        marksModel = new DefaultTableModel(
                new String[]{"Student ID","Subject","Marks","Grade","Status"},0);

        JTable table = new JTable(marksModel);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN,14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD,14));

        JScrollPane scrollPane = new JScrollPane(table);

        // ===== SEARCH SECTION =====
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200,40));

        JButton searchBtn = createButton("View Marks", new Color(139,92,246));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,15,10));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(new JLabel("Search by Student ID:"));
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);

        // ===== ADD MARKS SECTION (Teacher Only) =====
        JComboBox<String> studentBox = new JComboBox<>();
        studentBox.setPreferredSize(new Dimension(150,40));

        JTextField subject = new JTextField(10);
        JTextField marks = new JTextField(5);

        JButton add = createButton("Add Marks", new Color(16,185,129));

        JPanel addPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,15,10));
        addPanel.setBackground(Color.WHITE);

        addPanel.add(new JLabel("Student ID:"));
        addPanel.add(studentBox);
        addPanel.add(new JLabel("Subject:"));
        addPanel.add(subject);
        addPanel.add(new JLabel("Marks:"));
        addPanel.add(marks);
        addPanel.add(add);

        container.add(searchPanel, BorderLayout.NORTH);
        container.add(scrollPane, BorderLayout.CENTER);
        container.add(addPanel, BorderLayout.SOUTH);

        add(container, BorderLayout.CENTER);

        // ===== Load Enrolled Students =====
        Runnable loadStudents = () -> {
            studentBox.removeAllItems();
            for(int i=0;i<studentModel.getRowCount();i++){
                studentBox.addItem(studentModel.getValueAt(i,0).toString());
            }
        };

        loadStudents.run();
        studentModel.addTableModelListener(e -> loadStudents.run());

        // ===== Add Marks =====
        add.addActionListener(e -> {

            try{
                double m = Double.parseDouble(marks.getText());

                String grade = calculateGrade(m);
                String status = m >= 60 ? "Pass" : "Fail";

                marksModel.addRow(new Object[]{
                        studentBox.getSelectedItem(),
                        subject.getText(),
                        m,
                        grade,
                        status
                });

                subject.setText("");
                marks.setText("");

            }catch(Exception ex){
                JOptionPane.showMessageDialog(this,"Marks must be numeric!");
            }
        });

        // ===== Search / View Marks =====
        searchBtn.addActionListener(e -> {

            String id = searchField.getText();

            if(id.isEmpty()){
                JOptionPane.showMessageDialog(this,"Enter Student ID!");
                return;
            }

            boolean found = false;

            for(int i=0;i<marksModel.getRowCount();i++){
                if(marksModel.getValueAt(i,0).equals(id)){
                    table.setRowSelectionInterval(i,i);
                    found = true;
                }
            }

            if(!found){
                JOptionPane.showMessageDialog(this,"No records found!");
            }
        });
    }

    // ===== Grade Calculator =====
    private String calculateGrade(double marks){

        if(marks >= 90) return "A";
        if(marks >= 85) return "A-";
        if(marks >= 80) return "B+";
        if(marks >= 70) return "B";
        if(marks >= 65) return "C+";
        if(marks >= 60) return "C";
        return "F";
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

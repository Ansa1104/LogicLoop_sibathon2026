import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

class FeesPanel extends JPanel {

    private DefaultTableModel feesModel;

    public FeesPanel(DefaultTableModel studentModel){

        setLayout(new BorderLayout());
        setBackground(new Color(245,247,250));
        setBorder(new EmptyBorder(30,40,30,40));

        JLabel title = new JLabel("Fees Management");
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
        feesModel = new DefaultTableModel(
                new String[]{"Student ID","Total","Paid","Remaining","Status"},0);

        JTable table = new JTable(feesModel);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN,14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD,14));

        JScrollPane scrollPane = new JScrollPane(table);

        // ===== SEARCH SECTION =====
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200,40));

        JButton searchBtn = createButton("View Fee Status", new Color(139,92,246));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,15,10));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(new JLabel("Search by Student ID:"));
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);

        // ===== ADD FEE SECTION =====
        JComboBox<String> studentBox = new JComboBox<>();
        studentBox.setPreferredSize(new Dimension(150,40));

        JTextField total = new JTextField(6);
        JTextField paid = new JTextField(6);

        JButton add = createButton("Add Fee", new Color(16,185,129));

        JPanel addPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,15,10));
        addPanel.setBackground(Color.WHITE);

        addPanel.add(new JLabel("Student ID:"));
        addPanel.add(studentBox);
        addPanel.add(new JLabel("Total:"));
        addPanel.add(total);
        addPanel.add(new JLabel("Paid:"));
        addPanel.add(paid);
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

        // ===== Add Fee =====
        add.addActionListener(e -> {

            try{
                double t = Double.parseDouble(total.getText());
                double p = Double.parseDouble(paid.getText());

                double remaining = t - p;
                String status = remaining <= 0 ? "Paid" : "Pending";

                feesModel.addRow(new Object[]{
                        studentBox.getSelectedItem(),
                        t,
                        p,
                        remaining,
                        status
                });

                total.setText("");
                paid.setText("");

            }catch(Exception ex){
                JOptionPane.showMessageDialog(this,"Fee must be numeric!");
            }
        });

        // ===== Search / View Fee Status =====
        searchBtn.addActionListener(e -> {

            String id = searchField.getText();

            if(id.isEmpty()){
                JOptionPane.showMessageDialog(this,"Enter Student ID!");
                return;
            }

            boolean found = false;

            for(int i=0;i<feesModel.getRowCount();i++){
                if(feesModel.getValueAt(i,0).equals(id)){
                    table.setRowSelectionInterval(i,i);
                    found = true;
                }
            }

            if(!found){
                JOptionPane.showMessageDialog(this,"No fee record found!");
            }
        });
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
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

class MarksPanel extends JPanel {

    private DefaultTableModel marksModel;

    public MarksPanel(DefaultTableModel studentModel){

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        marksModel = new DefaultTableModel(
                new String[]{"Student","Subject","Marks"},0);

        JTable table = new JTable(marksModel);

        JComboBox<String> studentBox = new JComboBox<>();
        JTextField subject = new JTextField(8);
        JTextField marks = new JTextField(5);
        JButton add = new JButton("Add Marks");

        JPanel top = new JPanel();
        top.add(studentBox);
        top.add(subject);
        top.add(marks);
        top.add(add);

        add(top,BorderLayout.NORTH);
        add(new JScrollPane(table),BorderLayout.CENTER);

        add.addActionListener(e -> {

            studentBox.removeAllItems();
            for(int i=0;i<studentModel.getRowCount();i++){
                studentBox.addItem(studentModel.getValueAt(i,0).toString());
            }

            try{
                double m = Double.parseDouble(marks.getText());
                marksModel.addRow(new Object[]{
                        studentBox.getSelectedItem(),
                        subject.getText(),
                        m
                });
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this,"Marks must be number!");
            }
        });
    }
}
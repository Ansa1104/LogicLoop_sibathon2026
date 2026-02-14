import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

class FeesPanel extends JPanel {

    private DefaultTableModel feesModel;

    public FeesPanel(DefaultTableModel studentModel){

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        feesModel = new DefaultTableModel(
                new String[]{"Student","Total","Paid","Remaining"},0);

        JTable table = new JTable(feesModel);

        JComboBox<String> studentBox = new JComboBox<>();
        JTextField total = new JTextField(6);
        JTextField paid = new JTextField(6);
        JButton add = new JButton("Add Fee");

        JPanel top = new JPanel();
        top.add(studentBox);
        top.add(total);
        top.add(paid);
        top.add(add);

        add(top,BorderLayout.NORTH);
        add(new JScrollPane(table),BorderLayout.CENTER);

        add.addActionListener(e -> {

            studentBox.removeAllItems();
            for(int i=0;i<studentModel.getRowCount();i++){
                studentBox.addItem(studentModel.getValueAt(i,0).toString());
            }

            try{
                double t = Double.parseDouble(total.getText());
                double p = Double.parseDouble(paid.getText());
                feesModel.addRow(new Object[]{
                        studentBox.getSelectedItem(),
                        t,p,(t-p)
                });
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this,"Fee must be number!");
            }
        });
    }
}
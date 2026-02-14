import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

 class AttendancePanel extends JPanel {

    private DefaultTableModel attendanceModel;

     AttendancePanel(DefaultTableModel studentModel){

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        attendanceModel = new DefaultTableModel(
                new String[]{"Student","Status"},0);

        JTable table = new JTable(attendanceModel);

        JComboBox<String> studentBox = new JComboBox<>();
        JButton mark = new JButton("Mark Attendance");

        JPanel top = new JPanel();
        top.add(studentBox);
        top.add(mark);

        add(top,BorderLayout.NORTH);
        add(new JScrollPane(table),BorderLayout.CENTER);

        mark.addActionListener(e -> {

            studentBox.removeAllItems();
            for(int i=0;i<studentModel.getRowCount();i++){
                studentBox.addItem(studentModel.getValueAt(i,0).toString());
            }

            String student = (String) studentBox.getSelectedItem();
            if(student != null){

                String[] options = {"Present","Absent"};
                String status = (String) JOptionPane.showInputDialog(
                        this,"Select Status",
                        "Attendance",
                        JOptionPane.QUESTION_MESSAGE,
                        null,options,options[0]);

                if(status != null){
                    attendanceModel.addRow(new Object[]{student,status});
                }
            }
        });
    }
}
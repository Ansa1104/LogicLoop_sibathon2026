import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {

    public HomePanel() {

        setLayout(new GridLayout(2,2,20,20));
        setBorder(BorderFactory.createEmptyBorder(40,40,40,40));

        add(createCard("Total Students", "150", new Color(52,152,219)));
        add(createCard("Courses", "5", new Color(46,204,113)));
        add(createCard("Income", "50000", new Color(241,196,15)));
        add(createCard("Pending Fees", "10", new Color(231,76,60)));
    }

    JPanel createCard(String title, String value, Color color){
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.setLayout(new BorderLayout());

        JLabel t = new JLabel(title, SwingConstants.CENTER);
        t.setForeground(Color.WHITE);
        t.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel v = new JLabel(value, SwingConstants.CENTER);
        v.setForeground(Color.WHITE);
        v.setFont(new Font("Arial", Font.BOLD, 30));

        panel.add(t, BorderLayout.NORTH);
        panel.add(v, BorderLayout.CENTER);

        return panel;
    }
}

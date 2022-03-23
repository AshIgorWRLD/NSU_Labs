package view.windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RecordsWindow extends JDialog {

    private final JButton okButton;
    private final JTextField nameField;

    public RecordsWindow(JFrame frame) {
        super(frame, "New Record", true);

        nameField = new JTextField();

        GridLayout layout = new GridLayout(3, 1);
        Container contentPane = getContentPane();
        contentPane.setLayout(layout);

        contentPane.add(new JLabel("Enter your name:"));
        contentPane.add(nameField);
        okButton = createOkButton();
        contentPane.add(okButton);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(210, 120));
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    public String takeTextFromNameField() {
        return nameField.getText();
    }

    public void setNameListener(ActionListener actionListener) {
        okButton.addActionListener(actionListener);
    }

    private JButton createOkButton() {
        JButton button = new JButton("OK");
        button.addActionListener(e -> {
            if (takeTextFromNameField() != null) {
                dispose();
            }
        });
        return button;
    }
}

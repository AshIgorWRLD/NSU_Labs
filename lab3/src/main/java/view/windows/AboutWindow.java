package view.windows;

import javax.swing.*;
import java.awt.*;

public class AboutWindow extends JDialog {

    public AboutWindow(JFrame owner) {
        super(owner, "About", true);

        GridBagLayout layout = new GridBagLayout();
        Container contentPane = getContentPane();
        contentPane.setLayout(layout);

        contentPane.add(createAboutLabel(layout));
        contentPane.add(createCloseButton(layout));

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(300, 130));
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    private JLabel createAboutLabel(GridBagLayout layout) {
        JLabel label = new JLabel("Miner with love from Russia");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        layout.setConstraints(label, gbc);
        return label;
    }

    private JButton createCloseButton(GridBagLayout layout) {
        JButton closeButton = new JButton("Close");
        closeButton.setPreferredSize(new Dimension(100, 25));

        closeButton.addActionListener(e -> {
            dispose();
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(15, 5, 0, 0);
        layout.setConstraints(closeButton, gbc);

        return closeButton;
    }
}

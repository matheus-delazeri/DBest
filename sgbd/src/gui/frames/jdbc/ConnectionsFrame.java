package gui.frames.jdbc;

import controllers.ConstantController;
import controllers.MainController;
import database.jdbc.ConnectionConfig;
import database.jdbc.MySQLConnectionConfig;

import javax.swing.*;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * TODO: Separate components in different classes
 */
public class ConnectionsFrame extends JFrame {


    private ConnectionConfig currentConnection;
    private JPanel leftPanel = new JPanel(new GridLayout(2, 1));

    private JTextField hostTextField;

    private JTextField databaseTextField;

    private JTextField userTextField;

    private JPasswordField passwordField;
    private JTextField connectionURLField;

    public ConnectionsFrame() {
        initGUI();
    }

    private void initGUI() {
        setSize(400, 600);
        setLocationRelativeTo(null);
        setTitle(ConstantController.getString("connections"));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                MainController.connectionFrame = null;
            }
        });

        updateConnectionsList();
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        add(rightPanel, BorderLayout.CENTER);

        Dimension fieldDimension = new Dimension(200, 25);

        String[] connectionTypes = {"MySQL", "PostgreSQL", "Oracle"};
        JLabel typeLabel = new JLabel(ConstantController.getString("connections.frame.field.type"));
        typeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JComboBox<String> typeComboBox = new JComboBox<>(connectionTypes);
        typeComboBox.setMaximumSize(fieldDimension);

        JLabel connectionURLLabel = new JLabel(ConstantController.getString("connections.frame.field.connectionURL"));
        connectionURLLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        connectionURLField = new JTextField();
        connectionURLField.setMaximumSize(fieldDimension);
        connectionURLField.setEditable(false);

        JLabel hostLabel = new JLabel(ConstantController.getString("connections.frame.field.host"));
        hostLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        hostTextField = new JTextField();
        hostTextField.setMaximumSize(fieldDimension);
        JLabel databaseLabel = new JLabel(ConstantController.getString("connections.frame.field.database"));
        databaseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        databaseTextField = new JTextField();
        databaseTextField.setMaximumSize(fieldDimension);
        JLabel userLabel = new JLabel(ConstantController.getString("connections.frame.field.user"));
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userTextField = new JTextField();
        userTextField.setMaximumSize(fieldDimension);
        JLabel passwordLabel = new JLabel(ConstantController.getString("connections.frame.field.password"));
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField = new JPasswordField();
        passwordField.setMaximumSize(fieldDimension);

        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(typeLabel);
        rightPanel.add(typeComboBox);
        rightPanel.add(hostLabel);
        rightPanel.add(hostTextField);
        rightPanel.add(databaseLabel);
        rightPanel.add(databaseTextField);
        rightPanel.add(userLabel);
        rightPanel.add(userTextField);
        rightPanel.add(passwordLabel);
        rightPanel.add(passwordField);
        rightPanel.add(connectionURLLabel);
        rightPanel.add(connectionURLField);

        JButton saveButton = new JButton(ConstantController.getString("save"));
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(saveButton);


        saveButton.addActionListener(e -> {
            if (currentConnection == null) {
                switch (String.valueOf(typeComboBox.getSelectedItem())) {
                    case "MySQL" -> currentConnection = new MySQLConnectionConfig(
                        hostTextField.getText(),
                        databaseTextField.getText(),
                        userTextField.getText(),
                        new String(passwordField.getPassword())
                        );
                    default ->
                        throw new IllegalStateException("Unexpected value: " + String.valueOf(typeComboBox.getSelectedItem()));
                }
            }

            currentConnection.host = hostTextField.getText();
            currentConnection.username = userTextField.getText();
            currentConnection.password = new String(passwordField.getPassword());
            currentConnection.save();
            updateConnectionsList();
        });

        setVisible(true);
    }

    public void updateConnectionsList()
    {
        leftPanel.removeAll();

        DefaultListModel<String> connectionListModel = new DefaultListModel<>();
        JList<String> connectionList = new JList<>(connectionListModel);

        ArrayList<ConnectionConfig> configuredConnections = ConnectionConfig.getAllConfiguredConnections();
        for (ConnectionConfig connection : configuredConnections) {
            connectionListModel.addElement(connection.host);
        }

        JScrollPane configuredConnectionsList = new JScrollPane(connectionList);
        leftPanel.add(configuredConnectionsList);

        JButton addButton = new JButton(ConstantController.getString("connections.frame.button.new"));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        leftPanel.add(buttonPanel);

        add(leftPanel, BorderLayout.WEST);

        connectionList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = connectionList.getSelectedIndex();
                if (selectedIndex >= 0) {
                    currentConnection = configuredConnections.get(selectedIndex);
                    hostTextField.setText(currentConnection.host);
                    userTextField.setText(currentConnection.username);
                    passwordField.setText(currentConnection.password);
                    connectionURLField.setText(currentConnection.connectionURL);
                }
            }
        });

        addButton.addActionListener(e -> {
            currentConnection = null;
            hostTextField.setText("");
            userTextField.setText("");
            passwordField.setText("");
            connectionURLField.setText("");
        });

        leftPanel.revalidate();
    }
}

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.io.*;

class ElectionGUI extends JFrame {
    private static final String ADMIN_USERNAME = "ADMIN123";
    private static final String ADMIN_PASSWORD = "1234";
    private static final String VOTER_FILE = "voters.txt";
    private static final String RESULT_FILE = "votes.txt";

    private static int partyA = 0, partyB = 0, partyC = 0;

    private final JPanel cardPanel;
    private final CardLayout cardLayout;

    public ElectionGUI() {
        setTitle("Election System");
        setSize(800, 500); // Increased width and height
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 240)); // Light gray background

        // Make the window non-resizable
        setResizable(false);

        // Initialize CardLayout and Panels
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Add panels to the cardPanel
        cardPanel.add(createMainPanel(), "Main");
        cardPanel.add(createAdminLoginPanel(), "AdminLogin");
        cardPanel.add(createAdminPanel(), "Admin");
        cardPanel.add(createVotingPanel(), "Voting");
        cardPanel.add(createVoterRegistrationPanel(), "VoterRegistration");

        add(cardPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(36, 146, 104)); // Green background

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 128, 128)); // Darker green
        headerPanel.setBounds(20, 20, 740, 80); // Adjusted width
        panel.add(headerPanel);

        JLabel titleLabel = new JLabel("NATIONAL ELECTION BOARD OF ETHIOPIA");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.ITALIC, 20)); // Larger font
        headerPanel.add(titleLabel);

        // Admin Button
        JButton adminButton = new JButton("ADMIN");
        adminButton.setBounds(250, 150, 300, 60); // Adjusted position and size
        adminButton.setFont(new Font("Arial", Font.BOLD, 16));
        adminButton.setForeground(Color.WHITE);
        adminButton.setBackground(new Color(0, 80, 80)); // Dark green
        adminButton.addActionListener(e -> cardLayout.show(cardPanel, "AdminLogin"));
        panel.add(adminButton);

        // Voting Button
        JButton votingButton = new JButton("VOTING");
        votingButton.setBounds(250, 230, 300, 60); // Adjusted position and size
        votingButton.setFont(new Font("Arial", Font.BOLD, 16));
        votingButton.setForeground(Color.WHITE);
        votingButton.setBackground(new Color(0, 80, 80)); // Dark green
        votingButton.addActionListener(e -> cardLayout.show(cardPanel, "Voting"));
        panel.add(votingButton);

        // Exit Button
        JButton exitButton = new JButton("EXIT");
        exitButton.setBounds(350, 320, 100, 40); // Adjusted position
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));
        exitButton.setForeground(Color.WHITE);
        exitButton.setBackground(new Color(128, 128, 0)); // Olive green
        exitButton.addActionListener(e -> System.exit(0));
        panel.add(exitButton);

        return panel;
    }

    private JPanel createAdminLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(240, 240, 240)); // Light gray background

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 200, 200)); // Teal
        headerPanel.setBounds(30, 20, 740, 80); // Adjusted width
        panel.add(headerPanel);

        JLabel titleLabel = new JLabel("NATIONAL BOARD OF ELECTION");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.ITALIC, 20)); // Larger font
        headerPanel.add(titleLabel);

        // Username Label and Field
        JLabel userLabel = new JLabel("USER NAME:");
        userLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        userLabel.setBounds(150, 120, 150, 25);
        panel.add(userLabel);

        JTextField userTextField = new JTextField();
        userTextField.setBounds(320, 120, 250, 30); // Adjusted size
        panel.add(userTextField);

        // Password Label and Field
        JLabel passLabel = new JLabel("PASSWORD:");
        passLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        passLabel.setBounds(150, 170, 150, 25);
        panel.add(passLabel);

        JPasswordField passTextField = new JPasswordField();
        passTextField.setBounds(320, 170, 250, 30); // Adjusted size
        panel.add(passTextField);

        // Login Button
        JButton loginButton = new JButton("LOGIN");
        loginButton.setBounds(250, 230, 120, 40); // Adjusted position
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(0, 80, 80)); // Dark green
        loginButton.addActionListener(e -> {
            String username = userTextField.getText();
            String password = new String(passTextField.getPassword());

            if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
                cardLayout.show(cardPanel, "Admin"); // Switch to Admin Panel
                userTextField.setText(""); // Clear username field
                passTextField.setText(""); // Clear password field
            } else {
                JLabel errorLabel = new JLabel("Invalid Username or Password!");
                errorLabel.setForeground(Color.RED);
                errorLabel.setBounds(320, 200, 250, 25);
                panel.add(errorLabel);
                panel.revalidate();
                panel.repaint();
            }
        });
        panel.add(loginButton);

        // Back Button
        JButton backButton = new JButton("BACK");
        backButton.setBounds(400, 230, 120, 40); // Adjusted position
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(0, 80, 80)); // Dark green
        backButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "Main");
            userTextField.setText(""); // Clear username field
            passTextField.setText(""); // Clear password field
        });
        panel.add(backButton);

        return panel;
    }

    private JPanel createAdminPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(240, 240, 240)); // Light gray background

        // Register Voter Button
        JButton registerButton = new JButton("REGISTER VOTER");
        registerButton.setBounds(250, 80, 300, 60); // Adjusted position and size
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.setForeground(Color.WHITE);
        registerButton.setBackground(new Color(0, 80, 80)); // Dark green
        registerButton.addActionListener(e -> cardLayout.show(cardPanel, "VoterRegistration"));
        panel.add(registerButton);

        // View Results Button
        JButton viewResultsButton = new JButton("VIEW RESULTS");
        viewResultsButton.setBounds(250, 160, 300, 60); // Adjusted position and size
        viewResultsButton.setFont(new Font("Arial", Font.BOLD, 16));
        viewResultsButton.setForeground(Color.WHITE);
        viewResultsButton.setBackground(new Color(0, 80, 80)); // Dark green
        viewResultsButton.addActionListener(e -> displayResults());
        panel.add(viewResultsButton);

        // Back Button
        JButton backButton = new JButton("BACK");
        backButton.setBounds(350, 260, 100, 40); // Adjusted position
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(128, 128, 0)); // Olive green
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Main"));
        panel.add(backButton);

        return panel;
    }

    private JPanel createVotingPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(240, 240, 240)); // Light gray background

        // Enter ID Label and Field
        JLabel idLabel = new JLabel("Enter your Unique ID:");
        idLabel.setBounds(150, 50, 200, 25);
        idLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        panel.add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(350, 50, 250, 30); // Adjusted size
        panel.add(idField);

        // Load images for the buttons
        ImageIcon umbrellaIcon = new ImageIcon("src/umbrella.png"); // Path to umbrella image
        ImageIcon bulbIcon = new ImageIcon("src/bulb.png"); // Path to bulb image
        ImageIcon candleIcon = new ImageIcon("src/candle.png"); // Path to candle image

        // Resize images to fit the buttons
        Image umbrellaImage = umbrellaIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        Image bulbImage = bulbIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        Image candleImage = candleIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);

        // Create buttons with images and text
        JButton partyAButton = new JButton("Party A", new ImageIcon(umbrellaImage));
        partyAButton.setBounds(150, 120, 200, 80); // Adjusted position and size
        partyAButton.setFont(new Font("Arial", Font.BOLD, 14));
        partyAButton.setVerticalTextPosition(SwingConstants.BOTTOM); // Text below the image
        partyAButton.setHorizontalTextPosition(SwingConstants.CENTER); // Centered text
        partyAButton.addActionListener(e -> handleVote(idField, "Party A"));
        panel.add(partyAButton);

        JButton partyBButton = new JButton("Party B", new ImageIcon(bulbImage));
        partyBButton.setBounds(400, 120, 200, 80); // Adjusted position and size
        partyBButton.setFont(new Font("Arial", Font.BOLD, 14));
        partyBButton.setVerticalTextPosition(SwingConstants.BOTTOM); // Text below the image
        partyBButton.setHorizontalTextPosition(SwingConstants.CENTER); // Centered text
        partyBButton.addActionListener(e -> handleVote(idField, "Party B"));
        panel.add(partyBButton);

        JButton partyCButton = new JButton("Party C", new ImageIcon(candleImage));
        partyCButton.setBounds(650, 120, 200, 80); // Adjusted position and size
        partyCButton.setFont(new Font("Arial", Font.BOLD, 14));
        partyCButton.setVerticalTextPosition(SwingConstants.BOTTOM); // Text below the image
        partyCButton.setHorizontalTextPosition(SwingConstants.CENTER); // Centered text
        partyCButton.addActionListener(e -> handleVote(idField, "Party C"));
        panel.add(partyCButton);

        // Back Button
        JButton backButton = new JButton("BACK");
        backButton.setBounds(450, 220, 100, 40); // Adjusted position
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(128, 128, 0)); // Olive green
        backButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "Main");
            idField.setText(""); // Clear ID field when going back
        });
        panel.add(backButton);

        return panel;
    }

    private JPanel createVoterRegistrationPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(240, 240, 240)); // Light gray background

        // Name Label and Field
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(150, 50, 100, 25);
        nameLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        panel.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(350, 50, 250, 30); // Adjusted size
        nameField.setDocument(new PlainTextDocument()); // Only alphabetic characters
        panel.add(nameField);

        // Unique ID Label and Field
        JLabel idLabel = new JLabel("Unique ID:");
        idLabel.setBounds(150, 100, 100, 25);
        idLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        panel.add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(350, 100, 250, 30); // Adjusted size
        idField.setDocument(new AlphaNumericDocument()); // Alphabetic and numeric characters
        panel.add(idField);

        // Region Label and Field
        JLabel regionLabel = new JLabel("Region:");
        regionLabel.setBounds(150, 150, 100, 25);
        regionLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        panel.add(regionLabel);

        JTextField regionField = new JTextField();
        regionField.setBounds(350, 150, 250, 30); // Adjusted size
        regionField.setDocument(new PlainTextDocument()); // Only alphabetic characters
        panel.add(regionField);

        // Wereda Label and Field
        JLabel weredaLabel = new JLabel("Wereda:");
        weredaLabel.setBounds(150, 200, 100, 25);
        weredaLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        panel.add(weredaLabel);

        JTextField weredaField = new JTextField();
        weredaField.setBounds(350, 200, 250, 30); // Adjusted size
        weredaField.setDocument(new AlphaNumericDocument()); // Alphabetic and numeric characters
        panel.add(weredaField);

        // Kebele Label and Field
        JLabel kebeleLabel = new JLabel("Kebele:");
        kebeleLabel.setBounds(150, 250, 100, 25);
        kebeleLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        panel.add(kebeleLabel);

        JTextField kebeleField = new JTextField();
        kebeleField.setBounds(350, 250, 250, 30); // Adjusted size
        kebeleField.setDocument(new NumericDocument()); // Only numeric characters
        panel.add(kebeleField);

        // Register Button
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(250, 320, 120, 40); // Adjusted position
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setForeground(Color.WHITE);
        registerButton.setBackground(new Color(0, 80, 80)); // Dark green
        registerButton.addActionListener(e -> {
            String name = nameField.getText();
            String id = idField.getText();
            String region = regionField.getText();
            String wereda = weredaField.getText();
            String kebele = kebeleField.getText();

            if (name.isEmpty() || id.isEmpty() || region.isEmpty() || wereda.isEmpty() || kebele.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            saveVoterData(name, id, region, wereda, kebele);
            JOptionPane.showMessageDialog(null, "Voter registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Clear all fields after registration
            nameField.setText("");
            idField.setText("");
            regionField.setText("");
            weredaField.setText("");
            kebeleField.setText("");
        });
        panel.add(registerButton);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBounds(400, 320, 120, 40); // Adjusted position
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(128, 128, 0)); // Olive green
        backButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "Admin");

            // Clear all fields when going back
            nameField.setText("");
            idField.setText("");
            regionField.setText("");
            weredaField.setText("");
            kebeleField.setText("");
        });
        panel.add(backButton);

        return panel;
    }

    private void saveVoterData(String name, String id, String region, String wereda, String kebele) {
        try (FileWriter writer = new FileWriter(VOTER_FILE, true)) {
            writer.write(name + "," + id + "," + region + "," + wereda + "," + kebele + "\n");
        } catch (IOException e) {
            System.out.println("Error saving voter data: " + e.getMessage());
        }
    }

    private boolean isVoterRegistered(String id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(VOTER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.split(",")[1].equals(id)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading voter file: " + e.getMessage());
        }
        return false;
    }

    private boolean hasVoted(String id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(RESULT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.split(",")[0].equals(id)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading result file: " + e.getMessage());
        }
        return false;
    }

    private void recordVote(String id, String party) {
        try (FileWriter writer = new FileWriter(RESULT_FILE, true)) {
            writer.write(id + "," + party + "\n");
            switch (party) {
                case "Party A" -> partyA++;
                case "Party B" -> partyB++;
                case "Party C" -> partyC++;
            }
        } catch (IOException e) {
            System.out.println("Error recording vote: " + e.getMessage());
        }
    }

    private void displayResults() {
        partyA = 0;
        partyB = 0;
        partyC = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(RESULT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                switch (line.split(",")[1]) {
                    case "Party A" -> partyA++;
                    case "Party B" -> partyB++;
                    case "Party C" -> partyC++;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading result file: " + e.getMessage());
        }

        JOptionPane.showMessageDialog(null, "Party A: " + partyA + " votes\nParty B: " + partyB + " votes\nParty C: " + partyC + " votes\nWinner: " + getWinner(), "Results", JOptionPane.INFORMATION_MESSAGE);
    }

    private String getWinner() {
        if (partyA > partyB && partyA > partyC) return "Party A";
        if (partyB > partyA && partyB > partyC) return "Party B";
        if (partyC > partyA && partyC > partyB) return "Party C";
        return "Tie!";
    }

    // Helper method to handle voting
    private void handleVote(JTextField idField, String party) {
        String id = idField.getText();

        if (!isVoterRegistered(id)) {
            JOptionPane.showMessageDialog(null, "Voter ID not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (hasVoted(id)) {
            JOptionPane.showMessageDialog(null, "You have already voted!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Show a confirmation message box before voting
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to vote for " + party + "?", "Confirm Vote", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return; // Exit if the user chooses "No"
        }

        recordVote(id, party);
        JOptionPane.showMessageDialog(null, "Thank you for voting! Your vote has been recorded.", "Success", JOptionPane.INFORMATION_MESSAGE);
        idField.setText(""); // Clear ID field after voting
    }

    public static void main(String[] args) {
        // Run the application on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            new ElectionGUI();
        });
    }
}

// Custom Document classes for input validation
class PlainTextDocument extends PlainDocument {
    @Override
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null) return;
        if (str.matches("[a-zA-Z ]+")) { // Only alphabetic characters and spaces
            super.insertString(offset, str, attr);
        }
    }
}

class AlphaNumericDocument extends PlainDocument {
    @Override
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null) return;
        if (str.matches("[a-zA-Z0-9 ]+")) { // Alphabetic, numeric, and spaces
            super.insertString(offset, str, attr);
        }
    }
}

class NumericDocument extends PlainDocument {
    @Override
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null) return;
        if (str.matches("[0-9]+")) { // Only numeric characters
            super.insertString(offset, str, attr);
        }
    }
}
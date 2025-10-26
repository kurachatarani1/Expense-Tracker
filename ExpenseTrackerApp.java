import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Expense {
    String category;
    double amount;
    String date;

    Expense(String category, double amount, String date) {
        this.category = category;
        this.amount = amount;
        this.date = date;
    }
}

public class ExpenseTrackerApp extends JFrame implements ActionListener {
    private JTextField amountField, dateField;
    private JComboBox<String> categoryBox;
    private JButton addBtn, clearBtn, totalBtn;
    private JLabel totalLabel;
    private JTable table;
    private DefaultTableModel model;
    private ArrayList<Expense> expenses = new ArrayList<>();

    public ExpenseTrackerApp() {
        // Frame setup
        setTitle("Expense Tracker App");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ---------- Input Panel ----------
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add Expense"));

        categoryBox = new JComboBox<>(new String[]{"Food", "Travel", "Shopping", "Bills", "Other"});
        amountField = new JTextField();
        dateField = new JTextField("dd-mm-yyyy");
        addBtn = new JButton("Add Expense");
        clearBtn = new JButton("Clear");
        totalBtn = new JButton("Show Total");
        totalLabel = new JLabel("Total: ₹0.00");

        inputPanel.add(new JLabel("Category:"));
        inputPanel.add(categoryBox);
        inputPanel.add(new JLabel("Amount (₹):"));
        inputPanel.add(amountField);
        inputPanel.add(new JLabel("Date:"));
        inputPanel.add(dateField);
        inputPanel.add(addBtn);
        inputPanel.add(clearBtn);

        // ---------- Table Panel ----------
        String[] columns = {"Category", "Amount (₹)", "Date"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createTitledBorder("Expense Records"));

        // ---------- Bottom Panel ----------
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(totalBtn);
        bottomPanel.add(totalLabel);

        // ---------- Add to Frame ----------
        add(inputPanel, BorderLayout.NORTH);
        add(tableScroll, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // ---------- Event Handling ----------
        addBtn.addActionListener(this);
        clearBtn.addActionListener(e -> {
            amountField.setText("");
            dateField.setText("");
        });
        totalBtn.addActionListener(e -> calculateTotal());

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String category = (String) categoryBox.getSelectedItem();
            double amount = Double.parseDouble(amountField.getText());
            String date = dateField.getText();

            if (date.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a valid date!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Expense expense = new Expense(category, amount, date);
            expenses.add(expense);

            model.addRow(new Object[]{category, String.format("₹%.2f", amount), date});
            amountField.setText("");
            dateField.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calculateTotal() {
        double total = 0;
        for (Expense exp : expenses) {
            total += exp.amount;
        }
        totalLabel.setText("Total: ₹" + String.format("%.2f", total));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ExpenseTrackerApp::new);
    }
}

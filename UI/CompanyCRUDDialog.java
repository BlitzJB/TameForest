package UI;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import database.FSOperations;

public class CompanyCRUDDialog extends JDialog {
    public CompanyCRUDDialog(JFrame parentFrame, FSOperations fsOperations, JComboBox<String> companyDropdown, ArrayList<String> companies) {
        super(parentFrame, "Company CRUD", true);

        for (int i = 0; i < companyDropdown.getItemCount(); i++) {
            companies.add(companyDropdown.getItemAt(i));
        }

        JTextField companyNameField = new JTextField(20);
        JComboBox<String> operationDropdown = new JComboBox<>(new String[]{"Create", "Update", "Delete"});
        JButton performOperationButton = new JButton("Perform Operation");

        JPanel panel = new JPanel();
        panel.add(new JLabel("Company Name:"));
        panel.add(companyNameField);
        panel.add(new JLabel("Operation:"));
        panel.add(operationDropdown);
        panel.add(performOperationButton);

        performOperationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String companyName = companyNameField.getText().trim();
                String selectedOperation = (String) operationDropdown.getSelectedItem();

                if (selectedOperation.equals("Create")) {
                    if (!companyName.isEmpty()) {
                        fsOperations.createCompany(companyName);
                        companies.add(companyName);
                        companyDropdown.addItem(companyName);
                    }
                } else if (selectedOperation.equals("Update")) {
                    String selectedCompany = (String) companyDropdown.getSelectedItem();
                    if (!companyName.isEmpty()) {
                        fsOperations.updateCompany(selectedCompany, companyName);
                        int selectedIndex = companyDropdown.getSelectedIndex();
                        companies.set(selectedIndex, companyName);
                        companyDropdown.removeItemAt(selectedIndex);
                        companyDropdown.insertItemAt(companyName, selectedIndex);
                        companyDropdown.setSelectedIndex(selectedIndex);
                    }
                } else if (selectedOperation.equals("Delete")) {
                    String selectedCompany = (String) companyDropdown.getSelectedItem();
                    int confirm = JOptionPane.showConfirmDialog(CompanyCRUDDialog.this,
                            "Delete the selected company?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        fsOperations.deleteCompany(selectedCompany);
                        companies.remove(selectedCompany);
                        companyDropdown.removeItem(selectedCompany);
                    }
                }

                dispose(); 
            }
        });

        add(panel);
        pack();
        setLocationRelativeTo(parentFrame);
        setResizable(false);
        setVisible(true);
    }
}

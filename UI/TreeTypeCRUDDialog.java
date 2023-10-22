package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import database.FSOperations;
import database.TreeType;

public class TreeTypeCRUDDialog extends JDialog {
    public TreeTypeCRUDDialog(JFrame parentFrame, FSOperations fsOperations, JComboBox<String> treeDropdown, ArrayList<TreeType> treeTypes) {
        super(parentFrame, "Tree Type CRUD", true);

        for (int i = 0; i < treeDropdown.getItemCount(); i++) {
            String species = treeDropdown.getItemAt(i);
            TreeType treeType = fsOperations.getTreeType(species);
            if (treeType != null) {
                treeTypes.add(treeType);
            }
        }

        JTextField speciesField = new JTextField(20);
        JTextField kgCO2PerYearField = new JTextField(20);
        JTextField maintenanceCostPerYearField = new JTextField(20);
        JComboBox<String> operationDropdown = new JComboBox<>(new String[]{"Create", "Update", "Delete"});
        JButton performOperationButton = new JButton("Perform Operation");

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Species:"), gbc);

        gbc.gridx = 1;
        panel.add(speciesField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("CO2 Capture per Year (kg):"), gbc);

        gbc.gridx = 1;
        panel.add(kgCO2PerYearField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Maintenance Cost per Year:"), gbc);

        gbc.gridx = 1;
        panel.add(maintenanceCostPerYearField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Operation:"), gbc);

        gbc.gridx = 1;
        panel.add(operationDropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(performOperationButton, gbc);

        performOperationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String species = speciesField.getText().trim();
                String kgCO2PerYearStr = kgCO2PerYearField.getText().trim();
                String maintenanceCostPerYearStr = maintenanceCostPerYearField.getText().trim();
                String selectedOperation = (String) operationDropdown.getSelectedItem();

                if (selectedOperation.equals("Create")) {
                    if (!species.isEmpty() && !kgCO2PerYearStr.isEmpty() && !maintenanceCostPerYearStr.isEmpty()) {
                        int kgCO2PerYear = Integer.parseInt(kgCO2PerYearStr);
                        int maintenanceCostPerYear = Integer.parseInt(maintenanceCostPerYearStr);
                        TreeType newTreeType = new TreeType(species, kgCO2PerYear, maintenanceCostPerYear);
                        fsOperations.createTreeType(newTreeType);
                        treeTypes.add(newTreeType);
                        treeDropdown.addItem(species);
                    }
                } else if (selectedOperation.equals("Update")) {
                    String selectedSpecies = (String) treeDropdown.getSelectedItem();
                    if (!species.isEmpty() && !kgCO2PerYearStr.isEmpty() && !maintenanceCostPerYearStr.isEmpty()) {
                        int kgCO2PerYear = Integer.parseInt(kgCO2PerYearStr);
                        int maintenanceCostPerYear = Integer.parseInt(maintenanceCostPerYearStr);

                        TreeType updatedTreeType = treeTypes.stream()
                                .filter(treeType -> treeType.getSpecies().equals(selectedSpecies))
                                .findFirst()
                                .orElse(null);
                        if (updatedTreeType != null) {
                            updatedTreeType.setSpecies(species);
                            updatedTreeType.setKgCO2PerYear(kgCO2PerYear);
                            updatedTreeType.setMaintenanceCostPerYear(maintenanceCostPerYear);
                            fsOperations.updateTreeType(selectedSpecies, updatedTreeType);
                            int selectedIndex = treeDropdown.getSelectedIndex();
                            treeTypes.set(selectedIndex, updatedTreeType);
                            treeDropdown.removeItemAt(selectedIndex);
                            treeDropdown.insertItemAt(species, selectedIndex);
                            treeDropdown.setSelectedIndex(selectedIndex);
                        }
                    }
                } else if (selectedOperation.equals("Delete")) {
                    String selectedSpecies = (String) treeDropdown.getSelectedItem();
                    int confirm = JOptionPane.showConfirmDialog(TreeTypeCRUDDialog.this,
                            "Delete the selected tree species?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        fsOperations.deleteTreeType(selectedSpecies);
                        treeTypes.removeIf(treeType -> treeType.getSpecies().equals(selectedSpecies));
                        treeDropdown.removeItem(selectedSpecies);
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

package UI;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import database.FSOperations;

public class GridButton extends JButton implements ActionListener {
    private int x;
    private int y;
    private String[][] gridState;
    private FSOperations fsOperations;
    private JComboBox<String> companyDropdown;
    private JComboBox<String> treeDropdown;

    public GridButton(int x, int y, String[][] gridState, FSOperations fsOperations,
                      JComboBox<String> companyDropdown, JComboBox<String> treeDropdown) {
        this.x = x;
        this.y = y;
        this.gridState = gridState;
        this.fsOperations = fsOperations;
        this.companyDropdown = companyDropdown;
        this.treeDropdown = treeDropdown;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedCompany = (String) companyDropdown.getSelectedItem();
        String selectedTree = (String) treeDropdown.getSelectedItem();

        gridState[y][x] = selectedTree;
        setText(selectedTree);

        fsOperations.saveGridStateToCSV(x, y, selectedCompany, selectedTree);
    }
}

import javax.swing.*;

import UI.CompanyCRUDDialog;
import UI.GridButton;
import UI.ReportDialog;
import UI.TreeTypeCRUDDialog;

import java.awt.*;
import java.util.ArrayList;

import database.FSOperations;
import database.TreeType;


public class Main extends JFrame {

    private JComboBox<String> companyDropdown;
    private JComboBox<String> treeDropdown;

    private ArrayList<String> companies = new ArrayList<>();
    private ArrayList<TreeType> treeTypes = new ArrayList<>();
    private String[][] gridState;

    private FSOperations fsOperations;
    private int height = 5; // default
    private int width = 5;  // default

    public Main() {
        setTitle("Plantation Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);  

        fsOperations = new FSOperations();
        height = fsOperations.getDesignatedAreaRows();
        width = fsOperations.getDesignatedAreaColumns();

        companies = fsOperations.loadCompaniesFromCSV();
        treeTypes = fsOperations.loadTreeTypesFromCSV();

        companyDropdown = new JComboBox<>(companies.toArray(new String[0]));
        treeDropdown = new JComboBox<>(treeTypes.stream().map(TreeType::getSpecies).toArray(String[]::new));

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Company:"));
        topPanel.add(companyDropdown);
        topPanel.add(new JLabel("Tree Type:"));
        topPanel.add(treeDropdown);

        JPanel gridPanel = new JPanel(new GridLayout(height, width));
        gridState = new String[height][width]; 

        loadGridStateFromCSV();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                JButton cellButton = new GridButton(x, y, gridState, fsOperations, companyDropdown, treeDropdown);
                gridPanel.add(cellButton);
                cellButton.setText(gridState[y][x]); 
            }
        }

        JButton companyButton = new JButton("Company CRUD");
        JButton treeButton = new JButton("Tree CRUD");
        JButton showReportButton = new JButton("Show Report");

        companyButton.addActionListener(e -> {
            new CompanyCRUDDialog(this, fsOperations, companyDropdown, companies);
        });

        treeButton.addActionListener(e -> {
            new TreeTypeCRUDDialog(this, fsOperations, treeDropdown, treeTypes);
        });

        showReportButton.addActionListener(e -> {
            new ReportDialog(this, gridState, fsOperations);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(companyButton);
        buttonPanel.add(treeButton);
        buttonPanel.add(showReportButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(gridPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadGridStateFromCSV() {
        gridState = fsOperations.loadGridStateFromCSV();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main gridUI = new Main();
            gridUI.setVisible(true);
        });
    }
}

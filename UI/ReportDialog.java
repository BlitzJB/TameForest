package UI;
import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.HashMap;

import database.FSOperations;
import database.TreeType;

public class ReportDialog extends JDialog {
    public ReportDialog(JFrame parentFrame, String[][] gridState, FSOperations fsOperations) {
        super(parentFrame, "Company-wise Report", true);

        // Calculate the report data
        Map<String, Integer> totalCO2PerCompany = new HashMap<>();
        Map<String, Integer> totalMaintenanceCostPerCompany = new HashMap<>();

        int height = gridState.length;
        int width = gridState[0].length;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                String treeSpecies = gridState[y][x];
                String companyName = fsOperations.getCompanyNameFromGrid(x, y); 
                TreeType treeType = fsOperations.getTreeType(treeSpecies);

                if (companyName != null && treeType != null) {
                    totalCO2PerCompany.put(companyName, totalCO2PerCompany.getOrDefault(companyName, 0) + treeType.getKgCO2PerYear());
                    totalMaintenanceCostPerCompany.put(companyName, totalMaintenanceCostPerCompany.getOrDefault(companyName, 0) + treeType.getMaintenanceCostPerYear());
                }
            }
        }

        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append("Company-wise Report:\n\n");

        for (String company : totalCO2PerCompany.keySet()) {
            int totalCO2 = totalCO2PerCompany.get(company);
            int totalMaintenanceCost = totalMaintenanceCostPerCompany.get(company);

            reportBuilder.append("Company: ").append(company).append("\n");
            reportBuilder.append("Total kgCO2 Per Year: ").append(totalCO2).append("\n");
            reportBuilder.append("Total Maintenance Cost Per Year: ").append(totalMaintenanceCost).append("\n\n");
        }

        JTextArea reportTextArea = new JTextArea(reportBuilder.toString());
        reportTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(reportTextArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        getContentPane().add(scrollPane);
        pack();
        setLocationRelativeTo(parentFrame);
        setResizable(false);
        setVisible(true);
    }
}

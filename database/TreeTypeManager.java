package database;
import java.io.*;
import java.util.ArrayList;

public class TreeTypeManager {
    private String treesFileName;

    public TreeTypeManager(String treesFileName) {
        this.treesFileName = treesFileName;
    }

    public ArrayList<TreeType> loadTreeTypes() {
        ArrayList<TreeType> treeTypesList = new ArrayList<>();
        File treesFile = new File(treesFileName);
        if (treesFile.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(treesFile));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    String species = parts[0];
                    int kgCO2PerYear = Integer.parseInt(parts[1]);
                    int maintenanceCostPerYear = Integer.parseInt(parts[2]);
                    treeTypesList.add(new TreeType(species, kgCO2PerYear, maintenanceCostPerYear));
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return treeTypesList;
    }

    public void createTreeType(TreeType treeType) {
        try {
            FileWriter writer = new FileWriter(treesFileName, true);
            writer.append(treeType.getSpecies() + "," + treeType.getKgCO2PerYear() + "," + treeType.getMaintenanceCostPerYear() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateTreeType(String oldSpecies, TreeType newTreeType) {
        ArrayList<TreeType> treeTypesList = loadTreeTypes();
        for (int i = 0; i < treeTypesList.size(); i++) {
            TreeType tree = treeTypesList.get(i);
            if (tree.getSpecies().equals(oldSpecies)) {
                treeTypesList.set(i, newTreeType);
                saveTreeTypes(treeTypesList);
                return;
            }
        }
    }

    public void deleteTreeType(String speciesToDelete) {
        ArrayList<TreeType> treeTypesList = loadTreeTypes();
        treeTypesList.removeIf(tree -> tree.getSpecies().equals(speciesToDelete));
        saveTreeTypes(treeTypesList);
    }

    private void saveTreeTypes(ArrayList<TreeType> treeTypesList) {
        try {
            FileWriter writer = new FileWriter(treesFileName);
            for (TreeType treeType : treeTypesList) {
                writer.append(treeType.getSpecies() + "," + treeType.getKgCO2PerYear() + "," + treeType.getMaintenanceCostPerYear() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TreeType getTreeType(String species) {
        ArrayList<TreeType> treeTypesList = loadTreeTypes();
        for (TreeType treeType : treeTypesList) {
            if (treeType.getSpecies().equals(species)) {
                return treeType;
            }
        }
        return null;
    }
}

package database;
import java.util.ArrayList;

public class FSOperations {
    private GridStateLoader gridStateLoader;
    private CompanyManager companyManager;
    private TreeTypeManager treeTypeManager;
    private ConfigManager configManager;

    public FSOperations() {
        gridStateLoader = new GridStateLoader("./database/data/purchases.csv");
        companyManager = new CompanyManager("./database/data/companies.csv");
        treeTypeManager = new TreeTypeManager("./database/data/trees.csv");
        configManager = new ConfigManager("./database/data/config.csv");
    }

    public String[][] loadGridState() {
        return gridStateLoader.loadGridState(configManager.getDesignatedAreaRows(), configManager.getDesignatedAreaColumns());
    }

    public void saveGridState(int x, int y, String companyName, String treeType) {
        gridStateLoader.saveGridState(x, y, companyName, treeType);
    }

    public ArrayList<String> loadCompanies() {
        return companyManager.loadCompanies();
    }

    public void createCompany(String companyName) {
        companyManager.createCompany(companyName);
    }

    public void updateCompany(String oldCompanyName, String newCompanyName) {
        companyManager.updateCompany(oldCompanyName, newCompanyName);
    }

    public void deleteCompany(String companyName) {
        companyManager.deleteCompany(companyName);
    }

    public ArrayList<TreeType> loadTreeTypes() {
        return treeTypeManager.loadTreeTypes();
    }

    public void createTreeType(TreeType treeType) {
        treeTypeManager.createTreeType(treeType);
    }

    public void updateTreeType(String oldSpecies, TreeType newTreeType) {
        treeTypeManager.updateTreeType(oldSpecies, newTreeType);
    }

    public void deleteTreeType(String speciesToDelete) {
        treeTypeManager.deleteTreeType(speciesToDelete);
    }

    public void loadDesignatedAreaFromConfig() {
        configManager.loadDesignatedArea();
    }

    public String[][] loadGridStateFromCSV() {
        return gridStateLoader.loadGridState(configManager.getDesignatedAreaRows(), configManager.getDesignatedAreaColumns());
    }

    public void saveGridStateToCSV(int x, int y, String companyName, String treeType) {
        gridStateLoader.saveGridState(x, y, companyName, treeType);
    }

    public int getDesignatedAreaRows() {
        return configManager.getDesignatedAreaRows();
    }

    public int getDesignatedAreaColumns() {
        return configManager.getDesignatedAreaColumns();
    }

    public ArrayList<String> loadCompaniesFromCSV() {
        return companyManager.loadCompanies();
    }

    public ArrayList<TreeType> loadTreeTypesFromCSV() {
        return treeTypeManager.loadTreeTypes();
    }

    public String getCompanyNameFromGrid(int x, int y) {
        return gridStateLoader.getCompanyNameFromGrid(x, y, configManager.getDesignatedAreaRows(), configManager.getDesignatedAreaColumns());
    }

    public TreeType getTreeType(String species) {
        return treeTypeManager.getTreeType(species);
    }
}

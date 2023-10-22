package database;
public class TreeType {
    private String species;
    private int kgCO2PerYear;
    private int maintenanceCostPerYear;

    public TreeType(String species, int kgCO2PerYear, int maintenanceCostPerYear) {
        this.species = species;
        this.kgCO2PerYear = kgCO2PerYear;
        this.maintenanceCostPerYear = maintenanceCostPerYear;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public int getKgCO2PerYear() {
        return kgCO2PerYear;
    }

    public void setKgCO2PerYear(int kgCO2PerYear) {
        this.kgCO2PerYear = kgCO2PerYear;
    }

    public int getMaintenanceCostPerYear() {
        return maintenanceCostPerYear;
    }

    public void setMaintenanceCostPerYear(int maintenanceCostPerYear) {
        this.maintenanceCostPerYear = maintenanceCostPerYear;
    }
}

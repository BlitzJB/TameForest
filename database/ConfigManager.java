package database;
import java.io.*;

public class ConfigManager {
    private String configFileName;

    private int designatedAreaRows;
    private int designatedAreaColumns;

    public ConfigManager(String configFileName) {
        this.configFileName = configFileName;
        loadDesignatedArea();
    }

    public void loadDesignatedArea() {
        File configFile = new File(configFileName);
        if (configFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
                String line = reader.readLine();
                String[] dimensions = line.split(",");
                designatedAreaRows = Integer.parseInt(dimensions[0]);
                designatedAreaColumns = Integer.parseInt(dimensions[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getDesignatedAreaRows() {
        return designatedAreaRows;
    }

    public int getDesignatedAreaColumns() {
        return designatedAreaColumns;
    }
}

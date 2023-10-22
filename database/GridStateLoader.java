package database;
import java.io.*;

public class GridStateLoader {
    private String csvFileName;

    public GridStateLoader(String csvFileName) {
        this.csvFileName = csvFileName;
    }

    public String[][] loadGridState(int rows, int columns) {
        String[][] gridState = new String[rows][columns];
        File csvFile = new File(csvFileName);
        if (csvFile.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(csvFile));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    int x = Integer.parseInt(parts[0]);
                    int y = Integer.parseInt(parts[1]);
                    String treeType = parts[3];
                    gridState[y][x] = treeType;
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return gridState;
    }

    public void saveGridState(int x, int y, String companyName, String treeType) {
        try {
            FileWriter writer = new FileWriter(csvFileName, true);
            writer.append(x + "," + y + "," + companyName + "," + treeType + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCompanyNameFromGrid(int x, int y, int designatedAreaRows, int designatedAreaColumns) {
        if (x >= 0 && x < designatedAreaColumns && y >= 0 && y < designatedAreaRows) {
            File csvFile = new File(csvFileName);
            if (csvFile.exists()) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(csvFile));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        int xFromFile = Integer.parseInt(parts[0]);
                        int yFromFile = Integer.parseInt(parts[1]);
                        if (xFromFile == x && yFromFile == y) {
                            String output = parts[2];
                            reader.close();
                            return output;
                        }
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}

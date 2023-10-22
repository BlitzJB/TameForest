package database;
import java.io.*;
import java.util.ArrayList;

public class CompanyManager {
    private String companiesFileName;

    public CompanyManager(String companiesFileName) {
        this.companiesFileName = companiesFileName;
    }

    public ArrayList<String> loadCompanies() {
        ArrayList<String> companiesList = new ArrayList<>();
        File companiesFile = new File(companiesFileName);
        if (companiesFile.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(companiesFile));
                String line;
                while ((line = reader.readLine()) != null) {
                    companiesList.add(line);
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return companiesList;
    }

    public void createCompany(String companyName) {
        try {
            FileWriter writer = new FileWriter(companiesFileName, true);
            writer.append(companyName + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateCompany(String oldCompanyName, String newCompanyName) {
        ArrayList<String> companiesList = loadCompanies();
        int index = companiesList.indexOf(oldCompanyName);
        if (index != -1) {
            companiesList.set(index, newCompanyName);
            saveCompanies(companiesList);
        }
    }

    public void deleteCompany(String companyName) {
        ArrayList<String> companiesList = loadCompanies();
        companiesList.remove(companyName);
        saveCompanies(companiesList);
    }

    private void saveCompanies(ArrayList<String> companiesList) {
        try {
            FileWriter writer = new FileWriter(companiesFileName);
            for (String company : companiesList) {
                writer.append(company + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

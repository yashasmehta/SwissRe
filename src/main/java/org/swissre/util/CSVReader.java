package org.swissre.util;

import org.swissre.model.Employee;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    public static List<Employee> readCSV(String fileName) throws IOException {
        List<Employee> employees = new ArrayList<>();
        BufferedReader br = new BufferedReader(
                new FileReader(getFileFromResource(fileName)));

        String line = br.readLine();

        while ((line = br.readLine()) != null && !line.isEmpty()) {
            String[] data = line.split(",");
            Employee employee = new Employee();
            employee.setId(Integer.parseInt(data[0]));
            employee.setFirstName(data[1]);
            employee.setLastName(data[2]);
            employee.setSalary(Double.parseDouble(data[3]));
            employee.setManagerId(Integer.parseInt(data[4]));
            employees.add(employee);
        }
        br.close();
        return employees;
    }

    private static File getFileFromResource(String fileName) {

        ClassLoader classLoader = CSVReader.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return new File(resource.getFile());
        }

    }
}

package org.swissre.service;

import org.swissre.model.Employee;
import org.swissre.util.CSVReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeAnalysisService {
    private static final String FILE_NAME = "Employees.csv";

    public void analyseEmployees() {
        try {
            List<Employee> employees = CSVReader.readCSV(FILE_NAME);
            Map<Integer, Employee> employeeMap = new HashMap<>();

            for(Employee e : employees) {
                employeeMap.put(e.getId(), e);
            }

            System.out.println("Employees earning less....");
            for(Employee e : employees) {
                checkEmployeeEarnLess(e, employeeMap);
            }

            System.out.println("Employees earning more....");
            for(Employee e : employees) {
                checkEmployeeEarnMore(e, employeeMap);
            }

            System.out.println("Employees reporting line....");
            for(Employee e : employees) {
                checkLongEmployeeLine(e, employeeMap);
            }
        } catch (IOException e) {
            throw new RuntimeException("Some error occurred while reading CSV File.. "
                    + e.getMessage());
        }
    }

    private void checkEmployeeEarnLess(Employee e, Map<Integer, Employee> employeeMap) {
        Double employeeSalary = e.getSalary();

        List<Employee> subordinates =
                employeeMap.values()
                        .stream()
                        .filter(employee -> employee.getManagerId() == e.getId())
                        .toList();

        Double totalSubordinateSalary = 0.0;

        totalSubordinateSalary = getTotalSubordinateSalary(subordinates, totalSubordinateSalary);

        Double avgSubordinateSalary = totalSubordinateSalary/subordinates.size();
        if(employeeSalary < (1.2 * avgSubordinateSalary)) {
            System.out.println("Employee with Employee ID "
                    + e.getId() + " earn less than " + ((1.2 * avgSubordinateSalary) - employeeSalary));
        }
    }

    private Double getTotalSubordinateSalary(List<Employee> subordinates, Double totalSubordinateSalary) {
        for(Employee subordinate : subordinates) {
            totalSubordinateSalary += subordinate.getSalary();
        }
        return totalSubordinateSalary;
    }

    private void checkEmployeeEarnMore(Employee e, Map<Integer, Employee> employeeMap) {
        Double employeeSalary = e.getSalary();

        List<Employee> subordinates =
                employeeMap.values()
                        .stream()
                        .filter(employee -> employee.getManagerId() == e.getId())
                        .toList();

        Double totalSubordinateSalary = 0.0;

        totalSubordinateSalary = getTotalSubordinateSalary(subordinates, totalSubordinateSalary);

        Double avgSubordinateSalary = totalSubordinateSalary/subordinates.size();
        if(employeeSalary > (1.5 * avgSubordinateSalary)) {
            System.out.println("Employee with Employee ID "
                    + e.getId() + " earn more than " + (employeeSalary - (1.5 * avgSubordinateSalary)));
        }
    }

    private void checkLongEmployeeLine(Employee employee, Map<Integer, Employee> employeeMap) {
        if(employee.getManagerId() != 0) {
            int managerId = employee.getManagerId();
            int count = 0;
            boolean isCEO = false;

            while(!isCEO) {
                if(employeeMap.containsKey(managerId)) {
                    Employee manager = employeeMap.get(managerId);
                    count++;

                    if(manager.getManagerId() == 0) {
                        isCEO = true;
                    }
                    managerId = manager.getManagerId();
                }
            }

            if(count > 4) {
                System.out.println("Employee with Employee ID " + employee.getId() + " have too long reporting line of " + count);
            }
        }
    }
}

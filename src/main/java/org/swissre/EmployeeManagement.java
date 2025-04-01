package org.swissre;

import org.swissre.service.EmployeeAnalysisService;

public class EmployeeManagement {
    public static void main(String[] args) {
        EmployeeAnalysisService service = new EmployeeAnalysisService();
        service.analyseEmployees();
    }
}
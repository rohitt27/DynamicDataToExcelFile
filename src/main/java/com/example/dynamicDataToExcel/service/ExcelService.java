package com.example.dynamicDataToExcel.service;

import com.example.dynamicDataToExcel.entity.Person;
import com.example.dynamicDataToExcel.repository.PersonRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ExcelService {
    private static final String[] COLUMNS = {"Id", "Name", "Email"};
    private static final String SHEET = "Persons";
    private static final String FILE_PATH = "persons.xlsx";

    @Autowired
    private PersonRepository personRepository;

    public void saveToExcel(List<Person> persons) {
        try (Workbook workbook = getWorkbookInstance()) {
            Sheet sheet = workbook.getSheet(SHEET);
            if (sheet == null) {
                sheet = workbook.createSheet(SHEET);
                createHeaderRow(sheet);
            }

            Set<Person> existingPersons = new HashSet<>(getAllPersonsFromDatabase());
            existingPersons.addAll(persons);
            List<Person> sortedPersons = new ArrayList<>(existingPersons);
            sortedPersons.sort((p1, p2) -> Long.compare(p1.getId(), p2.getId()));

            clearSheetData(sheet);

            int rowNum = 1;
            for (Person person : sortedPersons) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(person.getId());
                row.createCell(1).setCellValue(person.getName());
                row.createCell(2).setCellValue(person.getEmail());
            }

            try (FileOutputStream fileOut = new FileOutputStream(FILE_PATH)) {
                workbook.write(fileOut);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Workbook getWorkbookInstance() throws IOException {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (FileInputStream fileIn = new FileInputStream(file)) {
                return new XSSFWorkbook(fileIn);
            }
        } else {
            return new XSSFWorkbook();
        }
    }

    private void createHeaderRow(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        for (int col = 0; col < COLUMNS.length; col++) {
            Cell cell = headerRow.createCell(col);
            cell.setCellValue(COLUMNS[col]);
        }
    }

    private List<Person> getAllPersonsFromDatabase() {
        return personRepository.findAll();
    }

    private void clearSheetData(Sheet sheet) {
        int lastRowNum = sheet.getLastRowNum();
        for (int i = lastRowNum; i >= 1; i--) {
            sheet.removeRow(sheet.getRow(i));
        }
    }

}

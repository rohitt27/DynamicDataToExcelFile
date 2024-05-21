package com.example.dynamicDataToExcel.controller;

import com.example.dynamicDataToExcel.entity.Person;
import com.example.dynamicDataToExcel.service.ExcelService;
import com.example.dynamicDataToExcel.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonController {
    @Autowired
    private PersonService personService;
    @Autowired
    private ExcelService excelService;

    @PostMapping("person")
    public ResponseEntity<?> createPerson(@RequestBody List<Person> person){
       List<Person> savedPerson = personService.savePersonWithFunction(person);
        excelService.saveToExcel(savedPerson);
        return ResponseEntity.ok("Person Save Successfully in database and Excel file");
    }

}

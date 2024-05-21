package com.example.dynamicDataToExcel.service;

import com.example.dynamicDataToExcel.entity.Person;

import java.util.List;

public interface PersonService {
    List<Person> savePersonWithFunction(List<Person> person);

}

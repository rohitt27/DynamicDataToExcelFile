package com.example.dynamicDataToExcel.service.implement;

import com.example.dynamicDataToExcel.entity.Person;
import com.example.dynamicDataToExcel.repository.PersonRepository;
import com.example.dynamicDataToExcel.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository personRepository;


    @Override
    public List<Person> savePersonWithFunction(List<Person> person) {
      List<Person> personList = new ArrayList<>();
      for (Person person1 : person){
        personRepository.insertPersonFunction(person1.getName(), person1.getEmail());
        Person savedPersons = personRepository.findByNameAndEmail(person1.getName(), person1.getEmail()).get(0);
        personList.add(savedPersons);
      }
        return personList;
    }

}

package com.example.dynamicDataToExcel.repository;

import com.example.dynamicDataToExcel.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query(value = "SELECT insert_person(:name, :email)", nativeQuery = true)

    void insertPersonFunction(@Param("name") String name,@Param("email")String email);

    List<Person> findByNameAndEmail(String name, String email);
}

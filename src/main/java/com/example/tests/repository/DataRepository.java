package com.example.tests.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tests.entity.Data;

public interface DataRepository extends JpaRepository<Data, Long> {
    Optional<Data> findByName(String name);
}

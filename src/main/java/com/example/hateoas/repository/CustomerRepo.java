package com.example.hateoas.repository;


import com.example.hateoas.model.customers.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepo extends JpaRepository<CustomerEntity, Long> {
}

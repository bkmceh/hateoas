package com.example.hateoas.repository;


import com.example.hateoas.model.customers.CustomerEntity;
import com.example.hateoas.model.orders.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrderRepo extends JpaRepository<OrderEntity, Long> {
    Optional<List<OrderEntity>> findAllByCustomerEntity(final CustomerEntity customerEntity);
}

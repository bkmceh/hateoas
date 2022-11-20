package com.example.hateoas.controller;


import com.example.hateoas.model.dto.CustomerDTO;
import com.example.hateoas.model.dto.OrderDTO;
import com.example.hateoas.model.dto.response.CustomerResponse;
import com.example.hateoas.model.dto.response.OrderResponse;
import com.example.hateoas.service.HateoasService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/hateoas")
@RequiredArgsConstructor
public class HateoasController {

    private final HateoasService service;

    @GetMapping("/customers")
    public ResponseEntity<CollectionModel<EntityModel<CustomerResponse>>> getAllCustomers() {
        return ResponseEntity.ok(CollectionModel.of(service.getCustomers().stream()
                .map(element -> EntityModel.of(element,
                        linkTo(methodOn(HateoasController.class).getCustomerById(element.getId())).withSelfRel(),
                        linkTo(methodOn(HateoasController.class).getAllCustomers()).withRel("customers"),
                        linkTo(methodOn(HateoasController.class).getCustomerOrders(element.getId())).withRel("orders")))
                .toList(), linkTo(methodOn(HateoasController.class).getAllCustomers()).withSelfRel()));
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable final Long id) {
        return ResponseEntity.ok(service.getCustomer(id)
                .add(
                        linkTo(HateoasController.class).slash(id).withSelfRel(),
                        linkTo(methodOn(HateoasController.class).getAllCustomers()).withRel("customers"),
                        linkTo(methodOn(HateoasController.class).getCustomerOrders(id)).withRel("orders")));
    }

    @PostMapping("/customers/new")
    public ResponseEntity<CustomerResponse> addNewCustomer(@RequestBody final CustomerDTO customerDTO) {
        final CustomerResponse response = service.addCustomer(customerDTO);
        return ResponseEntity.ok(response
                .add(
                        linkTo(HateoasController.class).slash(response.getId()).withSelfRel(),
                        linkTo(methodOn(HateoasController.class).getAllCustomers()).withRel("customers"),
                        linkTo(methodOn(HateoasController.class).getCustomerOrders(response.getId())).withRel("orders")));
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<OrderResponse> responses = service.getOrders();
        return ResponseEntity.ok(service.getOrders());
    }

    @GetMapping("/orders/customers")
    public ResponseEntity<List<OrderResponse>> getCustomerOrders(@RequestParam("customerId") final Long customerId) {
        return ResponseEntity.ok(service.getOrders(customerId));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable final Long id) {
        return ResponseEntity.ok(service.getOrder(id));
    }

    @PostMapping("/orders/new")
    public ResponseEntity<OrderResponse> addNewOrder(@RequestBody final OrderDTO orderDTO) {
        return ResponseEntity.ok(service.addOrder(orderDTO)
                .add(
                        linkTo(methodOn(HateoasController.class).addNewOrder(orderDTO)).withSelfRel(),
                        linkTo(methodOn(HateoasController.class).getAllOrders()).withRel("orders")
                ));
    }
}

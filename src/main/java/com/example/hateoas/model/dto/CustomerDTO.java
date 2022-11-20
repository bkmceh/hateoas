package com.example.hateoas.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;
}

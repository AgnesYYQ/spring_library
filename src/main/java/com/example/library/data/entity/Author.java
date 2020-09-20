package com.example.library.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Author {

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;
}

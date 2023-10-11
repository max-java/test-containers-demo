package com.tutrit.testcontainerdemo;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

@Data
@Entity
public class MyEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
}

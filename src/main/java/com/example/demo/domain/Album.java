package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Album")
@Getter @Setter
public class Album extends Item{
    private String artist;
    private String etc;
}

package com.demo.entities;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.persistence.GenerationType;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Boy {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "boy_generator")
    @SequenceGenerator(name = "boy_generator", sequenceName = "boy_seq", allocationSize = 500)
    private long id;

    private String name;
    private Integer age;
    private String city;
    private Float height;
    private Float weight;
    private String hobbit;
    private String hairColor;
    private String skill;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getHobbit() {
        return hobbit;
    }

    public void setHobbit(String hobbit) {
        this.hobbit = hobbit;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public Boy() {
    };

    public Boy(Integer id, String name, Integer age, String city, Float height, Float weight, String hobbit, String hairColor, String skill) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.city = city;
        this.height = height;
        this.weight = weight;
        this.hobbit = hobbit;
        this.hairColor = hairColor;
        this.skill = skill;
    }


}
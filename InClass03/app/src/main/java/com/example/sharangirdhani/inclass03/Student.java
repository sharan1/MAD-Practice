package com.example.sharangirdhani.inclass03;

import java.io.Serializable;

/**
 * Created by sharangirdhani on 7/3/17.
 */

public class Student implements Serializable {
    public String name;
    public String email;
    public String department;
    public String mood;
    public boolean state;

    public Student(String name, String email, String department, String mood) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.mood = mood;
        this.state = true;
    }
}

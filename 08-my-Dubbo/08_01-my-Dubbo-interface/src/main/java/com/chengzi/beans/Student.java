package com.chengzi.beans;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *Desc:
 *@author:chengli
 *@date:2020/12/15 11:29
 */
public class Student implements Serializable {
    private Integer id;
    private String name;
    private int age;
    private double score;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", score=" + score +
                '}';
    }
    public Student(){}
    public Student(Integer id, String name, int age, double score) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.score = score;
    }

    public Student(BigDecimal id, String name, String age, BigDecimal score) {
        this.id = id.intValue();
        this.name = name;
        this.age = Integer.valueOf(age);
        this.score = score.doubleValue();
    }
}


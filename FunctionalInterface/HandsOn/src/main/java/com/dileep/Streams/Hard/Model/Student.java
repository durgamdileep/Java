package com.dileep.Streams.Hard.Model;

import java.util.List;

public class Student {
    String name;
    int grade; // 1 to 12
    List<Integer> scores; // e.g., [85, 90, 78]
    // constructor, getters


    public Student() {
    }

    public Student(String name, int grade, List<Integer> scores) {
        this.name = name;
        this.grade = grade;
        this.scores = scores;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public List<Integer> getScores() {
        return scores;
    }

    public void setScores(List<Integer> scores) {
        this.scores = scores;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", grade=" + grade +
                ", scores=" + scores +
                '}';
    }
}

package com.dileep.Streams.Hard.Model;

import java.util.List;

public class Project {
    String name;
    List<Employee> team; // employees working on this project

    public Project(String name, List<Employee> team) {
        this.name = name;
        this.team = team;
    }

    public String getName() { return name; }
    public List<Employee> getTeam() { return team; }
}

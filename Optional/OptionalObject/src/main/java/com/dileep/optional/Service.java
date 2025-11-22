package com.dileep.optional;

import com.dileep.optional.DB.Database;
import com.dileep.optional.Model.Department;
import com.dileep.optional.Model.Employee;

import java.util.Optional;

public class Service {
    private final Database db;
    public  Service(Database db){
        this.db=db;
    }

//    public  static void orElseMethod(Employee employee){
//        employee.setDepartment(new Department());
//        Department department= Optional.of(employee.getDepartment()).orElse(new Department());
//        System.out.println(department);
//    }

    public  static void orElseThrowMethod(Employee employee){
        Department d= Optional.ofNullable(employee.getDepartment())
                .orElseThrow(()-> new IllegalArgumentException("No Department Exists"));
        System.out.println(d.toString());
    }


}

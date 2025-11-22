package com.dileep.optional.DB;

import com.dileep.optional.Model.Department;
import com.dileep.optional.Model.Employee;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private List<Employee> employees;

    public Database() {
        employees = new ArrayList<>();

        // Create Department objects
        Department hr = new Department(1, "Human Resources");
        Department it = new Department(2, "IT");
        Department finance = new Department(3, "Finance");
        Department sales = new Department(4, "Sales");

        // Add 10 Employees with various departments
        employees.add(new Employee(101, "Alice Johnson", 65000.0, hr));
        employees.add(new Employee(102, "Bob Smith", 72000.0, it));
        employees.add(new Employee(103, "Carol White", 68000.0, finance));
        employees.add(new Employee(104, "David Brown", 71000.0, it));
        employees.add(new Employee(105, "Eva Green", 66000.0, sales));
        employees.add(new Employee(106, "Frank Black", 69000.0, finance));
        employees.add(new Employee(107, "Grace Lee", 64000.0, hr));
        employees.add(new Employee(108, "Henry Kim", 73000.0, it));
        employees.add(new Employee(109, "Ivy Walker", 67000.0, sales));
        employees.add(new Employee(110, "Jack Taylor", 70000.0, finance));
    }

    public List<Employee> getAllEmployees() {
        return employees;
    }
    public  Employee  add(Employee e){
        employees.add(e);
        return  e;
    }

    // Optionally, get employees by department name
    public List<Employee> getEmployeesByDepartment(String deptName) {
        List<Employee> result = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.getDepartment().getName().equalsIgnoreCase(deptName)) {
                result.add(emp);
            }
        }
        return result;
    }
}

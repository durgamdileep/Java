package com.dileep.optional;

import com.dileep.optional.Model.Department;
import com.dileep.optional.Model.Employee;

import java.util.Optional;

public class Main {
    public  static  void main(String[] args){
        Employee newEmployee= new Employee(111,"Virat",800000.00,null);

        /**
         * Optional object is used to Avoid the NullPointerException
         *    -> creation of Optional Object can be in 3 ways
         *    -> all these three methods are static of Optional Class
         *
         *         -> empty() : empty method create an empty optional object
         *         -> of()    : of method used when the value which is not null ,for suppose if the value is null then it will throe NUllPointerException.
         *            of() method is used when we know that value will not be a null
         *         -> ofNullable() : ofNullable method check the value if it is not null then returns the value
         *                           if the value is null then it will return empty object
         *             ofNullable() method is used when we are not sure about the value whether it is null or not null
         *             it is safer to avoid the null pointer exception
         *
         *  Utility methods
         *  ---------------
         *
         *  1. get() : is used to get the value of an optional object
         *           for suppose if the value is empty object then it will throw an exception
         *            NoSuchElementException: No value present
         *
         *  2. isPresent() :
         *
         */


        /**
         * empty() returns the Optional.empty object
         *
         */
        Optional<Object> emptyObject= Optional.empty();
        System.out.println(emptyObject); // returns Optional.empty
        /**
         * to get the exact value we use get() method
         * but we use get() on empty object then it will throw an exception
         * NoSuchElementException: No value present
         */
        // System.out.println(emptyObject.get());

        /**
         * To avoid the NoSuchElementException: No value present when we Value is empty
         * before printing the getting the value check whether the object contains value or not
         * by using
         * isPresent() -> check whether the value is empty or not
         * if it present returns true , if not returns false
         */
        System.out.println(emptyObject.isPresent()?emptyObject.get():" this object doesn't contains any value");


        /**
         * of() method will check the value whether it is null or not
         * if it is null it will throw an NullPointerException
         * if not it return the value
         */
       // Optional<Department> department= Optional.of(newEmployee.getDepartment());

        // System.out.println(department); // throw NullPointerException because in newEmployee object we assign the department as null

        /**
         * To avoid NullPointerException then use ofNullable method
         * if the value is null then it will return empty object
         * if not it will return the value
         */
        Optional<Department> optionalDepartment= Optional.ofNullable(newEmployee.getDepartment());

         System.out.println(optionalDepartment.isPresent()?optionalDepartment.get(): " Department object is empty");

        System.out.println(optionalDepartment.isEmpty()? " Department object is empty": optionalDepartment.get());



        //Service.orElseMethod(newEmployee);

        Service.orElseThrowMethod(newEmployee);



    }
}

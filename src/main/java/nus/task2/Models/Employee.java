package nus.task2.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private String name;
    private int age;
    private String jobTitle;
    private int salary;
}

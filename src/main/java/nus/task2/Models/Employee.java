package nus.task2.Models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable{
    private static final long serialVersionUID = 1L;
    public Employee(int i, String string, int j, String string2, int k) {
    }
    private String name;
    private int age;
    private String jobTitle;
    private int salary;
    private int id;
}

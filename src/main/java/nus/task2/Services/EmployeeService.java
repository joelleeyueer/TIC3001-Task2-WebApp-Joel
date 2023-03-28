package nus.task2.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nus.task2.Models.Employee;
import nus.task2.Repositories.EmployeeRepository;

@Service
public class EmployeeService {
    
    @Autowired
    EmployeeRepository employeeRepositories;

    

    public List<Employee> getAllEmployees(int limit, int offset){
        return employeeRepositories.findAllEmployees(limit, offset);
    }

    public Employee getFindEmployeeById(int id){
        return employeeRepositories.findEmployeeById(id);
    }

    public Boolean getInsertEmployee(Employee employee){
        return employeeRepositories.insertEmployee(employee);
    }

    public int getUpdateSalaryByID(int id, int salary){
        return employeeRepositories.updateSalaryByID(id, salary);
    }

    public int getDeleteEmployeeById(int id){
        return employeeRepositories.deleteById(id);
    }
}

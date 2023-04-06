package nus.task2.Restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import nus.task2.Models.Employee;
import nus.task2.Services.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/all")
    public List<Employee> getAllCustomers(@RequestParam(defaultValue = "5", required=false) int limit, @RequestParam(defaultValue = "0", required=false) int offset){
        return employeeService.getAllEmployees(limit, offset);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getOneCustomerById(@PathVariable("id") int id ){
        Employee incomingCustomer = employeeService.getFindEmployeeById(id);

        if (incomingCustomer == null){
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "customer "+ id + " not found");
            }

        return new ResponseEntity<Employee>(incomingCustomer, HttpStatus.OK);
    }
//q: insertemployee with queryparam
//a: use requestparam
    @PostMapping("/insert")
    public ResponseEntity<Employee> insertEmployee(@RequestParam("name") String name, @RequestParam("age") int age, @RequestParam("jobTitle") String jobTitle, @RequestParam("salary") int salary){
        Employee employee = new Employee();
        employee.setName(name);
        employee.setAge(age);
        employee.setJobTitle(jobTitle);
        employee.setSalary(salary);
        Boolean result = employeeService.getInsertEmployee(employee);

        if (result == false){
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "employee not inserted");
            }

            System.out.println("Successfully inserted " + name + " with age " + age + " with jobTitle " + jobTitle + " with salary " + salary);
        return new ResponseEntity<Employee>(employee, HttpStatus.OK);
    }

    //q: putmapping code for localhost:8080/api/employees/all?id=1&salary=1000
    //a: use requestparam
    //q: how to us requestparam for id

    @PutMapping("/update")
    public ResponseEntity<Employee> updateSalaryByID(@RequestParam("id") int id, @RequestParam("salary") int salary){
        int result = employeeService.getUpdateSalaryByID(id, salary);

        if (result == 0){
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "employee not updated");
            }
        System.out.println("Successfully updated " + id + " with salary " + salary);
        Employee employee = employeeService.getFindEmployeeById(id);
        return new ResponseEntity<Employee>(employee, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> deleteEmployeeById(@PathVariable("id") int id){
        Employee employee = employeeService.getFindEmployeeById(id);
        int result = employeeService.getDeleteEmployeeById(id);

        if (result == 0){
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "employee not deleted");
            }

        System.out.println("Successfully deleted " + id);

        return new ResponseEntity<Employee>(employee, HttpStatus.OK);
    }



}

 

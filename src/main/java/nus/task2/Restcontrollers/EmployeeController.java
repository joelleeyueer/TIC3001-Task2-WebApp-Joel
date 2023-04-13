package nus.task2.Restcontrollers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Object> getOneCustomerById(@PathVariable("id") int id ){
        Employee incomingCustomer = employeeService.getFindEmployeeById(id);

        if (incomingCustomer == null){
            // throw new ResponseStatusException(
            //     HttpStatus.NOT_FOUND, "customer "+ id + " not found");
            return new ResponseEntity<>("Employee ID "+ id + " is not found", HttpStatus.NOT_FOUND);
            }

        return ResponseEntity.ok().body(incomingCustomer);
    }
//q: insertemployee with queryparam
//a: use requestparam
    @PostMapping("/insert")
    public ResponseEntity<Object> insertEmployee(@RequestParam("name") String name, @RequestParam("age") String ageStr, @RequestParam("jobTitle") String jobTitle, @RequestParam("salary") String salaryStr){
        try {
            Integer.parseInt(ageStr);
            Integer.parseInt(salaryStr);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Age or Salary is invalid, Employee is not inserted.", HttpStatus.BAD_REQUEST);
        }
        int age = Integer.parseInt(ageStr);
        int salary = Integer.parseInt(salaryStr);
        if ( name == null || name.length() == 0 || age <= 0 || jobTitle == null || jobTitle.length() == 0 || salary <= 0) {
            return new ResponseEntity<>("One of your parameters is invalid, Employee is not inserted.", HttpStatus.BAD_REQUEST);

        }
        System.out.println("in here");
        Employee employee = new Employee();
        employee.setName(name);
        employee.setAge(age);
        employee.setJobTitle(jobTitle);
        employee.setSalary(salary);
        int incomingId = employeeService.getInsertEmployee(employee);

        if (incomingId == 0){
            return new ResponseEntity<>("One of your parameters is invalid, Employee is not inserted.", HttpStatus.BAD_REQUEST);

            }
        employee.setId(incomingId);
        System.out.println("Successfully inserted " + name + " with age " + age + " with jobTitle " + jobTitle + " with salary " + salary + " with id " + incomingId);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "Successfully added employee");
        responseBody.put("employee", employee);

        return ResponseEntity.ok().body(responseBody);
    }

    //q: putmapping code for localhost:8080/api/employees/all?id=1&salary=1000
    //a: use requestparam
    //q: how to us requestparam for id

    @PutMapping("/update")
    public ResponseEntity<Object> updateSalaryByID(@RequestParam("id") int id, @RequestParam("salary") String salaryString){

        try {
            Integer.parseInt(salaryString);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Salary is invalid, Employee is not inserted.", HttpStatus.BAD_REQUEST);
        }

        int salary = Integer.parseInt(salaryString);
        if (salary <= 0) {
            return new ResponseEntity<>("Please input a valid salary (>0).", HttpStatus.BAD_REQUEST);

        }
        int result = employeeService.getUpdateSalaryByID(id, salary);

        if (result == 0){
            return new ResponseEntity<>("Employee ID "+ id + " is not found. Salary is not updated.", HttpStatus.NOT_FOUND);

            }
        System.out.println("Successfully updated " + id + " with salary " + salary);
        Employee employee = employeeService.getFindEmployeeById(id);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "Successfully updated " + id + " with salary " + salary);
        responseBody.put("employee", employee);

        return ResponseEntity.ok().body(responseBody);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmployeeById(@PathVariable("id") int id){
        Employee employee = employeeService.getFindEmployeeById(id);
        int result = employeeService.getDeleteEmployeeById(id);

        if (result == 0){
            return new ResponseEntity<>("Employee ID "+ id + " is not found. Employee is not deleted.", HttpStatus.NOT_FOUND);

            }

        System.out.println("Successfully deleted " + id);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "Successfully deleted " + id);
        responseBody.put("employee", employee);

        return ResponseEntity.ok().body(responseBody);
    }



}

 

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
    @PostMapping("/insert/{name}/{age}/{jobTitle}/{salary}")
    public ResponseEntity<Employee> insertEmployee(@PathVariable("name") String name, @PathVariable("age") int age, @PathVariable("jobTitle") String jobTitle, @PathVariable("salary") int salary){
        Employee employee = new Employee(name, age, jobTitle, salary);
        Boolean result = employeeService.getInsertEmployee(employee);

        if (result == false){
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "employee not inserted");
            }

        return new ResponseEntity<Employee>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateSalaryByID(@PathVariable("id") int id, @RequestParam int salary){
        int result = employeeService.getUpdateSalaryByID(id, salary);

        if (result == 0){
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "employee not updated");
            }
            System.out.println("Successfully updated " + id + " with salary " + salary);
        return new ResponseEntity<Employee>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> deleteEmployeeById(@PathVariable("id") int id){
        int result = employeeService.getDeleteEmployeeById(id);

        if (result == 0){
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "employee not deleted");
            }

        System.out.println("Successfully deleted " + id);

        return new ResponseEntity<Employee>(HttpStatus.OK);
    }



}

 

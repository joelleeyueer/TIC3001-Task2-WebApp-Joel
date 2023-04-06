package nus.day21_practice_joel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import nus.task2.Day21PracticeJoelApplication;
import nus.task2.Models.Employee;
import nus.task2.Repositories.EmployeeRepository;
import nus.task2.Services.EmployeeService;

@SpringBootTest(classes = Day21PracticeJoelApplication.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void getAllEmployees_returnsListOfEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Bluey", 25, "Train Conductor", 5000));
        employees.add(new Employee(2, "Bingo", 30, "Architect", 8000));
        Mockito.when(employeeRepository.findAllEmployees(10, 0)).thenReturn(employees);
        List<Employee> result = employeeService.getAllEmployees(10, 0);
        assertEquals(employees, result);
    }

    @Test
    public void getFindEmployeeById_returnsEmployee() {
        int id = 1;
        Employee employee = new Employee(1, "Bluey", 25, "Teacher", 50000);
        Mockito.when(employeeRepository.findEmployeeById(id)).thenReturn(employee);
        Employee result = employeeService.getFindEmployeeById(id);
        assertEquals(employee, result);
    }

    @Test
    public void getInsertEmployee_returnsTrue() {
        Employee employee = new Employee(1, "John", 25, "Developer", 5000);
        Mockito.when(employeeRepository.insertEmployee(employee)).thenReturn(true);
        Boolean result = employeeService.getInsertEmployee(employee);
        assertTrue(result);
    }

    @Test
    public void getUpdateSalaryByID_returnsOne() {
        int id = 1;
        int salary = 6000;
        Mockito.when(employeeRepository.updateSalaryByID(id, salary)).thenReturn(1);
        int result = employeeService.getUpdateSalaryByID(id, salary);
        assertEquals(1, result);
    }

    @Test
    public void getDeleteEmployeeById_returnsOne() {
        int id = 1;
        Mockito.when(employeeRepository.deleteById(id)).thenReturn(1);
        int result = employeeService.getDeleteEmployeeById(id);
        assertEquals(1, result);
    }
}


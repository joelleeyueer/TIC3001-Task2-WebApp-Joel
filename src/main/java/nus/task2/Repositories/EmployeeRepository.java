package nus.task2.Repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import nus.task2.Models.Employee;

@Repository
public class EmployeeRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    final String findAllEmployees_SQL = "SELECT * FROM Employee LIMIT ? OFFSET ?";
    final String findEmployeeId_SQL = "SELECT * FROM Employee WHERE id = ?";
    private final String deleteByIdSQL = "DELETE FROM Employee WHERE id = ?";
    private final String insertEmployeeSQL = "INSERT INTO Employee (name, age, job_title, salary) VALUES (?,?,?,?)";
    private final String updateSalaryByIDSQL = "UPDATE Employee SET salary = ? WHERE id = ?";


    public List<Employee> findAllEmployees(int limit, int offset){

        List<Employee> result = new ArrayList<>();
        
            result = jdbcTemplate.query(findAllEmployees_SQL, BeanPropertyRowMapper.newInstance(Employee.class), limit, offset);
            return result;

            
    }

    public Employee findEmployeeById(int id){


        try {
            Employee customer = jdbcTemplate.queryForObject(findEmployeeId_SQL, BeanPropertyRowMapper.newInstance(Employee.class), id);
            return customer;

        } catch (Exception e) {
            // throw new DataAccessResourceFailureException("Customer id " + id + " not found!");
            System.err.println(e.getMessage());
            return null;
        }
    }

    public int insertEmployee(Employee employee){

        try {
            KeyHolder generatedKeyHolder = new GeneratedKeyHolder(); 
            PreparedStatementCreator psc = new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement ps = con.prepareStatement(insertEmployeeSQL, new String[] {"id"});
                    ps.setString(1, employee.getName());
                    ps.setInt(2, employee.getAge());
                    ps.setString(3, employee.getJobTitle());
                    ps.setInt(4, employee.getSalary());
                    return ps;
                }
            };
            jdbcTemplate.update(psc, generatedKeyHolder);
    
            Integer empId = generatedKeyHolder.getKey().intValue();
            System.out.println("Employee has been inserted. Task: "+ employee + "\nEmployeeId added is " + empId);
            return empId;

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return 0;
        }

        
        
    }

    
    public int updateSalaryByID(int id, int salary) {
        try {
            int updated = 0;

            updated = jdbcTemplate.update(updateSalaryByIDSQL, new PreparedStatementSetter() {
                // private String name;
                // private int age;
                // private String jobTitle;
                // private int salary;
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, salary);
                    ps.setInt(2, id);
                }
            });
    
            return updated;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return 0;
        }
        
    }

    public int deleteById(int id) {
        try {
            int deleted = 0;

            deleted = jdbcTemplate.update(deleteByIdSQL, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, id);
                }
            });
    
            return deleted;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return 0;
        }
        
    }
    
}

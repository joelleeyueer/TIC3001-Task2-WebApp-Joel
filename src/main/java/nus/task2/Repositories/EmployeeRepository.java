package nus.task2.Repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

            //q: rg.springframework.jdbc.BadSqlGrammarException: PreparedStatementCallback; bad SQL grammar [SELECT * FROM Employee LIMIT ? OFFSET ?]; nested exception is java.sql.SQLSyntaxErrorException: Table 'Employee' doesn't exist
            //a: https://stackoverflow.com/questions/50093144/spring-boot-jdbc-badsqlgrammarexception-bad-sql-grammar-select-from-employe

    }

    public Employee findEmployeeById(int id){


        try {
            Employee customer = jdbcTemplate.queryForObject(findEmployeeId_SQL, BeanPropertyRowMapper.newInstance(Employee.class), id);
            return customer;

        } catch (DataAccessException daex) {
            // throw new DataAccessResourceFailureException("Customer id " + id + " not found!");
            return null;
        }
    }

    public Boolean insertEmployee(Employee employee){
        int inserted = 0;

        inserted = jdbcTemplate.update(insertEmployeeSQL, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, employee.getName());
                ps.setInt(2, employee.getAge());
                ps.setString(3, employee.getJobTitle());
                ps.setInt(4, employee.getSalary());
            }
        });

        return inserted>0?true:false;
    }

    
    public int updateSalaryByID(int id, int salary) {
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
    }

    public int deleteById(int id) {
        int deleted = 0;

        deleted = jdbcTemplate.update(deleteByIdSQL, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, id);
            }
        });

        return deleted;
    }
    
}

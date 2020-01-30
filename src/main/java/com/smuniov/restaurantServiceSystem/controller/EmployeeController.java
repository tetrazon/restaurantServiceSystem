package com.smuniov.restaurantServiceSystem.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.smuniov.restaurantServiceSystem.DTO.Transfer.UserDataAccess;
import com.smuniov.restaurantServiceSystem.DTO.UserDTO;
import com.smuniov.restaurantServiceSystem.entity.users.Employee;
import com.smuniov.restaurantServiceSystem.service.impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl employeeService;

    @GetMapping(value="/all")
    @JsonView(UserDataAccess.class)
    public @ResponseBody List getEmployees(){
        List all = employeeService.getAll();
        List<UserDTO> employeesDto = new UserDTO<Employee>().toDTO(all);
        return employeesDto;
    }

    @GetMapping(value="/{id}")
   // @RequestMapping(value="/{id}", method= RequestMethod.GET, produces="application/json")
    public Optional getEmployee(@PathVariable int id){
        return employeeService.findById(id);
    }

    @DeleteMapping(value="/{id}")
    //@RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity deleteEmployee(@PathVariable int id){
        employeeService.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value="/new")
    //@RequestMapping(value="/new", method= RequestMethod.POST)
    public  ResponseEntity addEmployee(@RequestBody Employee employee){

        if(employee == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        employeeService.create(employee);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping()
    //@RequestMapping(method= RequestMethod.PUT)
    public  ResponseEntity updateEmployee(@RequestBody Employee employee){
        if (employee == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        employeeService.update(employee);
        return new ResponseEntity(HttpStatus.OK);
    }




}

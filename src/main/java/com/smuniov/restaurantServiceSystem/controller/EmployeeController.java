package com.smuniov.restaurantServiceSystem.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.smuniov.restaurantServiceSystem.DTO.Transfer.UserDataAccess;
import com.smuniov.restaurantServiceSystem.DTO.UserDTO;
import com.smuniov.restaurantServiceSystem.entity.users.Employee;
import com.smuniov.restaurantServiceSystem.service.impl.EmployeeServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeServiceImpl employeeService;

    public EmployeeController(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(value="/all")
    //@JsonView(UserDataAccess.class)//!!! write own JSON builder
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<Page> getEmployees(@PageableDefault(page=0, size = 3, sort = "name") Pageable pageable){//@ResponseBody List
        List all = employeeService.getAll();
        List<UserDTO> employeesDto = new UserDTO<Employee>().toDTO(all);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), employeesDto.size());//(start + pageable.getPageSize()) > users.size() ? users.size() : (start + pageable.getPageSize())
        Page<UserDTO> employeeDTOPage= new PageImpl<>(employeesDto.subList(start, end), pageable, employeesDto.size());
        return new ResponseEntity<>(employeeDTOPage, HttpStatus.FOUND);
    }

    @GetMapping(value="/{id}")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
   // @RequestMapping(value="/{id}", method= RequestMethod.GET, produces="application/json")
    public Optional getEmployee(@PathVariable int id){
        return employeeService.findById(id);
    }

    @DeleteMapping(value="/{id}")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public ResponseEntity deleteEmployee(@PathVariable int id){
        employeeService.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value="/new")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public  ResponseEntity addEmployee(@RequestBody Employee employee){

        if(employee == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        employeeService.create(employee);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping()
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public  ResponseEntity updateEmployee(@RequestBody Employee employee){
        if (employee == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        employeeService.update(employee);
        return new ResponseEntity(HttpStatus.OK);
    }




}

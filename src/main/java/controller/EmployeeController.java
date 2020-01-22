package controller;

import entity.users.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.hardCode.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService){
     this.employeeService = employeeService;
    }

    @GetMapping(value="/all/{id}")
   //@RequestMapping(value="/all/{id}", method= RequestMethod.GET, produces="application/json")// this method will only handle requests where JSON output is expected.
    public @ResponseBody List<Employee> getEmployees(@PathVariable int id){
        return employeeService.getAllEmployees(id);
    }

    @GetMapping(value="/{id}")
   // @RequestMapping(value="/{id}", method= RequestMethod.GET, produces="application/json")
    public  Employee getEmployee(@PathVariable int id){
        return employeeService.getEmployeeById(id);
    }

    @DeleteMapping(value="/{id}")
    //@RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity deleteClient(@PathVariable int id){
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value="/new")
    //@RequestMapping(value="/new", method= RequestMethod.POST)
    public  ResponseEntity addClient(@RequestBody Employee employee){
        employeeService.create(employee);
        if(employee == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping()
    //@RequestMapping(method= RequestMethod.PUT)
    public  ResponseEntity updateClient(@RequestBody Employee employee){
        if (employee == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        employeeService.updateEmployee(employee);
        return new ResponseEntity(HttpStatus.OK);
    }




}

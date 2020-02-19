package com.smuniov.restaurantServiceSystem.controller;

import com.smuniov.restaurantServiceSystem.entity.food.Dish;
import com.smuniov.restaurantServiceSystem.entity.order.Table;
import com.smuniov.restaurantServiceSystem.service.OrderServiceI;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/restaurant")
@RolesAllowed("MANAGER")
public class RestaurantController {

    private final OrderServiceI orderService;

    public RestaurantController(OrderServiceI orderService) {
        this.orderService = orderService;
    }

    @PostMapping("dishes/new")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public ResponseEntity addDish(@RequestBody Dish dish){
        orderService.addSaveUpdateDish(dish);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/dishes")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public ResponseEntity getAllDishes(Pageable pageable){
        List<Dish> dishes = orderService.getAllDishes();
        Page<Dish> dishPage = new PageImpl<>(dishes, pageable, dishes.size());
        return new ResponseEntity(dishPage, HttpStatus.FOUND);
    }

    @GetMapping("/dishes/{dishId}")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public ResponseEntity getDish(@PathVariable int dishId){
        return new ResponseEntity(orderService.getDish(dishId), HttpStatus.FOUND);
    }

    @PutMapping("/dishes/{dishId}/update")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public ResponseEntity updateDish(@PathVariable int dishId, @RequestBody Dish dish){
        orderService.addSaveUpdateDish(dish);
        return new ResponseEntity(orderService.getDish(dishId), HttpStatus.OK);
    }

    @PostMapping("dishes/{dishId}")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public ResponseEntity deleteDish(@PathVariable int dishId){
        orderService.deleteDish(dishId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value="/tables")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public ResponseEntity getTables(Pageable pageable){
        List<Table> tables = orderService.getAllTables();
        Page<Table> tablesPage = new PageImpl<>(tables, pageable, tables.size());
        return new ResponseEntity(tablesPage, HttpStatus.FOUND);
    }

    @PostMapping("tables/new")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public ResponseEntity addTable(@RequestBody Table table){
        orderService.createTable(table);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("tables")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public ResponseEntity updateTable( @RequestBody Table table){
        orderService.updateTable(table);
        return new ResponseEntity(HttpStatus.OK);
    }

}

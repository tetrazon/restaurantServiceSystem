package com.smuniov.restaurantServiceSystem.controller;

import com.smuniov.restaurantServiceSystem.entity.food.Dish;
import com.smuniov.restaurantServiceSystem.repository.DishRepository;
import com.smuniov.restaurantServiceSystem.service.OrderServiceI;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/restaurant")
@RolesAllowed("MANAGER")
public class RestaurantController {

    @Autowired
    private OrderServiceI orderService;

    @PostMapping("dishes/new")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public ResponseEntity addDish(@RequestBody Dish dish){
        orderService.addSaveUpdateDish(dish);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/dishes")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public ResponseEntity getAllDishes(){
        return new ResponseEntity(orderService.getAllDishes(), HttpStatus.OK);
    }

    @GetMapping("/dishes/{dishId}")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
    public ResponseEntity getDish(@PathVariable int dishId){
        return new ResponseEntity(orderService.getDish(dishId), HttpStatus.OK);
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

}

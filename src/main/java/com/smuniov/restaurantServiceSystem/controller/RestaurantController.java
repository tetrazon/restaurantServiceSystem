package com.smuniov.restaurantServiceSystem.controller;

import com.smuniov.restaurantServiceSystem.entity.food.Dish;
import com.smuniov.restaurantServiceSystem.repository.DishRepository;
import com.smuniov.restaurantServiceSystem.service.OrderServiceI;
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
    public ResponseEntity addDish(@RequestBody Dish dish){
        orderService.addSaveUpdateDish(dish);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/dishes")
    public ResponseEntity getAllDishes(){
        return new ResponseEntity(orderService.getAllDishes(), HttpStatus.OK);
    }

    @GetMapping("/dishes/{dishId}")
    public ResponseEntity getDish(@PathVariable int dishId){
        return new ResponseEntity(orderService.getDish(dishId), HttpStatus.OK);
    }

    @PutMapping("/dishes/{dishId}/update")
    public ResponseEntity updateDish(@PathVariable int dishId, @RequestBody Dish dish){
        orderService.addSaveUpdateDish(dish);
        return new ResponseEntity(orderService.getDish(dishId), HttpStatus.OK);
    }

    @PostMapping("dishes/{dishId}")
    public ResponseEntity deleteDish(@PathVariable int dishId){
        orderService.deleteDish(dishId);
        return new ResponseEntity(HttpStatus.OK);
    }

}

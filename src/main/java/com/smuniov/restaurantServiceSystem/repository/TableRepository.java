package com.smuniov.restaurantServiceSystem.repository;

import com.smuniov.restaurantServiceSystem.entity.order.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<Table, Integer>{
    //List<Table> findAll();
    List<Table> findAllByIsReserved(boolean isReserved);
}

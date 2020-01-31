package com.smuniov.restaurantServiceSystem.repository;

import com.smuniov.restaurantServiceSystem.entity.order.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<Table, Integer>{
    List<Table> findAllByIsReserved(boolean isReserved);
    Table findById(int id);
    @Modifying
    @Query("UPDATE Table set isReserved = :state where id = :tableId")
    void changeTableStatus(@Param("state") boolean state, @Param("tableId") int tableId);

}

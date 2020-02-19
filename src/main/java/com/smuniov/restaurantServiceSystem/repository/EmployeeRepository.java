package com.smuniov.restaurantServiceSystem.repository;

import com.smuniov.restaurantServiceSystem.entity.enumeration.Position;
import com.smuniov.restaurantServiceSystem.entity.users.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Employee findByEmail(String email);

    List findAllByOrderByLoadFactorAsc();
    List findAllByPositionOrderByLoadFactorAsc(Position position);
    boolean existsByEmail(String email);
}

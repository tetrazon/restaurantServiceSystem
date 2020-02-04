package com.smuniov.restaurantServiceSystem.repository;

import com.smuniov.restaurantServiceSystem.entity.users.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Client findByEmail(String email);
    boolean existsByEmail(String email);
    //public Optional<Client> findById(Integer id);
}

package com.smuniov.restaurantServiceSystem.security;

import com.smuniov.restaurantServiceSystem.entity.users.Client;
import com.smuniov.restaurantServiceSystem.entity.users.Employee;
import com.smuniov.restaurantServiceSystem.repository.ClientRepository;
import com.smuniov.restaurantServiceSystem.repository.EmployeeRepository;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(UserDetailsServiceImpl.class.getName());
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client client = clientRepository.findByEmail(email);
        if(client != null) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_CLIENT"));
            logger.info("user role: CLIENT");
            return new User(client.getEmail(), client.getPassword(), authorities);
        }  else if(client ==  null) {
            Employee employee = employeeRepository.findByEmail(email);
            List<GrantedAuthority> authorities = new ArrayList<>();
            logger.info("user role: " + employee.getPosition().toUpperCase());
            authorities.add(new SimpleGrantedAuthority("ROLE_" + employee.getPosition().toUpperCase()));
            return new User(employee.getEmail(), employee.getPassword(), authorities);
        }throw new UsernameNotFoundException(
                "UserDataAccess '" + email + "' not found.");

    }
}

package com.aisa.itservice.testcarwash.Services;


import com.aisa.itservice.testcarwash.Exceptions.UserAlreadyExistException;
import com.aisa.itservice.testcarwash.Repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("customUserDetailService")
public class CustomUserDetailService implements UserDetailsService, IUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var customer = userRepository.getUserByEmail(email);
        if (customer == null) {
            throw new UsernameNotFoundException(email);
        }
        if(customer.getAdmin()){
            UserDetails user = User.withUsername(customer.getEmail())
                    .password(customer.getPassword())
                    .authorities("ROLE_ADMIN").build();
            return user;
        }
        else {
            UserDetails user = User.withUsername(customer.getEmail())
                    .password(customer.getPassword())
                    .authorities("ROLE_USER").build();
            return user;
        }
    }

    @Override
    public com.aisa.itservice.testcarwash.Entites.User registerNewUser(com.aisa.itservice.testcarwash.Entites.User user) throws UserAlreadyExistException {
        if (emailExist(user.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: "
                    + user.getEmail());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    public com.aisa.itservice.testcarwash.Entites.User getUserByUserEmail(String email) throws UsernameNotFoundException {
        var customer = userRepository.getUserByEmail(email);
        if (customer == null) {
            throw new UsernameNotFoundException(email);
        }
        return customer;
    }

    @Override
    public boolean emailExist(String email) {
        return userRepository.getUserByEmail(email) != null;
    }
}

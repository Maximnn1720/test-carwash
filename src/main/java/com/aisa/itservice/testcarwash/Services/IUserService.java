package com.aisa.itservice.testcarwash.Services;

import com.aisa.itservice.testcarwash.Entites.User;
import com.aisa.itservice.testcarwash.Exceptions.UserAlreadyExistException;

public interface IUserService {
    User registerNewUser(User user) throws UserAlreadyExistException;
    User getUserByUserEmail(String email);
    boolean emailExist(String email);
}

package com.aisa.itservice.testcarwash.Services;

import com.aisa.itservice.testcarwash.Entites.User;

public interface IUserService {
    User registerNewUser(User user);
    User getUserByUserEmail(String email);
}

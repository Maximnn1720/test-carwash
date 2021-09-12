package com.aisa.itservice.testcarwash.Repositories;

import com.aisa.itservice.testcarwash.Entites.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends CrudRepository<User, Integer> {

    @Query(value = "SELECT * FROM users  where e_mail = :email", nativeQuery = true)
    User getUserByEmail(@Param("email") String email);
}

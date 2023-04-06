package com.octa.userservice.port.persistence;

import com.octa.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRespository extends JpaRepository<User,Long> {

    User findByUsername(String username);

    User findByEmail(String email);

    User findByUserUuid(String userUuid);
}

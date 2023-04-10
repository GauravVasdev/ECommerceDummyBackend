package com.octa.userservice.port.persistence;

import com.octa.userservice.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITokenRepository extends JpaRepository<Token, Long> {
}

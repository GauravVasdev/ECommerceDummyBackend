package com.octa.adminservice.port.persistence;

import com.octa.adminservice.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IAdminRepository extends JpaRepository<Card,Long> {
    Optional<Card> findByCardUuid(String cardUuid);
}

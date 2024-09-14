package com.bfi.ecm.Repositories;

import com.bfi.ecm.Entities.Tokens;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasicTokensRepository extends JpaRepository<Tokens, Long> {
}
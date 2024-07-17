package com.bfi.ecm.Repositories;

import com.bfi.ecm.Entities.BasicTokens;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasicTokensRepository extends JpaRepository<BasicTokens, Long> {
}
package com.bfi.ecm.Repositories;

import com.bfi.ecm.Entities.Tokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Tokens, Long> {

    @Query("SELECT t FROM Tokens t WHERE LOWER(t.text) LIKE LOWER(CONCAT('%', :text, '%'))")
    List<Tokens> findByTextContainingIgnoreCase(@Param("text") String text);

    @Query("SELECT t FROM Tokens t WHERE LOWER(t.newToken) LIKE LOWER(CONCAT('%', :newToken, '%'))")
    List<Tokens> findByNewTokenContainingIgnoreCase(@Param("newToken") String newToken);

    @Query("SELECT t FROM Tokens t WHERE t.soundexCode = :soundexCode")
    List<Tokens> findBySoundexCode(@Param("soundexCode") String soundexCode);

    List<Tokens> findByText(String word);
}
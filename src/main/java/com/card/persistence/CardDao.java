package com.card.persistence;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.card.bean.Card;

@Repository
public interface CardDao extends JpaRepository<Card, Integer> {
	Optional<Card> findById(int cardId);
	
	Optional<Card> findByEmail(String email);
	
	Optional<Card> findByEmailAndPassword(String email, String password);
	
	@Transactional
	@Modifying
	@Query(value = "insert into card(email, password, balance) values(:email, :password, :balance)", nativeQuery = true)
	int generateCard(@Param("email") String email, @Param("password") String password, @Param("balance") double balance);

	@Query(value = "select balance from Card where id = :cardId")
	double checkBalance(@Param("cardId") int cardId);

	@Transactional
	@Modifying
	@Query(value = "update card set balance = :cardBalance where id = :cardId", nativeQuery = true)
	int updateBalance(@Param("cardId") int cardId, @Param("cardBalance") double cardBalance);
}
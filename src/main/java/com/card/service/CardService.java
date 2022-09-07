package com.card.service;

import java.util.Optional;

import com.card.bean.Card;

public interface CardService {
	double minimumBalance = 20;
	
	boolean generateCard(String email, String password, double balance);
	
	Optional<Card> getCardById(int cardId);
	
	Optional<Card> getCardByEmailAndPassword(String email, String password);
	
	double checkBalance(int cardId);
	
	boolean updateBalance(int cardId, double amount);
}
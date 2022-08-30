package com.card.service;

import com.card.bean.Card;

public interface CardService {
	double minimumBalance = 20;
	
	boolean generateCard(String email, String password, double balance);
	
	Card getCardById(int cardId);
	
	double checkBalance(int cardId);
	
	boolean updateBalance(int cardId, double amount);

//	boolean chargeFare(int cardId, double journeyFare);
}
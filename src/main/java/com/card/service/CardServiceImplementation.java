package com.card.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.card.bean.Card;
import com.card.bean.User;
import com.card.persistence.CardDao;

@Service
public class CardServiceImplementation implements CardService {
	@Autowired
	private CardDao cardDao;
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public boolean generateCard(String email, String password, double balance) {
		if(balance < 100) return false;
		
		User user = restTemplate.getForObject("http://user-service/users/" + email, User.class);
	    ResponseEntity<User> response = restTemplate.postForEntity("http://user-service/users/", user, User.class);
		if(user == null && response != null) {
			return cardDao.generateCard(email, password, balance) > 0;
		}
		
		return false;
	}
	
	@Override
	public Card getCardById(int cardId) {
		return cardDao.findById(cardId).orElse(null);
	}
	
	@Override
	public double checkBalance(int cardId) {
		return cardDao.checkBalance(cardId);
	}

	@Override
	public boolean updateBalance(int cardId, double amount) {
		return cardDao.updateBalance(cardId, amount) > 0;
	}
	
//	@Override
//	public boolean chargeFare(int cardId, double journeyFare) {
//		int rows = cardDao.chargeFare(cardId,  journeyFare);
//		
//		return (rows > 0);
//	}
}
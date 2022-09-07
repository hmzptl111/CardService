package com.card.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
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
		
		try {
			ResponseEntity<User> user = restTemplate.getForEntity("http://userService/users/" + email, User.class);
			if(user.getStatusCode() == HttpStatus.FOUND) {
				Optional<Card> card = cardDao.findByEmail(email);
				if(!card.isPresent()) {
					return cardDao.generateCard(email, password, balance) > 0;
				}
			}
		} catch(HttpClientErrorException e) {
			return false;
		}
		
		return false;
	}
	
	@Override
	public Optional<Card> getCardById(int cardId) {
		return cardDao.findById(cardId);
	}
	
	@Override
	public Optional<Card> getCardByEmailAndPassword(String email, String password) {
		return cardDao.findByEmailAndPassword(email, password);
	}
	
	@Override
	public double checkBalance(int cardId) {
		return cardDao.checkBalance(cardId);
	}

	@Override
	public boolean updateBalance(int cardId, double amount) {
		return cardDao.updateBalance(cardId, amount) > 0;
	}
}
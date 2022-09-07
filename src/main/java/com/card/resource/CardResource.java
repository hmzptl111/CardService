package com.card.resource;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.card.bean.Card;
import com.card.service.CardService;

@RestController
@RequestMapping("/cards")
public class CardResource {
	@Autowired
	private CardService cardService;
	
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Card> getCardById(@PathVariable("id") int id) {
		Optional<Card> card = cardService.getCardById(id);
		if(card.isPresent()) {
			return new ResponseEntity<Card>(card.get(), HttpStatus.FOUND);
		}
		
		return new ResponseEntity<Card>(new Card(), HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(path = "/{email}/{password}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Card> getCardByEmailAndPassword(@PathVariable("email") String email, @PathVariable("password") String password) {
		Optional<Card> card = cardService.getCardByEmailAndPassword(email, password);
		if(card.isPresent()) {
			return new ResponseEntity<Card>(card.get(), HttpStatus.FOUND);
		}
		
		return new ResponseEntity<Card>(new Card(), HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(path = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean generateCard(@RequestBody Card card) {
		return cardService.generateCard(card.getEmail(), card.getPassword(), card.getBalance());
	}
	
	@GetMapping(path = "/balance/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public double checkBalance(@PathVariable("id") int cardId) {
		return cardService.checkBalance(cardId);
	}
	
	@GetMapping(path = "/addBalance/{id}/{amount}", produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean addBalance(@PathVariable("id") int cardId, @PathVariable("amount") double amount) {
		return cardService.updateBalance(cardId, cardService.checkBalance(cardId) + amount);
	}
	
	@GetMapping(path = "/deductBalance/{id}/{amount}", produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean deductBalance(@PathVariable("id") int cardId, @PathVariable("amount") double amount) {
		return cardService.updateBalance(cardId, cardService.checkBalance(cardId) - amount);
	}
}
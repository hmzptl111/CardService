package com.card.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.card.bean.Card;
import com.card.service.CardService;

@RestController
@RequestMapping("/card")
public class CardResource {
	@Autowired
	private CardService cardService;
	
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Card getCard(@PathVariable("id") int id) {
		return cardService.getCardById(id);
	}
	
	@PostMapping(path = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean generateCard(@RequestBody Card card) {
		return cardService.generateCard(card.getEmail(), card.getPassword(), card.getBalance());
	}
	
	@GetMapping(path = "/balance/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public double checkBalance(@PathVariable("id") int cardId) {
		return cardService.checkBalance(cardId);
	}
	
	@PatchMapping(path = "/updateBalance/{id}/{amount}", produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean updateBalance(@PathVariable("id") int cardId, @PathVariable("amount") double amount) {
		return cardService.updateBalance(cardId, amount);
	}
	
//	@PatchMapping(path = "/card/charge/{id}/{amount}", produces = MediaType.APPLICATION_JSON_VALUE)
//	public boolean chargeFare(@PathVariable("id") int cardId, @PathVariable("amount") double amount) {
//		return cardService.chargeFare(cardId, amount);
//	}
}
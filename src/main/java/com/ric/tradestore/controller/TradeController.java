package com.ric.tradestore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ric.tradestore.exception.TradeException;
import com.ric.tradestore.model.Trade;
import com.ric.tradestore.service.TradeService;

@RestController
public class TradeController {
	@Autowired
	TradeService tradeService;

	/**
	 * Validates trade and stores it in trade store once validated
	 * 
	 * @param trade
	 * @return
	 */
	@PostMapping("/trade")
	public ResponseEntity<String> saveTrade(@RequestBody Trade trade) {
		try {
			if (tradeService.isValid(trade)) {
				tradeService.persist(trade);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
//               throw new TradeException(trade.getTradeId()+"  Trade id not found");
			}
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();

		}

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	/**
	 * 
	 * @return
	 */
	@GetMapping("/trades")
	public List<Trade> findAllTrades() {
		return tradeService.findAll();
	}
}

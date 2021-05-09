package com.ric.tradestore.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ric.tradestore.dao.TradeRepository;
import com.ric.tradestore.exception.TradeException;
import com.ric.tradestore.model.Trade;

@Service
public class TradeService {

	private static final Logger log = LoggerFactory.getLogger(TradeService.class);

	@Autowired
	TradeRepository tradeRepository;

	/**Checks whether trade is valid or not
	 * 
	 * @param trade
	 * @return
	 * @throws TradeException
	 */
	public boolean isValid(Trade trade) throws TradeException {
		if (validateMaturityDate(trade)) {

			Optional<Trade> exsitingTrade = tradeRepository.findById(trade.getTradeId());
			if (exsitingTrade.isPresent()) {
				return validateVersion(trade, exsitingTrade.get());
			} else {
				return true;
			}
		}
		return false;
	}

	/**Saves only latest version of trade else throws exception
	 * 
	 * @param trade
	 * @param oldTrade
	 * @return
	 * @throws TradeException
	 */
	private boolean validateVersion(Trade trade, Trade oldTrade) throws TradeException {
		if (trade.getVersion() >= oldTrade.getVersion())
			return true;
		else
			throw new TradeException(trade.getTradeId());
	}

	/**
	 * Not allow the trade which has less maturity date than today
	 * 
	 * @param trade
	 * @return
	 */
	private boolean validateMaturityDate(Trade trade) throws TradeException {
		if (trade.getMaturityDate().isBefore(LocalDate.now()))
			throw new TradeException(trade.getTradeId());
		else
			return true;
	}

	/**
	 * Saves the trade
	 * 
	 * @param trade
	 */
	public void persist(Trade trade) {
		trade.setCreatedDate(LocalDate.now());
		tradeRepository.save(trade);
	}

	/**Get all trades
	 * @return
	 */
	public List<Trade> findAll() {
		return tradeRepository.findAll();
	}

	/**Updated Expired flag of the trade
	 * 
	 */
	public void updateTradeExpiryFlag() {
		tradeRepository.findAll().stream().forEach(t -> {
			if (!validateMaturityDate(t)) {
				t.setExpiredFlag("Y");
				log.info("Trade updated as expired {}", t);
				tradeRepository.save(t);
			}
		});
	}

}

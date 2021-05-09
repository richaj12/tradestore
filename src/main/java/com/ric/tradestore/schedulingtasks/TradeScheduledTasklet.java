
package com.ric.tradestore.schedulingtasks;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ric.tradestore.service.TradeService;

@Component
public class TradeScheduledTasklet {

	private static final Logger log = LoggerFactory.getLogger(TradeScheduledTasklet.class);

	
	@Autowired
	TradeService tradeService;

	@Scheduled(cron = "${trade.expiry.schedule}")
	public void updateExpiry() {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

		log.info("The time is {}", dateFormat.format(new Date()));
		tradeService.updateTradeExpiryFlag();
	}
}
package com.ric.tradestore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ric.tradestore.model.Trade;

@Repository
public interface TradeRepository extends JpaRepository<Trade,String> {
}

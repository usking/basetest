package com.sz.example.dao.mapper;

import com.sz.example.pojo.Trade;

import tk.mybatis.mapper.common.Mapper;

public interface TradeMapper extends Mapper<Trade> {
	public int insertTrade(Trade trade);
}
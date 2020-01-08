package com.study.springboot.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.study.springboot.dto.CashListDto;
import com.study.springboot.dto.RecordListDto;

@Mapper
public interface PaymentDao {
	public ArrayList<CashListDto> cash_mileage_list(int end, int start);
	public int cash_mileage_list_count();
	
	public ArrayList<CashListDto> withdrawal_request_list(int end, int start);
	public int withdrawal_request_list_count();

	public int reward_list_count();
	public ArrayList<RecordListDto> reward_list(int end, int start);

	public ArrayList<CashListDto> reward_exchange_list(int end, int start);
	public int reward_exchange_list_count();
}	

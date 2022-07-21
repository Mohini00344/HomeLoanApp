package com.cg.homeloan.service;

import java.time.LocalDate;
import java.util.List;

import com.cg.homeloan.entities.EMI;

/* EmiService 
 * EMI addEmiDetails(double loanAppliedAmount,double intrestRate,int tenure) to add new EmiDetails to table
 * EMI calculateEmi(double principal,double intrestRate,int tenure) to calculate Emi
 * 
 * 
 * */

public interface IEmiService {

	public EMI addEmiDetails(double loanAppliedAmount,double intrestRate,int tenure); 
	public double calculateEmi(double principal,double intrestRate,int tenure);
	List<EMI> viewByEMIAmountASC();
	List<EMI> viewByEMIAmountDESC();
	List<EMI> dueDate(LocalDate date);
	List<EMI> interestAmountASC();
	List<EMI> interestAmountDESC();
	
}

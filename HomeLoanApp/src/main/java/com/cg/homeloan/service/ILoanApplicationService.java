package com.cg.homeloan.service;

import java.time.LocalDate; 

import java.util.List;

import com.cg.homeloan.entities.Customer;
import com.cg.homeloan.entities.LoanAgreement;
import com.cg.homeloan.entities.LoanApplication;
import com.cg.homeloan.exception.AdminApprovalException;
import com.cg.homeloan.exception.CustomerNotFoundException;
import com.cg.homeloan.exception.FinanceVerificationException;
import com.cg.homeloan.exception.InvalidLoanAgreementException;
import com.cg.homeloan.exception.InvalidLoanApplicationException;
import com.cg.homeloan.exception.LandVerificationException;

/* Loan Application Service
 * LoanApplicationService implements ILoanApplicationService interface
 * LoanApplication addLoanApplication(LoanApplication loanApplication) to add new loan application
 * LoanApplication updateLoanApplication(LoanApplication loanApplication) throws InvalidLoanApplicationException to update the loan application
 * LoanApplication deleteLoanApplication(long loanApplicationId) throws InvalidLoanApplicationException to delete the loan application
 * LoanApplication getLoanApplication(int loanApplicationId) throws InvalidLoanApplicationException
 * List<LoanApplication> getAllLoanApplication()
 * LoanAgreement getLoanAgreement(int loanApplicationId) throws InvalidLoanAgreementException
 * LoanApplication updateLandStatus(int loanApplicationId) throws LandVerificationException, InvalidLoanApplicationException
 * LoanApplication updateFinanceStatus(int loanApplicationId) throws FinanceVerificationException, InvalidLoanApplicationException
 * LoanApplication updateAdminStatus(int loanApplicationId) throws AdminApprovalException, InvalidLoanApplicationException
 * 
 * 
 * */

public interface ILoanApplicationService  {
	
	public LoanApplication addLoanApplication(int userId, double loanAppliedAmount,int loanTenureYears) throws CustomerNotFoundException;
	
	public LoanApplication updateLoanApplication(int loanApplicationId,LoanApplication loanApplication) throws InvalidLoanApplicationException;
	public LoanApplication deleteLoanApplication(int loanApplicationId) throws InvalidLoanApplicationException;
	public List<LoanApplication> getAllLoanApplication();
	public LoanApplication getLoanApplication(int loanApplicationId) throws InvalidLoanApplicationException;
	public LoanApplication updateLandStatus(int loanApplicationId) throws LandVerificationException,InvalidLoanApplicationException;
	public LoanApplication updateFinanceStatus(int loanApplicationId) throws FinanceVerificationException, InvalidLoanApplicationException;
	public LoanApplication updateAdminStatus(int loanApplicationId) throws AdminApprovalException, InvalidLoanApplicationException;
	public LoanAgreement getLoanAgreement(int loanApplicationId)throws InvalidLoanAgreementException;
//	public List<Customer> viewCustomerApplicationList(LocalDate dateOfApplication);

}

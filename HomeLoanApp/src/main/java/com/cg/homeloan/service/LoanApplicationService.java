package com.cg.homeloan.service;

import java.time.LocalDate; 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.homeloan.entities.Customer;
import com.cg.homeloan.entities.EMI;
import com.cg.homeloan.entities.LoanAgreement;
import com.cg.homeloan.entities.LoanApplication;
import com.cg.homeloan.entities.Status;
import com.cg.homeloan.exception.AdminApprovalException;
import com.cg.homeloan.exception.CustomerNotFoundException;
import com.cg.homeloan.exception.FinanceVerificationException;
import com.cg.homeloan.exception.InvalidLoanAgreementException;
import com.cg.homeloan.exception.InvalidLoanApplicationException;
import com.cg.homeloan.exception.LandVerificationException;
import com.cg.homeloan.repository.ICustomerRepository;
import com.cg.homeloan.repository.ILoanAgreementRepository;
import com.cg.homeloan.repository.ILoanApplicationRepository;

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

@Service
public class LoanApplicationService implements ILoanApplicationService {
	
	Logger logger = Logger.getLogger(ILoanApplicationService.class.getName());

	@Autowired
	ILoanApplicationRepository loanApplicationRepository;
	
	@Autowired
	ICustomerService customerService;
	
	@Autowired
	IEmiService emiService;
	
	@Autowired
	ILoanAgreementService loanAgreementService;
	
	@Autowired
	ICustomerRepository repository;
	
	@Autowired
	ILoanAgreementRepository agreementRepository;
	
	@Autowired
	ILoanApplicationRepository applicationRepository;

	//To add any loan application or to apply loan
	@Override
	public LoanApplication addLoanApplication(int userId, double loanAppliedAmount,int loanTenureYears) throws CustomerNotFoundException {
		Customer customer = customerService.getCustomer(userId);
		LoanApplication loanApplication = new LoanApplication(customer,loanAppliedAmount,loanTenureYears);
		return loanApplicationRepository.save(loanApplication);
	}

	//To update any changes in loan application
	@Override
	public LoanApplication updateLoanApplication(int loanApplicationId, LoanApplication loanApplication) throws InvalidLoanApplicationException {
		getLoanApplication(loanApplicationId);
		return loanApplicationRepository.save(loanApplication);
	}

	//To delete any particular loan application by its id
	@Override
	public LoanApplication deleteLoanApplication(int loanApplicationId) throws InvalidLoanApplicationException {
		LoanApplication loanApplication =getLoanApplication(loanApplicationId);
		loanApplicationRepository.deleteById(loanApplicationId);
		return loanApplication;
	}
	
	//Get any loan application by its id
	@Override
	public LoanApplication getLoanApplication(int loanApplicationId) throws InvalidLoanApplicationException {
		return loanApplicationRepository.findById(loanApplicationId)
				.orElseThrow(() -> new InvalidLoanApplicationException("Loan Application Not Found!!!"));
	}
	
	//Get all loan applications
	@Override
	public List<LoanApplication> getAllLoanApplication() {
		return loanApplicationRepository.findAll();
	}
	
	//This is for track any application if it's status is approved
	@Override
	public LoanAgreement getLoanAgreement(int loanApplicationId) throws InvalidLoanAgreementException {
		return loanAgreementService.getLoanAgreement(loanApplicationId);
	}
	
	//Land officer's verification and approval in any loan application
	@Override
	public LoanApplication updateLandStatus(int loanApplicationId) throws LandVerificationException, InvalidLoanApplicationException{
		LoanApplication loanApplication = getLoanApplication(loanApplicationId);
				
		if(loanApplication.getStatus()== Status.WAITING_FOR_LAND_VERIFICATION_OFFICE_APPROVAL 
						&& !loanApplication.isLandVerificationApproval() ) 
		{
			loanApplication.setLandVerificationApproval(true);
			loanApplication.setStatus(Status.WAITING_FOR_FINANCE_APPROVAL);
			return loanApplicationRepository.save(loanApplication);	
		}
		else  
		{
		 throw new LandVerificationException("Something went wrong") ;	
		}	
	}
	
	//Finance officer's verification and approval in any loan application
	@Override
	public LoanApplication updateFinanceStatus(int loanApplicationId) throws FinanceVerificationException, InvalidLoanApplicationException {
		LoanApplication loanApplication = getLoanApplication(loanApplicationId);
		
		if (loanApplication.getStatus() == Status.WAITING_FOR_FINANCE_APPROVAL
				&& loanApplication.isLandVerificationApproval()
				&& !loanApplication.isFinanceVerificationApproval()) {
			loanApplication.setFinanceVerificationApproval(true);
			loanApplication.setStatus(Status.PENDING);
			return loanApplicationRepository.save(loanApplication);

		} else 
			throw new FinanceVerificationException("Waiting for Land Verification Approval !!!");
	}
	
	//Admin's verification and approval in any loan application and also for add loan agreement
	@Override
	public LoanApplication updateAdminStatus(int loanApplicationId) throws AdminApprovalException, InvalidLoanApplicationException {
		LoanApplication loanApplication = getLoanApplication(loanApplicationId);
		
		if (loanApplication.getStatus() == Status.PENDING
				&& loanApplication.isLandVerificationApproval() 
				&& loanApplication.isFinanceVerificationApproval()) {
			loanApplication.setAdminApproval(true);
			loanApplication.setStatus(Status.APPROVED);
			
			EMI emi=emiService.addEmiDetails(loanApplication.getLoanAppliedAmount(), 10 , loanApplication.getLoanTenureYears());
			loanApplication.setLoanApprovedAmount(emi.getLoanAmount());
			
			loanAgreementService.addLoanAgreement(loanApplicationId, emi);
			
			return loanApplicationRepository.save(loanApplication);

		} else 
			throw new AdminApprovalException("Waiting for Officers Approval !!! ");
	}

//	@Override
//	public List<Customer> viewCustomerApplicationList(LocalDate dateOfApplication) {
//		List<Customer> customerList = new ArrayList<>();
//		List<LoanAgreement> allAgreement = null;
//		try {
//			allAgreement = agreementRepository.findAll();
//			for (LoanAgreement la : allAgreement) {
//				int agreementId = la.getLoanApplicationId();
//
//				Optional<LoanApplication> optional = applicationRepository.findById(agreementId);
//
//				if (optional.isPresent() && optional.get().getApplicationDate().equals(dateOfApplication)) {
//
//					customerList.add(optional.get().getCustomer());
//
//				}
//
//			}
//
//		} catch (Exception e) {
//			logger.info(e.getMessage());
//		}
//
//		return customerList;
//	}

}

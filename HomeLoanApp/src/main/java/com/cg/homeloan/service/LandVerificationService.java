package com.cg.homeloan.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;   
import org.springframework.stereotype.Service;

import com.cg.homeloan.entities.Admin;
import com.cg.homeloan.entities.LandVerificationOfficer;
import com.cg.homeloan.repository.ILandVerificationRepository;
import com.cg.homeloan.repository.ILoanApplicationRepository;

/*Land Verification Officer Service
 *ILandVerificationService checks for the existing of loan application and updates the status and 
 *ILandVerificationService isValidLandOfficer(String username, String password) validate LandVerificationOfficer
 *ILandVerificationService addLandOfficer(LandVerificationOfficer officer) add LandVerificationOfficer
 
 * */

@Service
public class LandVerificationService implements ILandVerificationService {

	@Autowired
	ILoanApplicationRepository loanApplicationRepository;
	
	@Autowired
	ILandVerificationRepository iLandVerificationRepository;
	
	@Autowired
	ILoanApplicationService loanApplicationService;
	
	// saving a specific record by using the method save() of CrudRepository
	public LandVerificationOfficer addLandOfficer(LandVerificationOfficer officer) {
		iLandVerificationRepository.save(officer);
		return officer;
	}	
	
	/*
	 * it checks whether the officer's credential is valid or not
	 * it returns true or false
	 */
	@Override
	public Boolean isValidLandOfficer(String userName, String password) {
		return iLandVerificationRepository.findByUsernameAndPassword(userName, password)!=null;
	}
	@Override
	public List<LandVerificationOfficer> getAllLandOfficer() {
		return iLandVerificationRepository.findAll();
	}

}

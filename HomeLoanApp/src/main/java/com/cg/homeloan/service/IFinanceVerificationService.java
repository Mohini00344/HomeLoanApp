package com.cg.homeloan.service;

import com.cg.homeloan.entities.FinanceVerificationOfficer;

/*Finance Verification Officer Service
 *IFinanceVerificationService checks for the existing of loan application and 
 *other field as per necessity if existing
 *
 *  
 * */

public interface IFinanceVerificationService  {
	
	public Boolean isValidFinanceOfficer(String username, String password);
	public FinanceVerificationOfficer addFinanceOfficer(FinanceVerificationOfficer officer); 

}

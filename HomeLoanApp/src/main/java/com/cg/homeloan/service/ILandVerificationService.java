package com.cg.homeloan.service;

import java.util.List;


import com.cg.homeloan.entities.Admin;
import com.cg.homeloan.entities.LandVerificationOfficer;

/*Land Verification Officer Service
 *ILandVerificationService checks for the existing of loan application and updates the status and 
 *ILandVerificationService isValidLandOfficer(String username, String password) validate LandVerificationOfficer
 *ILandVerificationService addLandOfficer(LandVerificationOfficer officer) add LandVerificationOfficer
 * 
 * */

public interface ILandVerificationService {
	
	public Boolean isValidLandOfficer(String username, String password);
	public List<LandVerificationOfficer> getAllLandOfficer();
	public LandVerificationOfficer addLandOfficer(LandVerificationOfficer officer); 
}



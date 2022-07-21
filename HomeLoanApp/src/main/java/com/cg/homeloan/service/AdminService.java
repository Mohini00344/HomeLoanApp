package com.cg.homeloan.service;

import java.util.List;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.homeloan.entities.Admin;
import com.cg.homeloan.repository.IAdminRepository;
import com.cg.homeloan.repository.ILoanApplicationRepository;

/* Admin Service 
 * AdminService implements IAdminService 
 * Admin addAdmin(Admin Admin) to add new user as admin.
 * Admin getAllAdmin to get the list of all admins.
 * isValidAdmin to check whether admin exist in the database or not.
 
 
 */

@Service
public class AdminService implements IAdminService {

	@Autowired
	IAdminRepository iAdminRepository;
	
	@Autowired
	ILoanApplicationRepository loanApplicationRepository;
	
	@Autowired
	ILoanApplicationService loanApplicationService;

	// saving a specific record by using the method save() of CrudRepository
	public Admin addAdmin(Admin admin) {
		iAdminRepository.save(admin);
		return admin;
	}

	// getting a specific record by using the method findById() of CrudRepository
	public Admin getAdmin(int userId) {
		return iAdminRepository.findById(userId).get();
	}

	// getting all Employee record by using the method findaAll() of CrudRepository
	public List<Admin> getAllAdmin() {
		return iAdminRepository.findAll();
	}
	
	/*
	 * this method check the admin's credential is valid or not
	 * it returns true or false
	 */
   
	@Override
	public Boolean isValidAdmin(String username, String password) {
		return iAdminRepository.findByUsernameAndPassword(username, password)!=null;
	}
	
}

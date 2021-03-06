package com.cg.homeloan.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.homeloan.entities.Customer;
import com.cg.homeloan.exception.CustomerNotFoundException;
import com.cg.homeloan.repository.ICustomerRepository;

/* Customer Service
 * ICustomerServiceImpl implements the interface ICustomerService
 * Customer addCustomer(Customer customer) to add new customer to table
 * Customer getUserIdByUsername(String username) throws CustomerNotFoundException to update customer details
 * Customer getCustomer(int userId) throws CustomerNotFoundException
 * Customer getAllCustomer(int custid) throws CustomerNotFoundException view customer by Id
 * Customer updateCustomer(int userId,Customer customer) throws exception if not found
 * Customer deleteCustomer(int userId) throws CustomerNotFoundException if not found
 * isValidCustomer(String username, String password) validate customer
 * 
 * 
 * */

@Service
public class CustomerService implements ICustomerService {
	@Autowired
	ICustomerRepository iCustomerRepository;
	
	
	// adding a specific record by using the method save() of CrudRepository
	@Override
	public Customer addCustomer(Customer customer) {
		iCustomerRepository.save(customer);
		return customer;
	}
	
   //	get userId by userName
	@Override
	public int getUserIdByUsername(String username) throws CustomerNotFoundException {
		int userId = iCustomerRepository.findByUsername(username).getUserId();
		return userId;
	}

  // getting a specific record by using the method findById() of CrudRepository
	@Override
	public Customer getCustomer(int userId) throws CustomerNotFoundException { 
		return iCustomerRepository.findById(userId).orElseThrow(()->new CustomerNotFoundException("Customer detail not found !!!"));
	}

  // it gets all the customers
	@Override
	public List<Customer> getAllCustomers(){
		return iCustomerRepository.findAll();
	}
	
	// updating a specific record by using specific userId of the customer
	@Override
	public Customer updateCustomer(Customer customer) throws CustomerNotFoundException {
		
		Optional<Customer> optional = iCustomerRepository.findById(customer.getUserId());
		if (optional.isPresent()) {
			iCustomerRepository.save(customer);
			return optional.get();
		} else {
			throw new CustomerNotFoundException("Customer couldn't be Updated! ");
		}

	}
	
	// remove a specific customer but using specific userId of the customer
	@Override
	public Customer deleteCustomer(int userId) throws CustomerNotFoundException {
		Customer customer = getCustomer(userId);
		iCustomerRepository.deleteById(userId);
		return customer;	
	}
	
	/*
	 * it checks whether the customer's credential is valid or not
	 * it returns true or false
	 */
	public boolean isValidCustomer(String username, String password) {
		return iCustomerRepository.findByUsernameAndPassword(username, password)!=null? true :false;
	}
}

package com.driver.services.impl;

import java.util.List;

import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Admin;
import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.AdminRepository;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminRepository adminRepository1;

	@Autowired
	DriverRepository driverRepository1;

	@Autowired
	CustomerRepository customerRepository1;

	@Override
	public void adminRegister(Admin admin) {
		//Save the admin in the database
		try{
			adminRepository1.save(admin);
		}catch (Exception e){
			System.out.println(e);
		}
	}

	@Override
	public Admin updatePassword(Integer adminId, String password) {
		//Update the password of admin with given id
		Admin admin = null;
		try{
			admin = adminRepository1.findById(adminId).get();
			admin.setPassword(password);
			adminRepository1.save(admin);
		}catch(Exception e){
			System.out.println(e);
		}
		return adminRepository1.findById(adminId).get();
	}

	@Override
	public void deleteAdmin(int adminId){
		// Delete admin without using deleteById function
		try{
			Admin admin = adminRepository1.findById(adminId).get();
			adminRepository1.delete(admin);
		}catch (Exception e){
			System.out.println(e);
		}
	}

	@Override
	public List<Driver> getListOfDrivers() {
		//Find the list of all drivers
		List<Driver> driverList = null;
		try{
			driverList =    driverRepository1.findAll();
		}catch (Exception e){
			System.out.println(e);
		}
		return driverList;
	}

	@Override
	public List<Customer> getListOfCustomers() {
		//Find the list of all customers
		List<Customer> customerList = null;
		try{
			customerList = customerRepository1.findAll();
		}catch (Exception e){
			System.out.println(e);
		}
		return customerList;
	}

}

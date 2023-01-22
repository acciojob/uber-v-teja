package com.driver.services.impl;

import com.driver.model.Cab;
import com.driver.repository.CabRepository;
import com.driver.services.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Driver;
import com.driver.repository.DriverRepository;

@Service
public class DriverServiceImpl implements DriverService {

	@Autowired
	DriverRepository driverRepository3;

	@Autowired
	CabRepository cabRepository3;

	@Override
	public void register(String mobile, String password){
		//Save a driver in the database having given details and a cab with ratePerKm as 10 and availability as True by default.
		try{
			Cab cab = new Cab(10,true);
			Driver driver = new Driver(mobile,password);
			driver.setCab(cab);
			driverRepository3.save(driver);
			cabRepository3.save(cab);
		}catch (Exception e){
			System.out.println(e);
		}

	}

	@Override
	public void removeDriver(int driverId){
		// Delete driver without using deleteById function
		try{
			driverRepository3.deleteById(driverId);
		}catch (Exception e){
			System.out.println(e);
		}
	}

	@Override
	public void updateStatus(int driverId){
		//Set the status of respective car to unavailable
		try{
			Driver driver = driverRepository3.findById(driverId).get();
			Cab cab = driver.getCab();
			cab.setAvailable(false);
			cabRepository3.save(cab);
			driverRepository3.save(driver);
		}catch (Exception e){
			System.out.println(e);
		}
	}
}
package com.driver.services.impl;

import com.driver.model.TripBooking;
import com.driver.repository.CabRepository;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;
import com.driver.model.TripStatus;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;
	@Autowired
	private CabRepository cabRepository;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		try{
			customerRepository2.save(customer);
		}catch(Exception e){
			System.out.println(e);
		}
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		try{
			Customer customer = customerRepository2.findById(customerId).get();
			customerRepository2.delete(customer);
		}catch(Exception e){
			System.out.println(e);
		}
	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
		TripBooking tripBooking = null;
		try{
			List<Driver> driverList = driverRepository2.findAll();
			boolean flag = false;
			int minId = Integer.MAX_VALUE;
			for(Driver driver : driverList) {
				if (driver.getDriverId() < minId && driver.getCab().getAvailable()) {
					minId = driver.getDriverId();
					flag = true;
				}
			}

			if(flag==false) {
				throw new Exception("No cab available!");
			}
			tripBooking = new TripBooking(fromLocation,toLocation,distanceInKm,TripStatus.CONFIRMED);
			Driver driver = driverRepository2.findById(minId).get();
			driver.getCab().setAvailable(false);
			int bill = (driver.getCab().getPerKmRate()) * distanceInKm;
			tripBooking.setBill(bill);
			Customer customer = customerRepository2.findById(customerId).get();
			if(driver!=null && customer !=null){
				tripBooking.setDriver(driver);
				tripBooking.setCustomer(customer);
			}
		}catch(Exception e){
			System.out.println(e);
		}

		tripBookingRepository2.save(tripBooking);
		return tripBooking;
	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		try{
			TripBooking tripBooking = tripBookingRepository2.findById(tripId).get();
			tripBooking.setStatus(TripStatus.CANCELED);
			tripBooking.setBill(0);

			tripBookingRepository2.save(tripBooking);
		}catch(Exception e){
			System.out.println(e);
		}

	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		try{
			TripBooking tripBooking = tripBookingRepository2.findById(tripId).get();
			tripBooking.setStatus(TripStatus.COMPLETED);
			tripBookingRepository2.save(tripBooking);
		}catch(Exception e){
			System.out.println(e);
		}

	}
}

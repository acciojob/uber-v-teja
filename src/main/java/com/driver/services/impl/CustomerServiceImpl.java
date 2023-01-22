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
		TripBooking tripBooking = new TripBooking();
		Driver driver = null;
		try{
			List<Driver> driverList = driverRepository2.findAll();
			boolean flag = false;
			int minId = Integer.MAX_VALUE;
			for(Driver driverTemp : driverList) {
				if (driverTemp.getDriverId() < minId) {
					if(driverTemp.getCab().getAvailable()==Boolean.TRUE){
						minId = driverTemp.getDriverId();
						driver = driverTemp;
						flag = true;
					}
				}
			}

			if(flag==false) {
				throw new Exception("No cab available!");
			}
			Customer customer = customerRepository2.findById(customerId).get();
			tripBooking.setCustomer(customer);
			driver.getCab().setAvailable(Boolean.FALSE);
			tripBooking.setFromLocation(fromLocation);
			tripBooking.setToLocation(toLocation);
			tripBooking.setDistanceInKm(distanceInKm);
			tripBooking.setStatus(TripStatus.CONFIRMED);

			customer.getTripBookingList().add(tripBooking);
			customerRepository2.save(customer);

			driver.getTripBookingList().add(tripBooking);
			driverRepository2.save(driver);

		}catch(Exception e){
			System.out.println(e);
		}

		return tripBooking;
	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		try{
			TripBooking tripBooking = tripBookingRepository2.findById(tripId).get();
			tripBooking.setStatus(TripStatus.CANCELED);
			tripBooking.setBill(0);
			tripBooking.getDriver().getCab().setAvailable(Boolean.TRUE);

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
			int bill = tripBooking.getDriver().getCab().getPerKmRate() * tripBooking.getDistanceInKm();
			tripBooking.setBill(bill);
			tripBooking.getDriver().getCab().setAvailable(Boolean.TRUE);
			tripBookingRepository2.save(tripBooking);
		}catch(Exception e){
			System.out.println(e);
		}

	}
}

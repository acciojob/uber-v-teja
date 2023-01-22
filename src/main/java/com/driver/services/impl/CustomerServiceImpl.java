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
		List<Driver> driverList = driverRepository2.findAll(Sort.by(Sort.Direction.ASC,"driver_id"));
		boolean flag = false;
		for(Driver driver : driverList){
			if(driver.getCab().isAvailable()){
				int bill = distanceInKm * driver.getCab().getPerKmRate();
				Customer customer = customerRepository2.findById(customerId).get();
				tripBooking = new TripBooking(fromLocation,toLocation,distanceInKm,TripStatus.CONFIRMED,bill,customer,driver);
				tripBookingRepository2.save(tripBooking);

				/*
				List<TripBooking> tripBookingList = customer.getTripBookingList();
				//updating list in customer
				if(tripBookingList==null){
					tripBookingList = new ArrayList<>();
					tripBookingList.add(tripBooking);
				}
				tripBookingList.add(tripBooking);
				customer.setTripBookingList(tripBookingList);
				customerRepository2.save(customer);

				//updating list in driver
				List<TripBooking> tripBookingList1 = driver.getTripBookingList();
				if(tripBookingList1==null){
					tripBookingList1 = new ArrayList<>();
					tripBookingList1.add(tripBooking);
				}
				tripBookingList1.add(tripBooking);
				driver.setTripBookingList(tripBookingList1);
				driverRepository2.save(driver);


				 */
				flag = true;
				break;
			}
		}
		if(flag == false){
			throw new Exception("No cab available!");
		}

		return tripBooking;
	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		try{
			TripBooking tripBooking = tripBookingRepository2.findById(tripId).get();
			tripBooking.setStatus(TripStatus.CANCELED);
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

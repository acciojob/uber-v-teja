package com.driver.controllers;

import com.driver.model.Customer;
import com.driver.model.TripBooking;
import com.driver.services.CustomerService;
import com.driver.services.impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {


	@Autowired
	CustomerServiceImpl customerService;

	@PostMapping("/register")
	public ResponseEntity<Void> registerCustomer(@RequestBody Customer customer){
		try{
			customerService.register(customer);
		}catch(Exception e){
			System.out.println(e);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public void deleteCustomer(@RequestParam Integer customerId){
		try{
			customerService.deleteCustomer(customerId);
		}catch(Exception e){
			System.out.println(e);
		}
	}

	@PostMapping("/bookTrip")
	public ResponseEntity<Integer> bookTrip(@RequestParam Integer customerId, @RequestParam String fromLocation, @RequestParam String toLocation, @RequestParam Integer distanceInKm) throws Exception {
		TripBooking bookedTrip = null;
		try{
			bookedTrip = customerService.bookTrip(customerId,fromLocation,toLocation,distanceInKm);
		}catch(Exception e){
			System.out.println(e);
		}
		return new ResponseEntity<>(bookedTrip.getTripBookingId(), HttpStatus.CREATED);
	}

	@DeleteMapping("/complete")
	public void completeTrip(@RequestParam Integer tripId){
		try{
			customerService.completeTrip(tripId);
		}catch(Exception e){
			System.out.println(e);
		}
	}

	@DeleteMapping("/cancelTrip")
	public void cancelTrip(@RequestParam Integer tripId){
		try{
			customerService.cancelTrip(tripId);
		}catch(Exception e){
			System.out.println(e);
		}
	}
}

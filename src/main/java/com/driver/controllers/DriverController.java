package com.driver.controllers;

import com.driver.services.DriverService;
import com.driver.services.impl.DriverServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/driver")
public class DriverController {

	@Autowired
	DriverServiceImpl driverService;

	@PostMapping(value = "/register")
	public ResponseEntity<Void> registerDriver(@RequestParam String mobile, @RequestParam String password){
		try{
			driverService.register(mobile,password);
		}catch (Exception e){
			System.out.println(e);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/delete")
	public void deleteDriver(@RequestParam Integer driverId){
		try{
			driverService.removeDriver(driverId);
		}catch(Exception e){
			System.out.println(e);
		}
	}

	@PutMapping("/status")
	public void updateStatus(@RequestParam Integer driverId){
		try{
			driverService.updateStatus(driverId);
		}catch (Exception e){
			System.out.println(e);
		}
	}
}

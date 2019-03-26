package com.jose.customers.controllers;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jose.customers.forms.CustomerForm;
import com.jose.customers.models.Customer;
import com.jose.customers.models.KpiCustomer;
import com.jose.customers.services.CustomerService;
import com.jose.customers.services.CustomerServiceImpl;
import com.jose.customers.utils.CustomerResponse;

@RestController
public class CustomerController {
	
	@Autowired
	CustomerService service;
	
	@RequestMapping("/")
	String home() throws InterruptedException, ExecutionException {
		return "Welcome to Customers XD!";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/creacliente", produces = "application/json")
	CustomerResponse createCustomer(@Valid @RequestBody CustomerForm customerForm) throws InterruptedException, ExecutionException {
		CustomerService customerService = new CustomerServiceImpl();
		return customerService.saveCustomer(customerService.customerFormToCustomer(customerForm));
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/listclientes", produces = "application/json")
	@ResponseBody
	@Valid	
	List<Customer> getCustomers() throws InterruptedException, ExecutionException {
		CustomerService customerService = new CustomerServiceImpl();
		List<Customer> customers = customerService.getCustomers();
		return customers;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/kpideclientes", produces = "application/json")
	@ResponseBody
	@Valid	
	KpiCustomer getKpiCustomers() throws InterruptedException, ExecutionException {
		CustomerService customerService = new CustomerServiceImpl();
		KpiCustomer kpiCustomer = customerService.getkpiCustomers();
		return kpiCustomer;
	}
	
	
}

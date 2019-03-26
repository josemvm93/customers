package com.jose.customers.services;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.jose.customers.forms.CustomerForm;
import com.jose.customers.models.Customer;
import com.jose.customers.models.KpiCustomer;
import com.jose.customers.utils.CustomerResponse;

public interface CustomerService {
	public CustomerResponse saveCustomer(Customer customer) throws InterruptedException, ExecutionException;
	public Customer customerFormToCustomer(CustomerForm customerForm);
	public List<Customer> getCustomers() throws InterruptedException, ExecutionException;
	public KpiCustomer getkpiCustomers() throws InterruptedException, ExecutionException;

}

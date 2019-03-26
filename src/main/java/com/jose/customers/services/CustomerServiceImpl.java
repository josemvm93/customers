package com.jose.customers.services;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.jose.customers.forms.CustomerForm;
import com.jose.customers.models.Customer;
import com.jose.customers.models.KpiCustomer;
import com.jose.customers.utils.CustomerResponse;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Override
	public CustomerResponse saveCustomer(Customer customer) throws InterruptedException, ExecutionException{
		CustomerResponse customerResponse = new CustomerResponse();
		Firestore db = FirestoreClient.getFirestore();
		ApiFuture<DocumentReference> apiFuture = db.collection("customers").add(customer);
		DocumentReference documentReference = apiFuture.get();
		System.out.print(apiFuture.isDone());
		if (apiFuture.isDone()) {
			customerResponse.setId(documentReference.getId());
			customerResponse.setMessage("Customer created");
			customerResponse.setStatus("200");
	    } else {
	    	customerResponse.setId(null);
			customerResponse.setMessage("400");
			customerResponse.setStatus("The customer could not created");
	    }
	
		return customerResponse;
	}

	public List<Customer> getCustomers() throws InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore();
		ApiFuture<QuerySnapshot> apiFuture= db.collection("customers").get();
		QuerySnapshot querySnapShot = apiFuture.get();
		List<DocumentSnapshot> documents = querySnapShot.getDocuments();
		List<Customer> customers = new ArrayList<Customer>();

		for (DocumentSnapshot document : documents ) {
			Customer customer = new Customer();
			Map<String, Object> data = document.getData();
			customer.setName((String) data.get("name"));
			customer.setLastname((String) data.get("lastname"));
			customer.setAge((Long) data.get("age"));
			customer.setBirthdate((Date) data.get("birthdate"));
			customers.add(customer);
		}
		
		return customers;
	}
	
	
	public KpiCustomer getkpiCustomers() throws InterruptedException, ExecutionException {
		List<Customer> customers = getCustomers();
		KpiCustomer kpiCustomer = new KpiCustomer();
		Integer sizeCustomers = customers.size();
		Double totalAge = 0.0;
		for (Customer customer : customers) {
			totalAge += customer.getAge();
		}
		Double average = totalAge / sizeCustomers;
		kpiCustomer.setAverage(average);
		
		Double amount = 0.0;
		for (Customer customer : customers) {
			amount += Math.pow(customer.getAge() - average, 2 );
		}
		Double varience = amount / sizeCustomers;
		Double standardDeviation = Math.sqrt(varience);
		
		kpiCustomer.setStandardDeviation(standardDeviation);
		return kpiCustomer;
	}

	public Customer customerFormToCustomer(CustomerForm customerForm) {
		Customer customer = new Customer();
		customer.setName(customerForm.getName());
		customer.setLastname(customerForm.getLastname());
		customer.setAge(customerForm.getAge());
		customer.setBirthdate(customerForm.getBirthdate());
		return customer;
	}
}

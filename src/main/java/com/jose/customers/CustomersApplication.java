package com.jose.customers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@SpringBootApplication
public class CustomersApplication {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		SpringApplication.run(CustomersApplication.class, args);
		try {
			FileInputStream serviceAccount = new FileInputStream("./ServiceAccountKey.json");
			
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials 
							.fromStream(serviceAccount)).build();
			FirebaseApp.initializeApp(options);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

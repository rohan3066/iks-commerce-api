package com.iks.commerce.returnorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * The type Return order application.
 * This is the entry point of
 * the Spring Boot application for managing return orders.
 */
@EnableMongoRepositories(
        basePackages = "com.iks.commerce.return_order.Repository")
@SpringBootApplication
public class ReturnOrderApplication {

    // Private constructor to prevent instantiation
    protected ReturnOrderApplication() {
    }

    /**
     * The entry point of the application.
     *
     * @param args the input arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(ReturnOrderApplication.class, args);
    }
}




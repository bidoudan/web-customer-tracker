package com.luv2code.springdemo.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	// need to inject session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Customer> getCustomers() {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// create a query ... sort by last name
		Query<Customer> theQuery = currentSession.createQuery("from Customer order by lastName", 
																				Customer.class);
		
		// execute query and get result list
		List<Customer> customers = theQuery.getResultList();
		
		// return the results
		
		return customers;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// save/update the customer ... final LOL
		currentSession.saveOrUpdate(theCustomer);
		
	}

	@Override
	public Customer getCustomer(int theId) {
		
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// get customer from the database using the primary key
		Customer theCustomer = currentSession.get(Customer.class, theId);
		
		return theCustomer;
	}

	@Override
	public void deleteCustomer(int theId) {
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// get customer from database using from database
		Query theQuery = currentSession.createQuery("delete from Customer where id=:customerId");
		theQuery.setParameter("customerId", theId);
		
		theQuery.executeUpdate();
		
	}

	@Override
	public List<Customer> searchCustomers(String theSearchName) {
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// search from customers from the database
		Query theQuery = null;
		if(theSearchName != null && theSearchName.trim().length() > 0) {
			theQuery = currentSession.createQuery("from Customer where lower(firstName) like :searchName or lower(lastName) like:searchName",Customer.class);
				theQuery.setParameter("searchName", "%"+theSearchName+"%");
		}else {
			// theSearchName is empty ... so just get all cutomers
			theQuery = currentSession.createQuery("from Customer", Customer.class);
		}
	
		// execute query and get result list
		List<Customer> theCustomers = theQuery.getResultList();
		
		return theCustomers;
	}

}

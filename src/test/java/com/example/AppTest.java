package com.example;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.testng.annotations.Test;

import com.example.entities.OneToMany.Customer;
import com.example.entities.OneToMany.Service;


public class AppTest {

    private static final String PERSISTENCE_UNIT = "Simplest";
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);

	@Test
	public void usingJPQL(){
		EntityManager em = factory.createEntityManager();
		StringBuilder sb =  new StringBuilder();
		sb.append("select c, c.services from Customer c ");
		sb.append("where c.customerId=:cust ");

		System.out.println(sb.toString());
		TypedQuery<Customer> query = em.createQuery(sb.toString(),Customer.class);
		query.setParameter("cust", 1);

		List<Customer> customers = query.getResultList();
		for (Customer customer:customers){
			System.out.println(customer.getName());
			System.out.println(customer.getServices().size());
		}
	}


    @Test
	public void usingCritera() {
		EntityManager em = factory.createEntityManager();
		CriteriaBuilder cb= em.getCriteriaBuilder();
		CriteriaQuery<Service> query = cb.createQuery(Service.class);
		Root<Service> service = query.from(Service.class);
		//customer.fetch("customer");
		/*Subquery<Service> sq =query.subquery(Service.class);
		Root<Service> service = sq.from(Service.class);*/
		/*Join<Customer, Service> services = customer.join("services", JoinType.INNER);*/

		List<Predicate> predicates = new ArrayList<Predicate>();

		//predicates.add(cb.equal(customer.get("customerId"), 1));
		predicates.add(cb.equal(service.get("customer").get("customerId"), "1"));
		//predicates.add(cb.equal(service.get("status"), "pending"));

		query.select(service).distinct(true)
				.where(predicates.toArray(new Predicate[]{}));

		List<Service> services = em.createQuery(query).getResultList();
		System.out.println(services.size());
		for (Service c:services){
			System.out.println(c.getStatus());
			System.out.println(c.getCustomer().getName());
		}
	}
}

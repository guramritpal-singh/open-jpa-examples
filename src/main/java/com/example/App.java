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



public class App 
{
	private static final String PERSISTENCE_UNIT = "Simplest";
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
    public static void main( String[] args )
    {
       jpql();
    }

	private static void jpql(){
		EntityManager em = factory.createEntityManager();
		StringBuilder sb =  new StringBuilder();
		sb.append("select c from Customer c JOIN c.services s ");
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

	private static void usingCritera() {
		EntityManager em = factory.createEntityManager();
		CriteriaBuilder cb= em.getCriteriaBuilder();
		CriteriaQuery<Customer> query = cb.createQuery(Customer.class);
		Root<Customer> customer = query.from(Customer.class);
		customer.fetch("services");
		/*Subquery<Service> sq =query.subquery(Service.class);
		Root<Service> service = sq.from(Service.class);*/
		/*Join<Customer, Service> services = customer.join("services", JoinType.INNER);*/

		List<Predicate> predicates = new ArrayList<Predicate>();

		predicates.add(cb.equal(customer.get("customerId"), 1));
		predicates.add(cb.equal(customer.get("services").get("status"), "pending"));
		/*predicates.add(cb.equal(services.get("status"), "pending"));
	*/

		query.select(customer).distinct(true)
				.where(predicates.toArray(new Predicate[]{}));

		List<Customer> customers = em.createQuery(query).getResultList();
		System.out.println(customers.size());
		for (Customer c:customers){
			System.out.println(c.getName());
			System.out.println(c.getServices().size());
		}
	}
}

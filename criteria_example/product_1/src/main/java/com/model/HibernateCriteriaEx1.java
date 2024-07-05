package com.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class HibernateCriteriaEx1 {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Transaction tx= session.beginTransaction();
		
		//Get all records
		Criteria criteria = session.createCriteria(Product.class);
		List<Product> proList=criteria.list();
		for(Product pro: proList) {
			System.out.println(pro.getId()+" "+pro.getName()+" "+pro.getQuantity()+" "+pro.getPrice());
		}
		
		//select all records of product table whose price is greater than 500/-
		criteria = session.createCriteria(Product.class).add(Restrictions.gt("price", new Float(500.00)));
         List<Product> exppro=criteria.list();
         for(Product pro:exppro) {
		System.out.println(pro.getName());
         }
         //select all records of product table whose qty is greater than 10 pcs;
         criteria = session.createCriteria(Product.class).add(Restrictions.gt("quantity", new Integer(10)));
         List<Product> qtypro=criteria.list();
         for(Product pro:qtypro) {
        	 System.out.println(pro.getName());
         }
         
         //select no of products available
         criteria = session.createCriteria(Product.class).setProjection(Projections.rowCount());
         Long productCount= (Long)criteria.uniqueResult();
         System.out.println("Totalproduct: "+productCount);
         
         
         
         //select highest and lowest amount product
       proList = session.createCriteria(Product.class).addOrder(Order.desc("price")).setFirstResult(0).setMaxResults(1).list();
          for(Product pro1:proList) {
        	 System.out.println("highest price product:"+pro1.getName());
             }
        proList = session.createCriteria(Product.class).addOrder(Order.asc("price")).setFirstResult(0).setMaxResults(1).list();
         for(Product pro1:proList) {
        	 System.out.println("lowest price product:"+pro1.getName());
         }
          
          //select sum of all available products price
		Criteria d =session.createCriteria(Product.class);
		proList=d.setProjection(Projections.sum("price")).list();
		System.out.println("Sum of Price="+proList.get(0));
		
		
		tx.commit();
		sessionFactory.close();
		
		
	}
  }

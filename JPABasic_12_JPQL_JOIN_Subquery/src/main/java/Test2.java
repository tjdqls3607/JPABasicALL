import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

// Group by, Order By
public class Test2 {

	public static void main(String[] args) {

		Map<String, String> props = new HashMap<>();
		props.put("hibernate.show_sql","true");
//		props.put("hibernate.hbm2ddl.auto","create");	// create : drop & create, update : 있으면 안만들고 없으면 만든다.

		EntityManagerFactory emf = new HibernatePersistenceProvider().createContainerEntityManagerFactory(
				new MyPersistenceUnitInfo(), props
		); // my persistence unit
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

////	 #1. group by
		// 주문 건들을 주문일자 기준으로 묶어서 고객 주문 건수
//		String jpql = 
//			"""
//			select o.orderDate, count(o.customer) as customerCount
//				from Orders o  
//			  group by o.orderDate
//				
//			""";
//	
//		em.createQuery(jpql, Object[].class)
//			.getResultList()
//			.forEach(objArray -> System.out.println(objArray[0] + ", " + objArray[1]));
		
		
////	 #2. group by + having
		// 주문 건들을 주문일자 기준으로 묶어서 고객 주문 건수 중 2 건 초과 건
		// alias 로 having 조건 사용 X ( customerCount > 2 )
//		String jpql = 
//			"""
//			select o.orderDate, count(o.customer) as customerCount
//				from Orders o  
//			  group by o.orderDate
//			  having count(o.customer) > 2
//			""";
//	
//		em.createQuery(jpql, Object[].class)
//			.getResultList()
//			.forEach(objArray -> System.out.println(objArray[0] + ", " + objArray[1]));
		
		
////	 #3. group by + where
		// 주문 건들을 주문일자 기준으로 묶어서 가져오되, 주문일자가 2024-03-11 보다 뒤 만
		// alias 로 having 조건 사용 X ( customerCount > 2 )
//		String jpql = 
//			"""
//			select o.orderDate, count(o.customer) as customerCount
//				from Orders o  
//			  where o.orderDate > :orderDate
//			  group by o.orderDate
//			""";
//	
//		em.createQuery(jpql, Object[].class)
//			.setParameter("orderDate", LocalDate.of(2024, 3, 11))
//			.getResultList()
//			.forEach(objArray -> System.out.println(objArray[0] + ", " + objArray[1]));
		
		
////	 #4. group by + where + order by
		// 주문 건들을 주문일자 기준으로 묶어서 가져오되, 주문일자가 2024-03-11 보다 뒤 만
		// alias 로 having 조건 사용 X ( customerCount > 2 )
//		String jpql = 
//			"""
//			select o.orderDate, count(o.customer) as customerCount
//				from Orders o  
//			  where o.orderDate > :orderDate
//			  group by o.orderDate
//			  order by o.orderDate asc
//			""";
//	
//		em.createQuery(jpql, Object[].class)
//			.setParameter("orderDate", LocalDate.of(2024, 3, 11))
//			.getResultList()
//			.forEach(objArray -> System.out.println(objArray[0] + ", " + objArray[1]));
		
		
////	 #5. group by + where + order by
		// 주문 건 중 여성고객이 주문하고, 가격이 2000원 이상인 건들에 대해서, 주문일자별 제품 가격의 합을 구하되
		// 주문 일자 내림차순으로 정렬
		// 제품가격의 합 으로 정렬한다면 order by sum(p.price) desc
		String jpql = 
			"""
			select o.orderDate, sum(p.price) as femaleOrderSum
				from Orders o join o.customer c
							  join o.product p
			  where c.gender = 'f'
			  and p.price >= 2000
			  group by o.orderDate
			  order by o.orderDate desc
			""";
	
		em.createQuery(jpql, Object[].class)
			.getResultList()
			.forEach(objArray -> System.out.println(objArray[0] + ", " + objArray[1]));
		
		
		em.getTransaction().commit();  // 이 시점에 테이블에 반영한다.
		em.close();
	}

}
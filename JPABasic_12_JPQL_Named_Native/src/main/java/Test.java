import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Orders;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

// Named Query: 별도의 이름을 가지는 query 를 자바코드 안이 아닌 관련 Entity 상단에 Annotation 으로 펴햔
//				자바 코드에서 이름으로 query 를 사용
// Native Query : JPQL 이 아닌 표준 SQL 을 사용
// 위 두 방법을 Spring Data JPA 를 이용하는 경우, @Query 에 name 또는 native=true 사용
public class Test {

	public static void main(String[] args) {

		Map<String, String> props = new HashMap<>();
		props.put("hibernate.show_sql", "true");
//		props.put("hibernate.hbm2ddl.auto", "create"); // create : drop & create,  update : 있으면 안 건드리고 없으면 만든다.
		

		EntityManagerFactory emf = new HibernatePersistenceProvider().createContainerEntityManagerFactory(
				new MyPersistenceUnitInfo(), props
		); 
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();

//	 #1. Orders.findByOrderDate
//		em.createNamedQuery("Orders.findByOrderDate", Orders.class)
//			.setParameter("orderDate", LocalDate.of(2024, 3, 11))
//			.getResultList()
//			.forEach(System.out::println);
		
		
//		 #2. Orders.findByOrderDateRange
//			em.createNamedQuery("Orders.findByOrderDateRange", Orders.class)
//				.setParameter("startDate", LocalDate.of(2024, 3, 12))
//				.setParameter("endDate", LocalDate.of(2024, 4, 16))
//				.getResultList()
//				.forEach(System.out::println);
		
		
//		 #3. Orders.findByProductPriceRange
			em.createNamedQuery("Orders.findByProductPriceRange", Object[].class)
				.setParameter("startPrice", 2000)
				.setParameter("endPrice", 3000)
				.getResultList()
				.forEach( objArray -> {
						System.out.println(objArray[0] + ", " + objArray[1]);
				});
		
	
		
		em.getTransaction().commit();  // 이 시점에 테이블에 반영한다.
		
		em.close();
	}

}
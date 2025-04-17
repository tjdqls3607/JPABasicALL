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

////	 #2. left outer join
////	 Customer 기준
		String jpql = "select c, o from Customer c left join c.orders o";

		em.createQuery(jpql, Object[].class)
			.getResultList()
			.forEach(objArray -> {
			System.out.println(objArray[0]);
			System.out.println(objArray[1]);
		});
	
		
		em.getTransaction().commit();  // 이 시점에 테이블에 반영한다.
		
		em.close();
	}

}
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

// join + subquery
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
	

		// #1. join
		String jpql = "select p, o from Product p, Orders o where p.id = o.product.id ";
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
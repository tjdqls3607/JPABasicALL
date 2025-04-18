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


		
		em.getTransaction().commit();  // 이 시점에 테이블에 반영한다.
		em.close();
	}

}
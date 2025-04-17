import config.MyPersistenceUnitInfo;
import entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;
import java.util.Map;

// key 칼럼에 대한 다양한 방법이 있지만, @GeneratedValue(strategy = GenerationType.IDENTITY) 기본으로 사용. <= auto increment key column 생성
public class Test {
	

	public static void main(String[] args) {
		// EntityManager <= EntityManagerFactory
		// src/main/resources/META-INF/persitence.xml

		Map<String, String> props = new HashMap<>();
		props.put("hibernate.show_sql","true");
		props.put("hibernate.hbm2ddl.auto","create");	// create : drop & create, update : 있으면 안만들고 없으면 만든다.

		EntityManagerFactory emf = new HibernatePersistenceProvider().createContainerEntityManagerFactory(
				new MyPersistenceUnitInfo(), props
		); // my persistence unit
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		
		
		
		
		Employee emp = new Employee();
		emp.setName("홍길동");
		emp.setAddress("서울 어디");
		
		Employee emp2 = new Employee();
		emp2.setName("홍길동2");
		emp2.setAddress("서울 어디2");
		
		
		em.persist(emp);
		em.persist(emp2);
		
		
		
		
		
		em.getTransaction().commit();  // 이 시점에 테이블에 반영한다.
//				em.close();
	}

}
import java.util.HashMap;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Student;
import entity.key.StudentKey;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;


// multi key
// jpa 2가지 방법
public class Test2 {

	public static void main(String[] args) {

		Map<String, String> props = new HashMap<>();
		props.put("hibernate.show_sql","true");
		props.put("hibernate.hbm2ddl.auto","create");	// create : drop & create, update : 있으면 안만들고 없으면 만든다.

		EntityManagerFactory emf = new HibernatePersistenceProvider().createContainerEntityManagerFactory(
				new MyPersistenceUnitInfo(), props
		); // my persistence unit
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		// @Idclass()
//		{
//			Product p = new Product();
//			p.setCode("uplus");
//			p.setNumber(1);
//			p.setColor("blue");
//			
//			em.persist(p);
			// Hibernate: insert into Product (color,code,number) values (?,?,?)
			
//			ProductKey key = new ProductKey();
//			key.setCode("uplus");
//			key.setNumber(1);
//			Product p = em.find(Product.class, key);
//			System.out.println(p);
//		}
		
		// Embedded
		// 항상 임베드 키 로부터 접근
		{
//			StudentKey key = new StudentKey();
//			key.setCode("uplus");
//			key.setNumber(1);
			
//			Student s = new Student();
//			s.setId(key);
//			s.setName("홍길동");
//			
//			em.persist(s);
			
			StudentKey key = new StudentKey();
			key.setCode("uplus");
			key.setNumber(1);
			
			Student s = em.find(Student.class, key);
			System.out.println(s);
		}
		
	
		em.getTransaction().commit();  // 이 시점에 테이블에 반영한다.
		
		em.close();
	}

}
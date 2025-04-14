import java.util.HashMap;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Passport;
import entity.Person;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;

// @OneToOne 연관관계를 통해 Person 테미블에 FK 로 Passport 가 설정된다.
// find
public class Test2 {

	public static void main(String[] args) {

		Map<String, String> props = new HashMap<>();
		props.put("hibernate.show_sql", "true");
//		props.put("hibernate.hbm2ddl.auto", "create"); // create : drop & create,  update : 있으면 안 건드리고 없으면 만든다.
		

		EntityManagerFactory emf = new HibernatePersistenceProvider().createContainerEntityManagerFactory(
				new MyPersistenceUnitInfo(), props
		); 
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		// #1. find Person
//		Person person = em.find(Person.class, 1);
//		// Hibernate: select p1_0.id,p1_0.name,p2_0.id,p2_0.number from Person p1_0 left join Passport p2_0 on p2_0.id=p1_0.passport where p1_0.id=?
//		// OneToOne 의 기본 fetch option 이 EAGER (즉시 로딩) 이므로 연관관계에 있는 Passport 도 join 으로 함께 가지고 오고
//		// person 객체의 출력 코드에서 passport 객체를 사용하지만, 다시 select 수행 X
//		System.out.println(person);
		
		// #2. find Passport
		Passport passport = em.find(Passport.class, 1);
//		// Hibernate: select p1_0.id,p1_0.number from Passport p1_0 where p1_0.id=?
//		// join 으로 Person 객체도 select
		// fetch=FetchType.LAZY 로 변경해도 Person 필드를 채우기 위해서 Person 객체를 가져오는 join 문이 함께 실행된다.( 의미 없다. )
		
		// #3. @OneToOne(cascade=CascadeType.PERSIST, fetch=FetchType.LAZY)
//		Person person = em.find(Person.class, 1);
		// Hibernate: select p1_0.id,p1_0.name,p1_0.passport from Person p1_0 where p1_0.id=?
		// toString 에서 passport 를 사용하는 코드를 통해 사용시점에 select 
//		System.out.println(person);
		// Hibernate: select p1_0.id,p1_0.number from Passport p1_0 where p1_0.id=?
		// Person [id=1, name=홍길동, passport=Passport [id=1, number=KOR124]]
		em.getTransaction().commit();  // 이 시점에 테이블에 반영한다.
		
		em.close();
	}

}
import java.util.HashMap;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Passport;
import entity.Person;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.OneToOne;

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
		
		
		Person person = new Person();
		person.setName("홍길동");
	
		Passport passport = new Passport();
		passport.setNumber("KOR124");
		
		// #1. 객체 연결 없이 각각 따로 persist 하면 오류 없이 insert 된다.
//		em.persist(person);
		// Hibernate: insert into Person (name,passport) values (?,?)
		// passport = null
		
//		em.persist(passport);
		// Hibernate: insert into Passport (number) values (?)
		
		// #2. 객체 연결, person 만 persist
//		person.setPassport(passport);
		
//		em.persist(person);
		// org.hibernate.TransientObjectException: persistent instance references an unsaved transient instance of 'entity.Passport' (save the transient instance before flushing)
		// passport 영속화 되지 않은 상태 -> 오류 발생
		
//		em.persist(passport);
		// Hibernate: insert into Passport (number) values (?)
		// 정상적으로 insert

		
		// #3. 객체 연결, person -> passport persist
//		person.setPassport(passport);
//		
//		em.persist(person);
//		em.persist(passport);
		/*
		Hibernate: insert into Person (name,passport) values (?,?)
		Hibernate: insert into Passport (number) values (?)
		Hibernate: update Person set name=?,passport=? where id=?
		 */
//		Person 이 먼저 Insert 되는 과정에서 Passport 의 id 값을 모르므로
//		Passport 가 Insert  되는 과정에서 획득한 AI key 값을 이용해서 다시 한번 update 수행
//		Insert 과정에서 AI key 를 반환하도록 수행(jpa)
		
		// #4. 객체 연결, passport -> person persist
//		person.setPassport(passport);
//		
//		em.persist(passport);
//		em.persist(person);
		/*
		Hibernate: insert into Passport (number) values (?)
		Hibernate: insert into Person (name,passport) values (?,?)
		 */
		
		// #5. 객체 연결, Person 의 @OneToOne (cascade=CascadeType.PERSIST) 추가
//		person.setPassport(passport);
//		
////		em.persist(passport); 
//		em.persist(person);
		/*
		Hibernate: insert into Passport (number) values (?)
		Hibernate: insert into Person (name,passport) values (?,?)
		 */
		
		
		// #6. 객체 연결, Person 의 @OneToOne (cascade=CascadeType.PERSIST) 추가
//		person.setPassport(passport);
//		
//		em.persist(passport);
		
		
		// #7. 객체 연결 양방향 @OneToOne 은 초기값 cascade=CascadeType.PERSIST 제외
//		person.setPassport(passport);
//		passport.setPerson(person);
		
		// 7-1
//		em.persist(person);
		// org.hibernate.TransientObjectException: persistent instance references an unsaved transient instance of 'entity.Passport' (save the transient instance before flushing)
		
		// 7-2
//		em.persist(passport);
		//  org.hibernate.TransientObjectException: persistent instance references an unsaved transient instance of 'entity.Person' (save the transient instance before flushing)
		// 양방향 일 경우, passport 만 persist 하지 못한다. ( 단방향 일 경우, 가능 )
		
		em.getTransaction().commit();  // 이 시점에 테이블에 반영한다.
		
		em.close();
	}

}
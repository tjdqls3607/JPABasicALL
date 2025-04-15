import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Team;
import entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

// ManyToMany 양방향
// teams,users, teams_users 3개의 테이블 생성확인
// persist
public class Test {

	public static void main(String[] args) {

		Map<String, String> props = new HashMap<>();
		props.put("hibernate.show_sql","true");
		props.put("hibernate.hbm2ddl.auto","create");	// create : drop & create, update : 있으면 안만들고 없으면 만든다.

		EntityManagerFactory emf = new HibernatePersistenceProvider().createContainerEntityManagerFactory(
				new MyPersistenceUnitInfo(), props
		); // my persistence unit
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		
		// #1
		// 테이블 생성 확인
		
		// #2. User 객체 2개 만 persist
//		User u1 = new User();
//		u1.setName("홍길동");
//		
//		User u2 = new User();
//		u2.setName("이길동");
//		
//		em.persist(u1);
//		em.persist(u2);
//		Hibernate: insert into users (name) values (?)
//		Hibernate: insert into users (name) values (?)
		
		
		// #3. Team 객체 2개 만 persist
//		Team t1 = new Team();
//		t1.setName("팀1");
//		
//		Team t2 = new Team();
//		t2.setName("팀2");
//		
//		em.persist(t1);
//		em.persist(t2);
		
		
		// #4. Team 객체 2개 User 객체 2개 모두 persist, 연결X
//		Team t1 = new Team();
//		t1.setName("팀1");
//		
//		Team t2 = new Team();
//		t2.setName("팀2");
//		
//		User u1 = new User();
//		u1.setName("홍길동");
//		
//		User u2 = new User();
//		u2.setName("이길동");
//		
//		em.persist(t1);
//		em.persist(t2);
//		
//		em.persist(u1);
//		em.persist(u2);
//		Hibernate: insert into teams (name) values (?)
//		Hibernate: insert into teams (name) values (?)
//		Hibernate: insert into users (name) values (?)
//		Hibernate: insert into users (name) values (?)
		
		
//		// #5. Team 객체 2개 User 객체 2개 연결, 모두 persist
//		Team t1 = new Team();
//		t1.setName("팀1");
//		
//		Team t2 = new Team();
//		t2.setName("팀2");
//		
//		User u1 = new User();
//		u1.setName("홍길동");
//		
//		User u2 = new User();
//		u2.setName("이길동");
//		
//		// 연결코드
//		t1.setUsers(List.of(u1, u2));	// t1에 u1, u2 포함
//		t2.setUsers(List.of(u2));	// t2에 u2 포함
//		em.persist(t1);
//		em.persist(t2);
//		
//		em.persist(u1);
//		em.persist(u2);
////		Hibernate: insert into teams (name) values (?)
////		Hibernate: insert into teams (name) values (?)
////		Hibernate: insert into users (name) values (?)
////		Hibernate: insert into users (name) values (?)
////		Hibernate: insert into teams_users (team_id,user_id) values (?,?)
////		Hibernate: insert into teams_users (team_id,user_id) values (?,?)
////		Hibernate: insert into teams_users (team_id,user_id) values (?,?)
		
		
//		// #6. Team 객체 2개 User 객체 2개 연결, Team만 persist
//		Team t1 = new Team();
//		t1.setName("팀1");
//		
//		Team t2 = new Team();
//		t2.setName("팀2");
//		
//		User u1 = new User();
//		u1.setName("홍길동");
//		
//		User u2 = new User();
//		u2.setName("이길동");
//		
//		// 연결코드
//		t1.setUsers(List.of(u1, u2));	// t1에 u1, u2 포함
//		t2.setUsers(List.of(u2));	// t2에 u2 포함
//		
//		em.persist(t1);
//		em.persist(t2);
		//  org.hibernate.TransientObjectException: persistent instance references an unsaved transient instance of 'entity.User' (save the transient instance before flushing)
		
		
		// #7. Team 객체 2개 User 객체 2개 연결, Team에 CascadeType=PERSIST 설정
		Team t1 = new Team();
		t1.setName("팀1");
		
		Team t2 = new Team();
		t2.setName("팀2");
		
		User u1 = new User();
		u1.setName("홍길동");
		
		User u2 = new User();
		u2.setName("이길동");
		
		// 연결코드
		t1.setUsers(List.of(u1, u2));	// t1에 u1, u2 포함
		t2.setUsers(List.of(u2));	// t2에 u2 포함
		
		em.persist(t1);
		em.persist(t2);
//		Hibernate: insert into teams (name) values (?)
//		Hibernate: insert into users (name) values (?)
//		Hibernate: insert into users (name) values (?)
//		Hibernate: insert into teams (name) values (?)
//		Hibernate: insert into teams_users (team_id,user_id) values (?,?)
//		Hibernate: insert into teams_users (team_id,user_id) values (?,?)
//		Hibernate: insert into teams_users (team_id,user_id) values (?,?)
		
		
		//#8. #9 
		
		em.getTransaction().commit();  // 이 시점에 테이블에 반영한다.
		
		

		em.close();
	}

}
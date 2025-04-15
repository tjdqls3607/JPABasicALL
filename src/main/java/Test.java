import java.util.HashMap;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Comment;
import entity.Post;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.ManyToOne;

//OneToMany
// 1. 테이블이 3개, Entity 각각 1개씩, 연관관꼐를 표현하는 테이블 1개
// 2. ManyToOne 은 Many 가 Owing Rntity 이므로 Many 를 표현하는 테이블에 One 에 해당하는 칼럼을 추가
// 3. OneToMany 는 One 이 Owing Entity 인데, One 을 표현하는 테이블에 복수개의 Many 를 표현 X -> 별도의 관계 테이블 추가
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
		
		Post p = new Post();
		p.setTitle("게시글 1");
		p.setContent("게시글 1 내용");
		
		// #1
		em.persist(p);
		// Hibernate: insert into Post (content,title) values (?,?)
		
		
		em.getTransaction().commit();  // 이 시점에 테이블에 반영한다.

		

		em.close();
	}

}
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Comment;
import entity.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

// ManyToOne - OneToMany BI
// 1. ManyToOne 을 가진 테이블이 Owing Entity
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
		p.setContent("게시글 1");
		
		Comment c1 = new Comment();
		c1.setContent("댓글 1 내용");
		
		Comment c2 = new Comment();
		c2.setContent("댓글 2 내용");
		
		// #1. 연결 없이, Post만 1건 persist
//		em.persist(p);
		// Hibernate: insert into Post (content,title) values (?,?)
		
		// #2. 연결 없이, Comment만 2건 persist
//		em.persist(c1);
//		em.persist(c2);
//		Hibernate: insert into Comment (content,post_id) values (?,?)
//		Hibernate: insert into Comment (content,post_id) values (?,?)
		// 연관관계 컬럼인 post_id 가 null
		
		// #3. 연결 없이, Post 1건, Comment만 2건 persist
//		em.persist(p);
//		em.persist(c1);
//		em.persist(c2);
//		Hibernate: insert into Post (content,title) values (?,?)
//		Hibernate: insert into Comment (content,post_id) values (?,?)
//		Hibernate: insert into Comment (content,post_id) values (?,?)
		// 3 건 모두 insert
		// Comment 의 연관 관계 컬럼인 post_id 가 null
		
		// #4. Comment 에만 post 연결 (ManyToOne), c1, c2 만 persist
//		c1.setPost(p);
//		c2.setPost(p);
//		
//		em.persist(c1);
//		em.persist(c2);
		//  org.hibernate.TransientObjectException: persistent instance references an unsaved transient instance of 'entity.Post' (save the transient instance before flushing)
		// Comment 의 연관 관계 컬럼인 post_id 에 채워질 post 객체가 연결되었으나, 영속화되지 못했기 때문
		
		// #5. Comment 에만 post 연결 (ManyToOne), c1, c2, p 모두 persist
//		c1.setPost(p);
//		c2.setPost(p);
//		
//		em.persist(p);
//		em.persist(c1);
//		em.persist(c2);
//		Hibernate: insert into Post (content,title) values (?,?)
//		Hibernate: insert into Comment (content,post_id) values (?,?)
//		Hibernate: insert into Comment (content,post_id) values (?,?)
		// 3건 모두 insert
		// Comment의 연관 관계 칼럼인 post_id 에 Post 객체의 id 값으로 사용됨
		
		// #5. Post에만 Comment 2개 연결 ( OneToMany ), p 만 persist
		p.setComments(List.of(c1, c2));
		
		em.persist(p);
		em.getTransaction().commit();  // 이 시점에 테이블에 반영한다.

		

		em.close();
	}

}
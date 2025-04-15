import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Comment;
import entity.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.FetchType;

// find
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
		
		// #1 FetchType 설정없이 Post 객체만 find
//		Post p = em.find(Post.class, 1);
		// Hibernate: select p1_0.id,p1_0.content,p1_0.title from Post p1_0 where p1_0.id=?
		// OneToMany 의 One 에 해당하는 Post 에 닳린 Many에 해당하는 Comment 가 매우 많을 수 있는 복수개 이므로
		// 기본 fetch 옵션은 LAZY
		
//		// #2 FetchType 설정없이 Comment 객체만 find
//		Comment c1 = em.find(Comment.class, 1);
//		// Hibernate: select c1_0.id,c1_0.content from Comment c1_0 where c1_0.id=?
//		// OneToMany 의 One 에 해당하는 Comment 는 연관관계가 없으므로 독립적으로 select 수행
		
//		// #3 FetchType 설정없이 Post 객체만 find, Post 객체의 comments 사용
//		Post p = em.find(Post.class, 1);
////		p.getComments();	// 참조변수만 가져오는 것으로 Comment 객체를 사용 코드X
//		
//		try {
//			Thread.sleep(5000);
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		
//		p.getComments().forEach(comment -> System.out.println(comment));
////		p.getComments().forEach(System.out::println);
//		Hibernate: select p1_0.id,p1_0.content,p1_0.title from Post p1_0 where p1_0.id=?
//		Hibernate: select c1_0.Post_id,c1_1.id,c1_1.content from Post_Comment c1_0 join Comment c1_1 on c1_1.id=c1_0.comments_id where c1_0.Post_id=?
		// OneToMany 의 기본 fetch 옵션은 LAZY 이므로 사용하는 시점에 다시 select
		
		// #4 FetchType.EAGER 로 설정, Post 객체만 find, Post 객체의 comments 사용
//		Post p = em.find(Post.class, 1);
//		
//		try {
//			Thread.sleep(5000);
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		
//		p.getComments().forEach(comment -> System.out.println(comment));
//		Hibernate: select c1_0.Post_id,c1_1.id,c1_1.content from Post_Comment c1_0 join Comment c1_1 on c1_1.id=c1_0.comments_id where c1_0.Post_id=?
//		FetchType.EAGER 이므로 Post와 Post 의 연관관계인 Comment 를 한꺼번에 가져온다.
		
		// #5. Post 1개 find, Comment 1 개 생성, 연결 -> Comment persist
		// fetch=FetchType.EAGER 없이 원복 후 테스트
		Post p = em.find(Post.class, 1);
		Comment c3 = new Comment();
		c3.setContent("댓글 3 내용");
		
		
		// Post p 와 comment c3 연결
//		p.setComments(List.of(c3));	// 기존 연결 모두 삭제하고 c3 연결한다는 의미
		p.getComments().add(c3);	// 기존 연결을 유지 한 채, 새로운 c3 객체 추가
		
		// 연결 후, p, c3 영속화가 되어야 한다. 근데 p 는 이미 find() 했으므로 영속화 진행된 상태
		// c3 만 하면 된다.
		em.persist(c3);
//		Hibernate: select p1_0.id,p1_0.content,p1_0.title from Post p1_0 where p1_0.id=?
//		Hibernate: select c1_0.Post_id,c1_1.id,c1_1.content from Post_Comment c1_0 join Comment c1_1 on c1_1.id=c1_0.comments_id where c1_0.Post_id=?
//		Hibernate: insert into Comment (content) values (?)
//		Hibernate: delete from Post_Comment where Post_id=?
//		Hibernate: insert into Post_Comment (Post_id,comments_id) values (?,?)
//		Hibernate: insert into Post_Comment (Post_id,comments_id) values (?,?)
//		Hibernate: insert into Post_Comment (Post_id,comments_id) values (?,?)
		
		em.getTransaction().commit();  // 이 시점에 테이블에 반영한다.
		em.close();
	}

}
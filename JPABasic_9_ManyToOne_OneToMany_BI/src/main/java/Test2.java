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

//ManyToOne - OneToMany BI
//1. ManyToOne 을 가진 테이블이 Owing Entity
//2. Comment, Post 2개의 테이블이 생성
//3. 연관관계를 Comment 의 post_id 칼럼으로 처리
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
		
		// #1. FetchType 설정 없이, Post 객체를 find
//		Post post = em.find(Post.class, 1);
// 		Hibernate: select p1_0.id,p1_0.content,p1_0.title from Post p1_0 where p1_0.id=?
		
		
		// #2. FetchType 설정 없이, Comment 객체를 find
//		Comment comment = em.find(Comment.class, 1);
//		Hibernate: select c1_0.id,c1_0.content,p1_0.id,p1_0.content,p1_0.title from Comment c1_0 left join Post p1_0 on p1_0.id=c1_0.post_id where c1_0.id=?
		// Post 1건 select
		
		
		// #3. FetchType 설정 없이, Post 객체를 find, toString() 으로 comments 사용
//		Post post = em.find(Post.class, 1);
//		System.out.println(post.getComments());
		// StackOverflowError <- 순환 참조 Post - Comment
		// 양방향에서 Post 에 comments 를 사용하는 코드를 실행할 때, Comment 의 toStirng() 이 Post 의 toString() 다시 Comment 의 toString() 이 Post 의 toString()
		// 양방향에서는 toString() 내의 상호참조 등에 주의
		
		
		// #4. Post에서 Comment 의 toString() 상호참조 제거
//		Post post = em.find(Post.class, 1);
//		System.out.println(post.getComments());
		// StackOverflowError <- 순환 참조 Post - Comment
		// 양방향에서 Post 에 comments 를 사용하는 코드를 실행할 때, Comment 의 FetchType 이 EAGER 이므로 발생
		
		
		// #5. FetchType=LAZY, Comment 객체를 find (#2 대비)
//		Comment comment = em.find(Comment.class, 1);
////		Hibernate: select c1_0.id,c1_0.content,p1_0.id,p1_0.content,p1_0.title from Comment c1_0 left join Post p1_0 on p1_0.id=c1_0.post_id where c1_0.id=?
//		// Post 1건 select
//		System.out.println(comment.getPost());
//		Hibernate: select p1_0.id,p1_0.content,p1_0.title from Post p1_0 where p1_0.id=?
//		Post [id=1, title=게시글 1 제목, content=게시글 1]
		
		
		// #6. Post 객체 find(), Comment 객체 생성, 연결 Comment 객체 persist
		Post p = em.find(Post.class, 1);
		
		Comment c3 = new Comment();
		c3.setContent("댓글 3 내용");
		
		c3.setPost(p);
		
		em.persist(c3);

		
		
		em.getTransaction().commit();  // 이 시점에 테이블에 반영한다.
		em.close();
	}

}
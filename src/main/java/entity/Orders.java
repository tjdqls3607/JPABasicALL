package entity;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

@Entity
// 복수개의 @NamedQuery 를 사용할 경우, @NamedQueries 로 묶어 준다. {} 필요
@NamedQueries({
	@NamedQuery(
		name="Orders.findByOrderDate",
		query="select o from Orders o where o.orderDate = :orderDate"
	),
	@NamedQuery(
		name="Orders.findByOrderDateRange",
		query="select o from Orders o where o.orderDate between :startDate and :endDate"
	),
	@NamedQuery(
		name="Orders.findByProductPriceRange",
		query="select o, p.price from Orders o join o.product p where p.price between :startPrice and :endPrice"
	)
})

public class Orders {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne(fetch=FetchType.LAZY)
    private Customer customer;
    
    @ManyToOne(fetch=FetchType.LAZY)
    private Product product;    
    
    
    @Column(name = "order_quantity")
    private int orderQuantity;
    
    @Column(name = "order_date")
    private LocalDate orderDate;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
        
    public int getOrderQuantity() {
        return orderQuantity;
    }
    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }
    
    public LocalDate getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    
    @Override
    public String toString() {
        return "Orders [id=" + id + ", orderQuantity=" + orderQuantity + ", orderDate=" + orderDate + "]";
    }
}
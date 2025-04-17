package entity;

import entity.key.ProductKey;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
//ProductKey 클래스가 프로덕트의 복합 키 표현
// persist 경우 사용X
// find 사용O
@IdClass(ProductKey.class)	
public class Product {
	
	@Id
	private String code;
	
	@Id
	private int number;
	
	private String color;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Product [code=" + code + ", number=" + number + ", color=" + color + "]";
	}
	
	
	

}

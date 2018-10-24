package com.sz.example.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name="item")
@Entity
public class Item implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    private String id;

	@Column(name="title")
    private String title;

    private String memo;

    private BigDecimal money;
    
    @Column(name="test_memo")
    private String testMemo1;
    
    @Transient
    private String str1;

	public String getStr1() {
		return str1;
	}

	public void setStr1(String str1) {
		this.str1 = str1;
	}

	public String getTestMemo1() {
		return testMemo1;
	}

	public void setTestMemo1(String testMemo1) {
		this.testMemo1 = testMemo1;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
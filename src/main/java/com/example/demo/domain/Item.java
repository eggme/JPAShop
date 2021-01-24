package com.example.demo.domain;

import com.example.demo.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
public abstract class Item {

    @Id @GeneratedValue
    @Column(name="ITEM_ID")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items" ,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Category> categories = new ArrayList<>();

    // 비즈니스 로직
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    public void removeStork(int quantity){
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

}

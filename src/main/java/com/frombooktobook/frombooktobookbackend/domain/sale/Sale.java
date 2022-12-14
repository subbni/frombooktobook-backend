package com.frombooktobook.frombooktobookbackend.domain.sale;

import com.frombooktobook.frombooktobookbackend.domain.Product;
import com.frombooktobook.frombooktobookbackend.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Sale {

    @Id
    @Column(name="SALE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="USER_ID")
    private User seller;

    private String title;

    private String content;

    private int hit;

    @OneToOne
    private Product product;

    @Builder
    public Sale(User seller, String title, String content, Product product) {
        this.seller = seller;
        this.title = title;
        this.content = content;
        this.product = product;
        hit = 0;
    }

    public void update(String title, String content, Product product) {
        this.title = title;
        this.content = content;
        this.product.update(product.getName(),product.getPrice(),product.getStock());
    }

    public int addHit() {
        this.hit++;
        return hit;
    }
}

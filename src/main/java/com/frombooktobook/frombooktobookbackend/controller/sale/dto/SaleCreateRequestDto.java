package com.frombooktobook.frombooktobookbackend.controller.sale.dto;

import com.frombooktobook.frombooktobookbackend.domain.Product;
import com.frombooktobook.frombooktobookbackend.domain.sale.Sale;
import com.frombooktobook.frombooktobookbackend.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SaleCreateRequestDto {
    private String userEmail;
    private String title;
    private String content;
    private Product product;

    @Builder
    public SaleCreateRequestDto(String userEmail, String title, String content, Product product) {
        this.userEmail=userEmail;
        this.title = title;
        this.content = content;
        this.product = product;
    }

    public Sale toEntity(User user) {
        return Sale.builder()
                .seller(user)
                .title(title)
                .content(content)
                .product(product)
                .build();
    }
}

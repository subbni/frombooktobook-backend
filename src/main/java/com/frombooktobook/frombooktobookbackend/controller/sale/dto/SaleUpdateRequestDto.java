package com.frombooktobook.frombooktobookbackend.controller.sale.dto;

import com.frombooktobook.frombooktobookbackend.domain.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SaleUpdateRequestDto {
    private Long saleId;
    private String title;
    private String content;
    private Product product;

    public SaleUpdateRequestDto(Long saleId, String title, String content, Product product) {
        this.saleId = saleId;
        this.title = title;
        this.content = content;
        this.product = product;
    }
}

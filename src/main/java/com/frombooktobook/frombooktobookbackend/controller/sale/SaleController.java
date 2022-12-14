package com.frombooktobook.frombooktobookbackend.controller.sale;

import com.frombooktobook.frombooktobookbackend.controller.auth.dto.ApiResponseDto;
import com.frombooktobook.frombooktobookbackend.controller.sale.dto.SaleCreateRequestDto;
import com.frombooktobook.frombooktobookbackend.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sale")
public class SaleController {
    private final SaleService saleService;

    @PreAuthorize("hasRole('ROLE_AUTHENTICATED_USER')")
    @PostMapping("/create")
    public ApiResponseDto createSale(@RequestBody SaleCreateRequestDto requestDto) {
        try{
            saleService.saveSale(requestDto);
            return new ApiResponseDto(true,"작성 완료되었습니다.");
        } catch(Exception e) {
            return new ApiResponseDto(false,"작성에 실패하였습니다. "+e.getMessage());
        }
    }


}

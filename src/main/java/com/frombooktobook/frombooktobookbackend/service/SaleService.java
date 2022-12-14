package com.frombooktobook.frombooktobookbackend.service;

import com.frombooktobook.frombooktobookbackend.controller.sale.dto.SaleCreateRequestDto;
import com.frombooktobook.frombooktobookbackend.domain.sale.Sale;
import com.frombooktobook.frombooktobookbackend.domain.sale.SaleRepository;
import com.frombooktobook.frombooktobookbackend.domain.user.User;
import com.frombooktobook.frombooktobookbackend.domain.user.UserRepository;
import com.frombooktobook.frombooktobookbackend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class SaleService {
    private final SaleRepository saleRepository;
    private final UserRepository userRepository;

    @Transactional
    public Sale saveSale(SaleCreateRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getUserEmail())
                .orElseThrow(()->new ResourceNotFoundException("User","email",requestDto.getUserEmail()));
        Sale sale = requestDto.toEntity(user);
        saleRepository.save(sale);
        return sale;
    }



}

package com.frombooktobook.frombooktobookbackend.domain.sale;

import com.frombooktobook.frombooktobookbackend.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale,Long> {
    List<Sale> findBySeller(User user);
    Page<Sale> findBySeller(User user, Pageable pageable);
}

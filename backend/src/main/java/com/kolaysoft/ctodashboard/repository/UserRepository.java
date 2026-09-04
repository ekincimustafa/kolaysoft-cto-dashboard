package com.kolaysoft.ctodashboard.repository;

import com.kolaysoft.ctodashboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Giriş yaparken kullanıcıyı e-posta ile bulmak için
    Optional<User> findByEmail(String email);

    // Aynı e-postayla mükerrer kayıt açılmasını engellemek için
    boolean existsByEmail(String email);
}
package com.example.urlshort.repository;

import com.example.urlshort.entity.Link; // Link 클래스 경로에 맞게 수정하세요
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// JpaRepository<엔티티 클래스, PK 데이터 타입> 을 상속받습니다.
public interface LinkRepository extends JpaRepository<Link, Long> {
    // 단축 코드로 원본 URL을 찾기 위한 메서드 (나중에 쓰입니다!)
    Optional<Link> findByShortCode(String shortCode);
}
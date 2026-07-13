package com.example.urlshort.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity // ⭐️ 중요: 이 클래스를 바탕으로 DB에 테이블을 만들라고 스프링에게 알립니다.
@Getter
@Setter
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 (AUTO_INCREMENT)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT") // 필수 입력, TEXT 타입
    private String originalUrl;

    @Column(nullable = false, unique = true, length = 10) // 필수 입력, 중복 불가, 길이 10
    private String shortCode;

    @CreationTimestamp // 데이터가 저장될 때 시간 자동 입력 (CURRENT_TIMESTAMP)
    private LocalDateTime createdAt;
}
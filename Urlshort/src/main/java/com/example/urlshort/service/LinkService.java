package com.example.urlshort.service;

import com.example.urlshort.entity.Link;
import com.example.urlshort.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service // 스프링에게 "이게 비즈니스 로직을 처리하는 클래스야"라고 알려줍니다.
@RequiredArgsConstructor // 의존성 주입을 편하게 해주는 롬복 어노테이션입니다.
public class LinkService {

    private final LinkRepository linkRepository;

    @Transactional
    public String createShortUrl(String originalUrl) {
        // 1. 8자리 랜덤 문자열 생성
        String shortCode = generateRandomCode(8);

        // 2. 만약 우연히 똑같은 코드가 DB에 있다면? 다시 생성! (중복 방지)
        while (linkRepository.findByShortCode(shortCode).isPresent()) {
            shortCode = generateRandomCode(8);
        }

        // 3. 데이터를 담을 객체(Entity) 생성
        Link link = new Link();
        // 주의: Link 클래스에 @Setter 또는 생성자가 있어야 아래 코드가 작동합니다.
        link.setOriginalUrl(originalUrl);
        link.setShortCode(shortCode);

        // 4. DB에 저장!
        linkRepository.save(link);

        // 5. 생성된 짧은 코드 반환
        return shortCode;
    }

    // [참고] 무작위 영문 대소문자 + 숫자 조합을 만들어주는 도우미 메서드
    private String generateRandomCode(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            result.append(characters.charAt(random.nextInt(characters.length())));
        }
        return result.toString();
    }
}
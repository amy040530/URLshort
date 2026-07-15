package com.example.urlshort.controller;

import com.example.urlshort.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;       // 추가됨
import org.springframework.http.ResponseEntity;

import java.net.URI;

@RestController // 데이터를 직접 반환하는 API 컨트롤러입니다.
@RequestMapping("/api") // 이 컨트롤러의 기본 주소는 /api 로 시작합니다.
@RequiredArgsConstructor
public class UrlController {

    private final LinkService linkService;

    // POST 방식으로 /api/shorten 주소에 요청이 오면 실행됩니다.
    @PostMapping("/shorten")
    public String shortenUrl(@RequestParam String originalUrl) {
        // 서비스에 원래 URL을 넘겨주고, 단축된 코드를 받아와서 화면에 출력합니다.
        return linkService.createShortUrl(originalUrl);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortCode) {
        // 1. Service를 통해 단축 코드에 해당하는 원본 URL을 가져옵니다.
        String originalUrl = linkService.getOriginalUrl(shortCode);

        // 2. 브라우저에게 "이쪽 주소로 이동해!" 라고 302(Found) 상태 코드와 함께 리다이렉트 지시를 내립니다.
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}
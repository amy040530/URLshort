package com.example.urlshort.controller;

import com.example.urlshort.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UrlController {

    private final LinkService linkService;

    // 💡 프론트엔드 주소(/api/links)에 맞게 변경하고, JSON 데이터를 받도록 @RequestBody를 사용합니다.
    @PostMapping("/links")
    public Map<String, String> shortenUrl(@RequestBody Map<String, String> requestData) {
        // 1. 프론트엔드에서 JSON으로 보낸 {"originalUrl": "입력한주소"} 에서 원본 URL을 꺼냅니다.
        String originalUrl = requestData.get("originalUrl");

        // 2. 기존 서비스 로직을 실행해서 단축 코드를 생성합니다.
        String shortCode = linkService.createShortUrl(originalUrl);

        // 3. 프론트엔드가 기대하는 JSON {"shortCode": "단축코드"} 형태로 만들어서 돌려줍니다.
        Map<String, String> response = new HashMap<>();
        response.put("shortCode", shortCode);

        return response;
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
package com.example.urlshort.controller;

import com.example.urlshort.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
}
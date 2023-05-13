package com.spring.week1.controller;

import com.spring.week1.dto.CookieLoginDto;
import com.spring.week1.dto.CookieModifyDto;
import com.spring.week1.dto.CookieWithdrawlDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/cookie")
public class CookieController {
    @GetMapping
    public Map<String,String> getCookies(HttpServletRequest request){
        Map<String,String> cookiesJson = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookiesJson.put(cookie.getName(), cookie.getValue());
            }
        }
        return cookiesJson;
    }
    @PostMapping(path = "/login")
    public String saveCookies(@RequestBody() CookieLoginDto dto, @CookieValue(value = "user",defaultValue = "")String id, HttpServletResponse response){
        if(!id.isBlank()) {
            return "Hi "+id;
        }
        String user = dto.getUser();
        Cookie cookie = new Cookie("user",user);
        response.addCookie(cookie);
        return "Setting Complete";
    }
    @PostMapping(path = "/modify")
    public String modifyCookies(@RequestBody() @Validated CookieModifyDto dto, HttpServletResponse response){
        String key = dto.getKey();
        String value = dto.getValue();
        Cookie cookie = new Cookie(key,value);
        response.addCookie(cookie);
        return "Modify Complete!";
    }
    @PostMapping(path = "/withdrawl")
    public String withdrawlCookies(@RequestBody()CookieWithdrawlDto dto,HttpServletResponse response){
        String key = dto.getKey();
        Cookie cookie = new Cookie(key,null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "Withdrawl Complete!";


    }

}

package org.nutsalhan87.web4backend.controller;

import org.nutsalhan87.web4backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/sign")
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/in")
    public Map<String, String> in(@RequestParam(value = "username") String username,
                  @RequestParam(value = "password") String password) {
        Long token = userService.signIn(username, password);
        Map<String, String> map = new HashMap<>();
        map.put("x-api-token", Long.toString(token));
        return map;
    }

    @PostMapping("/up")
    public Map<String, String> up(@RequestParam(value = "username") String username,
                   @RequestParam(value = "password") String password) {
        Long token = userService.addUser(username, password);
        Map<String, String> map = new HashMap<>();
        map.put("x-api-token", Long.toString(token));
        return map;
    }

    @PostMapping("/by-token")
    public Map<String, String> byToken(@RequestParam(value = "x-api-token") Long token) {
        userService.throwIfTokenInvalid(token);
        Map<String, String> map = new HashMap<>();
        map.put("x-api-token", Long.toString(token));
        return map;
    }

    @PostMapping("/exit")
    public void exit(@RequestParam(value = "x-api-token") Long token) {
        userService.makeTokenNull(token);
    }
}

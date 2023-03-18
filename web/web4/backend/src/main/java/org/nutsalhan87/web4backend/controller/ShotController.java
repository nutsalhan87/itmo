package org.nutsalhan87.web4backend.controller;

import org.nutsalhan87.web4backend.service.ShotService;
import org.nutsalhan87.web4backend.model.Shot;
import org.nutsalhan87.web4backend.service.UserService;
import org.nutsalhan87.web4backend.util.Clock;
import org.nutsalhan87.web4backend.util.ShotHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/shots")
public class ShotController {
    private final ShotService shotService;
    private final UserService userService;

    @Autowired
    public ShotController(ShotService shotService, UserService userService) {
        this.shotService = shotService;
        this.userService = userService;
    }

    @PostMapping("/get")
    public List<Shot> getCoordinates(@RequestParam(value = "x-api-token") Long token) {
        String username = userService.getUsernameByToken(token);
        return shotService.getShotsByUsername(username);
    }

    @PostMapping("/send")
    public Shot process(@RequestParam(value = "x") float x, @RequestParam("y") float y, @RequestParam("r") float r,
                        @RequestParam(value = "x-api-token") Long token) {
        Instant time = Instant.now();
        ShotHandler.validateOrThrow(x, y, r);
        String username = userService.getUsernameByToken(token);
        String date = Clock.formatter.format(new Date());
        boolean result = ShotHandler.isInRectangle(x, y, r) || ShotHandler.isInTriangle(x, y, r) || ShotHandler.isInQuartersphere(x, y, r);
        Shot shot = new Shot(date, (Instant.now().getNano() - time.getNano()) / 1000, x, y, r, result, username);
        shotService.addShot(shot);
        return shot;
    }
}

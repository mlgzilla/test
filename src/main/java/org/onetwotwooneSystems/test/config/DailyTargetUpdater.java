package org.onetwotwooneSystems.test.config;

import lombok.AllArgsConstructor;
import org.onetwotwooneSystems.test.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Configuration
@AllArgsConstructor
public class DailyTargetUpdater {

    private final UserServiceImpl userService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduleDailyTargetReset() {
        userService.resetDailyTarget();
    }
}

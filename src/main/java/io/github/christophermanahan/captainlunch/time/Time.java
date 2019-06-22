package io.github.christophermanahan.captainlunch.time;

import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class Time {

    public Date now() {
        return Calendar.getInstance().getTime();
    }
}

package io.github.christophermanahan.captainlunch.time;

import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class Clock implements Time {

    public Date now() {
        return Calendar.getInstance().getTime();
    }
}

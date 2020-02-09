package pl.ultimasolution.loan.tools;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class Tools {
    public Tools() {
    }

    public Date getCurentTime() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Now use today date.
        return c.getTime();
    }

    public boolean checkRiskyCurentTime() {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime minRange = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0); //Today, 10:30pm
        LocalDateTime maxRange = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 06, 0); //Tomorrow, 6:30am
        // maxRange = maxRange.plusDays(1); //Ensures that you don't run into an exception if minRange is the last day in the month.
        return now.isAfter(minRange) && now.isBefore(maxRange);
    }

    public String getCurentIp() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }
}
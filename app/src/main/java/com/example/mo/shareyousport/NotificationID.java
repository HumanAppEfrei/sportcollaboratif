package com.example.mo.shareyousport;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Mo on 16/04/2016.
 */
public class NotificationID {
    private final static AtomicInteger c = new AtomicInteger(0);
    public static int getID() {
        return c.incrementAndGet();
    }
}
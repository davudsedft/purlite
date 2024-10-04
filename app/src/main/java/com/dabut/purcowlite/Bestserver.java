package com.dabut.purcowlite;
public class Bestserver {
    private final String name;
    private final int ping;

    public Bestserver(String name, int ping) {
        this.name = name;
        this.ping = ping;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return ping;
    }
}
package com.example.legacy;

import java.util.Date;

public class LegacyCode {

    public String processDate(Date date) {
        if (date == null) {
            return "No date provided.";
        }
        return "Processed: " + date.toString(); // Deprecated toString() usage.
    }

    public void oldSwitch(String value) {
        switch (value) {
            case "A":
                System.out.println("Alpha");
                break;
            case "B":
                System.out.println("Beta");
                break;
            default:
                System.out.println("Unknown");
        }
    }

    public String longString(){
        return "This is a very long string that \n" +
                "spans multiple lines. It's quite \n" +
                "cumbersome to manage in Java 8.";
    }

    public void someOperation(){
        Integer someInteger = new Integer(10); // Deprecated constructor.
        System.out.println("Integer value: " + someInteger);
    }
}
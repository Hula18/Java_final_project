package org.example.java_final_project.Client.Controller.Bank;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Demo1 {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String currentDate = now.format(dateFormatter);
        String currentTime = now.format(timeFormatter);
        while (true){
            System.out.println(currentDate + " "+currentTime);
        }
    }
}

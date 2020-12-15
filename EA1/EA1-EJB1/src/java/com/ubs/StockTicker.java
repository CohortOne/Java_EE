/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ubs;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author chand
 */
@Stateless
@LocalBean
public class StockTicker {

    static int counter = 0;
    @Schedule(dayOfWeek = "*", month = "*", hour = "*", dayOfMonth = "*", year = "*", minute = "*", second = "0")
    
    public void myTimer() {
        
        System.out.println("**********************************************");
        System.out.println("Timer event: " + LocalDateTime.now());
        System.out.println("Timer event: " + counter++);
        System.out.println("**********************************************");
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}

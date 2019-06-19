/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.schedule;

import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Startup;
import javax.inject.Singleton;

/**
 *
 * @author avbravo
 */
@Startup
@Singleton
public class FixedTimerBean {
    @EJB
    private WorkerBean workerBean;
 
    @Lock(LockType.READ)
    @Schedule(second = "*/5", minute = "3", hour = "*", persistent = false)
    public void atSchedule() throws InterruptedException {
        System.out.println("------------------------------------------");
        System.out.println("----se disparo a "+ new Date());
        System.out.println("------------------------------------------");
        workerBean.doTimerWork();
    }
}

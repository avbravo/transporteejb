/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.services;

import com.avbravo.jmoordb.mongodb.history.services.AutoincrementableServices;
import com.avbravo.jmoordb.pojos.JmoordbNotifications;
import com.avbravo.jmoordb.pojos.UserInfo;
import com.avbravo.jmoordb.profiles.repository.JmoordbNotificationsRepository;
import com.avbravo.jmoordbutils.DateUtil;
import com.avbravo.jmoordbutils.JsfUtil;

import com.avbravo.transporteejb.entity.Estatus;
import com.avbravo.transporteejb.repository.SolicitudRepository;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @authoravbravo
 */
@Stateless
public class NotificacionServices {

    @Inject
   JmoordbNotificationsRepository repository;
    @Inject
    AutoincrementableServices autoincrementableServices;
    @Inject
    SolicitudRepository solicitudRepository;
    List<Estatus> estatusList = new ArrayList<>();

  
 // <editor-fold defaultstate="collapsed" desc="Boolean saveNotification(String username)">
    public Boolean saveNotification(String mensaje, String username, String tiposolicitud) {
        try {
            JmoordbNotifications jmoordbNotifications = new JmoordbNotifications();

            jmoordbNotifications.setIdjmoordbnotifications(autoincrementableServices.getContador("jmoordbnNotifications"));
            jmoordbNotifications.setUsername(username);
            jmoordbNotifications.setMessage(mensaje);
            jmoordbNotifications.setViewed("no");
            jmoordbNotifications.setDate(DateUtil.fechaActual());
            jmoordbNotifications.setType(tiposolicitud);

            List<UserInfo> list = repository.generateListUserinfo(username, "create");

            jmoordbNotifications.setUserInfo(list);
            
            repository.save(jmoordbNotifications);
            return true;
        } catch (Exception e) {
               JsfUtil.errorMessage("saveNotification " + e.getLocalizedMessage());


        }
        return false;
    }// </editor-fold>

}

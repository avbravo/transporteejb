/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.services;

import com.avbravo.jmoordb.mongodb.history.services.ErrorInfoServices;
import com.avbravo.jmoordb.util.JmoordbUtil;
import com.avbravo.transporteejb.entity.Conductor;
import com.avbravo.transporteejb.repository.ConductorRepository;
import com.avbravo.transporteejb.repository.ViajeRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.bson.Document;

/**
 *
 * @authoravbravo
 */
@Stateless
public class DiasServices {

    @Inject
    ErrorInfoServices errorServices;
    private List<String> diasList;

    public List<String> generarDiasList() {

        try {
            diasList = new ArrayList<String>();
            diasList.add("Día/ Días Consecutivo");
            diasList.add("Lunes");
            diasList.add("Martes");
            diasList.add("Miercoles");
            diasList.add("Jueves");
            diasList.add("Viernes");
            diasList.add("Sabado");
            diasList.add("Domingo");
        } catch (Exception e) {
            errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return diasList;
    }

}

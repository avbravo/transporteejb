/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.services;

import com.avbravo.avbravoutils.JsfUtil;
import com.avbravo.transporteejb.entity.Solicitud;
import com.avbravo.transporteejb.repository.SolicitudRepository;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.bson.Document;

/**
 *
 * @authoravbravo
 */
@Stateless
public class SolicitudServices {

    @Inject
    SolicitudRepository repository;
List<Solicitud> solicitudList = new ArrayList<>();
    public List<Solicitud> complete(String query) {
        List<Solicitud> suggestions = new ArrayList<>();
            try {
          suggestions=repository.complete(query);
        } catch (Exception e) {
            JsfUtil.errorMessage("complete() " + e.getLocalizedMessage());
        }

           return suggestions;
    }

    
    // <editor-fold defaultstate="collapsed" desc="getSolicitudList()">
    public List<Solicitud> getSolicitudList() {
        try {
           solicitudList= repository.findAll(new Document("solicitud",1));
        } catch (Exception e) {
              JsfUtil.errorMessage("getSolicitudList() " + e.getLocalizedMessage());
        }
        return solicitudList;
    }// </editor-fold>

    public void setSolicitudList(List<Solicitud> solicitudList) {
        this.solicitudList = solicitudList;
    }
}

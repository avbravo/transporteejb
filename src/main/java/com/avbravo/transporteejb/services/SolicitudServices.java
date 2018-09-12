/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.services;

import com.avbravo.avbravoutils.JsfUtil;
import com.avbravo.transporteejb.entity.Solicitud;
import com.avbravo.transporteejb.repository.SolicitudRepository;
import com.avbravo.transporteejb.repository.ViajesRepository;
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
public class SolicitudServices {

    @Inject
    SolicitudRepository repository;
      @Inject
    ViajesRepository viajesRepository;
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
    
       // <editor-fold defaultstate="collapsed" desc="isDeleted(Solicitud solicitud)">
    public Boolean isDeleted(Solicitud solicitud) {
        Boolean found = false;
        try {
            Document doc = new Document("solicitud.idsolicitud", solicitud.getIdsolicitud());
            Integer count = viajesRepository.count(doc);
            if (count > 0) {
                return false;
            }

        } catch (Exception e) {
            JsfUtil.errorMessage("isDeleted() " + e.getLocalizedMessage());
        }
        return true;
    }  // </editor-fold>
    
     // <editor-fold defaultstate="collapsed" desc="findById(Integer id)">

    
    public Solicitud findById(Integer id){
           Solicitud solicitud = new Solicitud();
        try {
         
            solicitud.setIdsolicitud(id);
            Optional<Solicitud> optional = repository.findById(solicitud);
            if (optional.isPresent()) {
               return optional.get();
            } 
        } catch (Exception e) {
             JsfUtil.errorMessage("findById() " + e.getLocalizedMessage());
        }
      
      return solicitud;
    }
  // </editor-fold>
}

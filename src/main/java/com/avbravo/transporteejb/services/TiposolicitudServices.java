/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.services;

import com.avbravo.jmoordbutils.JsfUtil;
import com.avbravo.transporteejb.entity.Tiposolicitud;
import com.avbravo.transporteejb.repository.SolicitudRepository;
import com.avbravo.transporteejb.repository.TiposolicitudRepository;
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
public class TiposolicitudServices {

    @Inject
    TiposolicitudRepository repository;
      @Inject
   SolicitudRepository solicitudRepository;
    List<Tiposolicitud> tiposolicitudList = new ArrayList<>();
     public List<Tiposolicitud> complete(String query) {
        List<Tiposolicitud> suggestions = new ArrayList<>();
           try {
          suggestions=repository.complete(query);
        } catch (Exception e) {
            JsfUtil.errorMessage("complete() " + e.getLocalizedMessage());
        }

           return suggestions;
    }

    public List<Tiposolicitud> getTiposolicitudList() {
          try {
        tiposolicitudList= repository.findAll(new Document("idtiposolicitud",1));
        } catch (Exception e) {
              JsfUtil.errorMessage("getTiposolicitudList() " + e.getLocalizedMessage());
        }

        return tiposolicitudList;
    }

    public void setTiposolicitudList(List<Tiposolicitud> tiposolicitudList) {
        this.tiposolicitudList = tiposolicitudList;
    }
     
     
         // <editor-fold defaultstate="collapsed" desc="isDeleted(Tiposolicitud tiposolicitud)">
  
    public Boolean isDeleted(Tiposolicitud tiposolicitud){
        Boolean found=false;
        try {
            Document doc = new Document("tiposolicitud.idtiposolicitud",tiposolicitud.getIdtiposolicitud());
            Integer count = solicitudRepository.count(doc);
            if (count > 0){
                return false;
            }
            
        } catch (Exception e) {
             JsfUtil.errorMessage("isDeleted() " + e.getLocalizedMessage());
        }
        return true;
    }  // </editor-fold>
     
     // <editor-fold defaultstate="collapsed" desc="findById(String id)">

        public Tiposolicitud findById(String id){
           Tiposolicitud tiposolicitud = new Tiposolicitud();
        try {
         
            tiposolicitud.setIdtiposolicitud(id);
            Optional<Tiposolicitud> optional = repository.findById(tiposolicitud);
            if (optional.isPresent()) {
               return optional.get();
            } 
        } catch (Exception e) {
             JsfUtil.errorMessage("findById() " + e.getLocalizedMessage());
        }
      
      return tiposolicitud;
    }
    // </editor-fold>
}

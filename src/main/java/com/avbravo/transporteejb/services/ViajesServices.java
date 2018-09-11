/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.services;

import com.avbravo.avbravoutils.JsfUtil;
import com.avbravo.transporteejb.entity.Viajes;
import com.avbravo.transporteejb.entity.Viajes;
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
public class ViajesServices {

    @Inject
    ViajesRepository repository;
List<Viajes> solicitudList = new ArrayList<>();
    public List<Viajes> complete(String query) {
        List<Viajes> suggestions = new ArrayList<>();
            try {
          suggestions=repository.complete(query);
        } catch (Exception e) {
            JsfUtil.errorMessage("complete() " + e.getLocalizedMessage());
        }

           return suggestions;
    }

    
    // <editor-fold defaultstate="collapsed" desc="getViajesList()">
    public List<Viajes> getViajesList() {
        try {
           solicitudList= repository.findAll(new Document("solicitud",1));
        } catch (Exception e) {
              JsfUtil.errorMessage("getViajesList() " + e.getLocalizedMessage());
        }
        return solicitudList;
    }// </editor-fold>

    public void setViajesList(List<Viajes> solicitudList) {
        this.solicitudList = solicitudList;
    }
    
    
    
    
       // <editor-fold defaultstate="collapsed" desc="isDeleted(Rol rol)">
  
    public Boolean isDeleted(Viajes viajes){
        Boolean found=false;
        try {
        
        } catch (Exception e) {
             JsfUtil.errorMessage("isDeleted() " + e.getLocalizedMessage());
        }
        return true;
    }  // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="findById(String id)">

    public Viajes findById(Integer id){
           Viajes viajes = new Viajes();
        try {
         
            viajes.setIdviaje(id);
            Optional<Viajes> optional = repository.findById(viajes);
            if (optional.isPresent()) {
               return optional.get();
            } 
        } catch (Exception e) {
             JsfUtil.errorMessage("findById() " + e.getLocalizedMessage());
        }
      
      return viajes;
    }
    // </editor-fold
}

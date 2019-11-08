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
public class ConductorServices {
     @Inject
    ErrorInfoServices errorServices;
 @Inject
    ViajeRepository viajesRepository;
        
    @Inject
    ConductorRepository repository;
    List<Conductor> conductorList = new ArrayList<>();
    
     public List<Conductor> complete(String query) {
        List<Conductor> suggestions = new ArrayList<>();
           try {
          suggestions=repository.complete(query);
        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e); // JmoordbUtil.errorMessage("complete() " + e.getLocalizedMessage());
        }

           return suggestions;
    }

    public List<Conductor> getConductorList() {
          try {
        conductorList= repository.findAll(new Document("idconductor",1));
        } catch (Exception e) {
               errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e); // JmoordbUtil.errorMessage("getConductorList() " + e.getLocalizedMessage());
        }

        return conductorList;
    }

    public void setConductorList(List<Conductor> conductorList) {
        this.conductorList = conductorList;
    }
     
     // <editor-fold defaultstate="collapsed" desc="isDeleted(Conductor conductor)">
  
    public Boolean isDeleted(Conductor conductor){
        Boolean found=false;
        try {
            Document doc = new Document("conductor.idconductor",conductor.getIdconductor());
            Integer count = viajesRepository.count(doc);
            if (count > 0){
                return false;
            }
            
        } catch (Exception e) {
              errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e); // JmoordbUtil.errorMessage("isDeleted() " + e.getLocalizedMessage());
        }
        return true;
    }  // </editor-fold>
     
       // <editor-fold defaultstate="collapsed" desc="findById(Integer id)">

    public Conductor findById(Integer id){
           Conductor conductor = new Conductor();
        try {
         
            conductor.setIdconductor(id);
            Optional<Conductor> optional = repository.findById(conductor);
            if (optional.isPresent()) {
               return optional.get();
            } 
        } catch (Exception e) {
              errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e); // JmoordbUtil.errorMessage("findById() " + e.getLocalizedMessage());
        }
      
      return conductor;
    }
    // </editor-fold>
     
}

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.services;

import com.avbravo.avbravoutils.JsfUtil;
import com.avbravo.transporteejb.entity.Conductor;
import com.avbravo.transporteejb.repository.ConductorRepository;
import com.avbravo.transporteejb.repository.ViajesRepository;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.bson.Document;

/**
 *
 * @authoravbravo
 */
@Stateless
public class ConductorServices {
 @Inject
    ViajesRepository viajesRepository;
        
    @Inject
    ConductorRepository repository;
    List<Conductor> conductorList = new ArrayList<>();
    
     public List<Conductor> complete(String query) {
        List<Conductor> suggestions = new ArrayList<>();
           try {
          suggestions=repository.complete(query);
        } catch (Exception e) {
            JsfUtil.errorMessage("complete() " + e.getLocalizedMessage());
        }

           return suggestions;
    }

    public List<Conductor> getConductorList() {
          try {
        conductorList= repository.findAll(new Document("idconductor",1));
        } catch (Exception e) {
              JsfUtil.errorMessage("getConductorList() " + e.getLocalizedMessage());
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
             JsfUtil.errorMessage("isDeleted() " + e.getLocalizedMessage());
        }
        return true;
    }  // </editor-fold>
     
     
     
}

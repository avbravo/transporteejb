/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.services;

import com.avbravo.avbravoutils.JsfUtil;
import com.avbravo.transporteejb.entity.Estatus;
import com.avbravo.transporteejb.repository.EstatusRepository;
import com.avbravo.transporteejb.repository.SolicitudRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
public class EstatusServices {

    @Inject
    EstatusRepository repository;
 @Inject
   SolicitudRepository solicitudRepository;
    List<Estatus> estatusList = new ArrayList<>();

    public List<Estatus> complete(String query) {
        List<Estatus> suggestions = new ArrayList<>();
           try {
          suggestions=repository.complete(query);
        } catch (Exception e) {
            JsfUtil.errorMessage("complete() " + e.getLocalizedMessage());
        }

        return suggestions;
    }

    public List<Estatus> getEstatusList() {
        try {
            estatusList = repository.findAll(new Document("estatus", 1));
        } catch (Exception e) {
            JsfUtil.errorMessage("getEstatusList() " + e.getLocalizedMessage());
        }

        return estatusList;
    }

    public void setEstatusList(List<Estatus> estatusList) {
        this.estatusList = estatusList;
    }
    
        // <editor-fold defaultstate="collapsed" desc="isDeleted(Estatus estatus)">
  
    public Boolean isDeleted(Estatus estatus){
        Boolean found=false;
        try {
            Document doc = new Document("estatus.idestatus",estatus.getIdestatus());
            Integer count = solicitudRepository.count(doc);
            if (count > 0){
                return false;
            }
            
        } catch (Exception e) {
             JsfUtil.errorMessage("isDeleted() " + e.getLocalizedMessage());
        }
        return true;
    }  // </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="findById(Integer id)">

    public Estatus findById(String id){
           Estatus estatus = new Estatus();
        try {
         
            estatus.setIdestatus(id);
            Optional<Estatus> optional = repository.findById(estatus);
            if (optional.isPresent()) {
               return optional.get();
            } 
        } catch (Exception e) {
             JsfUtil.errorMessage("findById() " + e.getLocalizedMessage());
        }
      
      return estatus;
    }
    // </editor-fold>

}

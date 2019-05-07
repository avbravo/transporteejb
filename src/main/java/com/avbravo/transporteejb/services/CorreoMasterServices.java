/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.services;

import com.avbravo.jmoordbutils.JsfUtil;

import com.avbravo.transporteejb.entity.CorreoMaster;
import com.avbravo.transporteejb.repository.CorreoMasterRepository;
import com.avbravo.transporteejb.repository.SolicitudRepository;
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
public class CorreoMasterServices {

    @Inject
    CorreoMasterRepository repository;
 @Inject
   SolicitudRepository solicitudRepository;
    List<CorreoMaster> correoMasterList = new ArrayList<>();

    public List<CorreoMaster> complete(String query) {
        List<CorreoMaster> suggestions = new ArrayList<>();
           try {
          suggestions=repository.complete(query);
        } catch (Exception e) {
            JsfUtil.errorMessage("complete() " + e.getLocalizedMessage());
        }

        return suggestions;
    }

    public List<CorreoMaster> getCorreoMasterList() {
        try {
            correoMasterList = repository.findAll(new Document("correoMaster", 1));
        } catch (Exception e) {
            JsfUtil.errorMessage("getCorreoMasterList() " + e.getLocalizedMessage());
        }

        return correoMasterList;
    }

    public void setCorreoMasterList(List<CorreoMaster> correoMasterList) {
        this.correoMasterList = correoMasterList;
    }
    
        // <editor-fold defaultstate="collapsed" desc="isDeleted(CorreoMaster correoMaster)">
  
    public Boolean isDeleted(CorreoMaster correoMaster){
        Boolean found=false;
        try {
            Document doc = new Document("correoMaster.email",correoMaster.getEmail());
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

    public CorreoMaster findById(String id){
           CorreoMaster correoMaster = new CorreoMaster();
        try {
         
            correoMaster.setEmail(id);
            Optional<CorreoMaster> optional = repository.findById(correoMaster);
            if (optional.isPresent()) {
               return optional.get();
            } 
        } catch (Exception e) {
             JsfUtil.errorMessage("findById() " + e.getLocalizedMessage());
        }
      
      return correoMaster;
    }
    // </editor-fold>

}

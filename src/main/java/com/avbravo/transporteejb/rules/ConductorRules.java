/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.rules;

import com.avbravo.avbravoutils.JsfUtil;
import com.avbravo.transporteejb.entity.Conductor;
import com.avbravo.transporteejb.repository.ViajesRepository;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.bson.Document;

/**
 *
 * @authoravbravo
 */
@Stateless
public class ConductorRules {

    @Inject
    ViajesRepository viajesRepository;
    
   

    // <editor-fold defaultstate="collapsed" desc="isDeleted(Tamano tamano)">
  
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

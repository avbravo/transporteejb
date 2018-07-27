/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.rules;

import com.avbravo.avbravoutils.JsfUtil;
import com.avbravo.transporteejb.entity.Estatus;
import com.avbravo.transporteejb.repository.SolicitudRepository;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.bson.Document;

/**
 *
 * @authoravbravo
 */
@Stateless
public class EstatusRules {

    @Inject
   SolicitudRepository solicitudRepository;
    
   

    // <editor-fold defaultstate="collapsed" desc="isDeleted(Tamano tamano)">
  
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
   
}

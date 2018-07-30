/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.rules;

import com.avbravo.avbravoutils.JsfUtil;
import com.avbravo.transporteejb.entity.Tiposolicitud;
import com.avbravo.transporteejb.repository.SolicitudRepository;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.bson.Document;

/**
 *
 * @authoravbravo
 */
@Stateless
public class TiposolicitudRules {

    @Inject
   SolicitudRepository solicitudRepository;
    
   

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
   
}

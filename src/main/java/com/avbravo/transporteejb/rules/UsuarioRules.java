/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.rules;

import com.avbravo.avbravoutils.JsfUtil;
import com.avbravo.transporteejb.entity.Usuario;
import com.avbravo.transporteejb.repository.SolicitudRepository;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.bson.Document;

/**
 *
 * @author avbravo
 */
@Stateless
public class UsuarioRules {
     @Inject
   SolicitudRepository solicitudRepository;
    
   

    
 
    // <editor-fold defaultstate="collapsed" desc="isDeleted(...)">
  
    public Boolean isDeleted(Usuario usuario){
        Boolean found=false;
        try {
            Document doc = new Document("usuario.username",usuario.getUsername());
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

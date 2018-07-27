/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.rules;

import com.avbravo.avbravoutils.JsfUtil;
import com.avbravo.transporteejb.entity.Rol;
import com.avbravo.transporteejb.repository.UsuarioRepository;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.bson.Document;

/**
 *
 * @authoravbravo
 */
@Stateless
public class RolRules {

    @Inject
    UsuarioRepository usuarioRepository;
    
   

    // <editor-fold defaultstate="collapsed" desc="isDeleted(Rol rol)">
  
    public Boolean isDeleted(Rol rol){
        Boolean found=false;
        try {
            Document doc = new Document("rol.idrol",rol.getIdrol());
            Integer count = usuarioRepository.count(doc);
            if (count > 0){
                return false;
            }
            
        } catch (Exception e) {
             JsfUtil.errorMessage("isDeleted() " + e.getLocalizedMessage());
        }
        return true;
    }  // </editor-fold>
   
}

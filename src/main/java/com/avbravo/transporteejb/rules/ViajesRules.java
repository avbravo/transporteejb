/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.rules;

import com.avbravo.avbravoutils.JsfUtil;
import com.avbravo.transporteejb.entity.Viajes;
import javax.ejb.Stateless;
import org.bson.Document;

/**
 *
 * @authoravbravo
 */
@Stateless
public class ViajesRules {

  
   

    // <editor-fold defaultstate="collapsed" desc="isDeleted(Rol rol)">
  
    public Boolean isDeleted(Viajes viajes){
        Boolean found=false;
        try {
        
        } catch (Exception e) {
             JsfUtil.errorMessage("isDeleted() " + e.getLocalizedMessage());
        }
        return true;
    }  // </editor-fold>
   
}

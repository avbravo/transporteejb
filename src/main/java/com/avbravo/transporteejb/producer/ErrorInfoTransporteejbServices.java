/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.producer;

import com.avbravo.avbravoutils.JsfUtil;
import com.avbravo.ejbjmoordb.pojos.ErrorInfo;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @authoravbravo
 */
@Stateless
public class ErrorInfoTransporteejbServices { 

  
    @Inject
    ErrorInfoTransporteejbRepository errorInfoTransporteejbRepository;

   
    
     public void  errorMessage(String clase, String metodo, String message){
    
        try {

             ErrorInfo errorInfo = new ErrorInfo();
             errorInfo.setIderror(JsfUtil.getUUID());
             errorInfo.setClase(clase);
             errorInfo.setMensaje(clase);
             errorInfo.setMetodo(metodo);
             errorInfoTransporteejbRepository.save(errorInfo);
            
        } catch (Exception e) {
            JsfUtil.errorMessage("errorMessage() " + e.getLocalizedMessage());
        }
  JsfUtil.errorMessage(metodo +" " + message);
    }
     public void  errorDialog(String clase, String metodo, String titulo,String message){
    
        try {

             ErrorInfo errorInfo = new ErrorInfo();
             errorInfo.setIderror(JsfUtil.getUUID());
             errorInfo.setClase(clase);
             errorInfo.setMensaje(titulo + " "+message);
             errorInfo.setMetodo(metodo);
             errorInfoTransporteejbRepository.save(errorInfo);
            
        } catch (Exception e) {
            JsfUtil.errorMessage("errorMessage() " + e.getLocalizedMessage());
        }
  JsfUtil.errorDialog(titulo, message);
    }
}

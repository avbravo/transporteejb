/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.converter;

import com.avbravo.jmoordbutils.JsfUtil;
import com.avbravo.transporteejb.entity.Estatus;
import com.avbravo.transporteejb.repository.EstatusRepository;
import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

/**
 *
 * @author avbravo
 */
@Named
@RequestScoped
public class EstatusConverter implements Converter {

    @Inject
    EstatusRepository estatusRepository;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Estatus estatus = new Estatus();
        try{
            if(!s.equals("null")){
               Estatus b = new Estatus();
               b.setIdestatus(s);
               Optional<Estatus> optional = estatusRepository.findById(b);
               if (optional.isPresent()) {
               estatus= optional.get();  
               }   
             }
          } catch (Exception e) {
             JsfUtil.errorMessage("getAsObject()" + e.getLocalizedMessage());
          }
          return estatus;
      }


    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        String r = "";
        try {
            if (o instanceof Estatus) {
                Estatus estatus = (Estatus) o;
                r = String.valueOf(estatus.getIdestatus());
            }else if (o instanceof String) {
               r = (String) o;
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("getAsString()" + e.getLocalizedMessage());
        }
        return r;
        }



}

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.converter;

import com.avbravo.jmoordbutils.JsfUtil;
import com.avbravo.transporteejb.entity.EstatusViaje;
import com.avbravo.transporteejb.repository.EstatusViajeRepository;
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
public class EstatusViajeConverter implements Converter {

    @Inject
    EstatusViajeRepository estatusViajeRepository;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        EstatusViaje estatusViaje = new EstatusViaje();
        try{
            if(!s.equals("null")){
               EstatusViaje b = new EstatusViaje();
               b.setIdestatusviaje(s);
               Optional<EstatusViaje> optional = estatusViajeRepository.findById(b);
               if (optional.isPresent()) {
               estatusViaje= optional.get();  
               }   
             }
          } catch (Exception e) {
             JsfUtil.errorMessage("getAsObject()" + e.getLocalizedMessage());
          }
          return estatusViaje;
      }


    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        String r = "";
        try {
            if (o instanceof EstatusViaje) {
                EstatusViaje estatusViaje = (EstatusViaje) o;
                r = String.valueOf(estatusViaje.getIdestatusviaje());
            }else if (o instanceof String) {
               r = (String) o;
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("getAsString()" + e.getLocalizedMessage());
        }
        return r;
        }



}

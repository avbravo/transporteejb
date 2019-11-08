/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.converter;

import com.avbravo.jmoordb.mongodb.history.services.ErrorInfoServices;
import com.avbravo.jmoordb.util.JmoordbUtil;
import com.avbravo.transporteejb.entity.Viaje;
import com.avbravo.transporteejb.repository.ViajeRepository;
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
public class ViajeConverter implements Converter {
 @Inject
    ErrorInfoServices errorServices;
    @Inject
    ViajeRepository viajesRepository;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Viaje viajes = new Viaje();
        try{
            if(!s.equals("null")){
               Viaje b = new Viaje();
               b.setIdviaje(Integer.parseInt(s));
               Optional<Viaje> optional = viajesRepository.findById(b);
               if (optional.isPresent()) {
               viajes= optional.get();  
               }   
             }
          } catch (Exception e) {
              errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e); // JmoordbUtil.errorMessage("getAsObject()" + e.getLocalizedMessage());
          }
          return viajes;
      }


    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        String r = "";
        try {
            if (o instanceof Viaje) {
                Viaje viajes = (Viaje) o;
                r = String.valueOf(viajes.getIdviaje());
            }else if (o instanceof String) {
               r = (String) o;
            }
        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e); // JmoordbUtil.errorMessage("getAsString()" + e.getLocalizedMessage());
        }
        return r;
        }



}

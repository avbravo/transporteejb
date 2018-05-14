/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.converter;

import com.avbravo.avbravoutils.JsfUtil;
import com.avbravo.transporteejb.entity.Viajes;
import com.avbravo.transporteejb.repository.ViajesRepository;
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
public class ViajesConverter implements Converter {

    @Inject
    ViajesRepository viajesRepository;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Viajes viajes = new Viajes();
        try{
            if(!s.equals("null")){
               Viajes b = new Viajes();
               b.setIdviaje(Integer.parseInt(s));
               Optional<Viajes> optional = viajesRepository.findById(b);
               if (optional.isPresent()) {
               viajes= optional.get();  
               }   
             }
          } catch (Exception e) {
             JsfUtil.errorMessage("getAsObject()" + e.getLocalizedMessage());
          }
          return viajes;
      }


    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        String r = "";
        try {
            if (o instanceof Viajes) {
                Viajes viajes = (Viajes) o;
                r = String.valueOf(viajes.getIdviaje());
            }else if (o instanceof String) {
               r = (String) o;
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("getAsString()" + e.getLocalizedMessage());
        }
        return r;
        }



}

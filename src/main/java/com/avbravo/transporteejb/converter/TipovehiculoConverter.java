/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.converter;

import com.avbravo.jmoordb.mongodb.history.services.ErrorInfoServices;
import com.avbravo.jmoordb.util.JmoordbUtil;
import com.avbravo.transporteejb.entity.Tipovehiculo;
import com.avbravo.transporteejb.repository.TipovehiculoRepository;
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
public class TipovehiculoConverter implements Converter {
  @Inject
    ErrorInfoServices errorServices;
    @Inject
    TipovehiculoRepository tipovehiculoRepository;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Tipovehiculo tipovehiculo = new Tipovehiculo();
        try{
            if(!s.equals("null")){
               Tipovehiculo b = new Tipovehiculo();
               b.setIdtipovehiculo(s);
               Optional<Tipovehiculo> optional = tipovehiculoRepository.findById(b);
               if (optional.isPresent()) {
               tipovehiculo= optional.get();  
               }   
             }
          } catch (Exception e) {
              errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e);  
          }
          return tipovehiculo;
      }


    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        String r = "";
        try {
            if (o instanceof Tipovehiculo) {
                Tipovehiculo tipovehiculo = (Tipovehiculo) o;
                r = String.valueOf(tipovehiculo.getIdtipovehiculo());
            }else if (o instanceof String) {
               r = (String) o;
            }
        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e);  
        }
        return r;
        }



}

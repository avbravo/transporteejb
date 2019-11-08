/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.converter;

import com.avbravo.jmoordb.mongodb.history.services.ErrorInfoServices;
import com.avbravo.jmoordb.util.JmoordbUtil;
import com.avbravo.transporteejb.entity.Vehiculo;
import com.avbravo.transporteejb.repository.VehiculoRepository;
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
public class VehiculoConverter implements Converter {
 @Inject
    ErrorInfoServices errorServices;
    @Inject
    VehiculoRepository vehiculoRepository;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Vehiculo vehiculo = new Vehiculo();
        try{
            if(!s.equals("null")){
               Vehiculo b = new Vehiculo();
               b.setIdvehiculo(Integer.parseInt(s));
               Optional<Vehiculo> optional = vehiculoRepository.findById(b);
               if (optional.isPresent()) {
               vehiculo= optional.get();  
               }   
             }
          } catch (Exception e) {
              errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e); // JmoordbUtil.errorMessage("getAsObject()" + e.getLocalizedMessage());
          }
          return vehiculo;
      }


    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        String r = "";
        try {
            if (o instanceof Vehiculo) {
                Vehiculo vehiculo = (Vehiculo) o;
                r = String.valueOf(vehiculo.getIdvehiculo());
            }else if (o instanceof String) {
               r = (String) o;
            }
        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e); // JmoordbUtil.errorMessage("getAsString()" + e.getLocalizedMessage());
        }
        return r;
        }



}

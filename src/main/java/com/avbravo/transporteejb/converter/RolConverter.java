/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.converter;

import com.avbravo.jmoordb.mongodb.history.services.ErrorInfoServices;
import com.avbravo.jmoordb.util.JmoordbUtil;
import com.avbravo.transporteejb.entity.Rol;
import com.avbravo.transporteejb.repository.RolRepository;
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
public class RolConverter implements Converter {
 @Inject
    ErrorInfoServices errorServices;
    @Inject
    RolRepository rolRepository;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Rol rol = new Rol();
        try{
            if(!s.equals("null")){
               Rol b = new Rol();
               b.setIdrol(s);
               Optional<Rol> optional = rolRepository.findById(b);
               if (optional.isPresent()) {
               rol= optional.get();  
               }   
             }
          } catch (Exception e) {
              errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e); // JmoordbUtil.errorMessage("getAsObject()" + e.getLocalizedMessage());
          }
          return rol;
      }


    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        String r = "";
        try {
            if (o instanceof Rol) {
                Rol rol = (Rol) o;
                r = String.valueOf(rol.getIdrol());
            }else if (o instanceof String) {
               r = (String) o;
            }
        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e); // JmoordbUtil.errorMessage("getAsString()" + e.getLocalizedMessage());
        }
        return r;
        }



}

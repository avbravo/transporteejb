/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.converter;

import com.avbravo.jmoordb.util.JmoordbUtil;
import com.avbravo.transporteejb.entity.Unidad;
import com.avbravo.transporteejb.repository.UnidadRepository;
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
public class UnidadConverter implements Converter {

    @Inject
    UnidadRepository unidadRepository;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Unidad unidad = new Unidad();
        try{
            if(!s.equals("null")){
               Unidad b = new Unidad();
               b.setIdunidad(s);
               Optional<Unidad> optional = unidadRepository.findById(b);
               if (optional.isPresent()) {
               unidad= optional.get();  
               }   
             }
          } catch (Exception e) {
             JmoordbUtil.errorMessage("getAsObject()" + e.getLocalizedMessage());
          }
          return unidad;
      }


    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        String r = "";
        try {
            if (o instanceof Unidad) {
                Unidad unidad = (Unidad) o;
                r = String.valueOf(unidad.getIdunidad());
            }else if (o instanceof String) {
               r = (String) o;
            }
        } catch (Exception e) {
            JmoordbUtil.errorMessage("getAsString()" + e.getLocalizedMessage());
        }
        return r;
        }



}

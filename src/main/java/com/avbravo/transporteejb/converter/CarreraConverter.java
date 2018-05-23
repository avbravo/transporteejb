/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.converter;

import com.avbravo.avbravoutils.JsfUtil;
import com.avbravo.transporteejb.entity.Carrera;
import com.avbravo.transporteejb.repository.CarreraRepository;
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
public class CarreraConverter implements Converter {

    @Inject
    CarreraRepository carreraRepository;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Carrera carrera = new Carrera();
        try{
            if(!s.equals("null")){
               Carrera b = new Carrera();
               b.setIdcarrera(Integer.parseInt(s));
               Optional<Carrera> optional = carreraRepository.findById(b);
               if (optional.isPresent()) {
               carrera= optional.get();  
               }   
             }
          } catch (Exception e) {
             JsfUtil.errorMessage("getAsObject()" + e.getLocalizedMessage());
          }
          return carrera;
      }


    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        String r = "";
        try {
            if (o instanceof Carrera) {
                Carrera carrera = (Carrera) o;
                r = String.valueOf(carrera.getIdcarrera());
            }else if (o instanceof String) {
               r = (String) o;
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("getAsString()" + e.getLocalizedMessage());
        }
        return r;
        }



}

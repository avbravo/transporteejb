/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.converter;

import com.avbravo.avbravoutils.JsfUtil;
import com.avbravo.transporteejb.entity.Facultad;
import com.avbravo.transporteejb.repository.FacultadRepository;
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
public class FacultadConverter implements Converter {

    @Inject
    FacultadRepository facultadRepository;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Facultad facultad = new Facultad();
        try{
            if(!s.equals("null")){
               Facultad b = new Facultad();
               b.setIdfacultad(Integer.parseInt(s));
               Optional<Facultad> optional = facultadRepository.findById(b);
               if (optional.isPresent()) {
               facultad= optional.get();  
               }   
             }
          } catch (Exception e) {
             JsfUtil.errorMessage("getAsObject()" + e.getLocalizedMessage());
          }
          return facultad;
      }


    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        String r = "";
        try {
            if (o instanceof Facultad) {
                Facultad facultad = (Facultad) o;
                r = String.valueOf(facultad.getIdfacultad());
            }else if (o instanceof String) {
               r = (String) o;
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("getAsString()" + e.getLocalizedMessage());
        }
        return r;
        }



}

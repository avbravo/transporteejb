/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.converter;

import com.avbravo.jmoordbutils.JsfUtil;
import com.avbravo.transporteejb.entity.Solicitud;
import com.avbravo.transporteejb.repository.SolicitudRepository;
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
public class SolicitudConverter implements Converter {

    @Inject
    SolicitudRepository solicitudRepository;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Solicitud solicitud = new Solicitud();
        try{
            if(!s.equals("null")){
               Solicitud b = new Solicitud();
               b.setIdsolicitud(Integer.parseInt(s));
               Optional<Solicitud> optional = solicitudRepository.findById(b);
               if (optional.isPresent()) {
               solicitud= optional.get();  
               }   
             }
          } catch (Exception e) {
             JsfUtil.errorMessage("getAsObject()" + e.getLocalizedMessage());
          }
          return solicitud;
      }


    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        String r = "";
        try {
            if (o instanceof Solicitud) {
                Solicitud solicitud = (Solicitud) o;
                r = String.valueOf(solicitud.getIdsolicitud());
            }else if (o instanceof String) {
               r = (String) o;
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("getAsString()" + e.getLocalizedMessage());
        }
        return r;
        }



}

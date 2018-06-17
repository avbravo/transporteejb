/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.converter;


import com.avbravo.avbravoutils.JsfUtil;
import com.avbravo.ejbjmoordb.pojos.Configuracion;
import com.avbravo.transporteejb.repository.ConfiguracionTransporteejbRepository;
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
public class ConfiguracionTransporteejbConverter implements Converter {

    @Inject
    ConfiguracionTransporteejbRepository configuracionRepository;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Configuracion configuracion = new Configuracion();
        try{
            if(!s.equals("null")){
               Configuracion b = new Configuracion();
               b.setIdconfiguracion(Integer.parseInt(s));
               Optional<Configuracion> optional = configuracionRepository.findById(b);
               if (optional.isPresent()) {
               configuracion= optional.get();  
               }   
             }
          } catch (Exception e) {
             JsfUtil.errorMessage("getAsObject()" + e.getLocalizedMessage());
          }
          return configuracion;
      }


    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        String r = "";
        try {
            if (o instanceof Configuracion) {
                Configuracion configuracion = (Configuracion) o;
                r = String.valueOf(configuracion.getIdconfiguracion());
            }else if (o instanceof String) {
               r = (String) o;
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("getAsString()" + e.getLocalizedMessage());
        }
        return r;
        }



}

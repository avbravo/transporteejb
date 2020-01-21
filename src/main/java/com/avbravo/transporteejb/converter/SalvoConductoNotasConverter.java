/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.converter;

import com.avbravo.jmoordb.mongodb.history.services.ErrorInfoServices;
import com.avbravo.jmoordb.util.JmoordbUtil;
import com.avbravo.transporteejb.entity.SalvoConductoNotas;
import com.avbravo.transporteejb.repository.SalvoConductoNotasRepository;
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
public class SalvoConductoNotasConverter implements Converter {
 @Inject
    ErrorInfoServices errorServices;
    @Inject
    SalvoConductoNotasRepository salvoConductoNotasRepository;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        SalvoConductoNotas salvoConductoNotas = new SalvoConductoNotas();
        try{
            if(!s.equals("null")){
               SalvoConductoNotas b = new SalvoConductoNotas();
               b.setIdsalvoconductonotas(s);
               Optional<SalvoConductoNotas> optional = salvoConductoNotasRepository.findById(b);
               if (optional.isPresent()) {
               salvoConductoNotas= optional.get();  
               }   
             }
          } catch (Exception e) {
              errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e); 
          }
          return salvoConductoNotas;
      }


    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        String r = "";
        try {
            if (o instanceof SalvoConductoNotas) {
                SalvoConductoNotas salvoConductoNotas = (SalvoConductoNotas) o;
                r = String.valueOf(salvoConductoNotas.getIdsalvoconductonotas());
            }else if (o instanceof String) {
               r = (String) o;
            }
        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e); 
        }
        return r;
        }



}

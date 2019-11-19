/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.converter;

import com.avbravo.jmoordb.mongodb.history.services.ErrorInfoServices;
import com.avbravo.jmoordb.util.JmoordbUtil;
import com.avbravo.jmoordb.util.JmoordbUtil;
import com.avbravo.transporteejb.entity.Conductor;
import com.avbravo.transporteejb.repository.ConductorRepository;
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
public class ConductorConverter implements Converter {
 @Inject
    ErrorInfoServices errorServices;
    @Inject
    ConductorRepository conductorRepository;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Conductor conductor = new Conductor();
        try {
            if (!s.equals("null")) {
                Conductor b = new Conductor();
                b.setIdconductor(Integer.parseInt(s));
                Optional<Conductor> optional = conductorRepository.findById(b);
                if (optional.isPresent()) {
                    conductor = optional.get();
                }
            }
        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e); 
        }
        return conductor;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        String r = "";
        try {
            if (o instanceof Conductor) {
                Conductor conductor = (Conductor) o;
                r = String.valueOf(conductor.getIdconductor());
            } else if (o instanceof String) {
                r = (String) o;
            }
        } catch (Exception e) {
             errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(),e); 
        }
        return r;
    }

}

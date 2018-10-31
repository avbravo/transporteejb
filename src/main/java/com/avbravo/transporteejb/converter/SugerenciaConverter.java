/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.converter;

import com.avbravo.avbravoutils.JsfUtil;
import com.avbravo.transporteejb.entity.Sugerencia;
import com.avbravo.transporteejb.repository.SugerenciaRepository;
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
public class SugerenciaConverter implements Converter {
 
    @Inject
    SugerenciaRepository sugerenciaRepository;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Sugerencia sugerencia = new Sugerencia();
        try {
            if (!s.equals("null")) {
                Sugerencia b = new Sugerencia();
                b.setIdsugerencia(s);
                Optional<Sugerencia> optional = sugerenciaRepository.findById(b);
                if (optional.isPresent()) {
                    sugerencia = optional.get();
                }
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("getAsObject()" + e.getLocalizedMessage());
        }
        return sugerencia;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        String r = "";
        try {
            if (o instanceof Sugerencia) {
                Sugerencia sugerencia = (Sugerencia) o;
                r = String.valueOf(sugerencia.getIdsugerencia());
            } else if (o instanceof String) {
                r = (String) o;
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("getAsString()" + e.getLocalizedMessage());
        }
        return r;
    }

}
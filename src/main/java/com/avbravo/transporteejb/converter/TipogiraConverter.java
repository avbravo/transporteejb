/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.converter;

import com.avbravo.avbravoutils.JsfUtil;
import com.avbravo.transporteejb.entity.Tipogira;
import com.avbravo.transporteejb.repository.TipogiraRepository;
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
public class TipogiraConverter implements Converter {
 
    @Inject
    TipogiraRepository tipogiraRepository;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Tipogira tipogira = new Tipogira();
        try {
            if (!s.equals("null")) {
                Tipogira b = new Tipogira();
                b.setIdtipogira(s);
                Optional<Tipogira> optional = tipogiraRepository.findById(b);
                if (optional.isPresent()) {
                    tipogira = optional.get();
                }
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("getAsObject()" + e.getLocalizedMessage());
        }
        return tipogira;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        String r = "";
        try {
            if (o instanceof Tipogira) {
                Tipogira tipogira = (Tipogira) o;
                r = String.valueOf(tipogira.getIdtipogira());
            } else if (o instanceof String) {
                r = (String) o;
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("getAsString()" + e.getLocalizedMessage());
        }
        return r;
    }

}

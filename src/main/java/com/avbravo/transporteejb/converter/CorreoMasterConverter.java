/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.converter;

import com.avbravo.jmoordbutils.JsfUtil;
import com.avbravo.transporteejb.entity.CorreoMaster;
import com.avbravo.transporteejb.repository.CorreoMasterRepository;
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
public class CorreoMasterConverter implements Converter {

    @Inject
    CorreoMasterRepository correoMasterRepository;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        CorreoMaster correoMaster = new CorreoMaster();
        try {
            if (!s.equals("null")) {
                CorreoMaster b = new CorreoMaster();
                b.setEmail(s);
                Optional<CorreoMaster> optional = correoMasterRepository.findById(b);
                if (optional.isPresent()) {
                    correoMaster = optional.get();
                }
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("getAsObject()" + e.getLocalizedMessage());
        }
        return correoMaster;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        String r = "";
        try {
            if (o instanceof CorreoMaster) {
                CorreoMaster correoMaster = (CorreoMaster) o;
                r = String.valueOf(correoMaster.getEmail());
            } else if (o instanceof String) {
                r = (String) o;
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("getAsString()" + e.getLocalizedMessage());
        }
        return r;
    }

}

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.services;

import com.avbravo.jmoordbutils.JsfUtil;
import com.avbravo.transporteejb.entity.Rol;
import com.avbravo.transporteejb.repository.RolRepository;
import com.avbravo.transporteejb.repository.UsuarioRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.bson.Document;

/**
 *
 * @authoravbravo
 */
@Stateless
public class RolServices {

    @Inject
    RolRepository repository;

    @Inject
    UsuarioRepository usuarioRepository;
    List<Rol> rolList = new ArrayList<>();

    public List<Rol> complete(String query) {
        List<Rol> suggestions = new ArrayList<>();
        try {
            suggestions = repository.complete(query);
        } catch (Exception e) {
            JsfUtil.errorMessage("complete() " + e.getLocalizedMessage());
        }

        return suggestions;
    }

    public List<Rol> completeFiltrado(String query) {
        List<Rol> suggestions = new ArrayList<>();
        try {
            query = query.trim();
            if (query.length() < 1) {
                return suggestions;
            }
            String field = (String) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("field");
            suggestions = repository.findRegex(field, query, true, new Document(field, 1));

        } catch (Exception e) {
            JsfUtil.errorMessage("complete() " + e.getLocalizedMessage());
        }
        return suggestions;
    }

    // <editor-fold defaultstate="collapsed" desc="getRolList()">
    public List<Rol> getRolList() {
        try {
            rolList = repository.findAll(new Document("rol", 1));
        } catch (Exception e) {
            JsfUtil.errorMessage("getRolList() " + e.getLocalizedMessage());
        }
        return rolList;
    }// </editor-fold>

    public void setRolList(List<Rol> rolList) {
        this.rolList = rolList;
    }

    // <editor-fold defaultstate="collapsed" desc="isDeleted(Rol rol)">
    public Boolean isDeleted(Rol rol) {
        Boolean found = false;
        try {
            Document doc = new Document("rol.idrol", rol.getIdrol());
            Integer count = usuarioRepository.count(doc);
            if (count > 0) {
                return false;
            }

        } catch (Exception e) {
            JsfUtil.errorMessage("isDeleted() " + e.getLocalizedMessage());
        }
        return true;
    }  // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="findById(String id)">
    public Rol findById(String id) {
        Rol rol = new Rol();
        try {

            rol.setIdrol(id);
            Optional<Rol> optional = repository.findById(rol);
            if (optional.isPresent()) {
                return optional.get();
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("findById() " + e.getLocalizedMessage());
        }

        return rol;
    }
    // </editor-fold>
}

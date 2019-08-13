/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.services;

import com.avbravo.jmoordbutils.JsfUtil;
import com.avbravo.transporteejb.entity.Usuario;
import com.avbravo.transporteejb.entity.VistoBueno;
import com.avbravo.transporteejb.repository.VistoBuenoRepository;
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
public class VistoBuenoServices {

    @Inject
    VistoBuenoRepository repository;

    @Inject
    UsuarioRepository usuarioRepository;
    List<VistoBueno> vistoBuenoList = new ArrayList<>();

    public List<VistoBueno> complete(String query) {
        List<VistoBueno> suggestions = new ArrayList<>();
        try {
            suggestions = repository.complete(query);
        } catch (Exception e) {
            JsfUtil.errorMessage("complete() " + e.getLocalizedMessage());
        }

        return suggestions;
    }

    public List<VistoBueno> completeFiltrado(String query) {
        List<VistoBueno> suggestions = new ArrayList<>();
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

    // <editor-fold defaultstate="collapsed" desc="getVistoBuenoList()">
    public List<VistoBueno> getVistoBuenoList() {
        try {
            vistoBuenoList = repository.findAll(new Document("vistoBueno", 1));
        } catch (Exception e) {
            JsfUtil.errorMessage("getVistoBuenoList() " + e.getLocalizedMessage());
        }
        return vistoBuenoList;
    }// </editor-fold>

    public void setVistoBuenoList(List<VistoBueno> vistoBuenoList) {
        this.vistoBuenoList = vistoBuenoList;
    }

    // <editor-fold defaultstate="collapsed" desc="isDeleted(VistoBueno vistoBueno)">
    public Boolean isDeleted(VistoBueno vistoBueno) {
        Boolean found = false;
        try {
//            Document doc = new Document("vistoBueno.idvistoBueno", vistoBueno.getIdvistoBueno());
//            Integer count = usuarioRepository.count(doc);
//            if (count > 0) {
//                return false;
//            }

        } catch (Exception e) {
            JsfUtil.errorMessage("isDeleted() " + e.getLocalizedMessage());
        }
        return true;
    }  // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="findById(String id)">
    public VistoBueno findById(String id) {
        VistoBueno vistoBueno = new VistoBueno();
        try {

            vistoBueno.setIdvistobueno(id);
            Optional<VistoBueno> optional = repository.findById(vistoBueno);
            if (optional.isPresent()) {
                return optional.get();
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("findById() " + e.getLocalizedMessage());
        }

        return vistoBueno;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="VistoBueno inicializar()">
    public VistoBueno inicializar() {
        VistoBueno vistoBueno = new VistoBueno();
        try {

            vistoBueno.setIdvistobueno(JsfUtil.generateUniqueID());
            vistoBueno.setAprobado("no");
            vistoBueno.setUsuario(null);
        } catch (Exception e) {
            JsfUtil.errorMessage("inicializar() " + e.getLocalizedMessage());
        }

        return vistoBueno;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="VistoBueno inicializarAprobado(Usuario usuario)">
    /**
     * Inicializa un usuario aprobado
     * @param usuario
     * @return 
     */
    public VistoBueno inicializarAprobado(Usuario usuario) {
        VistoBueno vistoBueno = new VistoBueno();
        try {

            vistoBueno.setIdvistobueno(JsfUtil.generateUniqueID());
            vistoBueno.setAprobado("si");
            vistoBueno.setUsuario(usuario);
        } catch (Exception e) {
            JsfUtil.errorMessage("inicializar() " + e.getLocalizedMessage());
        }

        return vistoBueno;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="VistoBueno aprobar(Usuario usuario, String aprobado) ">
    /**
     * Devuelve el entity Visto Bueno con el estatus asignado y el usuario
     * @param usuario
     * @return 
     */
    public VistoBueno aprobar(Usuario usuario, String aprobado) {
        VistoBueno vistoBueno = new VistoBueno();
        try {

            vistoBueno.setIdvistobueno(JsfUtil.generateUniqueID());
            vistoBueno.setAprobado(aprobado);
            vistoBueno.setUsuario(usuario);
        } catch (Exception e) {
            JsfUtil.errorMessage("aprobar() " + e.getLocalizedMessage());
        }

        return vistoBueno;
    }
    // </editor-fold>
}

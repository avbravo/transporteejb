/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.services;

import com.avbravo.jmoordb.mongodb.history.services.ErrorInfoServices;
import com.avbravo.jmoordb.util.JmoordbUtil;
import com.avbravo.transporteejb.entity.Usuario;
import com.avbravo.transporteejb.entity.VistoBuenoSubdirectorAdministrativo;
import com.avbravo.transporteejb.repository.VistoBuenoSubdirectorAdministrativoRepository;
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
public class VistoBuenoSubdirectorAdministrativoServices {

    @Inject
    ErrorInfoServices errorServices;
    @Inject
    VistoBuenoSubdirectorAdministrativoRepository repository;

    @Inject
    UsuarioRepository usuarioRepository;
    List<VistoBuenoSubdirectorAdministrativo> vistoBuenoList = new ArrayList<>();

    public List<VistoBuenoSubdirectorAdministrativo> complete(String query) {
        List<VistoBuenoSubdirectorAdministrativo> suggestions = new ArrayList<>();
        try {
            suggestions = repository.complete(query);
        } catch (Exception e) {
            errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return suggestions;
    }

    public List<VistoBuenoSubdirectorAdministrativo> completeFiltrado(String query) {
        List<VistoBuenoSubdirectorAdministrativo> suggestions = new ArrayList<>();
        try {
            query = query.trim();
            if (query.length() < 1) {
                return suggestions;
            }
            String field = (String) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("field");
            suggestions = repository.findRegex(field, query, true, new Document(field, 1));

        } catch (Exception e) {
            errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e); 
        }
        return suggestions;
    }

    // <editor-fold defaultstate="collapsed" desc="getVistoBuenoSubdirectorAdministrativoList()">
    public List<VistoBuenoSubdirectorAdministrativo> getVistoBuenoSubdirectorAdministrativoList() {
        try {
            vistoBuenoList = repository.findAll(new Document("vistoBueno", 1));
        } catch (Exception e) {
            errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return vistoBuenoList;
    }// </editor-fold>

    public void setVistoBuenoSubdirectorAdministrativoList(List<VistoBuenoSubdirectorAdministrativo> vistoBuenoList) {
        this.vistoBuenoList = vistoBuenoList;
    }

    // <editor-fold defaultstate="collapsed" desc="isDeleted(VistoBuenoSubdirectorAdministrativo vistoBueno)">
    public Boolean isDeleted(VistoBuenoSubdirectorAdministrativo vistoBueno) {
        Boolean found = false;
        try {
//            Document doc = new Document("vistoBueno.idvistoBueno", vistoBueno.getIdvistoBueno());
//            Integer count = usuarioRepository.count(doc);
//            if (count > 0) {
//                return false;
//            }

        } catch (Exception e) {
            errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e); 
        }
        return true;
    }  // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="findById(String id)">
    public VistoBuenoSubdirectorAdministrativo findById(String id) {
        VistoBuenoSubdirectorAdministrativo vistoBueno = new VistoBuenoSubdirectorAdministrativo();
        try {

            vistoBueno.setIdvistobuenosubdirectoradministrativo(id);
            Optional<VistoBuenoSubdirectorAdministrativo> optional = repository.findById(vistoBueno);
            if (optional.isPresent()) {
                return optional.get();
            }
        } catch (Exception e) {
            errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e); 
        }

        return vistoBueno;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="VistoBuenoSubdirectorAdministrativo inicializarPendiente(Usuario usuario)">
    /**
     * Inicializa en pendiente la solicitud
     *
     * @param usuario
     * @return
     */
    public VistoBuenoSubdirectorAdministrativo inicializarPendiente(Usuario usuario) {
        VistoBuenoSubdirectorAdministrativo vistoBueno = new VistoBuenoSubdirectorAdministrativo();
        try {

            vistoBueno.setIdvistobuenosubdirectoradministrativo(JmoordbUtil.generateUniqueID());
            vistoBueno.setAprobado("pe");
            vistoBueno.setUsuario(usuario);
            vistoBueno.setFecha(JmoordbUtil.getFechaActual());

        } catch (Exception e) {
            errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e); 
        }

        return vistoBueno;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="VistoBuenoSubdirectorAdministrativo inicializarAprobado(Usuario usuario)">
    /**
     * Inicializa un usuario aprobado
     *
     * @param usuario
     * @return
     */
    public VistoBuenoSubdirectorAdministrativo inicializarAprobado(Usuario usuario) {
        VistoBuenoSubdirectorAdministrativo vistoBueno = new VistoBuenoSubdirectorAdministrativo();
        try {

            vistoBueno.setIdvistobuenosubdirectoradministrativo(JmoordbUtil.generateUniqueID());
            vistoBueno.setAprobado("si");
            vistoBueno.setUsuario(usuario);
            vistoBueno.setFecha(JmoordbUtil.fechaActual());
        } catch (Exception e) {
            errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e); 
        }

        return vistoBueno;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="VistoBuenoSubdirectorAdministrativo aprobar(Usuario usuario, String aprobado) ">
    /**
     * Devuelve el entity Visto Bueno con el estatus asignado y el usuario
     *
     * @param usuario
     * @return
     */
    public VistoBuenoSubdirectorAdministrativo aprobar(Usuario usuario, String aprobado) {
        VistoBuenoSubdirectorAdministrativo vistoBueno = new VistoBuenoSubdirectorAdministrativo();
        try {

            vistoBueno.setIdvistobuenosubdirectoradministrativo(JmoordbUtil.generateUniqueID());
            vistoBueno.setAprobado(aprobado);
            vistoBueno.setUsuario(usuario);
            vistoBueno.setFecha(JmoordbUtil.fechaActual());
        } catch (Exception e) {
            errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e); 
        }

        return vistoBueno;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="columnColor(VistoBuenoSubdirectorAdministrativo vistoBueno))">
    /**
     * Devuelve el color en base aprobaado
     *
     * @param estatus
     * @return
     */
    public String columnColor(VistoBuenoSubdirectorAdministrativo vistoBueno) {
        String color = "";
        try {
            switch (vistoBueno.getAprobado()) {
                case "no":
                    color = "red";
                    break;

                case "si":
                    color = "black";
                    break;
                case "pe":
                    color = "blue";
                    break;
                default:
                    color = "black";
            }
        } catch (Exception e) {
            errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e); 
        }
        return color;
    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String columnNameVistoBuenoSubdirectorAdministrativo(VistoBuenoSubdirectorAdministrativo vistoBueno) ">
    /**
     * Devuelve el nombre de las siglas del visto bueno
     *
     * @param vistoBueno
     * @return
     */
    public String columnNameVistoBuenoSubdirectorAdministrativo(VistoBuenoSubdirectorAdministrativo vistoBuenoSubdirectorAdministrativo) {

        String name = "PENDIENTE";
        try {
            if(vistoBuenoSubdirectorAdministrativo == null || vistoBuenoSubdirectorAdministrativo.getAprobado() ==null){
                return name;
            }
            if (vistoBuenoSubdirectorAdministrativo.getAprobado().equals("si")) {
                name = "APROBADO";
            } else {
                if (vistoBuenoSubdirectorAdministrativo.getAprobado().equals("no")) {
                    name = "NO APROBADO";
                } else {
                    if (vistoBuenoSubdirectorAdministrativo.getAprobado().equals("pe")) {
                        name = "PENDIENTE";
                    }
                }
            }
        } catch (Exception e) {
            errorServices.errorMessage(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return name;
    }
// </editor-fold>
}

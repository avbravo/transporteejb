/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.services;


import com.avbravo.jmoordb.util.JmoordbUtil;
import com.avbravo.transporteejb.entity.Usuario;
import com.avbravo.transporteejb.entity.VistoBuenoSecretarioAdministrativo;
import com.avbravo.transporteejb.repository.VistoBuenoSecretarioAdministrativoRepository;
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
public class VistoBuenoSecretarioAdministrativoServices {

    @Inject
    VistoBuenoSecretarioAdministrativoRepository repository;

    @Inject
    UsuarioRepository usuarioRepository;
    List<VistoBuenoSecretarioAdministrativo> vistoBuenoList = new ArrayList<>();

    public List<VistoBuenoSecretarioAdministrativo> complete(String query) {
        List<VistoBuenoSecretarioAdministrativo> suggestions = new ArrayList<>();
        try {
            suggestions = repository.complete(query);
        } catch (Exception e) {
            JmoordbUtil.errorMessage("complete() " + e.getLocalizedMessage());
        }

        return suggestions;
    }

    public List<VistoBuenoSecretarioAdministrativo> completeFiltrado(String query) {
        List<VistoBuenoSecretarioAdministrativo> suggestions = new ArrayList<>();
        try {
            query = query.trim();
            if (query.length() < 1) {
                return suggestions;
            }
            String field = (String) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("field");
            suggestions = repository.findRegex(field, query, true, new Document(field, 1));

        } catch (Exception e) {
            JmoordbUtil.errorMessage("complete() " + e.getLocalizedMessage());
        }
        return suggestions;
    }

    // <editor-fold defaultstate="collapsed" desc="getVistoBuenoSecretarioAdministrativoList()">
    public List<VistoBuenoSecretarioAdministrativo> getVistoBuenoSecretarioAdministrativoList() {
        try {
            vistoBuenoList = repository.findAll(new Document("vistoBueno", 1));
        } catch (Exception e) {
            JmoordbUtil.errorMessage("getVistoBuenoSecretarioAdministrativoList() " + e.getLocalizedMessage());
        }
        return vistoBuenoList;
    }// </editor-fold>

    public void setVistoBuenoSecretarioAdministrativoList(List<VistoBuenoSecretarioAdministrativo> vistoBuenoList) {
        this.vistoBuenoList = vistoBuenoList;
    }

    // <editor-fold defaultstate="collapsed" desc="isDeleted(VistoBuenoSecretarioAdministrativo vistoBueno)">
    public Boolean isDeleted(VistoBuenoSecretarioAdministrativo vistoBueno) {
        Boolean found = false;
        try {
//            Document doc = new Document("vistoBueno.idvistoBueno", vistoBueno.getIdvistoBueno());
//            Integer count = usuarioRepository.count(doc);
//            if (count > 0) {
//                return false;
//            }

        } catch (Exception e) {
            JmoordbUtil.errorMessage("isDeleted() " + e.getLocalizedMessage());
        }
        return true;
    }  // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="findById(String id)">
    public VistoBuenoSecretarioAdministrativo findById(String id) {
        VistoBuenoSecretarioAdministrativo vistoBueno = new VistoBuenoSecretarioAdministrativo();
        try {

            vistoBueno.setIdvistobuenosecretarioadministrativo(id);
            Optional<VistoBuenoSecretarioAdministrativo> optional = repository.findById(vistoBueno);
            if (optional.isPresent()) {
                return optional.get();
            }
        } catch (Exception e) {
            JmoordbUtil.errorMessage("findById() " + e.getLocalizedMessage());
        }

        return vistoBueno;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="VistoBuenoSecretarioAdministrativo inicializarPendiente(Usuario usuario)">
    /**
     * Inicializa en pendiente la solicitud
     * @param usuario
     * @return 
     */
    public VistoBuenoSecretarioAdministrativo inicializarPendiente(Usuario usuario) {
        VistoBuenoSecretarioAdministrativo vistoBueno = new VistoBuenoSecretarioAdministrativo();
        try {

            vistoBueno.setIdvistobuenosecretarioadministrativo(JmoordbUtil.generateUniqueID());
            vistoBueno.setAprobado("pe");
            vistoBueno.setUsuario(usuario);
            vistoBueno.setFecha(JmoordbUtil.getFechaActual());
            
        } catch (Exception e) {
            JmoordbUtil.errorMessage("inicializar() " + e.getLocalizedMessage());
        }

        return vistoBueno;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="VistoBuenoSecretarioAdministrativo inicializarAprobado(Usuario usuario)">
    /**
     * Inicializa un usuario aprobado
     * @param usuario
     * @return 
     */
    public VistoBuenoSecretarioAdministrativo inicializarAprobado(Usuario usuario) {
        VistoBuenoSecretarioAdministrativo vistoBueno = new VistoBuenoSecretarioAdministrativo();
        try {

            vistoBueno.setIdvistobuenosecretarioadministrativo(JmoordbUtil.generateUniqueID());
            vistoBueno.setAprobado("si");
            vistoBueno.setUsuario(usuario);
            vistoBueno.setFecha(JmoordbUtil.fechaActual());
        } catch (Exception e) {
            JmoordbUtil.errorMessage("inicializar() " + e.getLocalizedMessage());
        }

        return vistoBueno;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="VistoBuenoSecretarioAdministrativo aprobar(Usuario usuario, String aprobado) ">
    /**
     * Devuelve el entity Visto Bueno con el estatus asignado y el usuario
     * @param usuario
     * @return 
     */
    public VistoBuenoSecretarioAdministrativo aprobar(Usuario usuario, String aprobado) {
        VistoBuenoSecretarioAdministrativo vistoBueno = new VistoBuenoSecretarioAdministrativo();
        try {

            vistoBueno.setIdvistobuenosecretarioadministrativo(JmoordbUtil.generateUniqueID());
            vistoBueno.setAprobado(aprobado);
            vistoBueno.setUsuario(usuario);
            vistoBueno.setFecha(JmoordbUtil.fechaActual());
        } catch (Exception e) {
            JmoordbUtil.errorMessage("aprobar() " + e.getLocalizedMessage());
        }

        return vistoBueno;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="columnColor(VistoBuenoSecretarioAdministrativo vistoBueno))">
    /**
     * Devuelve el color en base aprobaado
     *
     * @param estatus
     * @return
     */
    public String columnColor(VistoBuenoSecretarioAdministrativo vistoBueno) {
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
            JmoordbUtil.errorMessage("columnColor() " + e.getLocalizedMessage());
        }
        return color;
    } // </editor-fold>
    
     // <editor-fold defaultstate="collapsed" desc="String columnNameVistoBuenoSecretarioAdministrativo(VistoBuenoSecretarioAdministrativo vistoBueno) ">
    /**
     * Devuelve el nombre de las siglas del visto bueno
     * @param vistoBueno
     * @return 
     */
    public String columnNameVistoBuenoSecretarioAdministrativo(VistoBuenoSecretarioAdministrativo vistoBuenoSecretarioAdministrativo) {
        
        String name= "PENDIENTE";
        try {
           if(vistoBuenoSecretarioAdministrativo.getAprobado().equals("si")){
              name="APROBADO";
           }else{
               if(vistoBuenoSecretarioAdministrativo.getAprobado().equals("no")){
              name="NO APROBADO";
           }else{
                   if(vistoBuenoSecretarioAdministrativo.getAprobado().equals("pe")){
              name="PENDIENTE";
           }
               }
           }
        } catch (Exception e) {
           JmoordbUtil.errorMessage("columnNameVistoBuenoSecretarioAdministrativo() " + e.getLocalizedMessage());
        }
        return name;
    }
// </editor-fold>
}

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.services;

import com.avbravo.commonejb.entity.Facultad;
import com.avbravo.jmoordb.configuration.JmoordbContext;
import com.avbravo.jmoordbutils.JsfUtil;
import com.avbravo.transporteejb.entity.Rol;
import com.avbravo.transporteejb.entity.Usuario;
import com.avbravo.transporteejb.repository.SolicitudRepository;
import com.avbravo.transporteejb.repository.UsuarioRepository;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.or;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @authoravbravo
 */
@Stateless
public class UsuarioServices {

    Boolean coordinadorvalido = false;
    Boolean escoordinador = false;
    @Inject
    UsuarioRepository repository;
    @Inject
    SolicitudRepository solicitudRepository;

    List<Usuario> usuarioList = new ArrayList<>();

    public List<Usuario> complete(String query) {
        List<Usuario> suggestions = new ArrayList<>();
        try {
            suggestions = repository.complete(query);
        } catch (Exception e) {
            JsfUtil.errorMessage("complete() " + e.getLocalizedMessage());
        }

        return suggestions;
    }

    public List<Usuario> getUsuarioList() {
        try {
            usuarioList = repository.findAll(new Document("username", 1));
        } catch (Exception e) {
            JsfUtil.errorMessage("getUsuarioList() " + e.getLocalizedMessage());
        }

        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    // <editor-fold defaultstate="collapsed" desc="isDeleted(Usuario usuario)">
    public Boolean isDeleted(Usuario usuario) {
        Boolean found = false;
        try {
            Document doc = new Document("usuario.username", usuario.getUsername());
            Integer count = solicitudRepository.count(doc);
            if (count > 0) {
                return false;
            }

        } catch (Exception e) {
            JsfUtil.errorMessage("isDeleted() " + e.getLocalizedMessage());
        }
        return true;
    }  // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="findById(String id)">
    public Usuario findById(String id) {
        Usuario usuario = new Usuario();
        try {

            usuario.setUsername(id);
            Optional<Usuario> optional = repository.findById(usuario);
            if (optional.isPresent()) {
                return optional.get();
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("findById() " + e.getLocalizedMessage());
        }

        return usuario;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="List<Usuario> usuariosParaNotificar()">
    /*
    Obtiene la lista de usuarios validos 
     */
    public List<Usuario> usuariosParaNotificar() {
        List<Usuario> list = new ArrayList<>();

        try {

            Usuario jmoordb_user = (Usuario) JmoordbContext.get("jmoordb_user");
            Bson filter = or(eq("rol.idrol", "ADMINISTRADOR"), eq("rol.idrol", "SECRETARIO ADMINISTRATIVO"), eq("rol.idrol", "SECRETARIA"));
   
            list = repository.filters(filter);
          
        } catch (Exception e) {
          JsfUtil.errorMessage("usuariosParaNotificar() " + e.getLocalizedMessage());
        }
        return list;
    }
    // </editor-fold>  
    // <editor-fold defaultstate="collapsed" desc="List<Usuario> usuariosParaNotificar(List<Facultad> facultadList)">
    /*
    Obtiene la lista de usuarios validos y verifica que el coordinador pertenezca a la facultad
     */
    public List<Usuario> usuariosParaNotificar(List<Facultad> facultadList) {
        List<Usuario> l = new ArrayList<>();

        try {

            Usuario jmoordb_user = (Usuario) JmoordbContext.get("jmoordb_user");
            Bson filter = or(eq("rol.idrol", "ADMINISTRADOR"), eq("rol.idrol", "SECRETARIO ADMINISTRATIVO"), eq("rol.idrol", "SECRETARIA"), eq("rol.idrol", "COORDINADOR"));
            List<Usuario> list = new ArrayList<>();
            list = repository.filters(filter);
            if (list == null || list.isEmpty()) {
                return l;

            } else {

                //Verifica si es un coordinador y le envia la notificacion
                coordinadorvalido = false;
                escoordinador = false;

                for (Usuario u : list) {
                    escoordinador = false;
                    coordinadorvalido = false;
                    for (Rol r : u.getRol()) {
                        if (r.getIdrol().equals("COORDINADOR")) {
                            escoordinador = true;
                            break;
                        }
                    }
                    if (escoordinador) {
                        //Verifica la facultad del coordinador
                        //la facultad debe ser el mismo nombre que el de la unidad
                        for (Facultad f : facultadList) {
                            if (f.getDescripcion().equals(u.getUnidad().getIdunidad())) {
                                coordinadorvalido = true;
                                break;
                            }
                        }
                        if (coordinadorvalido) {
                            //agrega el usuario valido
                            l.add(u);
                        }
                    } else {
                        //agrega el usuario valido
                        l.add(u);
                    }

                }

            }
        } catch (Exception e) {
          JsfUtil.errorMessage("usuariosParaNotificar() " + e.getLocalizedMessage());
        }
        return l;
    }
    // </editor-fold>  
}

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.converter;

import com.avbravo.jmoordb.util.JmoordbUtil;
import com.avbravo.transporteejb.entity.Usuario;
import com.avbravo.transporteejb.repository.UsuarioRepository;
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
public class UsuarioConverter implements Converter {

    @Inject
    UsuarioRepository usuarioRepository;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Usuario usuario = new Usuario();
        try{
            if(!s.equals("null")){
               Usuario b = new Usuario();
               b.setUsername(s);
               Optional<Usuario> optional = usuarioRepository.findById(b);
               if (optional.isPresent()) {
               usuario= optional.get();  
               }   
             }
          } catch (Exception e) {
             JmoordbUtil.errorMessage("getAsObject()" + e.getLocalizedMessage());
          }
          return usuario;
      }


    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        String r = "";
        try {
            if (o instanceof Usuario) {
                Usuario usuario = (Usuario) o;
                r = String.valueOf(usuario.getUsername());
            }else if (o instanceof String) {
               r = (String) o;
            }
        } catch (Exception e) {
            JmoordbUtil.errorMessage("getAsString()" + e.getLocalizedMessage());
        }
        return r;
        }



}

package com.avbravo.transporteejb.entity;

import com.avbravo.ejbjmoordb.anotations.Embedded;
import com.avbravo.ejbjmoordb.anotations.Id;
import com.avbravo.ejbjmoordb.anotations.Referenced;
import com.avbravo.ejbjmoordb.pojos.UserInfo;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Usuario {

    @Id
    private String username;
    private String password;
    private String nombre;
     private String celular;
    private String cargo;
        private String email;
        
    @Referenced(documment = "Rol",
            field = "idrol", javatype = "String", lazy = false,
           repository = "com.avbravo.transporteejb.repository.RolRepository")
    private Rol rol;

private String activo;
  @Embedded
    List<UserInfo> userInfo;
   

    public Usuario() {
    }

    @Override
    public String toString() {
        return "Usuario{" + "username=" + username + ", password=" + password + ", nombre=" + nombre + ", celular=" + celular + ", cargo=" + cargo + ", email=" + email + ", rol=" + rol + ", userInfo=" + userInfo + '}';
    }

    

    
    

}

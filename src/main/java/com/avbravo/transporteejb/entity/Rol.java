package com.avbravo.transporteejb.entity;

import com.avbravo.ejbjmoordb.anotations.Embedded;
import com.avbravo.ejbjmoordb.anotations.Id;
import com.avbravo.ejbjmoordb.pojos.UserInfo;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rol {
  @Id
    private String idrol;
    private String rol;
      private String activo;
   @Embedded
    List<UserInfo> userInfo;
    public Rol() {
    }

    @Override
    public String toString() {
        return "Rol{" + "idrol=" + idrol + ", rol=" + rol + '}';
    }
    
    
}

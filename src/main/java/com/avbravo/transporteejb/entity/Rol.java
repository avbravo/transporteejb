package com.avbravo.transporteejb.entity;

import com.avbravo.jmoordb.anotations.Embedded;
import com.avbravo.jmoordb.anotations.Id;
import com.avbravo.jmoordb.pojos.UserInfo;
import java.util.Date;
import java.util.List;

public class Rol {

    @Id
    private String idrol;
    private String rol;
    private String activo;
    @Embedded
    List<UserInfo> userInfo;

    public Rol() {
    }

    public Rol(String idrol, String rol, String activo, List<UserInfo> userInfo) {
        this.idrol = idrol;
        this.rol = rol;
        this.activo = activo;
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "Rol{" + "idrol=" + idrol + ", rol=" + rol + '}';
    }

    public String getIdrol() {
        return idrol;
    }

    public void setIdrol(String idrol) {
        this.idrol = idrol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public List<UserInfo> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(List<UserInfo> userInfo) {
        this.userInfo = userInfo;
    }

    public static class Builder {

        private String idrol;
        private String rol;
        private String activo;
        List<UserInfo> userInfo;

        public Builder withIdrol(String idrol) {
            this.idrol = idrol;
            return this;
        }

        public Builder withRol(String rol) {
            this.rol = rol;
            return this;
        }

        public Builder withActivo(String activo) {
            this.activo = activo;
            return this;
        }

        public Builder withUserinfo(List<UserInfo> userInfo) {
            this.userInfo = userInfo;
            return this;
        }

        public Rol build() {
            return new Rol(idrol, rol, activo, userInfo);
        }

    }

}

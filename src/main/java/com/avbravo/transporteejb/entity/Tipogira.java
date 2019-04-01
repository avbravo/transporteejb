/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.transporteejb.entity;

import com.avbravo.jmoordb.anotations.Embedded;
import com.avbravo.jmoordb.anotations.Id;
import com.avbravo.jmoordb.pojos.UserInfo;
import java.util.List;


/**
 *
 * @author avbravo
 */
public class Tipogira {
    @Id
    private String idtipogira;
    private String activo;
    @Embedded
    List<UserInfo> userInfo;

    public String getIdtipogira() {
        return idtipogira;
    }

    public void setIdtipogira(String idtipogira) {
        this.idtipogira = idtipogira;
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
    
    
    
}

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.datamodel;

import com.avbravo.transporteejb.entity.Usuario;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author avbravo
 */
public class ConductorDataModel extends ListDataModel<Usuario> implements SelectableDataModel<Usuario>{

    public ConductorDataModel() {
    }
    public ConductorDataModel(List<Usuario>data) {
        super(data);
    }

    @Override
    public Usuario  getRowData(String rowKey) {
        List<Usuario> usuarioList = (List<Usuario>) getWrappedData();
        for (Usuario usuario : usuarioList) {
             if (usuario.getUsername().equals(rowKey)) {
                 return usuario;
             }
        }
        return null;
     }
     @Override
     public Object getRowKey(Usuario usuario) {
         return usuario.getUsername();
     }


}

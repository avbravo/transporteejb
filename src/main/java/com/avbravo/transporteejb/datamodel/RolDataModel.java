/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.datamodel;

import com.avbravo.transporteejb.entity.Rol;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author avbravo
 */
public class RolDataModel extends ListDataModel<Rol> implements SelectableDataModel<Rol>{

    public RolDataModel() {
    }
    public RolDataModel(List<Rol>data) {
        super(data);
    }

    @Override
    public Rol  getRowData(String rowKey) {
        List<Rol> rolList = (List<Rol>) getWrappedData();
        for (Rol rol : rolList) {
             if (rol.getIdrol().equals(rowKey)) {
                 return rol;
             }
        }
        return null;
     }
     @Override
     public Object getRowKey(Rol rol) {
         return rol.getIdrol();
     }


}

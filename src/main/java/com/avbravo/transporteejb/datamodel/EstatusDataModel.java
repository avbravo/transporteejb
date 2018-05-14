/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.datamodel;

import com.avbravo.transporteejb.entity.Estatus;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author avbravo
 */
public class EstatusDataModel extends ListDataModel<Estatus> implements SelectableDataModel<Estatus>{

    public EstatusDataModel() {
    }
    public EstatusDataModel(List<Estatus>data) {
        super(data);
    }

    @Override
    public Estatus  getRowData(String rowKey) {
        List<Estatus> estatusList = (List<Estatus>) getWrappedData();
        for (Estatus estatus : estatusList) {
             if (estatus.getEstatus().equals(rowKey)) {
                 return estatus;
             }
        }
        return null;
     }
     @Override
     public Object getRowKey(Estatus estatus) {
         return estatus.getEstatus();
     }


}

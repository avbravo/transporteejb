/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.datamodel;

import com.avbravo.transporteejb.entity.Unidad;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author avbravo
 */
public class UnidadDataModel extends ListDataModel<Unidad> implements SelectableDataModel<Unidad>{

    public UnidadDataModel() {
    }
    public UnidadDataModel(List<Unidad>data) {
        super(data);
    }

    @Override
    public Unidad  getRowData(String rowKey) {
        List<Unidad> unidadList = (List<Unidad>) getWrappedData();
        for (Unidad unidad : unidadList) {
             if (unidad.getIdunidad().equals(rowKey)) {
                 return unidad;
             }
        }
        return null;
     }
     @Override
     public Object getRowKey(Unidad unidad) {
         return unidad.getIdunidad();
     }


}

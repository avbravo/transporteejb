/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.datamodel;

import com.avbravo.transporteejb.entity.Viajes;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author avbravo
 */
public class ViajesDataModel extends ListDataModel<Viajes> implements SelectableDataModel<Viajes>{

    public ViajesDataModel() {
    }
    public ViajesDataModel(List<Viajes>data) {
        super(data);
    }

    @Override
    public Viajes  getRowData(String rowKey) {
        List<Viajes> viajesList = (List<Viajes>) getWrappedData();
        for (Viajes viajes : viajesList) {
             if (viajes.getIdviaje().equals(rowKey)) {
                 return viajes;
             }
        }
        return null;
     }
     @Override
     public Object getRowKey(Viajes viajes) {
         return viajes.getIdviaje();
     }


}

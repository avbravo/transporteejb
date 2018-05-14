/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.datamodel;

import com.avbravo.transporteejb.entity.Tipovehiculo;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author avbravo
 */
public class TipovehiculoDataModel extends ListDataModel<Tipovehiculo> implements SelectableDataModel<Tipovehiculo>{

    public TipovehiculoDataModel() {
    }
    public TipovehiculoDataModel(List<Tipovehiculo>data) {
        super(data);
    }

    @Override
    public Tipovehiculo  getRowData(String rowKey) {
        List<Tipovehiculo> tipovehiculoList = (List<Tipovehiculo>) getWrappedData();
        for (Tipovehiculo tipovehiculo : tipovehiculoList) {
             if (tipovehiculo.getTipovehiculo().equals(rowKey)) {
                 return tipovehiculo;
             }
        }
        return null;
     }
     @Override
     public Object getRowKey(Tipovehiculo tipovehiculo) {
         return tipovehiculo.getTipovehiculo();
     }


}

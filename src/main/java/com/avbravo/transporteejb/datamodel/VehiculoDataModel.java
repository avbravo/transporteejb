/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.datamodel;

import com.avbravo.transporteejb.entity.Vehiculo;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author avbravo
 */
public class VehiculoDataModel extends ListDataModel<Vehiculo> implements SelectableDataModel<Vehiculo>{

    public VehiculoDataModel() {
    }
    public VehiculoDataModel(List<Vehiculo>data) {
        super(data);
    }

    @Override
    public Vehiculo  getRowData(String rowKey) {
        List<Vehiculo> vehiculoList = (List<Vehiculo>) getWrappedData();
        for (Vehiculo vehiculo : vehiculoList) {
             if (vehiculo.getIdvehiculo().equals(rowKey)) {
                 return vehiculo;
             }
        }
        return null;
     }
     @Override
     public Object getRowKey(Vehiculo vehiculo) {
         return vehiculo.getIdvehiculo();
     }


}

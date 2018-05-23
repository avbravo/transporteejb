/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.datamodel;

import com.avbravo.transporteejb.entity.Carrera;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author avbravo
 */
public class CarreraDataModel extends ListDataModel<Carrera> implements SelectableDataModel<Carrera>{

    public CarreraDataModel() {
    }
    public CarreraDataModel(List<Carrera>data) {
        super(data);
    }

    @Override
    public Carrera  getRowData(String rowKey) {
        List<Carrera> carreraList = (List<Carrera>) getWrappedData();
        for (Carrera carrera : carreraList) {
             if (carrera.getIdcarrera().equals(rowKey)) {
                 return carrera;
             }
        }
        return null;
     }
     @Override
     public Object getRowKey(Carrera carrera) {
         return carrera.getIdcarrera();
     }


}

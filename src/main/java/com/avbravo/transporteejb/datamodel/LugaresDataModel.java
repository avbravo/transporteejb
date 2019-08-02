/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.datamodel;

import com.avbravo.transporteejb.entity.Lugares;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author avbravo
 */
public class LugaresDataModel extends ListDataModel<Lugares> implements SelectableDataModel<Lugares>{

    public LugaresDataModel() {
    }
    public LugaresDataModel(List<Lugares>data) {
        super(data);
    }

    @Override
    public Lugares  getRowData(String rowKey) {
        List<Lugares> lugaresList = (List<Lugares>) getWrappedData();
        for (Lugares lugares : lugaresList) {
             if (lugares.getIdlugares().equals(rowKey)) {
                 return lugares;
             }
        }
        return null;
     }
     @Override
     public Object getRowKey(Lugares lugares) {
         return lugares.getIdlugares();
     }


}

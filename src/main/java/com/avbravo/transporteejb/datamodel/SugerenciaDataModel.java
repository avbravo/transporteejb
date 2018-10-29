/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.datamodel;

import com.avbravo.transporteejb.entity.Sugerencia;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author avbravo
 */
public class SugerenciaDataModel extends ListDataModel<Sugerencia> implements SelectableDataModel<Sugerencia>{

    public SugerenciaDataModel() {
    }
    public SugerenciaDataModel(List<Sugerencia>data) {
        super(data);
    }

    @Override
    public Sugerencia  getRowData(String rowKey) {
        List<Sugerencia> sugerenciaList = (List<Sugerencia>) getWrappedData();
        for (Sugerencia sugerencia : sugerenciaList) {
             if (sugerencia.getIdsugerencia().equals(rowKey)) {
                 return sugerencia;
             }
        }
        return null;
     }
     @Override
     public Object getRowKey(Sugerencia sugerencia) {
         return sugerencia.getIdsugerencia();
     }


}

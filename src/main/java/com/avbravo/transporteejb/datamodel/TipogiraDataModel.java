/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.datamodel;

import com.avbravo.transporteejb.entity.Tipogira;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author avbravo
 */
public class TipogiraDataModel extends ListDataModel<Tipogira> implements SelectableDataModel<Tipogira>{

    public TipogiraDataModel() {
    }
    public TipogiraDataModel(List<Tipogira>data) {
        super(data);
    }

    @Override
    public Tipogira  getRowData(String rowKey) {
        List<Tipogira> tipogiraList = (List<Tipogira>) getWrappedData();
        for (Tipogira tipogira : tipogiraList) {
             if (tipogira.getIdtipogira().equals(rowKey)) {
                 return tipogira;
             }
        }
        return null;
     }
     @Override
     public Object getRowKey(Tipogira tipogira) {
         return tipogira.getIdtipogira();
     }


}

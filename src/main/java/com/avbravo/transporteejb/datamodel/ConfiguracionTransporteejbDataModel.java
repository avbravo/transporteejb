/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.datamodel;

import com.avbravo.ejbjmoordb.pojos.Configuracion;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author avbravo
 */
public class ConfiguracionTransporteejbDataModel extends ListDataModel<Configuracion> implements SelectableDataModel<Configuracion>{

    public ConfiguracionTransporteejbDataModel() {
    }
    public ConfiguracionTransporteejbDataModel(List<Configuracion>data) {
        super(data);
    }

    @Override
    public Configuracion  getRowData(String rowKey) {
        List<Configuracion> configuracionList = (List<Configuracion>) getWrappedData();
        for (Configuracion configuracion : configuracionList) {
             if (configuracion.getIdconfiguracion().equals(rowKey)) {
                 return configuracion;
             }
        }
        return null;
     }
     @Override
     public Object getRowKey(Configuracion configuracion) {
         return configuracion.getIdconfiguracion();
     }


}

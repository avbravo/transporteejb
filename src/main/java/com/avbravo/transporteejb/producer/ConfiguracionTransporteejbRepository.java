/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.producer;
import javax.ejb.Stateless;
import javax.ejb.EJB;
import com.avbravo.ejbjmoordb.mongodb.repository.Repository;
import com.avbravo.ejbjmoordb.pojos.Configuracion;

import com.avbravo.transporteejb.provider.MongoClientTransporteejbProvider;
import com.mongodb.MongoClient;

/**
 *
 * @author avbravo
 */
@Stateless
public class ConfiguracionTransporteejbRepository extends Repository<Configuracion> {

    @EJB
    MongoClientTransporteejbProvider MongoClientTransporteejbProvider;
    @Override
    protected MongoClient getMongoClient() {
       return MongoClientTransporteejbProvider.getMongoClient();
    }
    public ConfiguracionTransporteejbRepository(){
        super(Configuracion.class,"transporte","configuracion");
    }
    @Override
    public Object findById(String key, String value) {
       return search(key,value); 
    }
    @Override
    public Object findById(String key, Integer value) {
        return search(key,value);
    }

}
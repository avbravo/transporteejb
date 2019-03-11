/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.repository;

import com.avbravo.jmoordb.mongodb.repository.Repository;
import com.avbravo.transporteejb.entity.Unidad;
import com.avbravo.transporteejb.provider.MongoClientTransporteejbProvider;
import javax.ejb.Stateless;
import javax.ejb.EJB;
import com.mongodb.MongoClient;

/**
 *
 * @author avbravo
 */
@Stateless
public class UnidadRepository extends Repository<Unidad> {

    @EJB
    MongoClientTransporteejbProvider mongoClientProvider;
    @Override
    protected MongoClient getMongoClient() {
       return mongoClientProvider.getMongoClient();
    }
    public UnidadRepository(){
        super(Unidad.class,"transporte","unidad");
    }
   

}

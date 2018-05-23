/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.repository;

import com.avbravo.ejbjmoordb.mongodb.repository.Repository;
import com.avbravo.transporteejb.entity.Carrera;
import com.avbravo.transporteejb.provider.MongoClientProvider;
import javax.ejb.Stateless;
import javax.ejb.EJB;
import com.mongodb.MongoClient;

/**
 *
 * @author avbravo
 */
@Stateless
public class CarreraRepository extends Repository<Carrera> {

    @EJB
    MongoClientProvider mongoClientProvider;
    @Override
    protected MongoClient getMongoClient() {
       return mongoClientProvider.getMongoClient();
    }
    public CarreraRepository(){
        super(Carrera.class,"transporte","carrera");
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

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.producer;
import javax.ejb.Stateless;
import javax.ejb.EJB;
import com.avbravo.ejbjmoordb.mongodb.repository.Repository;
import com.avbravo.ejbjmoordb.pojos.Autoincrementable;
import com.avbravo.transporteejb.provider.MongoClientTransporteejbProvider;


import com.mongodb.MongoClient;

/**
 *
 * @author avbravo
 */
@Stateless
public class AutoincrementableTransporteejbRepository extends Repository<Autoincrementable> {

    @EJB
    MongoClientTransporteejbProvider mongoClientProvider;
    @Override
    protected MongoClient getMongoClient() {
       return mongoClientProvider.getMongoClient();
    }
    public AutoincrementableTransporteejbRepository(){
        super(Autoincrementable.class,"transporte","autoincrementable");
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

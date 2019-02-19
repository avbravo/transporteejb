/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.transporteejb.repository;
import com.avbravo.ejbjmoordb.mongodb.repository.Repository;
import com.avbravo.transporteejb.entity.Viaje;
import com.avbravo.transporteejb.provider.MongoClientTransporteejbProvider;
import javax.ejb.Stateless;
import javax.ejb.EJB;
import com.mongodb.MongoClient;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @author avbravo
 */
@Stateless
public class ViajeRepository extends Repository<Viaje> {

    @EJB
    MongoClientTransporteejbProvider mongoClientProvider;
    @Override
    protected MongoClient getMongoClient() {
       return mongoClientProvider.getMongoClient();
    }
    public ViajeRepository(){
        super(Viaje.class,"transporte","viajes");
    }

    public List<Viaje> findPagination(Bson eq, boolean b, Integer page, Integer rowPage, Document document) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   

}

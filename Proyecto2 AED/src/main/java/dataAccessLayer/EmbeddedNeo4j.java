/**
 * 
 */
package dataAccessLayer;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;

import static org.neo4j.driver.Values.parameters;

import java.util.LinkedList;
import java.util.List;
/**
 * @author Administrator
 *
 */
public class EmbeddedNeo4j implements AutoCloseable{

    private final Driver driver;
    

    public EmbeddedNeo4j( String uri, String user, String password )
    {
        driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
    }

    @Override
    public void close() throws Exception
    {
        driver.close();
    }

    public void printGreeting( final String message )
    {
        try ( Session session = driver.session() )
        {
            String greeting = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    Result result = tx.run( "CREATE (a:Greeting) " +
                                                     "SET a.message = $message " +
                                                     "RETURN a.message + ', from node ' + id(a)",
                            parameters( "message", message ) );
                    return result.single().get( 0 ).asString();
                }
            } );
            System.out.println( greeting );
        }
    }

    public LinkedList<String> getHabs()
    {
    	 try ( Session session = driver.session() )
         {
    		 
    		 
    		 LinkedList<String> habs = session.readTransaction( new TransactionWork<LinkedList<String>>()
             {
                 @Override
                 public LinkedList<String> execute( Transaction tx )
                 {
                     Result result = tx.run( "MATCH (house:casa) RETURN house.habitaciones");
                     LinkedList<String> myhabs = new LinkedList<String>();
                     List<Record> registros = result.list();
                     for (int i = 0; i < registros.size(); i++) {
                    	 //myactors.add(registros.get(i).toString());
                    	 myhabs.add(registros.get(i).get("house.habitaciones").toString());
                     }
                     
                     return myhabs;
                 }
             } );
             
             return habs;
         }
    }
    
    public LinkedList<String> getAreas()
    {
    	 try ( Session session = driver.session() )
         {
    		 
    		 
    		 LinkedList<String> area = session.readTransaction( new TransactionWork<LinkedList<String>>()
             {
                 @Override
                 public LinkedList<String> execute( Transaction tx )
                 {
                     Result result = tx.run( "MATCH (house:casa) RETURN house.area");
                     LinkedList<String> myareas = new LinkedList<String>();
                     List<Record> registros = result.list();
                     for (int i = 0; i < registros.size(); i++) {
                    	 //myactors.add(registros.get(i).toString());
                    	 myareas.add(registros.get(i).get("house.area").asString());
                     }
                     
                     return myareas;
                 }
             } );
             
             return area;
         }
    }
    
    public LinkedList<String> getZones()
    {
    	 try ( Session session = driver.session() )
         {
    		 
    		 
    		 LinkedList<String> zones = session.readTransaction( new TransactionWork<LinkedList<String>>()
             {
                 @Override
                 public LinkedList<String> execute( Transaction tx )
                 {
                     Result result = tx.run( "MATCH (house:casa) RETURN house.zona");
                     LinkedList<String> myzones = new LinkedList<String>();
                     List<Record> registros = result.list();
                     for (int i = 0; i < registros.size(); i++) {
                    	 //myactors.add(registros.get(i).toString());
                    	 myzones.add(registros.get(i).get("house.zona").toString());
                     }
                     
                     return myzones;
                 }
             } );
             
             return zones;
         }
    }
    
    public LinkedList<String> getPrices()
    {
    	 try ( Session session = driver.session() )
         {
    		 
    		 
    		 LinkedList<String> prices = session.readTransaction( new TransactionWork<LinkedList<String>>()
             {
                 @Override
                 public LinkedList<String> execute( Transaction tx )
                 {
                	 LinkedList<String> myprices = new LinkedList<String>();
                     Result result = tx.run( "MATCH (user:casa) RETURN user.precio");
                     
                     List<Record> registros = result.list();
                     for (int i = 0; i < registros.size(); i++) {
                    	 //myactors.add(registros.get(i).toString());
                    	 myprices.add(registros.get(i).get("user.precio").toString());
                     }
                     
                     return myprices;
                 }
             } );
             
             return prices;
         }
    	 
    }
    
    public LinkedList<String> getIdes()
    {
    	 try ( Session session = driver.session() )
         {
    		 
    		 
    		 LinkedList<String> ides = session.readTransaction( new TransactionWork<LinkedList<String>>()
             {
                 @Override
                 public LinkedList<String> execute( Transaction tx )
                 {
                     Result result = tx.run( "MATCH (house:casa) RETURN house.ide");
                     LinkedList<String> myides = new LinkedList<String>();
                     List<Record> registros = result.list();
                     for (int i = 0; i < registros.size(); i++) {
                    	 //myactors.add(registros.get(i).toString());
                    	 myides.add(registros.get(i).get("house.ide").toString());
                     }
                     
                     return myides;
                 }
             } );
             
             return ides;
         }
    }
    
    
    
    public LinkedList<String> getUsers()
    {
    	 try ( Session session = driver.session() )
         {
    		 
    		 
    		 LinkedList<String> users = session.readTransaction( new TransactionWork<LinkedList<String>>()
             {
                 @Override
                 public LinkedList<String> execute( Transaction tx )
                 {
                     Result result = tx.run( "MATCH (user:usuario) RETURN user.name");
                     LinkedList<String> myusers = new LinkedList<String>();
                     List<Record> registros = result.list();
                     for (int i = 0; i < registros.size(); i++) {
                    	 //myactors.add(registros.get(i).toString());
                    	 myusers.add(registros.get(i).get("user.name").asString());
                     }
                     
                     return myusers;
                 }
             } );
             
             return users;
         }
    }
    
    public LinkedList<String> getParking()
    {
    	 try ( Session session = driver.session() )
         {
    		 
    		 
    		 LinkedList<String> parks = session.readTransaction( new TransactionWork<LinkedList<String>>()
             {
                 @Override
                 public LinkedList<String> execute( Transaction tx )
                 {
                     Result result = tx.run( "MATCH (house:casa) RETURN house.parqueos");
                     LinkedList<String> myparks = new LinkedList<String>();
                     List<Record> registros = result.list();
                     for (int i = 0; i < registros.size(); i++) {
                    	 //myactors.add(registros.get(i).toString());
                    	 myparks.add(registros.get(i).get("house.parqueos").toString());
                     }
                     
                     return myparks;
                 }
             } );
             
             return parks;
         }
    }
    
    public String userCreation(String name) {
    	try ( Session session = driver.session() )
        {
   		 
   		 String result = session.writeTransaction( new TransactionWork<String>()
   		 
            {
                @Override
                public String execute( Transaction tx )
                {
                	LinkedList<String> users = getUsers();
                	
                    if(!users.contains(name)) {
                    	tx.run( "CREATE (user:usuario {name:'"+name+"'})");
                    	return "OK";
                    }
                    else return "Usuario ya existente";
                    
                    
                }
            }
   		 
   		 );
            
            return result;
        } catch (Exception e) {
        	return e.getMessage();
        }
    }
    
    public LinkedList<String> getMoviesByActor(String actor)
    {
   	 try ( Session session = driver.session() )
        {
   		 
   		 
   		 LinkedList<String> actors = session.readTransaction( new TransactionWork<LinkedList<String>>()
            {
                @Override
                public LinkedList<String> execute( Transaction tx )
                {
                    Result result = tx.run( "MATCH (tom:Person {name: \"" + actor + "\"})-[:ACTED_IN]->(actorMovies) RETURN actorMovies.title");
                    LinkedList<String> myactors = new LinkedList<String>();
                    List<Record> registros = result.list();
                    for (int i = 0; i < registros.size(); i++) {
                   	 //myactors.add(registros.get(i).toString());
                   	 myactors.add(registros.get(i).get("actorMovies.title").asString());
                    }
                    
                    return myactors;
                }
            } );
            
            return actors;
        }
   }

}

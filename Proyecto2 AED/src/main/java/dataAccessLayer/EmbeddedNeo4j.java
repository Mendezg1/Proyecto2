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
    
    public LinkedList<String> searchHouse(String price, String area, String zone, String habits, String parking)
    {
    	 try ( Session session = driver.session() )
         {
    		 
    		 
    		 LinkedList<String> result = session.readTransaction( new TransactionWork<LinkedList<String>>()
             {
                 @Override
                 public LinkedList<String> execute( Transaction tx )
                 {
                	 String query = "MATCH (house:casa {";
                     if(area != "") {
                    	 String ar = area+"m^2";
                    	 query += "area:'" + ar +"'";
                     }
                     if(zone != "") {
                    	 int zona = Integer.parseInt(zone);
                    	 query += ", zona:"+zona;
                     }
                     if(habits != "") {
                    	 int habs = Integer.parseInt(habits);
                    	 query += ", habitaciones:"+habs;
                     }
                     if(parking != "") {
                    	 int parqueos = Integer.parseInt(parking);
                    	 query += ", parqueos:"+parqueos;
                     }
                     query += "})";
                     if (price != "") {
                    	 int precio = Integer.parseInt(price);
                    	 query += "WHERE house.precio <="+precio;
                     }
                     if (area != "" || price != "" || zone != "" || habits != "" || parking != "")
                    	 query+= " RETURN house.ide, house.precio, house.zona, house.area, house.habitaciones, house.parqueos ORDER BY house.ide";
                     Result result = tx.run(query);
                     List<Record> registros = result.list();
                     
                     LinkedList<String> saves = new LinkedList<String>();
                	 LinkedList<String> myides = new LinkedList<String>();
                	 LinkedList<String> myareas = new LinkedList<String>();
                	 LinkedList<String> myzones = new LinkedList<String>();
                	 LinkedList<String> myprices = new LinkedList<String>();
                	 LinkedList<String> myparks = new LinkedList<String>();
                	 LinkedList<String> myhabs = new LinkedList<String>();
                	 
                	 saves.add("\nID          " +  "                Precio      " + "                Zona" + "                 Area" + "                 Habitaciones" + "                 Parqueos");
                     
                     for (int i = 0; i < registros.size(); i++) {
                    	 //myactors.add(registros.get(i).toString());
                    	 myides.add(registros.get(i).get("house.ide").toString());
                    	 myprices.add(registros.get(i).get("house.precio").toString());
                    	 myzones.add(registros.get(i).get("house.zona").toString());
                    	 myareas.add(registros.get(i).get("house.area").toString());
                    	 myparks.add(registros.get(i).get("house.parqueos").toString());
                    	 myhabs.add(registros.get(i).get("house.habitaciones").toString());
                    	 saves.add(myides.get(i) + "              " +myprices.get(i) + "               " + myzones.get(i) + "                " + myareas.get(i) + "             " + myhabs.get(i) +  "                   " + myparks.get(i));
                     }
                     return saves;
                 }
             } );
             
             return result;
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
                    	tx.run( "CREATE (user:usuario {name:'"+name+"', admn:"+0+"})");
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
    
    public String newHouse(String name, String id, String precio, String zona, String area, String habits, String parqueos) {
    	try ( Session session = driver.session() )
        {
   		 
    		String ar = area+"m^2";
   		 String result = session.writeTransaction( new TransactionWork<String>()
   		 
            {
                @Override
                public String execute( Transaction tx )
                {
                	
                	int ide = Integer.parseInt(id);
                	int price = Integer.parseInt(precio);
                    int zone = Integer.parseInt(zona);
                    int habs = Integer.parseInt(habits);
                    int parks = Integer.parseInt(parqueos);
                	LinkedList<String> users = getUsers();
                	
                    if(users.contains(name)) {
                    	String veri = verifyAdmn(name);
                    	if (veri.equals("Y")) {
                    		tx.run("create (house:casa { ide:"+id+", precio:"+price+", zona:"+zone+", area:'"+ar+"', habitaciones:"+habs+", parqueos:"+parks+"})");
                    		return "Casa creada correctamente";
                    	}
                    	else return "El usuario no es admin";
                    }
                    else return "El usuario no existe";
                    
                    
                }
            }
   		 
   		 );
            
            return result;
        } catch (Exception e) {
        	return e.getMessage();
        }
    }
    
    
    
    public String Relation(String name, String id) {
    	try ( Session session = driver.session() )
        {
   		 
   		 String result = session.writeTransaction( new TransactionWork<String>()
   		 
            {
                @Override
                public String execute( Transaction tx )
                {
                	try {
                		
                		int ide = Integer.parseInt(id);
                		LinkedList<String> users = getUsers();
                		
                		if(users.contains(name)) {
                			tx.run("MATCH (user:usuario {name:'"+name+"'}), (house:casa {ide:"+id+"}) create (user) - [:GUARDADO] -> (house)");
                		}
                		else {
                			
                			userCreation(name);
                			Relation(name, id);
                			
                		}
                        return "Casa "+ id+" guardada para el usuario " + name;
                	}
                	catch (Exception e) {
                		
                		return "Ingreso incorrecto";
                	}
                }
            }
   		 
   		 );
   		 
   		 
            
            return result;
        } catch (Exception e) {
        	return e.getMessage();
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
    
    public String verifyAdmn(String name) {
    	try ( Session session = driver.session() )
        {
   		 
    	    String result = session.writeTransaction( new TransactionWork<String>()
   		 
            {
                @Override
                public String execute( Transaction tx )
                {
                	String res = "";
                	LinkedList<String> users = getUsers();
                	if(users.contains(name)) {
                		Result result = tx.run("MATCH (n:usuario) 	WHERE n.name='"+name+"' RETURN n.admn");
                		String admn="";
                		List<Record> registros = result.list();
                		admn = registros.get(0).get("n.admn").toString();
                		if (admn.equals("1")) {
                			res = "Y";
                		}
                		else res = "N";
                	}
                	else res = "NE";
                	return res;
                }
            }
   		 
   		 );
            
            return result;
        } catch (Exception e) {
        	return e.getMessage();
        }
    }
    
    
    
    public String Delete(String name, String id) {
    	try ( Session session = driver.session() )
        {
   		 
    		
    	int ide = Integer.parseInt(id);
   		 String result = session.writeTransaction( new TransactionWork<String>()
   		 
            {
                @Override
                public String execute( Transaction tx )
                {
                	int ide = Integer.parseInt(id);
                	String res = "";
                	LinkedList<String> ids = getIdes();
                	
                	String exist = verifyAdmn(name);
                	if(exist.equals("Y")) {
                		if(ids.contains(id)) {
                			tx.run("MATCH (house:casa) WHERE house.ide="+ide+" DETACH DELETE house");
                			res = "Casa eliminada correctamente";
                		}
                		else res = "La casa no existe, no se puede eliminar.";
                	}
                	else if (exist.equals("NE")) {
                		res = "El usuario no existe";
                	}
                	else res="El usuario no es admin";
                	return res;
                }
            }
   		 
   		 );
            
            return result;
        } catch (Exception e) {
        	return e.getMessage();
        }
    }
    
    public LinkedList<String> seeProf(String name)
    {
    	 try ( Session session = driver.session() )
         {
    		 
    		 
    		 LinkedList<String> saved = session.readTransaction( new TransactionWork<LinkedList<String>>()
             {
                 @Override
                 public LinkedList<String> execute( Transaction tx )
                 {
                	 
                	 LinkedList<String> saves = new LinkedList<String>();
                	 LinkedList<String> myides = new LinkedList<String>();
                	 LinkedList<String> myareas = new LinkedList<String>();
                	 LinkedList<String> myzones = new LinkedList<String>();
                	 LinkedList<String> myprices = new LinkedList<String>();
                	 LinkedList<String> myparks = new LinkedList<String>();
                	 LinkedList<String> myhabs = new LinkedList<String>();
                	 LinkedList<String> users = getUsers();
                	 
                	 saves.add("ID          " +  "                Precio      " + "                Zona" + "                 Area" + "                 Habitaciones" + "                 Parqueos");
                	 
                	 if(users.contains(name)) {
                		 Result result = tx.run( "MATCH (user:usuario {name:'"+name+"'}) - [:GUARDADO] -> (house:casa) RETURN house.ide, house.precio, house.area, house.zona, house.habitaciones, house.parqueos ORDER BY house.ide");
                         
                         List<Record> registros = result.list();
                         for (int i = 0; i < registros.size(); i++) {
                        	 //myactors.add(registros.get(i).toString());
                        	 myides.add(registros.get(i).get("house.ide").toString());
                        	 myprices.add(registros.get(i).get("house.precio").toString());
                        	 myzones.add(registros.get(i).get("house.zona").toString());
                        	 myareas.add(registros.get(i).get("house.area").toString());
                        	 myparks.add(registros.get(i).get("house.parqueos").toString());
                        	 myhabs.add(registros.get(i).get("house.habitaciones").toString());
                        	 saves.add(myides.get(i) + "              " +myprices.get(i) + "               " + myzones.get(i) + "                " + myareas.get(i) + "             " + myhabs.get(i) +  "                   " + myparks.get(i));
                         }
                	 }
                	 
                	 return saves;
                	 
                    
                 }
             } );
             
             return saved;
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

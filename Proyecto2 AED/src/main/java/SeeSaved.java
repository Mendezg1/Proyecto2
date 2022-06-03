

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import dataAccessLayer.EmbeddedNeo4j;

/**
 * Servlet implementation class SeeSaved
 */
@WebServlet("/SeeSaved")
public class SeeSaved extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SeeSaved() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
	 	response.setContentType("application/json");
	 	response.setCharacterEncoding("UTF-8");
	 	JSONObject myResponse = new JSONObject();
	 	
	 	JSONArray guardados = new JSONArray();
	 	
	 	String name = request.getParameter("name");
	 	
	 	 try ( EmbeddedNeo4j neo4jDriver = new EmbeddedNeo4j( "bolt://localhost:7687", "neo4j", "Mendezg1122" ) )
	        {
	 		 	LinkedList<String> saved = neo4jDriver.seeProf(name);
	 		 	
	 		 	for(int i = 0; i < saved.size(); i++) {
	 		 		guardados.add(saved.get(i));
	 		 	}
	        	
			 	
	        } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				myResponse.put("resultado", "Error: " + e.getMessage());
			}
	 	
	 	
	 	myResponse.put("guardados", guardados);
	 	out.println(myResponse);
	 	out.flush();  
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

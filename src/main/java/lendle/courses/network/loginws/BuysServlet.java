/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lendle.courses.network.loginws;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mac
 */
@WebServlet(name = "BuysServlet", urlPatterns = {"/buys"})
public class BuysServlet extends HttpServlet {

    static {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter(); Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app")) {
            //select from login
            //output in id:password style
            List list = new Vector(); //長出一個vector
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from PRODUCT_ORDER");

            
            while(rs.next()){
                String p_no = rs.getString("p_no"); 
                String item = rs.getString("item");         
                String price = rs.getString("price");
                String qty = rs.getString("qty");
                String buy_name = rs.getString("buy_name");
                String buy_add = rs.getString("buy_add");
                String buy_phone = rs.getString("buy_phone");
                String buy_date = rs.getString("buy_date");
                Map map = new HashMap();    //用Hash的方式建立
                map.put("p_no", p_no);
                map.put("item", item);
                map.put("price",price);
                map.put("qty",qty);
                map.put("buy_name",buy_name);
                map.put("buy_add",buy_add);
                map.put("buy_phone",buy_phone);
                map.put("buy_date",buy_date);
                
                list.add(map);
            }
            //建立gson
            Gson gson = new Gson();
            String json = gson.toJson(list);
            out.println(json);
            //////////////////////////////
        }catch(Exception e){
            throw new ServletException(e);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
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
@WebServlet(name = "BuyinfoServlet", urlPatterns = {"/buy_info"})
public class BuyinfoServlet extends HttpServlet {
    static{
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginInfoServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        response.setContentType("application/json");
        try (PrintWriter out=response.getWriter(); Connection conn=DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app")) {
            //select from login
            //output in id:password style
            //this time, consider the id parameter
            String p_no=request.getParameter("p_no");
            PreparedStatement pstmt = conn.prepareStatement("select * from PRODUCT_ORDER where P_no=?");
            pstmt.setString(1, p_no); //第一個問號後面的value
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                Map map = new HashMap();
                map.put("p_no",rs.getString("p_no"));
                out.print(new Gson().toJson(map));
            }else{
            response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
            }
            
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
        response.setContentType("text/plain;charset=UTF-8");
        try (PrintWriter out=response.getWriter(); Connection conn=DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app")) {
            //insert the corresponding use
            String P_No=request.getParameter("p_no");
            String Item=request.getParameter("item");
            String Price=request.getParameter("price");
            String Qty=request.getParameter("qty");
            String Buy_name=request.getParameter("buy_name"); 
            String Buy_add=request.getParameter("buy_add"); 
            String Buy_phone=request.getParameter("buy_phone"); 
            String Buy_date=request.getParameter("buy_date"); 
            out.println(P_No);
            out.println(Item);
            out.println(Price);
            out.println(Qty);
            PreparedStatement pstmt = conn.prepareStatement("insert into PRODUCT_ORDER (P_NO, ITEM, PRICE, QTY, BUY_NAME, BUY_ADD, BUY_PHONE, BUY_DATE) VALUES(?,?,?,?,?,?,?,?)");        
            pstmt.setString(1, P_No); //第1個問號後面的value
            pstmt.setString(2, Item); //第2個問號後面的value
            pstmt.setString(3, Price);
            pstmt.setString(4, Qty);
            pstmt.setString(5,Buy_name);
            pstmt.setString(6,Buy_add);
            pstmt.setString(7,Buy_phone);
            pstmt.setString(8,Buy_date);
            int ret = pstmt.executeUpdate();
            out.println(ret);
            
        }catch(Exception e){
            throw new ServletException(e);
        }
    }
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        try (PrintWriter out=response.getWriter(); Connection conn=DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app")) {
            //update the corresponding user
            Statement stmt = conn.createStatement();
            String P_No=request.getParameter("p_no");
            String Buy_name=request.getParameter("buy_name"); 
            String Buy_add=request.getParameter("buy_add"); 
            String Buy_phone=request.getParameter("buy_phone"); 
            String Buy_date=request.getParameter("buy_date"); 
            //////////////////////////////
            String qry = "update PRODUCT_ORDER set buy_name='"+Buy_name+"',buy_add='"+Buy_add+"',buy_phone='"+Buy_phone+"',buy_date='"+Buy_date+"' where p_no="+P_No;      
            out.print(qry);
            stmt.executeUpdate(qry);
        }catch(Exception e){
            throw new ServletException(e);
        }
    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out=response.getWriter(); Connection conn=DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app")) {
            //delete the corresponding user
            String P_no=request.getParameter("p_no");
            PreparedStatement stmt = conn.prepareStatement("delete from PRODUCT_ORDER where p_no=?");
            stmt.setString(1, P_no);
            //////////////////////////////
            int ret = stmt.executeUpdate();
            out.println(ret);
        }catch(Exception e){
            throw new ServletException(e);
        }
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import Contract.AbstractContract;
import Exceptions.WrongNumberValueException;
import UFNS.UFNS;
import Workers.Worker;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ivan
 */
@WebServlet(name = "ControllerServlet",
        loadOnStartup = 1,
        urlPatterns = {"/workers", 
            "/deleteWorker", 
            "/addWorker", 
            "/contracts"
        })
public class ControllerServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ControllerServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControllerServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
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
        String userPath = request.getServletPath();
         
        // if cart page is requested
        if (userPath.equals("/workers")) {
               ArrayList<Worker> accounts = new ArrayList(UFNS.getInstance().getAllWorkers());
               ServletContext sc = getServletContext();
               RequestDispatcher rd = sc.getRequestDispatcher(userPath);
               request.setAttribute("accountList", accounts );
        } else if (userPath.equals("/contracts")) {
               String id;
               id = request.getQueryString();
               id = id.replaceAll("id=", "");
               ArrayList<AbstractContract> contracts;
            try {
                contracts = new ArrayList(UFNS.getInstance().getAllContractsForWorkerWithId(Long.parseLong(id)));
                ServletContext sc = getServletContext();
                RequestDispatcher rd = sc.getRequestDispatcher(userPath);
                request.setAttribute("contractsList", contracts );
            } catch (WrongNumberValueException ex) {
                Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
               
               
        } 

        // use RequestDispatcher to forward request internally
        String url = "/WEB-INF/pages" + userPath + ".jsp";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
        
                String userPath = request.getServletPath();
                
         if (userPath.equals("/addWorker")) {
            String name = (String) request.getParameter("nameField");
                    try {
                        UFNS.getInstance().addWorkerWithName(name);
                            String url = "workers";
                            response.sendRedirect(url);
                    } catch (WrongNumberValueException ex) {
                        Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                                ex.printStackTrace();
                    }
        } else if (userPath.equals("/deleteWorker"))  {
            String id = (String) request.getParameter("id");
                    try {
                        UFNS.getInstance().removeWorkerWithId(Long.parseLong(id));
                    } catch (WrongNumberValueException ex) {
                        Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
            String url = "workers";
            response.sendRedirect(url);
        }
        
    }
    
        @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userPath = req.getServletPath();
        if (userPath.equals("/deletionsTest")) {
            try {
                UFNS.getInstance().removeWorkerWithId(1);
            } catch (WrongNumberValueException ex) {
                Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

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

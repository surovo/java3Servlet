/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import Exceptions.WrongNumberValueException;
import UFNS.UFNS;
import Workers.Worker;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
        urlPatterns = {"/workers", "/deletionsTest", "/addWorker"})
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
         // if category page is requested
        if (userPath.equals("/index")) {
            // TODO: Implement category request

        // if cart page is requested
        } else if (userPath.equals("/workers")) {
            try {
                UFNS.getInstance().addWorkerWithName("test");
            } catch (WrongNumberValueException ex) {
                Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
               ArrayList<Worker> accounts = new ArrayList(UFNS.getInstance().getAllWorkers());
               ServletContext sc = getServletContext();
               RequestDispatcher rd = sc.getRequestDispatcher(userPath);
               request.setAttribute("accountList", accounts );
        } else if (userPath.equals("/checkout")) {
            // TODO: Implement checkout page request

        // if user switches language
        } else if (userPath.equals("/deletionsTest")) {
            try {
                UFNS.getInstance().removeWorkerWithId(1);
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
                Map p = request.getParameterMap();
         if (userPath.equals("/addWorker")) {
            String name = (String) request.getParameter("nameField");
                    try {
                        UFNS.getInstance().addWorkerWithName(name);
                            String url = "/WEB-INF/pages" + "/workers" + ".jsp";
                            request.getRequestDispatcher(url).forward(request, response);
                    } catch (WrongNumberValueException ex) {
                        Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                                ex.printStackTrace();
                    }
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

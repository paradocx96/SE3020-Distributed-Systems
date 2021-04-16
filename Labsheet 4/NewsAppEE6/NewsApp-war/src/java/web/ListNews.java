/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import ejb.NewsEntity;
import ejb.NewsEntityFacade;
import ejb.NewsEntitySorted;
import ejb.SessionManagerBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nb
 */
@WebServlet(name = "ListNews", urlPatterns = {"/ListNews"})
public class ListNews extends HttpServlet {

    @EJB
    private SessionManagerBean sessionManagerBean;
    @EJB
    private NewsEntityFacade newsEntityFacade;

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
        request.getSession(true);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ListNews</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListNews at " + request.getContextPath() + "</h1>");

            // TODO: Change this line to call findAllSortedDesc() isntead
            List news = newsEntityFacade.findAllSortedDesc();

            // TODO: Update the code to display the NewsEntitySorted entity details
            for (Iterator it = news.iterator(); it.hasNext();) {
                NewsEntitySorted elem = (NewsEntitySorted) it.next();
                out.println(" <b>" + elem.getTitle() + " </b><br />");
                out.println(elem.getBody() + "<br /> ");

                // TODO: Uncomment to display the time 
                out.println(elem.getDate() + "<br /> ");
            }
            out.println("<a href='PostMessage'>Add new message</a>");

            out.println("<br><br>");
            out.println(sessionManagerBean.getActiveSessionsCount() + " user(s) reading the news.");

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

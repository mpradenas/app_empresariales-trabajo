/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import dao.InsEstudiantesJpaController;
import dao.InsInstitucionEducativaJpaController;
import dao.exceptions.RollbackFailureException;
import entities.InsEstudiantes;
import entities.InsInstitucionEducativa;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Gamalyon
 */
public class Controlador2 extends HttpServlet {

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
            throws ServletException, IOException, RollbackFailureException, Exception {
        
        if(request.getParameter("tipoOp").equals("eliminar"))
        {
               Integer idIns= request.getParameter("elId").equals("")? 0: Integer.parseInt(request.getParameter("elId"));
               InsEstudiantesJpaController insEstudiante=new InsEstudiantesJpaController(); 
               //request.setAttribute("tablaresultado", asList(ins.findInsInstitucionEducativaEntities()));
               //request.getRequestDispatcher("index.jsp").forward(request, response);
               insEstudiante.destroy(idIns);
               response.getWriter().write("//OK//Se ha eliminado registro sin inconvenientes//");
        
        }
        else
        {
            Integer idAlumn=request.getParameter("elId").equals("")? 0: Integer.parseInt(request.getParameter("elId"));
            String nombre=request.getParameter("nombre");
            String apPat=request.getParameter("apPat");
            String apMat=request.getParameter("apMat");
            int edad=Integer.parseInt(request.getParameter("edad"));
            int idInst=request.getParameter("institucion").equals("")? 0:Integer.parseInt(request.getParameter("institucion"));
            InsEstudiantesJpaController insEstudiante=new InsEstudiantesJpaController(); 
            //request.setAttribute("tablaresultado", asList(ins.findInsInstitucionEducativaEntities()));
               //request.getRequestDispatcher("index.jsp").forward(request, response);
            InsEstudiantes elEstudiante=new InsEstudiantes();
            try
            {
                elEstudiante.setEstNombre(nombre);
                elEstudiante.setEstApellidopaterno(apPat);
                elEstudiante.setEstApellidomaterno(apMat);
                elEstudiante.setEstEdad(edad);
                    if(idInst>0)
                    {
                        InsInstitucionEducativa insEd= new InsInstitucionEducativa();
                        InsInstitucionEducativaJpaController InsEdDao=new InsInstitucionEducativaJpaController();
                        insEd=InsEdDao.findInsInstitucionEducativa(idInst);
                        elEstudiante.setEstIdinstitucion(insEd);
                    }
                    if(idAlumn>0)
                    {
                      elEstudiante.setEstIdestudiante(idAlumn);
                      
                      insEstudiante.edit(elEstudiante);
                    }
                    else
                    {
                       insEstudiante.create(elEstudiante);
                    }
                    request.setAttribute("mensaje", "funciona");
                    request.getRequestDispatcher("alumnos.jsp").forward(request, response);

                }
                catch(Exception ex)
                {
                    request.setAttribute("mensaje", ex.getMessage());
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(Controlador2.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(Controlador2.class.getName()).log(Level.SEVERE, null, ex);
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

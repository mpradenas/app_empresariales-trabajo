/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import dao.InsInstitucionEducativaJpaController;
import dao.exceptions.RollbackFailureException;
import entities.InsInstitucionEducativa;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import static java.util.Arrays.asList;
import java.util.Date;
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
public class Controlador extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, RollbackFailureException, Exception {
        if(request.getParameter("tipoOp").equals("eliminar"))
        {
               //aquí implementaré todo mi código que apunta al dao
               Integer idIns= request.getParameter("elId").equals("")? 0: Integer.parseInt(request.getParameter("elId"));
               InsInstitucionEducativaJpaController ins=new InsInstitucionEducativaJpaController();
               ins.destroy(idIns);
               response.getWriter().write("//OK//Se ha eliminado registro sin inconvenientes//");
        }
        else{
            
               Integer idIns= request.getParameter("elId").equals("")? 0: Integer.parseInt(request.getParameter("elId"));
               InsInstitucionEducativaJpaController ins=new InsInstitucionEducativaJpaController();

               //request.setAttribute("tablaresultado", asList(ins.findInsInstitucionEducativaEntities()));
               //request.getRequestDispatcher("index.jsp").forward(request, response);

               SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-YYYY");
               InsInstitucionEducativa iEducObj=new InsInstitucionEducativa();

               iEducObj.setInstNombre(request.getParameter("nombre"));
               iEducObj.setInstFechainicio(formatter.parse(request.getParameter("fechaIni")));
               iEducObj.setInstFechatermino(formatter.parse(request.getParameter("fechaTerm")));
               iEducObj.setVacantes(Integer.parseInt(request.getParameter("vacantes")));
               try
                {

                    if(idIns>0)
                    {
                      iEducObj.setIntsIdinstitucion(idIns);
                      ins.edit(iEducObj);
                    }
                    else
                    {
                       ins.create(iEducObj);
                    }
                    request.setAttribute("mensaje", "funciona");
                    request.getRequestDispatcher("index.jsp").forward(request, response);

                }
                catch(Exception ex)
                {
                    request.setAttribute("mensaje", ex.getMessage());
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
        }
}

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
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
            
        } catch (ParseException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            response.getWriter().write("//NOK//"+ex.getMessage()+"//");
            
        } catch (Exception ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
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

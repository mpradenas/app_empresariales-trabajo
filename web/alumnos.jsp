<%-- 
    Document   : alumnos
    Created on : 03-jun-2017, 20:11:15
    Author     : Gamalyon
--%>

<%@page import="entities.InsInstitucionEducativa"%>
<%@page import="dao.InsInstitucionEducativaJpaController"%>
<%@page import="java.util.Objects"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="entities.InsEstudiantes"%>
<%@page import="java.util.List"%>
<%@page import="dao.InsEstudiantesJpaController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script type="text/javascript">
            function editar(indice,tipoOp)
            {
                  var idyidIns=document.getElementById("iddato_"+indice).value.split("//");
                  var id=idyidIns[0];
                  var idIns=idyidIns[1];
                  var nombre=document.getElementById("nom_"+indice).innerHTML;
                  var apPat=document.getElementById("apPat_"+indice).innerHTML;
                  var apMat=document.getElementById("apMat_"+indice).innerHTML;
                  var edad=document.getElementById("edad_"+indice).innerHTML;
                  //var instituto=document.getElementById("idIns_"+indice).innerHTML;
                  document.getElementById("tipoOp").value=tipoOp;
                  document.getElementById("elId").value=id;
                  document.getElementById("nombre").value=nombre;
                  document.getElementById("apPat").value=apPat;
                  document.getElementById("apMat").value=apMat;
                  document.getElementById("edad").value=edad;
                  document.getElementById("institucion").value=idIns;

            }
            function eliminar(indice,tipoOp)
            {
                
                   var idYidIns=document.getElementById("iddato_"+indice).value.split("//");
                   var id=idYidIns[0];
                   var xmlhttp;
                   document.getElementById("tipoOp").value=tipoOp;
                   if (window.XMLHttpRequest) {
                   // code for modern browsers
                   xmlhttp = new XMLHttpRequest();
                   } else {
                    // code for old IE browsers
                    xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
                   }
                    xmlhttp.onreadystatechange = function() {
                        if (this.readyState == 4 && this.status == 200) {
                          var data=this.responseText.split("//");
                          if(data[1]=="OK"){
                              alert(data[2]);
                              location.href="alumnos.jsp";
                          }
                          else
                          {
                                  alert(data[2]);
                              
                          }
                     };
                 }
                 xmlhttp.open("post", "controlador2?action=eliminar&elId="+id+"&tipoOp="+tipoOp, true);
                 xmlhttp.send();
                 
             

            }
        </script>
    </head>
    <body>
        <h1>Alumnos</h1>
                <h1>CRUD tablas relacionadas</h1>
        <ol>
            <li><a href="index.jsp">Institución educativa</a></li>
            <li><a href="#">Alumnos postulantes</a></li>
        </ol>

        <form action="controlador2" method="POST">
            <table border="1">
                <thead>
                    <tr>
                        <th colspan="2">ingrese institucion</th>
                        
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>nombre</td>
                        <td><input type="text" id="nombre" name="nombre" value="">
                            <input type="hidden" id="elId" name="elId" value="">
                            <input type="hidden" id="tipoOp" name="tipoOp" value="">
                        </td>
                    </tr>
                    <tr>
                        <td>Apellido paterno</td>
                        <td>
                            <input type="text" id="apPat" name="apPat" value="">
                        </td>
                    </tr>
                    <tr>
                        <td>Apellido materno</td>
                        <td><input type="text" id="apMat" name="apMat" value="">
                        </td>
                    </tr>
                    <tr>
                        <td>Edad</td>
                        <td><input type="text" id="edad" name="edad" value=""></td>
                    </tr>
                    <tr>
                       <td>Selecciona curso</td>
                       <td>
                        <select id="institucion" name="institucion">
                        <%
                             InsInstitucionEducativaJpaController insInst=new InsInstitucionEducativaJpaController();
                             List<InsInstitucionEducativa>ListaInsti=insInst.findInsInstitucionEducativaEntities();
                             if(ListaInsti!=null && ListaInsti.size()>0)
                             {
                                  for(InsInstitucionEducativa laInsti : ListaInsti)
                                  {
                                       out.println("<option value='"+laInsti.getIntsIdinstitucion()+"'>"+laInsti.getInstNombre()+"</option>");
                                  }
                             }    
                        %>
                        </select>
                        </td>
                    </tr>
                </tbody>
            </table>
            
            
            <button type="submit" >aceptar</button>
        </form>
        <form action="controlador">
        <table border="1">
                <thead>
                    <tr>
                        <th>Acción</th>
                        <th>Nombre institución educativa</th>
                        <th>Apellido paterno</th>
                        <th>Apellido materno</th>
                        <th>Edad</th>
                        <th>Institucion</th>
                    </tr>
                </thead>
                <tbody>
           <%
           InsEstudiantesJpaController ins=new InsEstudiantesJpaController();
          
           List<InsEstudiantes>Lista=ins.findInsEstudiantesEntities();
           if(Lista!=null)
           {
                    if(Lista.size()>0)
                    {
                       int id=0;
                       for(int i=0;i<Lista.size();i++)
                       {
                             
                             out.println("<tr>");
                             out.println("<td><button type=\"button\" onclick=\"editar("+i+",'editar');\">editar</button><button type=\"button\" onclick=\"eliminar("+i+",'eliminar');\">Eliminar</button><input type=\"hidden\" id=\"iddato_"+i+"\" value=\""+Lista.get(i).getEstIdestudiante()+"//"+Lista.get(i).getEstIdinstitucion().getIntsIdinstitucion()+"\"/></td>");
                             out.println("<td id=\"nom_"+i+"\">"+Lista.get(i).getEstNombre()+"</td>");
                             out.println("<td id=\"apPat_"+i+"\">"+Lista.get(i).getEstApellidopaterno()+"</td>");
                             out.println("<td id=\"apMat_"+i+"\">"+Lista.get(i).getEstApellidomaterno()+"</td>");
                             out.println("<td id=\"edad_"+i+"\">"+Lista.get(i).getEstEdad()+"</td>");
                             if(Lista.get(i).getEstIdinstitucion()!=null)
                             {
                                 out.println("<td id=\"inst_"+i+"\">"+Lista.get(i).getEstIdinstitucion().getInstNombre()+"</td>");
                             }
                             else
                             {
                                 out.println("<td id=\"inst_"+i+"\">No tiene curso asignado</td>");
                             }    
                             out.println("</tr>");

                        } 
                     }
                }
               %>
                </tbody>
            </table>
        </form>
        <%
         
        String msg=(String) request.getAttribute("mensaje");
        if(msg!=null){
        %>
             <h3><%=msg %></h3>
        <% }%>

    </body>
    
</html>

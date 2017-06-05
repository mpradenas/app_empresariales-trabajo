<%-- 
    Document   : index
    Created on : 31-may-2017, 21:49:02
    Author     : Gamalyon
--%>

<%@page import="dao.InsInstitucionEducativaJpaController"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Formatter"%>
<%@page import="java.util.Date"%>
<%@page import="entities.InsInstitucionEducativa"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script type="text/javascript" language="Javascript">
              
             function editar(indice,tipoOp){
                  var id=document.getElementById("iddato_"+indice).value;
                  var nombre=document.getElementById("nom_"+indice).innerHTML;
                  var fechaIni=document.getElementById("fechaIn_"+indice).innerHTML;
                  var fechaTer=document.getElementById("fechaTer_"+indice).innerHTML;
                  var vacantes=document.getElementById("vacan_"+indice).innerHTML;
                  document.getElementById("tipoOp").value=tipoOp;
                  document.getElementById("elId").value=id;
                  document.getElementById("nombre").value=nombre;
                  document.getElementById("fechaIni").value=fechaIni;
                  document.getElementById("fechaTerm").value=fechaTer;
                  document.getElementById("vacantes").value=vacantes;
             }
             
             function eliminar(indice,tipoOp)
             {
                   var id=document.getElementById("iddato_"+indice).value;
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
                              location.href="index.jsp";
                          }
                          else
                          {
                                  alert(data[2]);
                              
                          }
                     };
                 }
                 xmlhttp.open("post", "controlador?action=eliminar&elId="+id+"&tipoOp="+tipoOp, true);
                 xmlhttp.send();
                 
             
         }
        </script>
    </head>
    <body>
        <h1>CRUD tablas relacionadas</h1>
        <ol>
            <li><a href="#">Institución educativa</a></li>
            <li><a href="alumnos.jsp">Alumnos postulantes</a></li>
        </ol>

        <form action="controlador" method="POST">
            <table border="1">
                <thead>
                    <tr>
                        <th colspan="2">ingrese institucion</th>
                        
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>nombre institucion</td>
                        <td><input type="text" id="nombre" name="nombre" value="">
                            <input type="hidden" id="elId" name="elId" value="">
                            <input type="hidden" id="tipoOp" name="tipoOp" value="">
                        </td>
                    </tr>
                    <tr>
                        <td>Fecha inicio</td>
                        <td><input type="text"  id="fechaIni" name="fechaIni" value=""></td>
                    </tr>
                    <tr>
                        <td>Fecha término</td>
                        <td><input type="text" id="fechaTerm" name="fechaTerm" value=""></td>
                    </tr>
                    <tr>
                        <td>Vacantes</td>
                        <td><input type="text" id="vacantes" name="vacantes" value=""></td>
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
                        <th>Fecha inicio</th>
                        <th>Fecha término</th>
                        <th>Vacantes</th>
                    </tr>
                </thead>
                <tbody>
           <%
           InsInstitucionEducativaJpaController ins=new InsInstitucionEducativaJpaController();
          
           List<InsInstitucionEducativa>Lista=ins.findInsInstitucionEducativaEntities();
           if(Lista!=null)
           {
                    if(Lista.size()>0)
                    {
                       for(int i=0;i<Lista.size();i++)
                       {
                             SimpleDateFormat fr=new SimpleDateFormat("dd-MM-YYYY");
                             
                             int id=Integer.parseInt(Lista.get(i).getIntsIdinstitucion().toString());
                             out.println("<tr>");
                             out.println("<td><button type=\"button\" onclick=\"editar("+i+",'editar');\">editar</button><button type=\"button\" onclick=\"eliminar("+i+",'eliminar');\">Eliminar</button><input type=\"hidden\" id=\"iddato_"+i+"\" value=\""+id+"\"/></td>");
                             out.println("<td id=\"nom_"+i+"\">"+Lista.get(i).getInstNombre()+"</td>");
                             out.println("<td id=\"fechaIn_"+i+"\">"+fr.format(Lista.get(i).getInstFechainicio())+"</td>");
                             out.println("<td id=\"fechaTer_"+i+"\">"+fr.format(Lista.get(i).getInstFechatermino())+"</td>");
                             out.println("<td id=\"vacan_"+i+"\">"+Lista.get(i).getVacantes()+"</td>");
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

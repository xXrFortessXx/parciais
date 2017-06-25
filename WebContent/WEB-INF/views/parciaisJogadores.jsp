<%@page import="com.rfs.parciaiscartola.keys.ParciaisCartolaKeys"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link href="<c:url value="/resourses/css/theme_home.css" />" rel="stylesheet">
		<script type="text/javascript" src="resourses/js/Scripts.js"></script>
		<meta name="description" content="Parciais Cartola - O site do Parciais Cartola é um desenvolvimento autônomo e tem como objetivo acompanhar a pontuação dos jogadores durante a rodada e também facilitar o acompanhamento da liga propria:'O Ultimo é Viado(UEV)'">
		<meta name="author" content="Rodrigo Fortes Souza">
		<meta name="reply-to" content="rodrigo.fortes.souza@hotmail.com">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Parciais dos Jogadores - Parciais Cartola</title>
		<script type="text/javascript">
			function redirectDetailLiga(index)
		      {
				debugger;
				document.getElementById("idInputIndexLiga").setAttribute('value', index); 
				document.getElementById("idFormDetailLiga").submit();
		      }
		</script>
	</head>
	
	<body>
	  <nav class="navbar navbar-expand-md fixed-top bg-primary navbar-light">
	    <div class="container">
	      <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbar2SupportedContent" aria-controls="navbar2SupportedContent" aria-expanded="false" aria-label="Toggle navigation"> <span class="navbar-toggler-icon"></span> </button>
	      <div class="collapse navbar-collapse justify-content-center" id="navbar2SupportedContent">
	        <ul class="navbar-nav">
	          <li class="nav-item text-gray-dark">
	            <a class="nav-link text-white" href=<%=ParciaisCartolaKeys.INIT_ACTION_BACK%>><strong>Home</strong></a>
	          </li>
	        </ul>
	      </div>
	    </div>
	  </nav>
	  <div class="p-5">
	    <div class="container">
	      <div class="row">
	        <div class="col-md-12">
	          <h1 class="mb-4 text-primary text-center">Parciais dos Jogadores</h1>
	        </div>
	      </div>
	    </div>
	    <form id="idFormDetailLiga" name="formDetailLiga" action=<%=ParciaisCartolaKeys.DETALHE_LIGAS%>>
		    <div class="container">
			    <c:forEach var="jogador" items="${usuarioBean.jogadores}" varStatus="index">
		    	<input type="hidden" name="indexLiga" id="idInputIndexLiga" value="">
			      <div class="row">
			        <div class="col-md-12">
			          <div class="row m-2">
			            <div class="col-md-12 bg-inverse my-2">
			              <table class="table">
			                <thead> </thead>
			                <tbody class="m-2">
			                  <tr class="my-2">
			                    <td class="bg-faded" style="border-left: 2px solid gray; width: 200px;">
	                				<img src="${jogador.foto}" style="width: 150px;">
                				</td>
		                		<td class="bg-faded" style="vertical-align: middle; display: table-cell; 
		                		text-align: left; font-family: Roboto-Regular; font-size: xx-large;">
	                				<strong>${jogador.apelido}<br/></strong>
	                			</td>
		                		<td class="bg-faded" style="width: 28%; border-right: 2px solid gray; vertical-align: middle; display: table-cell; 
		                		text-align: left; font-family: Roboto-Regular; font-size: xx-large; color: black;">
		                			<strong>Pontos: </strong>${jogador.pontuacao}<br/>
	                			</td>
			                  </tr>
			                  
			                </tbody>
			              </table>
			            </div>
			          </div>
			        </div>
			      </div>
		      </c:forEach>
		    </div>
	    </form>
	  </div>
	</body>
</html>
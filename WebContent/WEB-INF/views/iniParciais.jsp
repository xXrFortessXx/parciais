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
		<meta name="description" content="Parciais Cartola - O site do Parciais Cartola é um desenvolvimento autônomo e tem como objetivo acompanhar a pontuação dos jogadores durante a rodada e também facilitar o acompanhamento da liga propria:'O Ultimo é Viado(UEV)'">
		<meta name="author" content="Rodrigo Fortes Souza">
		<meta name="reply-to" content="rodrigo.fortes.souza@hotmail.com">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Home - Parciais Cartola</title>
	</head>
	<body class="text-center">
	  <nav class="navbar navbar-expand-md fixed-top bg-primary navbar-light">
	    <div class="container">
	      <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbar2SupportedContent" aria-controls="navbar2SupportedContent" aria-expanded="false" aria-label="Toggle navigation"> <span class="navbar-toggler-icon"></span> </button>
	      <div class="collapse navbar-collapse justify-content-center" id="navbar2SupportedContent">
	        <ul class="navbar-nav">
	          <li class="nav-item text-gray-dark">
	            <a class="nav-link text-white" href=<%=ParciaisCartolaKeys.INIT_ACTION_BACK%>><strong>Home</strong></a>
	          </li>
<!-- <!-- 	          <li class="nav-item"> -->
<%-- 	            <a class="nav-link text-white" href=<%=ParciaisCartolaKeys.LIGAS%>>Outras ligas</a> --%>
<!-- 	          </li> -->
	        </ul>
	      </div>
	    </div>
	  </nav>
	  <div class="pt-5 bg-opaque mask" id="speakers">
	    <div class="container-fluid" style="margin: 5em; margin-top: 0;">
	      <div class="row">
	        <div class="col-md-12">
	          <h1 class="mb-4 text-primary">Parciais</h1>
	        </div>
	      </div>
	      <div class="row">
	        <div class="col-md-6 col-6">
	          <a href=<%=ParciaisCartolaKeys.PARCIAIS_JOGADORES%>>
	            <img src="resourses/img/campeonato.png" class="center-block img-fluid my-3 rounded-circle" style="border-radius: 0; width: 300px;">
	            <h3 class="text-primary">Parciais dos jogadores<br></h3>
	          </a>
	        </div>
	        <div class="col-md-6 col-6 bg-primary">
	          <a href=<%=ParciaisCartolaKeys.PARCIAIS_TIME%>>
	            <img src="resourses/img/tabelinha-final.png" class="center-block img-fluid my-3 rounded-circle" style="border-radius: 0; width: 300px;">
	            <h3 class="text-white">Parciais do time<br></h3>
	          </a>
	        </div>
	        
	      </div>
	      <div class="row">
	        <div class="col-md-6 col-6 bg-primary">
	          <a href=<%=ParciaisCartolaKeys.DETALHE_UEV%>>
	            <img src="resourses/img/uev_logo.png" class="center-block img-fluid my-3 rounded-circle" style="border-radius: 0; width: 300px;">
	            <h3 class="text-white">Liga UEV<br></h3>
	            
	          </a>
	        </div>
	        <div class="col-md-6 col-6">
	          <a href=<%=ParciaisCartolaKeys.DETALHE_PRIMER_TURNO%>>
	            <img src="resourses/img/flamula_brahma.png" class="center-block img-fluid my-3 rounded-circle" style="border-radius: 0; width: 300px;">
	            <h3 class="text-primary">Liga primeiro turno<br></h3>
	          </a>
	        </div>
	      </div>
	    </div>
	  </div>
	  
	  <footer class="text-left bg-faded py-4" id="footer"></footer>
	  <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	  <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>
	  <script src="https://pingendo.com/assets/bootstrap/bootstrap-4.0.0-alpha.6.min.js"></script>
	  <script src="https://pingendo.com/assets/scripts/smooth-scroll.js"></script>
	  <script src="https://pingendo.com/assets/scripts/slidein-images.js"></script>
	
	
	</body>
</html>
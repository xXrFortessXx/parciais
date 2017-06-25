<%@page import="com.rfs.parciaiscartola.keys.ParciaisCartolaKeys"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1">
  		<meta name="description" content="Parciais Cartola - O site do Parciais Cartola é um desenvolvimento autônomo e tem como objetivo acompanhar a pontuação dos jogadores durante a rodada e também facilitar o acompanhamento da liga propria:'O Ultimo é Viado(UEV)'">
  		<meta name="keywords" content="cartola, cartolafc, parciais, parcial">
  		<meta name="author" content="Rodrigo Fortes Souza">
  		<meta name="reply-to" content="rodrigo.fortes.souza@hotmail.com">
  		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" type="text/css">
  		<link href="<c:url value="/resourses/css/theme.css" />" rel="stylesheet">
		<title>Bem vindo ao site Parciais Cartola</title>
	</head>
	<body>
		<div class="bg-primary text-center d-flex align-items-center w-100 h-100">
        	<div class="container">
            	<div class="row">
                	<div class="col-lg-10     offset-md-1">
                    	<h1 class="display-1 text-white text-center">PARCIAIS CARTOLA<br></h1>
                    	<c:choose>
							<c:when test = "${statusMercado eq '2'}">
							<p class="lead text-white text-center">Por favor, digite abaixo seu e-mail e senha do cartola para entrar.</p>
								<form action=<%=ParciaisCartolaKeys.INIT_ACTION%> method="post" class="text-justify mx-4" style="">
									<div class="form-group w-50">
										<input class="form-control" name="emailUsu" placeholder="Enter email" type="email">
									</div>
									<div class="form-group w-50">
										<input class="form-control" name="senhaUsu" placeholder="Password" type="password">
									</div>
									<input type="submit" class="btn btn-secondary" value="Login">
								</form>
								
<!-- 								<p class="lead text-white text-center">
									Vá para o site do cartola, escale seu time e volte durante a <br/>
									rodada para acompanhar suas ligas e as parciais dos jogadores.<br/>
								</p>
								<a class="btn btn-secondary" href=<%=ParciaisCartolaKeys.URL_CARTOLA_GLOBOESPORTE%>>ENTRE NO CARTOLA</a> -->
							</c:when>
							<c:otherwise>
								<p class="lead text-white text-center">Por favor, digite abaixo seu e-mail e senha do cartola para entrar.</p>
								<form action=<%=ParciaisCartolaKeys.INIT_ACTION%> method="post" class="text-justify mx-4" style="">
									<div class="form-group w-50">
										<input class="form-control" name="emailUsu" placeholder="Enter email" type="email">
									</div>
									<div class="form-group w-50">
										<input class="form-control" name="senhaUsu" placeholder="Password" type="password">
									</div>
									<input type="submit" class="btn btn-secondary" value="Login">
								</form>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</div>
		<div class="py-5 section">
        	<div class="container">
            	<div class="row">
                	<div class="col-md-6">
                    	<h2 class="text-sm-center text-primary">INTUITO DO SITE<br></h2>
                        <p class="text-justify my-1 h-100">
                        O site do Parciais Cartola é um desenvolvimento autônomo e tem como objetivo acompanhar a pontuação dos jogadores durante a rodada e também facilitar o acompanhamento da liga "O Ultimo é Viado(UEV)" e também as ligas de primeiro e segundo turnos.
						</p>
					</div>
					<div class="col-md-6">
                    	<h2 class="text-sm-center text-primary">HISTÓRICO<br></h2>
						<table class="table">
							<thead>
								<tr>
									<th>Ano<br></th>
									<th>Campeão<br></th>
									<th>Ultimo (Viado)<br></th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>2016<br></td>
									<td>Victory Express</td>
									<td>Lucas<br></td>
								</tr>
								<tr>
									<td>2015</td>
									<td>Beto Barreiro</td>
									<td>Jean<br></td>
								</tr>
								<tr>
									<td>2014<br></td>
									<td>Soberano M1to</td>
									<td><br></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
<!-- 		<div class="bg-faded py-5"> 
			<div class="container">
				<div class="row">
					<div class="text-left col-md-4">
					</div><div class="text-left col-md-4 bg-faded"></div>
					<div class="text-left col-md-4">
						<h1 class=" pi-item">Desenvolvedor<br></h1>
						<p>
							<strong>Rodrigo Fortes Souza</strong>
							<br>+55 11 98145-5750<br>rodrigo.fortes.souza@hotmail.com<br>
						</p>
					</div>
				</div>
			</div>
		</div>-->
		<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>
		<script src="https://pingendo.com/assets/bootstrap/bootstrap-4.0.0-alpha.6.min.js"></script>
	</body>
</html>
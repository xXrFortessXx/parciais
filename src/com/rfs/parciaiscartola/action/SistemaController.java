package com.rfs.parciaiscartola.action;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.enterprise.context.SessionScoped;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rfs.parciaiscartola.datatypes.DetalheLigaBean;
import com.rfs.parciaiscartola.datatypes.JogadorTD;
import com.rfs.parciaiscartola.datatypes.PontosTD;
import com.rfs.parciaiscartola.datatypes.RankingTD;
import com.rfs.parciaiscartola.datatypes.TimesBean;
import com.rfs.parciaiscartola.datatypes.UsuarioBean;
import com.rfs.parciaiscartola.datatypes.VariacaoTD;
import com.rfs.parciaiscartola.keys.ParciaisCartolaKeys;;

@Controller
public class SistemaController {
	@RequestMapping(ParciaisCartolaKeys.HOME_ACTION)
	public ModelAndView home() throws ParseException {
		String statusMercado = null;
		try {
			// Consultamos o servidor do cartola para saber se o mercado esta
			// aberto
			URL url = new URL(ParciaisCartolaKeys.URL_STATUS_MERCADO);
			URLConnection conn;
			conn = url.openConnection();

			BufferedReader buffer = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			// Passamos a saida json para uma string
			String linha = buffer.readLine();

			if (linha != null) {
				// Instanciamos um objeto jason
				JSONObject jsonObjeto = new JSONObject(linha);
				// Recuperamos o status do mercado ( 1-Mercado aberto | 2-Mercado Fechado
				statusMercado = jsonObjeto.get("status_mercado").toString().trim();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Criamos o objeto para guardar os dados na requisicao e indicamos a
		// jsp de retorno
		ModelAndView mv = new ModelAndView(ParciaisCartolaKeys.HOME_JSP);
		// populamos o objeto
		mv.addObject(ParciaisCartolaKeys.STATUS_MERCADO, statusMercado);
		// retornamos a jsp indicado com os dados na requisicao
		return mv;
	}
	@SessionScoped
	@RequestMapping(value = "/"+ParciaisCartolaKeys.INIT_ACTION, method = RequestMethod.POST)
	public ModelAndView iniParciais(@RequestParam(value = "emailUsu", required = false, defaultValue = "") String email,
			@RequestParam(value = "senhaUsu", required = false, defaultValue = "") String senha, HttpSession session) throws Exception {
		//Recuperamos o bean da sessao
		UsuarioBean beanUsuario = (UsuarioBean)session.getAttribute(ParciaisCartolaKeys.USUARIO_BEAN);
		// se o bean for null instanciamos o bean de usuario
		if(beanUsuario == null){
			beanUsuario = new UsuarioBean();
			// Instanciamos a string que vai receber o token de sessao
			String token = null;
			
			try {
				String json = ParciaisCartolaKeys.PRIMER_AUTENT + email
						+ ParciaisCartolaKeys.SEGUN_AUTENT + senha 
					+ ParciaisCartolaKeys.TERCE_AUTENT;
				String autenticado = autenticaUsu(ParciaisCartolaKeys.URL_AUTENTICACAO, json);
				
				// Instanciamos um objeto jason para recuperar o token de sessao
				JSONObject jsonObjeto = new JSONObject(autenticado);
				// Recuperamos o status do mercado ( 1-Mercado aberto |
				token = jsonObjeto.get("glbId").toString();
				
	    		// Setamos na bean o email e senha recuperados do request
	    		beanUsuario.setEmail(email);
	    		beanUsuario.setSenha(senha);
	    		beanUsuario.setTokenSession(token);
	    		
	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		session.setAttribute(ParciaisCartolaKeys.USUARIO_BEAN, beanUsuario);
		
		/* Criamos o objeto para guardar os dados na requisicao e indicamos a
		** jsp de retorno*/
		ModelAndView mv = new ModelAndView(ParciaisCartolaKeys.INIT_JSP);
		// populamos o objeto
		mv.addObject(ParciaisCartolaKeys.USUARIO_BEAN, beanUsuario);
		// retornamos a jsp indicado com os dados na requisicao
		return mv;
	}
	//Action de retorno que sera usada quando o usuario ja tiver a sessao e clicar em voltar
	@SessionScoped
	@RequestMapping(value = "/"+ParciaisCartolaKeys.INIT_ACTION_BACK, method = RequestMethod.GET)
	public ModelAndView iniParciaisBack(HttpSession session) throws Exception {
		//Recuperamos o bean da sessao
		UsuarioBean beanUsuario = (UsuarioBean)session.getAttribute(ParciaisCartolaKeys.USUARIO_BEAN);
		
		session.setAttribute(ParciaisCartolaKeys.USUARIO_BEAN, beanUsuario);
		
		/* Criamos o objeto para guardar os dados na requisicao e indicamos a
		** jsp de retorno*/
		ModelAndView mv = new ModelAndView(ParciaisCartolaKeys.INIT_JSP);
		// populamos o objeto
		mv.addObject(ParciaisCartolaKeys.USUARIO_BEAN, beanUsuario);
		// retornamos a jsp indicado com os dados na requisicao
		return mv;
	}
	
	@RequestMapping(value = "/"+ParciaisCartolaKeys.DETALHE_LIGAS, method = RequestMethod.GET)
	public ModelAndView outrasLigasDetalhe(HttpSession session, @RequestParam(value = "indexLiga", required = false, defaultValue = "") String indexLiga) throws Exception {
		// Recuperamos o bean da sessao
        UsuarioBean beanUsuario = (UsuarioBean)session.getAttribute(ParciaisCartolaKeys.USUARIO_BEAN);
        // Limpamos o index da liga para transformar em int
    	indexLiga = indexLiga.replace(",", ""); indexLiga = indexLiga.trim();
        
		try {
			// recuperamos o slug da liga selecionada
    		String slugLiga = beanUsuario.getLigas().get(Integer.parseInt(indexLiga)).getSlug();
			//Recuperamos os nomes das ligas do usuario em formato json
			String liga = buscaDadosGET(ParciaisCartolaKeys.URL_BUSCA_DETALHE_LIGAS+slugLiga, beanUsuario.getTokenSession().trim());
			
			//Convertemos a string de retorno para o formato UTF-8
			byte array[] = liga.getBytes("ISO-8859-1");
			liga = new String(array, "UTF-8");
			
			DetalheLigaBean ligaSelecionada = new DetalheLigaBean();
			
			// Instanciamos um objeto jason para recuperar as informacoes da liga
			JSONObject jsonObjetoPai = new JSONObject(liga);
			// Criamos uma lista com os times
			JSONArray jsonArray = jsonObjetoPai.getJSONArray("times");
			//Recuperamos as informacoes da liga
			jsonObjetoPai = jsonObjetoPai.getJSONObject("liga");
			//Setamos as informacoes da liga no bean da liga selecionada
			ligaSelecionada.setDescricao(jsonObjetoPai.getString("descricao"));
			ligaSelecionada.setFlamula(jsonObjetoPai.getString("url_flamula_png"));
			ligaSelecionada.setLigaID(jsonObjetoPai.getInt("liga_id"));
			ligaSelecionada.setNomeLiga(jsonObjetoPai.getString("nome"));
			ligaSelecionada.setSlug(jsonObjetoPai.getString("slug"));
			ligaSelecionada.setTimeDono(jsonObjetoPai.getInt("time_dono_id"));
			
			//Criamos uma lista de times para adicionar os dados da liga selecionada
			ArrayList<TimesBean> listTimes = new ArrayList<>(); 
			//Percorremos o retorno json com todas os times da liga selecionada
			for(int i=0; i<jsonArray.length(); i++){
				//Instanciamos um objeto de time para adicionar os dados de cada time da liga selecionada
				TimesBean timeObject = new TimesBean();
				//Criamos o jsonObject para recuperar cada posicao da lista
				JSONObject jsonObjeto = new JSONObject(jsonArray.getJSONObject(i).toString());
				//Setamos os dados de cada time
				timeObject.setEscudo(jsonObjeto.get("url_escudo_png").toString());
				timeObject.setNome(jsonObjeto.get("nome").toString());
				timeObject.setNomeCartola(jsonObjeto.get("nome_cartola").toString());
				timeObject.setPatrimonio(jsonObjeto.get("patrimonio").toString());
				//Instanciamos um jsonObject para recuperar os pontos de rodada/mes/turno/campeonato do time
				JSONObject jsonObjetoPontos = new JSONObject();
				jsonObjetoPontos = jsonObjeto.getJSONObject("pontos");
				timeObject.setPontos(new PontosTD());
				timeObject.getPontos().setCampeonato(jsonObjetoPontos.get("campeonato").toString());
				timeObject.getPontos().setMes(jsonObjetoPontos.get("mes").toString());
				timeObject.getPontos().setTurno(jsonObjetoPontos.get("turno").toString());
				timeObject.getPontos().setRodada(jsonObjetoPontos.get("rodada").toString());
				//Instanciamos um jsonObject para recuperar o ranking de rodada/mes/turno/campeonato do time
				JSONObject jsonObjetoRanking = new JSONObject();
				jsonObjetoRanking = jsonObjeto.getJSONObject("ranking");
				timeObject.setRanking(new RankingTD());
				timeObject.getRanking().setCampeonato(jsonObjetoRanking.get("campeonato").toString());
				timeObject.getRanking().setMes(jsonObjetoRanking.get("mes").toString());
				timeObject.getRanking().setTurno(jsonObjetoRanking.get("turno").toString());
				timeObject.getRanking().setRodada(jsonObjetoRanking.get("rodada").toString());
				timeObject.getRanking().setPatrimonio(jsonObjetoRanking.get("patrimonio").toString());
				//Instanciamos um jsonObject para recuperar os variacao de rodada/mes/turno/campeonato do time
				JSONObject jsonObjetoVariacao = new JSONObject();
				jsonObjetoVariacao = jsonObjeto.getJSONObject("variacao");
				timeObject.setVariacao(new VariacaoTD());
				timeObject.getVariacao().setCampeonato(jsonObjetoVariacao.get("campeonato").toString());
				timeObject.getVariacao().setMes(jsonObjetoVariacao.get("mes").toString());
				timeObject.getVariacao().setTurno(jsonObjetoVariacao.get("turno").toString());
				timeObject.getVariacao().setPatrimonio(jsonObjetoVariacao.get("patrimonio").toString());
				
				//Adicionamos o time a lista de times
				listTimes.add(timeObject);
			}
			//Setamos a lista de times no bean detalhes da liga
			ligaSelecionada.setTimes(listTimes);
			
			//Setamos a liga selecionada no bean do usuario
			beanUsuario.setLigaSelecionada(ligaSelecionada);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Setamos o bean do usuario na sessao
		session.setAttribute(ParciaisCartolaKeys.USUARIO_BEAN, beanUsuario);
		
		// Criamos o objeto para guardar os dados na requisicao e indicamos a
		// jsp de retorno
		ModelAndView mv = new ModelAndView(ParciaisCartolaKeys.LIGA_SELECIONADA_JSP);
		// populamos o objeto
		mv.addObject(ParciaisCartolaKeys.USUARIO_BEAN, beanUsuario);
		// retornamos a jsp indicado com os dados na requisicao
		return mv;
	}
	
	@RequestMapping(value = "/"+ParciaisCartolaKeys.PARCIAIS_JOGADORES, method = RequestMethod.GET)
	public ModelAndView parciaisJogadores(HttpSession session) throws Exception {
		// Recuperamos o bean da sessao
        UsuarioBean beanUsuario = (UsuarioBean)session.getAttribute(ParciaisCartolaKeys.USUARIO_BEAN);
		
    	try {
    		//Metodo que retorna a parcial dos jogadores
    		beanUsuario.setJogadores(recuperaParciaisJogadoresRodadaAtu());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	//Setamos o bean do usuario na sessao
		session.setAttribute(ParciaisCartolaKeys.USUARIO_BEAN, beanUsuario);
		
		// Criamos o objeto para guardar os dados na requisicao e indicamos a
		// jsp de retorno
		ModelAndView mv = new ModelAndView(ParciaisCartolaKeys.PARCIAIS_JOGADORES_JSP);
		// populamos o objeto
		mv.addObject(ParciaisCartolaKeys.USUARIO_BEAN, beanUsuario);
		// retornamos a jsp indicado com os dados na requisicao
		return mv;
	}
	
	@SessionScoped
	@RequestMapping(value = "/"+ParciaisCartolaKeys.PARCIAIS_TIME, method = RequestMethod.GET)
	public ModelAndView parciaisJTime(HttpSession session) throws Exception {
		//Recuperamos o bean da sessao
		UsuarioBean beanUsuario = (UsuarioBean)session.getAttribute(ParciaisCartolaKeys.USUARIO_BEAN);
		
		try {
			//Setamos o time do usuario no bean do usuario
			beanUsuario.setTimeUsuario(recuperaTimeUsuario(beanUsuario.getTokenSession()));
			
			//Setamos todos os jogadores e suas pontuacoes parciais no bean do usuario
			beanUsuario.setJogadores(recuperaParciaisJogadoresRodadaAtu());
			
			//Adicionamos as pontuacoes ao time do usuario logado
			for(int i=0; i<beanUsuario.getTimeUsuario().size(); i++){
				int count = 0;
				int idJogador = beanUsuario.getTimeUsuario().get(i).getAtletaId();
				for(int j=0; j<beanUsuario.getJogadores().size(); j++){
					if(idJogador==beanUsuario.getJogadores().get(j).getAtletaId()){
						beanUsuario.getTimeUsuario().get(i).setPontuacao(beanUsuario.getJogadores().get(j).getPontuacao());
						count++;
						break;
					}
				}
				if(count>11){
					break;
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Setamos o bean do usuario na sessao
		session.setAttribute(ParciaisCartolaKeys.USUARIO_BEAN, beanUsuario);
		
		/* Criamos o objeto para guardar os dados na requisicao e indicamos a
		** jsp de retorno*/
		ModelAndView mv = new ModelAndView(ParciaisCartolaKeys.PARCIAIS_TIME_JSP);
		// populamos o objeto
		mv.addObject(ParciaisCartolaKeys.USUARIO_BEAN, beanUsuario);
		// retornamos a jsp indicado com os dados na requisicao
		return mv;
	}
	
	@RequestMapping(value = "/"+ParciaisCartolaKeys.DETALHE_UEV, method = RequestMethod.GET)
	public ModelAndView ligasUEV(HttpSession session) throws Exception {
		// Recuperamos o bean da sessao
        UsuarioBean beanUsuario = (UsuarioBean)session.getAttribute(ParciaisCartolaKeys.USUARIO_BEAN);
        
		try {
			//Setamos a liga selecionada no bean do usuario
			beanUsuario.setLigaSelecionada(recuperaDetalheLiga(beanUsuario.getTokenSession().trim(),ParciaisCartolaKeys.SLUG_UEV));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Setamos o bean do usuario na sessao
		session.setAttribute(ParciaisCartolaKeys.USUARIO_BEAN, beanUsuario);
		
		// Criamos o objeto para guardar os dados na requisicao e indicamos a
		// jsp de retorno
		ModelAndView mv = new ModelAndView(ParciaisCartolaKeys.LIGA_SELECIONADA_JSP);
		// populamos o objeto
		mv.addObject(ParciaisCartolaKeys.USUARIO_BEAN, beanUsuario);
		// retornamos a jsp indicado com os dados na requisicao
		return mv;
	}
	
	@RequestMapping(value = "/"+ParciaisCartolaKeys.DETALHE_PRIMER_TURNO, method = RequestMethod.GET)
	public ModelAndView primerTurno(HttpSession session) throws Exception {
		// Recuperamos o bean da sessao
        UsuarioBean beanUsuario = (UsuarioBean)session.getAttribute(ParciaisCartolaKeys.USUARIO_BEAN);
        
		try {
			//Setamos a liga selecionada no bean do usuario
			beanUsuario.setLigaSelecionada(recuperaDetalheLiga(beanUsuario.getTokenSession().trim(),ParciaisCartolaKeys.SLUG_UEV));
			
			//Removemos os times que nao sao do primeiro turno
			beanUsuario.setLigaSelecionada(removeTime(beanUsuario.getLigaSelecionada()));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Setamos o bean do usuario na sessao
		session.setAttribute(ParciaisCartolaKeys.USUARIO_BEAN, beanUsuario);
		
		// Criamos o objeto para guardar os dados na requisicao e indicamos a
		// jsp de retorno
		ModelAndView mv = new ModelAndView(ParciaisCartolaKeys.DETALHE_PRIMER_TURNO_JSP);
		// populamos o objeto
		mv.addObject(ParciaisCartolaKeys.USUARIO_BEAN, beanUsuario);
		// retornamos a jsp indicado com os dados na requisicao
		return mv;
	}

	/* Metodos */
	// Metodo de autenticacao no site do globo esporte
	public String autenticaUsu(String url, String json) throws Exception {

		try {
			// Cria um objeto HttpURLConnection:
			HttpURLConnection request = (HttpURLConnection) new URL(url).openConnection();

			try {
				// Define que a conexão pode enviar informações e obtê-las de
				// volta:
				request.setDoOutput(true);
				request.setDoInput(true);

				// Define o content-type:
				request.setRequestProperty("Content-Type", "application/json");

				// Define o método da requisição:
				request.setRequestMethod("POST");

				// Conecta na URL:
				request.connect();

				// Escreve o objeto JSON usando o OutputStream da requisição:
				try (OutputStream outputStream = request.getOutputStream()) {
					outputStream.write(json.getBytes("UTF-8"));
				}

				// Caso você queira usar o código HTTP para fazer alguma coisa,
				// descomente esta linha.
				// int response = request.getResponseCode();

				return readResponse(request);
			} finally {
				request.disconnect();
			}
		} catch (IOException ex) {
			throw new MinhaException(ex);
		}
	}
	
	//Metodo que formata a liga UEV para deixar como primeiro turno
	private DetalheLigaBean removeTime(DetalheLigaBean ligaSelecionada) {
		
		ArrayList<TimesBean> listaParalela = new ArrayList<>(); 
		
		for(int i=0; i<ligaSelecionada.getTimes().size();i++){
			if(ligaSelecionada.getTimes().get(i).getNome().equals("Sport Clube Heisenberg")){
				listaParalela.add(ligaSelecionada.getTimes().get(i));
			}
			else if(ligaSelecionada.getTimes().get(i).getNome().equals("Disfunção Erétil FC")){
				listaParalela.add(ligaSelecionada.getTimes().get(i));
			}
			else if(ligaSelecionada.getTimes().get(i).getNome().equals("Mutantes do Futebol")){
				listaParalela.add(ligaSelecionada.getTimes().get(i));
			}
			else if(ligaSelecionada.getTimes().get(i).getNome().equals("Soberano M1TO")){
				listaParalela.add(ligaSelecionada.getTimes().get(i));
			}
			else if(ligaSelecionada.getTimes().get(i).getNome().equals("Murilo ms fc")){
				listaParalela.add(ligaSelecionada.getTimes().get(i));
			}
			else if(ligaSelecionada.getTimes().get(i).getNome().equals("Victory Express  ")){
				listaParalela.add(ligaSelecionada.getTimes().get(i));
			}
			else if(ligaSelecionada.getTimes().get(i).getNome().equals("EC Mambha Negra2")){
				listaParalela.add(ligaSelecionada.getTimes().get(i));
			}
			else if(ligaSelecionada.getTimes().get(i).getNome().equals("GJ FUTEBOL CLUBE")){
				listaParalela.add(ligaSelecionada.getTimes().get(i));
			}
			else if(ligaSelecionada.getTimes().get(i).getNome().equals("TricampeãoMundial")){
				listaParalela.add(ligaSelecionada.getTimes().get(i));
			}
			else if(ligaSelecionada.getTimes().get(i).getNome().equals("Beto Barreiro")){
				listaParalela.add(ligaSelecionada.getTimes().get(i));
			}
			else if(ligaSelecionada.getTimes().get(i).getNome().equals("Ursolinos Club")){
				listaParalela.add(ligaSelecionada.getTimes().get(i));
			}
			else if(ligaSelecionada.getTimes().get(i).getNome().equals("Racha bago F.C")){
				listaParalela.add(ligaSelecionada.getTimes().get(i));
			}
		}
		ligaSelecionada.setTimes(listaParalela);
		
		//refazemos as posicoes dos times na liga do primeiro turno e limitamos a pontuacao em duas casas apos a virgula
		for(int i=0; i<ligaSelecionada.getTimes().size();i++){
			BigDecimal b = new BigDecimal(ligaSelecionada.getTimes().get(i).getPontos().getCampeonato());
			b = b.setScale(2, BigDecimal.ROUND_DOWN);
			ligaSelecionada.getTimes().get(i).getPontos().setCampeonato(b.toString());
			ligaSelecionada.getTimes().get(i).setPosicaoTurno(i+1);
		}
		
		return ligaSelecionada;
	}
	
	//Metodo que recupera o detalhe da liga informada
	private DetalheLigaBean recuperaDetalheLiga(String token, String slugLiga) throws Exception {
		//Recuperamos a liga UEV
		String liga = buscaDadosGET(ParciaisCartolaKeys.URL_DETALHE_LIGA+slugLiga, token);
		
		//Convertemos a string de retorno para o formato UTF-8
		byte array[] = liga.getBytes("ISO-8859-1");
		liga = new String(array, "UTF-8");
		
		DetalheLigaBean ligaSelecionada = new DetalheLigaBean();
		
		// Instanciamos um objeto jason para recuperar as informacoes da liga
		JSONObject jsonObjetoPai = new JSONObject(liga);
		// Criamos uma lista com os times
		JSONArray jsonArray = jsonObjetoPai.getJSONArray("times");
		//Recuperamos as informacoes da liga
		jsonObjetoPai = jsonObjetoPai.getJSONObject("liga");
		//Setamos as informacoes da liga no bean da liga selecionada
		ligaSelecionada.setDescricao(jsonObjetoPai.getString("descricao"));
		ligaSelecionada.setFlamula(jsonObjetoPai.getString("url_flamula_png"));
		ligaSelecionada.setLigaID(jsonObjetoPai.getInt("liga_id"));
		ligaSelecionada.setNomeLiga(jsonObjetoPai.getString("nome"));
		ligaSelecionada.setSlug(jsonObjetoPai.getString("slug"));
		ligaSelecionada.setTimeDono(jsonObjetoPai.getInt("time_dono_id"));
		
		//Criamos uma lista de times para adicionar os dados da liga selecionada
		ArrayList<TimesBean> listTimes = new ArrayList<>(); 
		//Percorremos o retorno json com todas os times da liga selecionada
		for(int i=0; i<jsonArray.length(); i++){
			//Instanciamos um objeto de time para adicionar os dados de cada time da liga selecionada
			TimesBean timeObject = new TimesBean();
			//Criamos o jsonObject para recuperar cada posicao da lista
			JSONObject jsonObjeto = new JSONObject(jsonArray.getJSONObject(i).toString());
			//Setamos os dados de cada time
			timeObject.setEscudo(jsonObjeto.get("url_escudo_png").toString());
			timeObject.setNome(jsonObjeto.get("nome").toString());
			timeObject.setNomeCartola(jsonObjeto.get("nome_cartola").toString());
			timeObject.setPatrimonio(jsonObjeto.get("patrimonio").toString());
			//Instanciamos um jsonObject para recuperar os pontos de rodada/mes/turno/campeonato do time
			JSONObject jsonObjetoPontos = new JSONObject();
			jsonObjetoPontos = jsonObjeto.getJSONObject("pontos");
			timeObject.setPontos(new PontosTD());
			timeObject.getPontos().setCampeonato(jsonObjetoPontos.get("campeonato").toString());
			timeObject.getPontos().setMes(jsonObjetoPontos.get("mes").toString());
			timeObject.getPontos().setTurno(jsonObjetoPontos.get("turno").toString());
			timeObject.getPontos().setRodada(jsonObjetoPontos.get("rodada").toString());
			//Instanciamos um jsonObject para recuperar o ranking de rodada/mes/turno/campeonato do time
			JSONObject jsonObjetoRanking = new JSONObject();
			jsonObjetoRanking = jsonObjeto.getJSONObject("ranking");
			timeObject.setRanking(new RankingTD());
			timeObject.getRanking().setCampeonato(jsonObjetoRanking.get("campeonato").toString());
			timeObject.getRanking().setMes(jsonObjetoRanking.get("mes").toString());
			timeObject.getRanking().setTurno(jsonObjetoRanking.get("turno").toString());
			timeObject.getRanking().setRodada(jsonObjetoRanking.get("rodada").toString());
			timeObject.getRanking().setPatrimonio(jsonObjetoRanking.get("patrimonio").toString());
			//Instanciamos um jsonObject para recuperar os variacao de rodada/mes/turno/campeonato do time
			JSONObject jsonObjetoVariacao = new JSONObject();
			jsonObjetoVariacao = jsonObjeto.getJSONObject("variacao");
			timeObject.setVariacao(new VariacaoTD());
			timeObject.getVariacao().setCampeonato(jsonObjetoVariacao.get("campeonato").toString());
			timeObject.getVariacao().setMes(jsonObjetoVariacao.get("mes").toString());
			timeObject.getVariacao().setTurno(jsonObjetoVariacao.get("turno").toString());
			timeObject.getVariacao().setPatrimonio(jsonObjetoVariacao.get("patrimonio").toString());
			
			//Adicionamos o time a lista de times
			listTimes.add(timeObject);
		}
		
		recuperaParcial(listTimes);
		//Setamos a lista de times no bean detalhes da liga
		ligaSelecionada.setTimes(listTimes);
		
		//Formatamos o campo de pontuacao
		for(int i=0; i<ligaSelecionada.getTimes().size();i++){
			BigDecimal b = new BigDecimal(ligaSelecionada.getTimes().get(i).getPontos().getCampeonato());
			b = b.setScale(2, BigDecimal.ROUND_DOWN);
			ligaSelecionada.getTimes().get(i).getPontos().setCampeonato(b.toString());
		}
		
		return ligaSelecionada;
	}
	
	public String buscaDadosGET(String url, String token) throws Exception {

		try {
			// Cria um objeto HttpURLConnection:
			HttpURLConnection request = (HttpURLConnection) new URL(url).openConnection();

			try {
				// Define que a conexão pode enviar informações e obtê-las de
				// volta:
				request.setDoOutput(true);
				request.setDoInput(true);

				// Define o content-type:
				request.setRequestProperty("Content-Type", "application/json");
				request.setRequestProperty("X-GLB-Token", token);

				// Define o método da requisição:
				request.setRequestMethod("GET");

				// Conecta na URL:
				request.connect();

				return readResponse(request);
			} finally {
				request.disconnect();
			}
		} catch (IOException ex) {
			throw new MinhaException(ex);
		}
	}

	private String readResponse(HttpURLConnection request) throws IOException {
		ByteArrayOutputStream os;
		try (InputStream is = request.getInputStream()) {
			os = new ByteArrayOutputStream();
			int b;
			while ((b = is.read()) != -1) {
				os.write(b);
			}
		}
		return new String(os.toByteArray());
	}
	private void recuperaParcial(ArrayList<TimesBean> listTimes) throws IOException {
		
		ArrayList<JogadorTD> jogadores = recuperaParciaisJogadoresRodadaAtu();
		
		for(int i=0; i< listTimes.size(); i++){
		}
	}

	private ArrayList<JogadorTD> recuperaParciaisJogadoresRodadaAtu() throws IOException {
		//Recuperamos a pontuacao dos jogadores na rodada
		ArrayList<JogadorTD> listaJogadores = new ArrayList<>(); 
		URL url = new URL(ParciaisCartolaKeys.URL_PARCIAIS_JOGADORES);
		URLConnection conn;
		conn = url.openConnection();

		BufferedReader buffer = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		// Passamos a saida json para uma string
		String parciaisJogadores = buffer.readLine();

		if (parciaisJogadores != null) {
			// Instanciamos um objeto jason
			JSONObject jsonObjeto = new JSONObject(parciaisJogadores);
			jsonObjeto = jsonObjeto.getJSONObject("atletas");
			String limpaChar = jsonObjeto.toString();
			
			//Convertemos a string de retorno para o formato UTF-8
			byte array[] = limpaChar.getBytes("ISO-8859-1");
			limpaChar = new String(array, "UTF-8");
			//Convertemos novamente para json
			JSONObject jsonObjetoLimpo = new JSONObject(limpaChar);
			
			JSONArray varNames = jsonObjetoLimpo.names();
			JSONArray listaDeJogadores = jsonObjetoLimpo.toJSONArray(varNames);
			// Recuperamos a lista de jogadores
			for(int i=0; i<listaDeJogadores.length(); i++){
				JogadorTD jogador = new JogadorTD();
				jsonObjeto = listaDeJogadores.getJSONObject(i);
				jogador.setApelido(jsonObjeto.getString("apelido"));
				jogador.setFoto(jsonObjeto.getString("foto").replace("FORMATO", "140x140"));
				jogador.setPontuacao(jsonObjeto.getDouble("pontuacao"));
				jogador.setAtletaId(varNames.getInt(i));
				
				/*abaixo comeca o trexo de ordenacao da lista de jogadores pela pontuacao*/
				if(i==0){
					listaJogadores.add(jogador);
				}
				for(int j=0; j<listaJogadores.size();j++){
					boolean add = false;
					if(jogador.getPontuacao()>listaJogadores.get(j).getPontuacao()){
						listaJogadores.add(j, jogador);
						add = true;
						break;
					}
					if(!add && j==listaJogadores.size()-1 && i>0){
						listaJogadores.add(jogador);
						break;
					}
				}
			}
		}
		return listaJogadores;
	}
	//Metodo que recupera os jogadores do time do usuario logado
	public ArrayList<JogadorTD> recuperaTimeUsuario(String token) throws Exception{
		//Recuperamos o time do usuario logado
		String timeUsuario = buscaDadosGET(ParciaisCartolaKeys.URL_BUSCA_TIME_USUARIO, token);
		//Convertemos a string de retorno para o formato UTF-8
		byte array[] = timeUsuario.getBytes("ISO-8859-1");
		timeUsuario = new String(array, "UTF-8");
		//Transformamos o time do usuario em um objeto json
		JSONObject jsonObjeto = new JSONObject(timeUsuario);
		//Transformamos a lista de atletas em um array json
		JSONArray listaDeJogadores =  jsonObjeto.getJSONArray("atletas");
		//Criamos uma lista Java de jogadores para adicionar os jogadores do time e depois setar na bean de usuario
		ArrayList<JogadorTD> listaDeJogadoresJava = new ArrayList<>();
		//Setamos as informacoes dos jogadores na lista java
		for(int i=0; i<listaDeJogadores.length(); i++){
			JogadorTD obj = new JogadorTD();
			jsonObjeto = listaDeJogadores.getJSONObject(i);
			obj.setApelido(jsonObjeto.getString("apelido"));
			obj.setFoto(jsonObjeto.getString("foto").replace("FORMATO", "140x140"));
			obj.setAtletaId(jsonObjeto.getInt("atleta_id"));
			obj.setClubeId(jsonObjeto.getInt("clube_id"));
			obj.setPosicaoId(jsonObjeto.getInt("posicao_id"));
			
			listaDeJogadoresJava.add(obj);
		}
		//Ordenamos a lista java de jogadores por posicao
		for(int i=0; i<listaDeJogadoresJava.size(); i++){
			for(int j=i+1; j<listaDeJogadoresJava.size(); j++){
				double posI = listaDeJogadoresJava.get(i).getPosicaoId();
				double posJ = listaDeJogadoresJava.get(j).getPosicaoId();
				if(posJ < posI){
					listaDeJogadoresJava.add(i, listaDeJogadoresJava.get(j));
					listaDeJogadoresJava.remove(j+1);
				}
			}
		}
		return listaDeJogadoresJava;
	}

	public static class MinhaException extends Exception {
		private static final long serialVersionUID = 1L;

		public MinhaException(Throwable cause) {
			super(cause);
		}
	}
}

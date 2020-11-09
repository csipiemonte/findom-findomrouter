/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.servizifindomweb.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import it.csi.findom.findomrouter.business.servizifindomweb.mapper.AggrDataDtoRowMapper;
import it.csi.findom.findomrouter.business.servizifindomweb.mapper.AmministratoriDtoRowMapper;
import it.csi.findom.findomrouter.business.servizifindomweb.mapper.FileDtoRowMapper;
import it.csi.findom.findomrouter.business.servizifindomweb.mapper.FormeGiuridicheDtoRowMapper;
import it.csi.findom.findomrouter.business.servizifindomweb.mapper.ImpresaEnteRowMapper;
import it.csi.findom.findomrouter.business.servizifindomweb.mapper.NumberMaxDtoRowMapper;
import it.csi.findom.findomrouter.business.servizifindomweb.mapper.ParametriRegolaRowMapper;
import it.csi.findom.findomrouter.business.servizifindomweb.mapper.ProssimoSportelloAttivoDtoRowMapper;
import it.csi.findom.findomrouter.business.servizifindomweb.mapper.RegolaRowMapper;
import it.csi.findom.findomrouter.business.servizifindomweb.mapper.ShellSoggettiDtoRowMapper;
import it.csi.findom.findomrouter.business.servizifindomweb.mapper.StatoDomandaDtoRowMapper;
import it.csi.findom.findomrouter.business.servizifindomweb.mapper.StatoEsteroDtoRowMapper;
import it.csi.findom.findomrouter.business.servizifindomweb.mapper.TipolBeneficiariDtoRowMapper;
import it.csi.findom.findomrouter.business.servizifindomweb.mapper.VistaDomandeBeneficiariDtoRowMapper;
import it.csi.findom.findomrouter.business.servizifindomweb.mapper.VistaDomandeDtoRowMapper;
import it.csi.findom.findomrouter.business.servizifindomweb.mapper.VistaSportelliAttiviDtoRowMapper;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.AggrDataDto;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.AmministratoriDto;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.FormeGiuridicheDto;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.NumberMaxDto;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.ProssimoSportelloAttivoDto;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.ShellSoggettiDto;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.StatoDomandaDto;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.StatoEsteroDto;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.TipolBeneficiariDto;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.VistaDomandeBeneficiariDto;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.VistaDomandeDto;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.VistaSportelliAttiviDto;
import it.csi.findom.findomrouter.exception.ServiziFindomWebException;
//import it.csi.findom.findomrouter.integration.extservices.index.IndexDAO;
import it.csi.findom.findomrouter.presentation.util.Constants;
import it.csi.findom.findomrouter.presentation.util.StringUtils;
import it.csi.findom.findomrouter.presentation.vo.ImpresaEnte;
import it.csi.findom.findomrules.dto.ParametriRegola;
import it.csi.findom.findomrules.dto.Regola;

public class ServiziFindomWebDaoImpl implements ServiziFindomWebDao {

	protected static final Logger LOGGER = Logger.getLogger(Constants.APPLICATION_CODE + ".business.servizifindomweb.dao");
	private static final String CLASS_NAME = "ServiziFindomWebDaoImpl";
	private NamedParameterJdbcTemplate template;
//	private IndexDAO indexDAO; //MB2017_04_24

	public ServiziFindomWebDaoImpl() throws ServiziFindomWebException {
	}
	
		
	@Override
	public ArrayList<StatoDomandaDto> getStatiDomanda() throws ServiziFindomWebException {
		
		final String methodName = "getStatiDomanda";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN");
		
		ArrayList<StatoDomandaDto> listaStati = new ArrayList<StatoDomandaDto>();
		
		MapSqlParameterSource params = new MapSqlParameterSource();

		try {
			String query = ServiziFindomWebDaoUtil.getSelectStatements().getProperty("SELECT_STATI_DOMANDA");
			LOGGER.debug(logprefix + " Query == " + query);

			listaStati = (ArrayList<StatoDomandaDto>) template.query(query, params, new StatoDomandaDtoRowMapper());

		} catch (Exception e) {
			LOGGER.error(logprefix + " Errore occorso durante l'esecuzione del metodo:" + e, e);

			throw new ServiziFindomWebException("Exception while execute query getStatiDomanda", e);
		} finally {
			LOGGER.debug(logprefix + " - END");
		}

		return listaStati;
	}

	/**
	 *  String codApplicativo : <codice prodotto>_<codice linea cliente>_<codice ambiente>_<codice Unita' di Installazione>
	 *  String ip : ip della pdl di chi accede
	 *  String utente :  Identificativo univoco dell'utente che ha effettuato l'operazione
	 *  String tipoOperazione : login / logout / read / insert / update / delete / merge
	 *  String descrOperazione : descrizione dell'operazione (max 150 char)
	 *  String chiaveOperazione : ulteriori informazioni sull'operazione (max 500 char)
	 *  
	 */
	@Override
	public int insertLogAudit(String codApplicativo, String ip, String utente ,String tipoOperazione ,String descrOperazione, String chiaveOperazione) 
			throws ServiziFindomWebException { 
		
		final String methodName = "insertLogAudit";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN");
		
		int ris = 0;
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		String query = "";
		
		try {
			query = ServiziFindomWebDaoUtil.getSelectStatements().getProperty("INSERT_LOGAUDIT");
			LOGGER.debug(logprefix + " Query == " + query);
			
			params.addValue("idApp", codApplicativo , java.sql.Types.VARCHAR);
			params.addValue("ipAddress", ip, java.sql.Types.VARCHAR);
			params.addValue("utente", utente , java.sql.Types.VARCHAR);
			params.addValue("operazione", tipoOperazione, java.sql.Types.VARCHAR);
			params.addValue("oggOper", descrOperazione, java.sql.Types.VARCHAR);
			params.addValue("keyOper", chiaveOperazione, java.sql.Types.VARCHAR);
			
			ris = template.update(query, params);
			LOGGER.debug(logprefix + " inserita ["+ris+"] entry");
			
		} catch (Exception e) {
			
			LOGGER.error(logprefix + " - Exception : Errore occorso durante l'esecuzione del metodo:" + e, e);
			throw new ServiziFindomWebException("Exception while execute query insertLogAudit", e);
			
		} finally {
			LOGGER.debug(logprefix + " END");
		}
		return ris;
	}

	@Override
	public ShellSoggettiDto getDatiSoggettoByCodiceFiscale(String cf)
			throws ServiziFindomWebException {
		final String methodName = "getDatiSoggettoByCodiceFiscale";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN");
		
		ShellSoggettiDto soggetto = null; 
		ArrayList<ShellSoggettiDto> listaDS = null;
		
		MapSqlParameterSource params = new MapSqlParameterSource();

		try {
			String query = ServiziFindomWebDaoUtil.getSelectStatements().getProperty("SELECT_SHELL_SOGGETTI_BYCF");
			LOGGER.debug(logprefix + " cf == " + cf);
			LOGGER.debug(logprefix + " Query == " + query);

			params.addValue("cfUtente", cf , java.sql.Types.VARCHAR);
			
			listaDS = ((ArrayList<ShellSoggettiDto>) template.query(query, params, new ShellSoggettiDtoRowMapper()));
			
			if(listaDS!=null && listaDS.size()>0){
				soggetto = listaDS.get(0);
				LOGGER.debug(logprefix + " trovato un soggetto");
			}
			
		} catch (Exception e) {
			LOGGER.error(logprefix + " Errore occorso durante l'esecuzione del metodo:" + e, e);

			throw new ServiziFindomWebException("Exception while execute query getDatiSoggettoByCodiceFiscale", e);
		} finally {
			LOGGER.debug(logprefix + " - END");
		}
		
		return soggetto;
	} 
	
	@Override
	public ArrayList<ShellSoggettiDto> getDatiSoggettoByIdSoggetto(ArrayList<String> listaId) throws ServiziFindomWebException {
		final String methodName = "getDatiSoggettoByIdSoggetto";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN");
		
		ArrayList<ShellSoggettiDto> listaDS = new ArrayList<ShellSoggettiDto>();
		
		if(listaId != null && listaId.size()>0){
			
			MapSqlParameterSource params = new MapSqlParameterSource();
	
			// costruisco la condizione (x, y, z)
			String condizione = "(";
			
			int j = 0;
			for (Iterator itr = listaId.iterator(); itr.hasNext();) {
				String val = (String) itr.next();
				if(j==0){
					// solo per il primo elemento non metto la virgola
					condizione += val;
				}else{
					condizione += " ,"+val;
				}
				j++;
			}
			
			condizione += ")";
			LOGGER.debug(logprefix + " condizione="+condizione);
			
			try {
				String query = ServiziFindomWebDaoUtil.getSelectStatements().getProperty("SELECT_SHELL_SOGGETTI_BYID");
				LOGGER.debug(logprefix + " Query == " + query);
	
				String qu = query.replace("#condizione#", condizione);
				LOGGER.debug(logprefix + " Query == " + qu);
				
				listaDS = ((ArrayList<ShellSoggettiDto>) template.query(qu, params, new ShellSoggettiDtoRowMapper()));
	
			} catch (Exception e) {
				LOGGER.error(logprefix + " Errore occorso durante l'esecuzione del metodo:" + e, e);
	
				throw new ServiziFindomWebException("Exception while execute query getDatiSoggettoByIdSoggetto", e);
			} finally {
				LOGGER.debug(logprefix + " - END");
			}
		}
		return listaDS;
	}
	
	@Override
	public ArrayList<VistaDomandeDto> getVistaDomanda(Integer idSoggettoCreatore, Integer idSoggettoBeneficiario, Integer idNormativa, 
			Integer idBando, Integer idDomanda, Integer idAreaTematica)	
					throws ServiziFindomWebException {
		final String methodName = "getVistaDomanda";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN");
		LOGGER.debug(logprefix + " idSoggettoCreatore=["+idSoggettoCreatore+"]");
		LOGGER.debug(logprefix + " idSoggettoBeneficiario=["+idSoggettoBeneficiario+"]");
		LOGGER.debug(logprefix + " idNormativa=["+idNormativa+"]");
		LOGGER.debug(logprefix + " idBando=["+idBando+"]");
		LOGGER.debug(logprefix + " idDomanda=["+idDomanda+"]");
		LOGGER.debug(logprefix + " idAreaTematica=["+idAreaTematica+"]");
		
		ArrayList<VistaDomandeDto> listaDomande = new ArrayList<VistaDomandeDto>();
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		try {
			String query = ServiziFindomWebDaoUtil.getSelectStatements().getProperty("SELECT_VISTA_DOMANDE_BY_FILTRO_SEARCH");
//			LOGGER.debug(logprefix + " Query == " + query);
			
			String condizione = "";
			
//			String condizione = " id_soggetto_beneficiario = "+idSoggettoBeneficiario; 
			if(idSoggettoBeneficiario!=null) condizione += " AND id_soggetto_beneficiario = " + idSoggettoBeneficiario;
			
			if(idSoggettoCreatore!=null) condizione += " AND id_soggetto_creatore = " + idSoggettoCreatore;
			
			if(idNormativa!=null) condizione += " AND id_normativa = " + idNormativa;
			
			if(idBando!=null) condizione += " AND id_bando = " + idBando;
			
			if(idDomanda!=null) condizione += " AND id_domanda = " + idDomanda;
			
			if(idAreaTematica!=null && idAreaTematica > 0) condizione += " AND id_area_tematica = " + idAreaTematica;
			
			if(condizione!=null && condizione.startsWith(" AND")) {
				condizione = condizione.replaceFirst(" AND", "");
			}
			
//			if(StringUtils.isBlank(condizione)) {
//				LOGGER.error(logprefix + "condizione nulla, non eseguo la query");
//			}else {
				String q = query.replace("#condizione#", condizione);
				LOGGER.debug(logprefix + " Query == " + q);
				
				listaDomande = (ArrayList<VistaDomandeDto>) template.query(q, params, new VistaDomandeDtoRowMapper());
//			}
		} catch (Exception e) {
			LOGGER.error(logprefix + " Errore occorso durante l'esecuzione del metodo:" + e, e);
			throw new ServiziFindomWebException("Exception while execute query getVistaDomanda", e);
		} finally {
			LOGGER.debug(logprefix + " - END");
		}
		
		return listaDomande;
	}

	@Override
	public ArrayList<FormeGiuridicheDto> getFormeGiuridiche() throws ServiziFindomWebException {
		final String methodName = "getFormeGiuridiche";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN");
		
		ArrayList<FormeGiuridicheDto> listaFG = new ArrayList<FormeGiuridicheDto>();
		MapSqlParameterSource params = new MapSqlParameterSource();

		try {
			String query = ServiziFindomWebDaoUtil.getSelectStatements().getProperty("SELECT_FORME_GIURIDICHE");
			LOGGER.debug(logprefix + " Query == " + query);

			listaFG = (ArrayList<FormeGiuridicheDto>) template.query(query, params, new FormeGiuridicheDtoRowMapper());

		} catch (Exception e) {
			LOGGER.error(logprefix + " Errore occorso durante l'esecuzione del metodo:" + e, e);
			throw new ServiziFindomWebException("Exception while execute query getFormeGiuridiche", e);
		} finally {
			LOGGER.debug(logprefix + " - END");
		}
		
		return listaFG;
	}

	@Override
	public AmministratoriDto getAmministratoreByCodiceFiscale(String codFisc)
			throws ServiziFindomWebException {
		final String methodName = "getAmministratoreByCodiceFiscale";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN");
		
		ArrayList<AmministratoriDto> listaAmministratori = new ArrayList<AmministratoriDto>();
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		AmministratoriDto amministratore = null;
		
		try {
			String query = ServiziFindomWebDaoUtil.getSelectStatements().getProperty("SELECT_FINDOM_AMMINISTRATORI_BY_COD_FISCALE");
			LOGGER.debug(logprefix + " Query == " + query);
		
			params.addValue("codiceFiscale", codFisc , java.sql.Types.VARCHAR);
			
			listaAmministratori = (ArrayList<AmministratoriDto>) template.query(query, params, new AmministratoriDtoRowMapper());
			
			if(listaAmministratori!=null && listaAmministratori.size()>0){
				LOGGER.debug(logprefix + " trovata una lista di dimensione " + listaAmministratori.size());
				amministratore = listaAmministratori.get(0);
			}
			
		} catch (Exception e) {
			LOGGER.error(logprefix + " Errore occorso durante l'esecuzione del metodo:" + e, e);
			throw new ServiziFindomWebException("Exception while execute query getAmministratoreByCodiceFiscale", e);
		} finally {
			LOGGER.debug(logprefix + " - END");
		}
			
		return amministratore;
	}
	
	@Override
	public ArrayList<VistaDomandeDto> getDomandeInserite(Integer idSoggBeneficiario,
			Integer idSoggCreatore, String ruolo,  Integer idAreaTematica, String normativa,
			String descBreveBando, String bando, String sportello,
			String statoDomanda, String numDomanda) throws ServiziFindomWebException {

		final String methodName = "getDomandeInserite";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN");
		
		ArrayList<VistaDomandeDto> lista = new ArrayList<VistaDomandeDto>();
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		try {
			String query = ServiziFindomWebDaoUtil.getSelectStatements().getProperty("SELECT_VISTA_DOMANDE_BY_FILTRO_SEARCH");
			LOGGER.debug(logprefix + " Query == " + query);
		
			String q = ServiziFindomWebDaoUtil.composeSearchDomandeQuery(query, idSoggBeneficiario, idSoggCreatore , ruolo,
					idAreaTematica, normativa, descBreveBando, bando,sportello,statoDomanda, numDomanda);
			
			lista = (ArrayList<VistaDomandeDto>) template.query(q, params, new VistaDomandeDtoRowMapper());
			
			if(lista!=null && lista.size()>0){
				LOGGER.debug(logprefix + " trovata una lista di dimensione " + lista.size());		
			}
			
		} catch (Exception e) {
			LOGGER.error(logprefix + " Errore occorso durante l'esecuzione del metodo:" + e, e);
			throw new ServiziFindomWebException("Exception while execute query getDomandeInserite", e);
		} finally {
			LOGGER.debug(logprefix + " - END");
		}

		return lista;
	}
	
	@Override
	public void insertShellTSoggetto(ShellSoggettiDto newSoggetto)
			throws ServiziFindomWebException {
		final String methodName = "insertShellTSoggetto";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		int ris;
		
		String query = "";
		try {
			String cf = newSoggetto.getCodiceFiscale();
			if(cf!=null){
				cf = cf.toUpperCase();
			}
			params.addValue("codFiscale", cf, java.sql.Types.VARCHAR);
			params.addValue("denominazione", newSoggetto.getDenominazione(), java.sql.Types.VARCHAR);
			if(newSoggetto.getIdFormaGiuridica()>0){
				params.addValue("idFormaGiuridica", newSoggetto.getIdFormaGiuridica(), java.sql.Types.NUMERIC);
			}
			params.addValue("cognome", newSoggetto.getCognome(), java.sql.Types.VARCHAR);
			params.addValue("nome", newSoggetto.getNome(), java.sql.Types.VARCHAR);
			params.addValue("siglaNazione", newSoggetto.getSiglaNazione(), java.sql.Types.VARCHAR); //MB2019_04_18 
			
			// inserisco record in SHELL_T_DETTAGLIO_DOMANDA
			if(newSoggetto.getIdFormaGiuridica()>0){
				query = ServiziFindomWebDaoUtil.getSelectStatements().getProperty("INSERT_SHELL_SOGGETTI");
			}else{
				query = ServiziFindomWebDaoUtil.getSelectStatements().getProperty("INSERT_SHELL_SOGGETTI_BIS");
			}
			LOGGER.debug(logprefix + " Query == " + query);
			
			ris = template.update(query, params);
			
		}catch (Exception e) {
			LOGGER.error(logprefix + " Errore occorso durante l'esecuzione del metodo:" + e, e);
			throw new ServiziFindomWebException("Exception while execute query insertShellTSoggetto", e);
		} finally {
			LOGGER.debug(logprefix + " - END");
		}	
	}
	
	@Override
	public void updateShellTSoggetto(ShellSoggettiDto soggetto) throws ServiziFindomWebException {
		final String methodName = "updateShellTSoggetto";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		int ris;
		
		String query = "";
		try {
			
			params.addValue("idSoggetto", soggetto.getIdSoggetto(), java.sql.Types.NUMERIC);
			params.addValue("denominazione", soggetto.getDenominazione(), java.sql.Types.VARCHAR);
			params.addValue("idFormaGiuridica", soggetto.getIdFormaGiuridica(), java.sql.Types.NUMERIC);
			params.addValue("cognome", soggetto.getCognome(), java.sql.Types.VARCHAR);
			params.addValue("nome", soggetto.getNome(), java.sql.Types.VARCHAR);
			
			// aggiorno record in SHELL_T_SOGGETTI
			query = ServiziFindomWebDaoUtil.getSelectStatements().getProperty("UPDATE_SHELL_SOGGETTI");
			LOGGER.debug(logprefix + " Query == " + query);
			
			ris = template.update(query, params);
			
		}catch (Exception e) {
			LOGGER.error(logprefix + " Errore occorso durante l'esecuzione del metodo:" + e, e);
			throw new ServiziFindomWebException("Exception while execute query updateShellTSoggetto", e);
		} finally {
			LOGGER.debug(logprefix + " - END");
		}	
		
	}
	
	@Override
	public ProssimoSportelloAttivoDto getProssimoSportelloAttivo(Integer idAreaTematica) throws ServiziFindomWebException {
		final String methodName = "getProssimoSportelloAttivo";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN");
		
		ProssimoSportelloAttivoDto sportello = null;
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		try {
			String query = "";
			
			if(idAreaTematica!=null && idAreaTematica >0) {
				query = ServiziFindomWebDaoUtil.getSelectStatements().getProperty("SELECT_PROSSIMO_SPORTELLO_ATTIVO_BYIDAREA");
				params.addValue("idAreaTematica", idAreaTematica);
			}else {
				query = ServiziFindomWebDaoUtil.getSelectStatements().getProperty("SELECT_PROSSIMO_SPORTELLO_ATTIVO");
			}
			LOGGER.debug(logprefix + " Query == " + query);
			
			sportello = (ProssimoSportelloAttivoDto) template.queryForObject(query, params, new ProssimoSportelloAttivoDtoRowMapper());
			
			if(sportello!=null){
				LOGGER.debug(logprefix + " trovato prossimo bando:" + sportello.getDescrizioneBando());
			}

		} catch (EmptyResultDataAccessException e){
			LOGGER.info(logprefix + " nessun bando prossimo all'apertura trovato.");
		} catch (Exception e) {
			LOGGER.error(logprefix + " Errore occorso durante l'esecuzione del metodo:" + e, e);
		} finally {
			LOGGER.debug(logprefix + " END");
		}

		return sportello;
	}
	
	@Override
	public ArrayList<TipolBeneficiariDto> getListaTipolBeneficiari() throws ServiziFindomWebException {
		final String methodName = "getListaTipolBeneficiari";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN");
		
		ArrayList<TipolBeneficiariDto> lista = null;
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		try {
			String query = ServiziFindomWebDaoUtil.getSelectStatements().getProperty("SELECT_TIPOL_BENEFICIARI");
			LOGGER.debug(logprefix + " Query == " + query);
			
			lista = (ArrayList<TipolBeneficiariDto>) template.query(query, params, new TipolBeneficiariDtoRowMapper());
			
			if(lista!=null && lista.size()>0){
				LOGGER.debug(logprefix + " trovata una lista di dimensione " + lista.size());
			}
			
		} catch (Exception e) {
			LOGGER.error(logprefix + " Errore occorso durante l'esecuzione del metodo:" + e, e);
			throw new ServiziFindomWebException("Exception while execute query getListaTipolBeneficiari", e);
		} finally {
			LOGGER.debug(logprefix + " - END");
		}

		return lista;
	}
	
	
	@Override
	public ArrayList<TipolBeneficiariDto> getListaTipolBeneficiariByIdBando(int idBando) throws ServiziFindomWebException {
		final String methodName = "getListaTipolBeneficiariByIdBando";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN");
		LOGGER.debug(logprefix + " idBando="+idBando);
		
		ArrayList<TipolBeneficiariDto> lista = null;
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		try {
			String query = ServiziFindomWebDaoUtil.getSelectStatements().getProperty("SELECT_TIPOL_BENEFICIARI_BY_IDBANDO");
			LOGGER.debug(logprefix + " Query == " + query);
			
			params.addValue("idBando", idBando, java.sql.Types.INTEGER);
			lista = (ArrayList<TipolBeneficiariDto>) template.query(query, params, new TipolBeneficiariDtoRowMapper());
			
			if(lista!=null && lista.size()>0){
				LOGGER.debug(logprefix + " trovata una lista di dimensione " + lista.size());
			}
			
		} catch (Exception e) {
			LOGGER.error(logprefix + " Errore occorso durante l'esecuzione del metodo:" + e, e);
			throw new ServiziFindomWebException("Exception while execute query getListaTipolBeneficiariByIdBando", e);
		} finally {
			LOGGER.debug(logprefix + " - END");
		}

		return lista;
	}
	
	@Override
	public ArrayList<VistaDomandeBeneficiariDto> getVistaDomandeBeneficiari(int idBando, int idSoggettoBeneficiario)
			throws ServiziFindomWebException {
		final String methodName = "getVistaDomandeBeneficiari";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN");
		LOGGER.debug(logprefix + " idBando="+idBando+" , idSoggettoBeneficiario="+idSoggettoBeneficiario);
		
		ArrayList<VistaDomandeBeneficiariDto> lista = null;
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		try {
			String query = ServiziFindomWebDaoUtil.getSelectStatements().getProperty("SELECT_VISTA_DOMANDE_BENEFICIARI");
			LOGGER.debug(logprefix + " Query == " + query);
			
			params.addValue("idBando", idBando, java.sql.Types.INTEGER);
			params.addValue("idSoggettoBeneficiario", idSoggettoBeneficiario, java.sql.Types.INTEGER);
			
			lista = (ArrayList<VistaDomandeBeneficiariDto>) template.query(query, params, new VistaDomandeBeneficiariDtoRowMapper());
			
			if(lista!=null && lista.size()>0){
				LOGGER.debug(logprefix + " trovata una lista di dimensione " + lista.size());
			}
			
		} catch (Exception e) {
			LOGGER.error(logprefix + " Errore occorso durante l'esecuzione del metodo:" + e, e);
			throw new ServiziFindomWebException("Exception while execute query getVistaDomandeBeneficiari", e);
		} finally {
			LOGGER.debug(logprefix + " - END");
		}

		return lista;
	}

	@Override
	public NumberMaxDto getNumeroMassimoDomandeInviate(Integer idDomanda) throws ServiziFindomWebException {
		final String methodName = "getNumeroMassimoDomandeInviate";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN");
		LOGGER.debug(logprefix + " idDomanda="+idDomanda);
		
		NumberMaxDto nMax = null;
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		if(idDomanda!=null){
			try {
				String query = ServiziFindomWebDaoUtil.getSelectStatements().getProperty("SELECT_NUMMAXDOMANDE");
				LOGGER.debug(logprefix + " Query == " + query);
				
				params.addValue("idDomanda", idDomanda, java.sql.Types.INTEGER);
				nMax = (NumberMaxDto) template.queryForObject(query, params, new NumberMaxDtoRowMapper());
				
			} catch (Exception e) {
				LOGGER.error(logprefix + " Errore occorso durante l'esecuzione del metodo:" + e, e);
				LOGGER.debug(logprefix + " - END");
				throw new ServiziFindomWebException("Exception while execute query getNumeroMassimoDomandeInviate", e);
			} 
		}
		
		LOGGER.debug(logprefix + " - END");
		return nMax;
	}
		
	@Override
	public Integer updateShellDomande(Integer idDomanda, Integer idSoggettoCollegato, String statoDomanda, Integer idStatoIstruttoria) throws ServiziFindomWebException {
		final String methodName = "updateShellDomande";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN");
		LOGGER.debug(logprefix + " idDomanda="+idDomanda);
		LOGGER.debug(logprefix + " idSoggettoCollegato="+idSoggettoCollegato);
		
		int ris = 0;
		MapSqlParameterSource params = new MapSqlParameterSource();
		String query = "";
		
		try {
			if (statoDomanda.equalsIgnoreCase(Constants.STATO_CONCLUSA))
				query = ServiziFindomWebDaoUtil.getSelectStatements().getProperty("UPDATE_SHELLDOMANDE_STATO_CO"); 
			else{				
				 query = ServiziFindomWebDaoUtil.getSelectStatements().getProperty("UPDATE_SHELLDOMANDE");				
			}
			LOGGER.debug(logprefix + " Query == " + query);
			
			params.addValue("idDomanda", idDomanda , java.sql.Types.INTEGER);
			params.addValue("idSoggettoCollegato", idSoggettoCollegato, java.sql.Types.INTEGER);
			
			//MB2017_12_13 ini
			if (!statoDomanda.equalsIgnoreCase(Constants.STATO_CONCLUSA)){
				String condizione = "";
				if(statoDomanda.equalsIgnoreCase(Constants.STATO_INVIATA) && idStatoIstruttoria!=null){					
					condizione= " , id_stato_istr = "+idStatoIstruttoria;						
				}
				query = query.replace("#condizione#", condizione);
			}
			//MB2017_12_13 fine
			
			ris = template.update(query, params);
			
		} catch (Exception e) {
			
			LOGGER.error(logprefix + " - Errore occorso durante l'esecuzione del metodo:" + e, e);
			throw new ServiziFindomWebException("Exception while execute query updateShellDomande", e);
			
		} finally {
			LOGGER.debug(logprefix + " END");
		}
		return ris;
	}
		
	@Override
	public byte[] getPdfDomanda(Integer idDomanda) throws ServiziFindomWebException {
		final String methodName = "getPdfDomanda";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN");
		LOGGER.debug(logprefix + " idDomanda="+idDomanda);
		
		byte[] pdfArray = null;
		MapSqlParameterSource params = new MapSqlParameterSource();
		String query = "";
		
		try {
			query = ServiziFindomWebDaoUtil.getSelectStatements().getProperty("SELECT_PDF_DOMANDA");
			LOGGER.debug(logprefix + " Query == " + query);
			
			params.addValue("idDomanda", idDomanda, java.sql.Types.INTEGER);
			pdfArray = (byte[]) template.queryForObject(query, params, new FileDtoRowMapper());
			
		} catch (Exception e) {
			
			LOGGER.error(logprefix + " - Errore occorso durante l'esecuzione del metodo:" + e, e);
			throw new ServiziFindomWebException("Exception while execute query getPdfDomanda", e);
			
		} finally {
			LOGGER.debug(logprefix + " END");
		}
		return pdfArray;
	}

	public String getFlagIstruttoriaEsterna(Integer idBando)  throws ServiziFindomWebException {
		final String methodName = "getFlagIstruttoriaEsterna";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN");

		MapSqlParameterSource params = new MapSqlParameterSource();
		String valore = "";
		
		try {
			String query = ServiziFindomWebDaoUtil.getSelectStatements().getProperty("SELECT_FLAG_ISTRUTTORIA_ESTERNA");
			LOGGER.debug(logprefix + " Query == " + query);

			params.addValue("idBando", idBando, java.sql.Types.INTEGER);
			valore = (String) template.queryForObject(query, params, String.class);			
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error(logprefix + " EmptyResultDataAccessException - Errore occorso durante l'esecuzione del metodo:" + e, e);
			LOGGER.debug(logprefix + " - END");
			throw new ServiziFindomWebException("Exception while execute query getFlagIstruttoriaEsterna", e);
        
		} catch (Exception e) {
			LOGGER.error(logprefix + " Errore occorso durante l'esecuzione del metodo:" + e, e);
			LOGGER.debug(logprefix + " - END");
			throw new ServiziFindomWebException("Exception while execute query getFlagIstruttoriaEsterna", e);
		} 

		LOGGER.debug(logprefix + " - END");
		return (valore == null ? "" : valore);
	}
	
	
	public Integer getIdStatoIstruttoriaByCodice(String codice) throws ServiziFindomWebException {
		final String methodName = "getIdStatoIstruttoriaByCodice";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN");

		MapSqlParameterSource params = new MapSqlParameterSource();		
		Integer idStatoIstruttoria = null;		
		
		try {
			String query = ServiziFindomWebDaoUtil.getSelectStatements().getProperty("SELECT_ID_STATO_ISTRUTTORIA");
			LOGGER.debug(logprefix + " Query == " + query);

			params.addValue("codice", codice, java.sql.Types.VARCHAR);			
			String idStatoIstruttoriaStr = (String) template.queryForObject(query, params, String.class);
			
			if (idStatoIstruttoriaStr!=null) {
				idStatoIstruttoria = new Integer(idStatoIstruttoriaStr);
			}
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error(logprefix + " EmptyResultDataAccessException - Errore occorso durante l'esecuzione del metodo:" + e, e);
			LOGGER.debug(logprefix + " - END");
			throw new ServiziFindomWebException("Exception while execute query getIdStatoIstruttoriaByCodice", e);
        
		} catch (Exception e) {
			LOGGER.error(logprefix + " Errore occorso durante l'esecuzione del metodo:" + e, e);
			LOGGER.debug(logprefix + " - END");
			throw new ServiziFindomWebException("Exception while execute query getIdStatoIstruttoriaByCodice", e);
		} 

		LOGGER.debug(logprefix + " - END");
		return idStatoIstruttoria;	
	}
	
	@Override
	public String getIstanzaRoutingBySportello(Integer idSportello, String fase) throws ServiziFindomWebException {
		final String methodName = "getIstanzaRoutingBySportello";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN");
		LOGGER.debug(logprefix + " idSportello="+idSportello);
		LOGGER.debug(logprefix + " fase="+fase);
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		String istanzaRoutingDomanda = null;
		
		if(idSportello!=null){
			try {
				String query = ServiziFindomWebDaoUtil.getSelectStatements().getProperty("SELECT_ISTANZA_BY_SPORTELLO");
				LOGGER.debug(logprefix + " Query == " + query);
				
				params.addValue("idSportello", idSportello, java.sql.Types.INTEGER);
				params.addValue("fase", fase, java.sql.Types.VARCHAR);

				istanzaRoutingDomanda = (String) template.queryForObject(query, params, String.class);
				
			} catch (Exception e) {
				LOGGER.error(logprefix + " Errore occorso durante l'esecuzione del metodo:" + e, e);
				LOGGER.debug(logprefix + " - END");
				throw new ServiziFindomWebException("Exception while execute query getIstanzaRoutingBySportello", e);
			} 
		}
		
		LOGGER.debug(logprefix + " - END");
		return istanzaRoutingDomanda;
	}
	
	@Override
	public String getIstanzaRoutingByDomanda(Integer idDomanda, String fase) throws ServiziFindomWebException {
		final String methodName = "getIstanzaRoutingByDomanda";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN");
		LOGGER.debug(logprefix + " idDomanda="+idDomanda);
		LOGGER.debug(logprefix + " fase="+fase);

		MapSqlParameterSource params = new MapSqlParameterSource();
		String istanzaRoutingDomanda = null;
		
		if(idDomanda!=null){
			try {
				String query = ServiziFindomWebDaoUtil.getSelectStatements().getProperty("SELECT_ISTANZA_BY_DOMANDA");
				LOGGER.debug(logprefix + " Query == " + query);
				
				params.addValue("idDomanda", idDomanda, java.sql.Types.INTEGER);
				params.addValue("fase", fase, java.sql.Types.VARCHAR);
				
				istanzaRoutingDomanda = (String) template.queryForObject(query, params, String.class);
				
			} catch (Exception e) {
				LOGGER.error(logprefix + " Errore occorso durante l'esecuzione del metodo:" + e, e);
				LOGGER.debug(logprefix + " - END");
				throw new ServiziFindomWebException("Exception while execute query getIstanzaRoutingByDomanda", e);
			} 
		}
		
		LOGGER.debug(logprefix + " - END");
		return istanzaRoutingDomanda;
	}
	

	@Override
	public ArrayList<AggrDataDto> getAggrDataByIdDomanda(Integer idDomanda)
			throws ServiziFindomWebException {
			
			final String methodName = "getAggrDataByIdDomanda";
			final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
			LOGGER.debug(logprefix + " BEGIN");
			LOGGER.debug(logprefix + " idDomanda="+idDomanda);
			
			ArrayList<AggrDataDto> lista = null;
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			try {
				String query = ServiziFindomWebDaoUtil.getSelectStatements().getProperty("SELECT_AGGR_DATA_BY_IDDOMANDA");
				LOGGER.debug(logprefix + " Query == " + query);
				
				params.addValue("idDomanda", idDomanda, java.sql.Types.INTEGER);
				
				lista = (ArrayList<AggrDataDto>) template.query(query, params, new AggrDataDtoRowMapper());
				
				if(lista!=null && lista.size()>0){
					LOGGER.debug(logprefix + " trovata una lista di dimensione " + lista.size());
				}
				
			} catch (Exception e) {
				LOGGER.error(logprefix + " Errore occorso durante l'esecuzione del metodo:" + e, e);
				throw new ServiziFindomWebException("Exception while execute query getAggrDataByIdDomanda", e);
			} finally {
				LOGGER.debug(logprefix + " - END");
			}

			return lista;
			
	}
	
	/**
	 * * Jira: 1332-step5/5 : 2R
	 * - findomrouter
	 */
	@Override
	public String getDenominazioneByCodiceFiscale(String cfBeneficiario) throws ServiziFindomWebException 
	{
		final String methodName = "getDenominazioneByCodiceFiscale";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN");

		MapSqlParameterSource params = new MapSqlParameterSource();
		String valore = "";
		
		try {
			params.addValue("cfBeneficiario", cfBeneficiario, java.sql.Types.VARCHAR);
			
			String query = ServiziFindomWebDaoUtil.getSelectStatements().getProperty("SELECT_DENOMINAZIONE_BENEFICIARIO");
			LOGGER.info(logprefix + " Query == " + query);
			
			// TODO: da testare... 2R - findomrouter
				valore = (String) template.queryForObject(query, params, String.class);
			
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error(logprefix + " EmptyResultDataAccessException - Errore occorso durante l'esecuzione del metodo:" + e, e);
			LOGGER.info(logprefix + " - END");
			throw new ServiziFindomWebException("Exception while execute query getDenominazioneByCodiceFiscale", e);
		
		} catch (Exception e) {
			LOGGER.error(logprefix + " Errore occorso durante l'esecuzione del metodo:" + e, e);
			LOGGER.debug(logprefix + " - END");
			throw new ServiziFindomWebException("Exception while execute query getDenominazioneByCodiceFiscale", e);
		} 

		LOGGER.info(logprefix + "denominazione vale: "+valore+" - END");
		return valore;
	}


	/**
	 * TODO: Jira: 1381: in elaborazione - 2R 
	 * - verifica domande concluse by cf beneficiario
	 * 
	 */
	@Override
	public int cntDomandeDaInviareByCF(Integer idSoggettoCreatore, Integer idSoggettoBeneficiario) throws ServiziFindomWebException {
		
		final String methodName = "cntDomandeDaInviareByCF";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		
		LOGGER.info(logprefix + " BEGIN");
		LOGGER.info(logprefix + " \n idSoggettoCreatore risulta: " + idSoggettoCreatore);
		LOGGER.info(logprefix + " \n idSoggettoBeneficiario risulta: " + idSoggettoBeneficiario);
		
		Integer ris = 0;
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		String query = "";
		
		try {
			
			query = ServiziFindomWebDaoUtil.getSelectStatements().getProperty("SELECT_NUMERO_DOMANDE_CONCLUSE_BY_BENEFICIARIO");
			LOGGER.info(logprefix + " Query == " + query);
			
			String condizione = " id_soggetto_beneficiario = " + idSoggettoBeneficiario;
			LOGGER.info("condizione: " + condizione);
			
			if(idSoggettoCreatore!=null) condizione += " AND id_soggetto_creatore = " + idSoggettoCreatore;
			
			String q = query.replace("#condizione#", condizione);
			LOGGER.info(logprefix + " Query == " + q);
			
			String strDomandeConcluse = (String) template.queryForObject(q, params, String.class);
			
			if(strDomandeConcluse != null && strDomandeConcluse.length() > 0){
				ris = Integer.parseInt(strDomandeConcluse);
			}
			
			LOGGER.info(logprefix + " select ["+ris+"] entry");
			
		}catch (Exception e0) {
			
			LOGGER.error(logprefix + " - Exception : Errore occorso durante l'esecuzione del metodo:" + e0, e0);
			throw new ServiziFindomWebException("Exception while execute query cntDomandeDaInviareByCF", e0);
			
		} finally {
			LOGGER.info(logprefix + " END");
		}
		return ris;
	}

	public ArrayList<StatoEsteroDto> getStatoEsteroList(boolean esclusaItalia) throws ServiziFindomWebException {
		final String methodName = "getStatoEsteroList";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN, esclusaItalia="+esclusaItalia);
		ArrayList<StatoEsteroDto> listaStati = new ArrayList<StatoEsteroDto>();		
		MapSqlParameterSource params = new MapSqlParameterSource();		
		try {
			String query = ServiziFindomWebDaoUtil.getSelectStatements().getProperty("SELECT_STATI_ESTERI");			
			String condizione = "";
			if(esclusaItalia){
				condizione = " WHERE cod_stato <> :codStato ";
				params.addValue("codStato", "000" , java.sql.Types.VARCHAR);
			}
			String q = query.replace("#condizione#", condizione);
			LOGGER.debug(logprefix + " Query == " + q);
			StatoEsteroDtoRowMapper o = new StatoEsteroDtoRowMapper();
			//			listaStati = (ArrayList<StatoEsteroDto>) template.query(q,params, new StatoEsteroDtoRowMapper());
			listaStati = (ArrayList<StatoEsteroDto>) template.query(q,params, o);

		} catch (Exception e) {
			LOGGER.error(logprefix + " Errore occorso durante l'esecuzione del metodo:" + e, e);
			throw new ServiziFindomWebException("Si e' verificata una eccezione durante l'esecuzione di getStatoEsteroList() ", e);
		} finally {
			LOGGER.debug(logprefix + " - END");
		}
		return listaStati;
	}

	public StatoEsteroDto getStatoEsteroByDescrizione(String descrizione) throws ServiziFindomWebException {
		final String methodName = "getStatoEsteroByDescrizione";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN");			
		StatoEsteroDto stato = null;		
		MapSqlParameterSource params = new MapSqlParameterSource();		
		try {
			String query = ServiziFindomWebDaoUtil.getSelectStatements().getProperty("SELECT_STATO_ESTERO_BY_DESCRIZIONE");			
			params.addValue("descrizione", descrizione , java.sql.Types.VARCHAR);
			
			LOGGER.debug(logprefix + " Query == " + query);
			StatoEsteroDtoRowMapper o = new StatoEsteroDtoRowMapper();			
			ArrayList<StatoEsteroDto> listaStati = (ArrayList<StatoEsteroDto>) template.query(query,params, o);
            if(listaStati!=null && !listaStati.isEmpty() && listaStati.get(0)!=null){
            	stato = listaStati.get(0);
            }
		} catch (Exception e) {
			LOGGER.error(logprefix + " Errore occorso durante l'esecuzione del metodo:" + e, e);
			throw new ServiziFindomWebException("Si e' verificata una eccezione durante l'esecuzione di getStatoEsteroByDescrizione() ", e);
		} finally {
			LOGGER.debug(logprefix + " - END");
		}		
		return stato;
	}
	
	@Override
	public void updateNazioneSoggettoByIdSoggetto(Integer idSoggetto, String statoEstero) throws ServiziFindomWebException {

		final String methodName = "updateNazioneSoggettoByIdSoggetto";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN");

		MapSqlParameterSource params = new MapSqlParameterSource();
		int ris;

		String query = "";
		try {
			params.addValue("statoEstero", statoEstero, java.sql.Types.VARCHAR);
			params.addValue("idSoggetto", idSoggetto, java.sql.Types.NUMERIC);	
			// aggiorno record in SHELL_T_SOGGETTI
			query = ServiziFindomWebDaoUtil.getSelectStatements().getProperty("UPDATE_NAZIONE_SHELL_SOGGETTI");
			LOGGER.debug(logprefix + " Query == " + query);
			ris = template.update(query, params);

		}catch (Exception e) {
			LOGGER.error(logprefix + " Errore occorso durante l'esecuzione del metodo:" + e, e);
			throw new ServiziFindomWebException("Exception while execute query updateShellTSoggetto", e);
		} finally {
			LOGGER.debug(logprefix + " - END");
		}	

	}
	
	@Override
	public ArrayList<VistaSportelliAttiviDto> getVistaSportelliAttiviByFilter(Integer idNormativa, Integer idBando, String statoEstero, Integer idAreaTematica) 
			throws ServiziFindomWebException {

		final String methodName = "getVistaSportelliAttiviByFilter";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN");
		
		ArrayList<VistaSportelliAttiviDto> lista = null;
		MapSqlParameterSource params = new MapSqlParameterSource();
	
		String condizione = "";
		
		try {
			String query = ServiziFindomWebDaoUtil.getSelectStatements().getProperty("SELECT_VISTA_SPORTELLI_ATTIVI_BY_FILTER");
			LOGGER.debug(logprefix + " Query == " + query);
			

			if(idNormativa!=null) condizione += " AND id_normativa = " + idNormativa;
			
			if(idBando!=null) condizione += " AND id_bando = " + idBando;
			
			if(idAreaTematica!=null && idAreaTematica>0) condizione += " AND id_area_tematica = " + idAreaTematica;
			
			if(statoEstero!=null && !StringUtils.equals(statoEstero, "000")) condizione += " AND flag_amm_aziende_estere = 'true' ";
			
			if(condizione!=null && condizione.startsWith(" AND")) {
				condizione = condizione.replaceFirst(" AND", "");
			}
			
			if(StringUtils.isNotBlank(condizione)) {
				condizione = " WHERE " + condizione;
			}
			
			String q = query.replace("#condizione#", condizione);
			LOGGER.debug(logprefix + " Query == " + q);
			
			lista = (ArrayList<VistaSportelliAttiviDto>) template.query(q, params, new VistaSportelliAttiviDtoRowMapper());
			
			if(lista!=null && lista.size()>0){
				LOGGER.info(logprefix + " trovata una lista di dimensione " + lista.size());
			}
			
		} catch (Exception e) {
			LOGGER.error(logprefix + " Errore occorso durante l'esecuzione del metodo:" + e, e);
			throw new ServiziFindomWebException("Exception while execute query getVistaSportelliAttivi", e);
		} finally {
			LOGGER.debug(logprefix + " - END");
		}

		return lista;
	}

	// GETTERS && SETTERS	
	public void setTemplate(NamedParameterJdbcTemplate template) {
		this.template = template;
	}
//	public IndexDAO getIndexDAO() {
//		return indexDAO;
//	}
//	public void setIndexDAO(IndexDAO indexDAO) {
//		this.indexDAO = indexDAO;
//	}


	@Override
	public ArrayList<ImpresaEnte> getListaImprese(int idSoggetto) throws ServiziFindomWebException {
		final String methodName = "getListaImprese";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN");
		
		ArrayList<ImpresaEnte> lista = null;
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		try {
			String query = ServiziFindomWebDaoUtil.getSelectStatements().getProperty("SELECT_LISTA_IMPRESE");
			LOGGER.debug(logprefix + " Query == " + query);

			params.addValue("idSoggetto", idSoggetto, java.sql.Types.NUMERIC);	
			
			lista = (ArrayList<ImpresaEnte>) template.query(query, params, new ImpresaEnteRowMapper());
			
			if(lista!=null && lista.size()>0){
				LOGGER.info(logprefix + " trovata una lista di dimensione " + lista.size());
			}
			
		} catch (Exception e) {
			LOGGER.error(logprefix + " Errore occorso durante l'esecuzione del metodo:" + e, e);
			throw new ServiziFindomWebException("Exception while execute query getVistaSportelliAttivi", e);
		} finally {
			LOGGER.debug(logprefix + " - END");
		}

		return lista;
	}



	@Override
	public ArrayList<Regola> getRegoleFromDB(int idTipoRegola, int idBando) throws ServiziFindomWebException {
		final String methodName = "getRegoleFromDB";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN");
		LOGGER.debug(" idTipoRegola="+idTipoRegola+", idBando="+idBando);
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		String query = "SELECT dr.id_regola, dr.cod_regola, dr.descr_regola, dr.corpo_regola, dr.id_tipo_regola, " +
				" tr.descr_breve, tr.descrizione " +
				"FROM findom_d_regole dr, findom_d_tipi_regole tr, findom_r_bandi_regole rb " +
				"WHERE tr.id_tipo_regola = :idTipoRegola " +
				"AND rb.id_bando = :idBando " +
				"AND tr.id_tipo_regola= dr.id_tipo_regola " +
				"AND rb.id_regola = dr.id_regola";
		
		LOGGER.info(" Query == " + query.toString());
		ArrayList<Regola> lista = null;

		try {
			
			params.addValue("idTipoRegola", idTipoRegola , java.sql.Types.INTEGER);
			params.addValue("idBando", idBando , java.sql.Types.INTEGER);
			lista = (ArrayList<Regola>)template.query(query, params, new RegolaRowMapper() );
			
		} catch (Exception e) {
			LOGGER.error(" - Exception : Errore occorso durante l'esecuzione del metodo getRegoleFromDB:" + e, e);
			throw new ServiziFindomWebException ("Exception while execute query getRegoleFromDB", e);
		} finally {
			LOGGER.debug(logprefix + " - END");
			
		}
		return lista;
	}
	
	@Override
	public ArrayList<ParametriRegola> getParametriRegola(Integer idBando, int idRegola) throws ServiziFindomWebException {
		final String methodName = "getParametriRegola";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN");
		LOGGER.debug(" idBando="+idBando+", idRegola="+idRegola);
		
		String query = "SELECT  r.id_regola, r.id_parametro,rb.ordine, r.valore_parametro,  d.codice " +
					"FROM findom_r_bandi_parametri_regole r, findom_r_bandi_regole rb, findom_d_parametri d " +
					"WHERE  r.id_bando = :idBando " +
					"AND rb.id_regola = :idRegola " +
					"AND rb.id_regola = r.id_regola " +
					"AND d.id_parametro = r.id_parametro ";
		
		LOGGER.info(" Query == " + query.toString());
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		ArrayList<ParametriRegola> lista = null;
		try {
			
			params.addValue("idBando", idBando , java.sql.Types.INTEGER);
			params.addValue("idRegola", idRegola , java.sql.Types.INTEGER);
			lista = (ArrayList<ParametriRegola>)template.query(query, params, new ParametriRegolaRowMapper() );
			
		} catch (Exception e) {
			LOGGER.error(" - Exception : Errore occorso durante l'esecuzione del metodo getRegoleFromDB:" + e, e);
			throw new ServiziFindomWebException ("Exception while execute query getParametriRegola", e);
		} finally {
			LOGGER.debug(logprefix + " - END");
			
		}
		return lista;
	}
	
	public Boolean getFlagUploadIndex(Long idBando)  throws ServiziFindomWebException {
		final String methodName = "getFlagUploadIndex";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN");

		MapSqlParameterSource params = new MapSqlParameterSource();
		Boolean valore = null;
		
		try {
			String query = ServiziFindomWebDaoUtil.getSelectStatements().getProperty("SELECT_FLAG_UPLOAD_INDEX");
			LOGGER.debug(logprefix + " Query == " + query);

			params.addValue("idBando", idBando, java.sql.Types.INTEGER);
			valore = (Boolean) template.queryForObject(query, params, Boolean.class);

		} catch (EmptyResultDataAccessException e) {
			LOGGER.error(logprefix + " EmptyResultDataAccessException - Errore occorso durante l'esecuzione del metodo:" + e, e);
			LOGGER.debug(logprefix + " - END");
			throw new ServiziFindomWebException("Exception while execute query getFlagIstruttoriaEsterna", e);
        
		} catch (Exception e) {
			LOGGER.error(logprefix + " Errore occorso durante l'esecuzione del metodo:" + e, e);
			LOGGER.debug(logprefix + " - END");
			throw new ServiziFindomWebException("Exception while execute query getFlagIstruttoriaEsterna", e);
		} 

		LOGGER.debug(logprefix + " - END");
		return (valore == null ? false : valore);
	}
}

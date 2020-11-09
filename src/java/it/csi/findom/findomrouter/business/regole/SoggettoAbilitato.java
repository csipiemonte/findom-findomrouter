/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.regole;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import it.csi.findom.findomrouter.presentation.vo.StatusInfo;
import it.csi.findom.findomrules.business.RegoleExecutionResult;
import it.csi.findom.findomrules.business.RegoleTemplate;
import it.csi.findom.findomrules.dto.ParametriRegola;
import it.csi.findom.findomrules.dto.Regola;
import it.csi.findom.findomrules.dto.VistaDomandeGraduatoria;

public class SoggettoAbilitato extends RegoleTemplate {

//	Regola di tipo 7 - CRZDMD - Creazione nuova domanda 
//  Verifica, alla selezione del bando cui e' associata, se:
//	  1. il codice fiscale del soggetto beneficiario e' presente nella tabella findom_t_soggetti_abilitati
//	     a. non e' presente, la verifica fallisce
//	     b. e' presente, recuperare il relativo id_sogg_abil e passare al punto 2
//	  2. id_sogg_abil e' associato al bando selezionato sulla tabella findom_r_bandi_soggetti_abilitati, con dt_inizio >= data odierna e dt_fine non valorizzata o > data odierna.
//	     a. Non esiste l'associazione descritta, la verifica fallisce.
//	     b. Esiste l'associazione descritta, la verifica termina con successo e il sistema permette di inserire la domanda.
//
//	Se la verifica fallisce, visualizza il messaggio: 
//	    "Soggetto non abilitato a presentare domanda per il bando selezionato". 

	
	private static final Logger logger = Logger.getLogger("findomrouter");
	private static NamedParameterJdbcTemplate template;
	public static final String ERROR_OUT = "Soggetto non ammesso a presentare domanda in base ai requisiti del bando selezionato";

	@Override
	protected RegoleExecutionResult executeRegola(VistaDomandeGraduatoria arg0, Regola arg1, Map arg2) {
		return null;
	}

	@Override
	protected RegoleExecutionResult executeRegolaIns(Regola regola, Map parametri) {
		String prf  = "[SoggettoAbilitato::executeRegolaIns] ";
		
		logger.debug( prf + "BEGIN");
		logger.debug( prf + "parametri="+parametri);
		
		RegoleExecutionResult result = new RegoleExecutionResult();
		
		boolean error = false;
		boolean res = true;
		
		StatusInfo statusInfo = (StatusInfo)parametri.get("statusInfo");

		String codFiscaleBeneficiario = statusInfo.getCodFiscaleBeneficiario();
		logger.debug(prf + "codFiscaleBeneficiario="+codFiscaleBeneficiario);
		
		Integer idBando = (Integer)parametri.get("idBando");
		logger.debug(prf + "idBando="+idBando);
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		params.addValue("codFiscaleBeneficiario", codFiscaleBeneficiario , java.sql.Types.VARCHAR);
		
	    String query = "SELECT id_sogg_abil " + 
	    				" FROM findom_t_soggetti_abilitati " + 
	        			" WHERE upper(codice_fiscale) = upper(:codFiscaleBeneficiario)";
	    
	    String query2 = "SELECT rsa.importo_contributo from  findom_r_bandi_soggetti_abilitati rsa" + 
	    				" WHERE rsa.id_bando = :idBando" + 
	    				" and id_sogg_abil = :idSoggetto" + 
	    				" and now() >= rsa.dt_inizio" + 
	    				" and (rsa.dt_fine is null OR now() <= rsa.dt_fine) ";
	    
	    boolean soggettoPresente = false;
	    try {
			
	    	logger.debug(prf + " query="+query);
	    	
			Map<String, Object> item = template.queryForMap(query, params);
			logger.debug(prf + " item="+item);
			
			if(item!=null && !item.isEmpty()) {
				Integer idSoggetto = (Integer) item.get("id_sogg_abil");
				logger.debug(prf + " idSoggetto="+idSoggetto);
				
				// soggetto non presente nella tabella 
		        if (idSoggetto==null){
		        	logger.debug(prf + " idSoggetto="+idSoggetto+" non presente nella tabella");
		        	error = true;
		        } else {
		        	
		        	soggettoPresente = true;
		        	
		        	logger.debug(prf + " query2="+query2);
		        	
		        	MapSqlParameterSource params2 = new MapSqlParameterSource();
		        	params2.addValue("idBando", idBando , java.sql.Types.INTEGER);
		        	params2.addValue("idSoggetto", idSoggetto , java.sql.Types.INTEGER);
		        	
		        	// un soggetto puo' avere piu' importi in tabella (es politecnico e suoi dipartimenti)
		        	List<Long> listaImporti = template.queryForList(query2, params2, Long.class);
		        	
		        	if(listaImporti!=null && !listaImporti.isEmpty()) {
						logger.debug(prf + " trovati "+ listaImporti.size() + " importi");
						logger.debug(prf + " listaImporti= "+ listaImporti);
					}else {
						 logger.debug(prf + " nessun importo presente in tabella");
						 error = true;
					}
					
		        }
		        
			} else {
				error = true;
			}

		} catch (EmptyResultDataAccessException e) {
			
			if(soggettoPresente) {
				logger.warn(prf + " - EmptyResultDataAccessException, nessun importo trovato nella findom_r_bandi_soggetti_abilitati");
			}else {
				logger.warn(prf + " - EmptyResultDataAccessException, soggetto non presente nella tabella findom_t_soggetti_abilitati");
			}
			
			error = true;
			
		} catch (Exception e) {
			
			logger.error(prf + " - Exception : Errore occorso durante l'esecuzione del metodo:" + e, e);
			error = true;
		}
		
	    if(error) {
	    	Vector inErrorMessages = new Vector<String>();
			inErrorMessages.addElement(ERROR_OUT);
			result.addErrorMessages(inErrorMessages);
			res = false;
	    }
		 
		result.setExecutionSucceded(res);
		
		logger.debug(prf + "END");
		return result;
	}

	@Override
	public List<ParametriRegola> leggiParametri(Integer arg0, Integer arg1) throws Exception {
		return null;
	}

	@Override
	public void usaParametri(List<ParametriRegola> arg0) throws Exception {
	}

	public static NamedParameterJdbcTemplate getTemplate() {
		return template;
	}

	public static void setTemplate(NamedParameterJdbcTemplate template) {
		SoggettoAbilitato.template = template;
	}

}

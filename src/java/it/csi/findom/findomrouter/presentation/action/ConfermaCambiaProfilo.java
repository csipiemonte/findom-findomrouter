/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.action;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.findom.findomrouter.exception.ServiziFindomWebException;
import it.csi.findom.findomrouter.presentation.util.Constants;
import it.csi.findom.findomrouter.presentation.util.StringUtils;
import it.csi.findom.findomrouter.presentation.vo.StatusInfo;

public class ConfermaCambiaProfilo extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private static final String CLASS_NAME = "ConfermaCambiaProfilo";
	
	private String prosegui;

	@Override
	public String executeAction() throws SystemException,
			UnrecoverableException, CSIException {
		
		final String methodName = "executeAction";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		
		log.info(logprefix +" BEGIN, prosegui="+prosegui);
		String result = Constants.ACTION_NOTCONFIRMED;
		
		// il bottone "no, chiudi" viene gestito da CSS, non e' mappato su struts
		
		if(StringUtils.isNotBlank(prosegui) && prosegui.equals(Constants.BUTTON_TXT_CONFERMA)){
			log.debug(logprefix +" PROSEGUO COL cambia profilo");
			
			StatusInfo state = getStatus();
			try {
				String te = "L'utente cambia profilo e abbandona il beneficiario id="+state.getIdSoggettoBeneficiario()+" cf:"+state.getCodFiscaleBeneficiario();
				getServiziFindomWeb().insertLogAudit(Constants.CSI_LOG_IDAPPL, "", 
						getUserInfo().getCodFisc()+" - "+ getUserInfo().getCognome() + " " + getUserInfo().getNome(), 
						Constants.CSI_LOG_OPER_CAMBIO_PROFILO, te, "");
			} catch (ServiziFindomWebException e) {
				log.error(logprefix +" impossibile scrivere CSI_LOG_AUDIT: "+e);
			}
			
			
			//forzo il ricaricamento della sessione al prossimo giro
			getServletRequest().getSession().setAttribute(Constants.SESSION_KEY_INIT_CP,Constants.FLAG_FALSE);
			log.debug(logprefix +" Forzato il ripopolamento della sessione al prossimo giro");
			
			// elimino dalla sessione 
			getServletRequest().getSession().removeAttribute(Constants.CONTEXT_ATTR_NAME);
			log.debug(logprefix +" Rimosso l'attributo ["+Constants.CONTEXT_ATTR_NAME+"] dalla sessione");
			
			// rimuovo i valori delle combo del form di ricerca 
			getServletRequest().getSession().removeAttribute("listaNormative");
			getServletRequest().getSession().removeAttribute("listadescBreveBando");
			getServletRequest().getSession().removeAttribute("listaBandi");
			getServletRequest().getSession().removeAttribute("listaSportelli");
			getServletRequest().getSession().removeAttribute("listaStati");
			
			// rimuovo i valori postati dal form di ricerca 
			getServletRequest().getSession().removeAttribute("normativa");
			getServletRequest().getSession().removeAttribute("descBreveBando");
			getServletRequest().getSession().removeAttribute("bando");
			getServletRequest().getSession().removeAttribute("sportello");
			getServletRequest().getSession().removeAttribute("statoDomanda");
			getServletRequest().getSession().removeAttribute("numDomanda");
			
			// rimuovo la lista delle domande trovate dal form di ricerca
			getServletRequest().getSession().removeAttribute("listaDomande");
			
			// rimuovo  i valori delle combo del form di creazione nuova domanda
			getServletRequest().getSession().removeAttribute("listaNormativeINS");
			getServletRequest().getSession().removeAttribute("listadescBreveBandoINS");
			getServletRequest().getSession().removeAttribute("listaBandiINS");
			getServletRequest().getSession().removeAttribute("listaSportelliINS");
			getServletRequest().getSession().removeAttribute("listaTipologieBeneficiariINS");
			
			// rimuovo la lista delle domande trovate dal form di ricerca
			getServletRequest().getSession().removeAttribute("normativaINS");
			getServletRequest().getSession().removeAttribute("descBreveBandoINS");
			getServletRequest().getSession().removeAttribute("bandoINS");
			getServletRequest().getSession().removeAttribute("sportelloINS");
			getServletRequest().getSession().removeAttribute("tipologiaBeneficiarioINS");
			
			// rimuovo impresa derivante da AAEP dalla sessione
			getServletRequest().getSession().removeAttribute(Constants.SESSION_ENTEIMPRESA);

			result = Constants.ACTION_CONFIRMED;
		}
			
		log.debug(logprefix +" END. result="+result);
		return result;
	}
	
	
	//GETTERS && SETTERS
	
	public String getProsegui() {
		return prosegui;
	}

	public void setProsegui(String prosegui) {
		this.prosegui = prosegui;
	}
}

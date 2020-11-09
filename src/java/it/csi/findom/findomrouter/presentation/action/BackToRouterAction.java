/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.action;

import it.csi.aaep.aaeporch.business.AAEPException_Exception;
import it.csi.aaep.aaeporch.business.CSIException_Exception;
import it.csi.aaep.aaeporch.business.ImpresaINFOC;
import it.csi.aaep.aaeporch.business.SystemException_Exception;
import it.csi.aaep.aaeporch.business.UnrecoverableException_Exception;
import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.findom.findomrouter.integration.extservices.aaep.AaepDAO;
import it.csi.findom.findomrouter.integration.extservices.ipa.IpaDAO;
import it.csi.findom.findomrouter.presentation.util.Constants;
import it.csi.findom.findomrouter.presentation.util.StringUtils;
import it.csi.findom.findomrouter.presentation.vo.ImpresaEnte;
import it.csi.findom.findomrouter.presentation.vo.StatusInfo;
import it.csi.findom.findomrouter.presentation.vo.UserInfo;
import it.csi.findom.findomrouter.util.TrasformaClassiAAEP;
import it.csi.findom.findomwebnew.dto.aaep.Carica;
import it.csi.findom.findomwebnew.dto.aaep.Impresa;
import it.csi.findom.findomwebnew.dto.aaep.Persona;
import it.csi.findom.findomwebnew.dto.aaep.Sede;
import it.csi.util.performance.StopWatch;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.omg.CORBA.SystemException;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ValidationAware;


public class BackToRouterAction extends BaseAction implements ValidationAware {

	private static final long serialVersionUID = 1L;
	
	private static final String CLASS_NAME = "BackToRouterAction";

	public ArrayList<ImpresaEnte> listaImprese = null;

	private AaepDAO aaepDAO;
	private IpaDAO ipaDAO;
	
	
	@Override
	public String executeAction() throws SystemException, UnrecoverableException, CSIException {
		
		final String methodName = "executeAction";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		
		String result = new String();
		try {
			result = super.execute();
			
		} catch (SystemException e){
			log.error(logprefix + " SystemException: " + e);
			return ERROR;
		} catch (UnrecoverableException e){
			log.error(logprefix + " UnrecoverableException: " + e);
			return ERROR;
		} catch (CSIException e){
			log.error(logprefix + " CSIException: " + e);
			return ERROR;
		} catch (Exception e){
			log.error(logprefix + " Exception: " + e);
			return ERROR;
		} catch (Throwable e) {
			log.error(logprefix + " Throwable: " + e);
			return ERROR;
		} finally {
			log.debug(logprefix + " END");
		}
		return result;
		
	}
	
	@Override
	public String execute() throws SystemException,	UnrecoverableException, CSIException {
		
		final String methodName = "execute";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";

		log.debug(logprefix + " BEGIN");
		StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
		stopWatch.start();
		
		getSessionParametersAppendedToUrl();
		
		stopWatch.stop();
		stopWatch.dumpElapsed(CLASS_NAME, methodName+"()", "test", "test");
					
		log.debug(logprefix + "  END");
		return SUCCESS;
	}

	public void caricaDatiImpresa() {
		final String methodName = "caricaDatiImpresa";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";

		log.info(logprefix + " BEGIN");

		StatusInfo state = getStatus();
		UserInfo us = getUserInfo();
		
		

		// invoco AAEP e recupero le info dell'impresa
		Impresa azienda = null;
		Sede sedeLegale = null;
		try {
			
			/**
			 *  TODO: Jira: 1332:step1/5 2R - recupero denominazione beneficiario - 
			 *  - findomrouter
			 *  -- valore recuperato da db dopo il concludi 
			 */
			String denominazioneBeneficiario = "";
			denominazioneBeneficiario = getServiziFindomWeb().getDenominazioneByCodiceFiscale(state.getCodFiscaleBeneficiario());
			log.info("Test recupero denominazione da db e metto in sessione: " + denominazioneBeneficiario);
			if ( denominazioneBeneficiario != null && !denominazioneBeneficiario.isEmpty()) {
				state.setDescrImpresaEnte(denominazioneBeneficiario);
				getServletRequest().getSession().setAttribute(Constants.STATUS_INFO, state); // TODO:  Jira: 1332 - da testare... ***
			}
			log.info(logprefix + "recupero info azienda da AAEP con getDettaglioImpresa(INFOC,"+state.getCodFiscaleBeneficiario()+")");
			ImpresaINFOC aziendaINFOC = aaepDAO.getDettaglioImpresa2("INFOC", state.getCodFiscaleBeneficiario(), "", "", "");
			log.info(logprefix + "recuperate info azienda da AAEP");

			// trasformo l'oggett orestituito in una interno per poterlo serializzare e mettere in sessione
			azienda = TrasformaClassiAAEP.impresaINFOC2ImpresaI(aziendaINFOC);
			getServletRequest().getSession().setAttribute(Constants.SESSION_ENTEIMPRESA, azienda);
			log.debug(logprefix + "messo in sessione EnteImpresa");
			// TODO : valorizzare ul RUOLO LR 
			// se AAEP non risponde cosa faccio?????
			String lr = verificaLegaleRappresentante(azienda, state.getOperatore());
			if (StringUtils.isNotBlank(lr) && lr.equals("S"))	{			
				us.setRuolo(Constants.RUOLO_LR);
			}
			if (azienda!=null) {
				log.info(logprefix + "Cerco la sede legale per idAzienda = ["+aziendaINFOC.getIdAzienda()+"]" );
				it.csi.aaep.aaeporch.business.Sede sede = aaepDAO.getDettaglioSedeLegale(aziendaINFOC.getIdAzienda(),determinaIdSedeLegale(aziendaINFOC.getSedi().iterator()) , "INFOC");
				sedeLegale = TrasformaClassiAAEP.sedeINFOC2Sede(sede);
				log.debug(logprefix + "Ho trovato la sede legale ["+sedeLegale.getDenominazione()+"]" );
				getServletRequest().getSession().setAttribute(Constants.SESSION_SEDE_LEGALE, sedeLegale);						
				log.info(logprefix + "LA SEDE HA ATECO 2007 = ["+sedeLegale.getCodiceAteco2007()+"]" );
			}
			
			
			
			
		} catch (UnrecoverableException_Exception e) {
			log.error(logprefix +" UnrecoverableException_Exception: "+e);

		} catch (CSIException_Exception e) {
			log.error(logprefix +" CSIException_Exception: "+e);

		} catch (SystemException_Exception e) {
			log.error(logprefix +" SystemException_Exception: "+e);

		} catch (AAEPException_Exception e) {
			log.error(logprefix +" AAEPException_Exception: "+e);

		} catch (Exception e){
			// entro qui se il WS non risponde correttamente 
			// Exception: javax.xml.ws.soap.SOAPFaultException: Fault occurred while processing.
			log.error(logprefix +" Exception: "+e);

		}
		
		log.debug(logprefix + "  END");
//		return SUCCESS;
	}


	private String determinaIdSedeLegale(Iterator<it.csi.aaep.aaeporch.business.Sede> sediIter) {
		while (sediIter.hasNext()) {
			it.csi.aaep.aaeporch.business.Sede sede = sediIter.next();
			if (sede.getDescrTipoSede().equalsIgnoreCase("SEDE LEGALE")) {
				return sede.getIdSede(); 
			}
		}
		throw new UnsupportedOperationException("Attenzione! l'azienda individuata non presenta la sede legale su AAEP ");
	}
	
	private String verificaLegaleRappresentante(Impresa azienda, String codFisc) {
		final String methodName = "verificaLegaleRappresentante";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";

		log.debug(logprefix + " BEGIN");
		log.debug(logprefix + " codFisc=" + codFisc);
		String lr = "";

		if (azienda != null) {
			log.debug(logprefix + "azienda trovata su AAEP, IdAzienda=" + azienda.getIdAzienda());
			// TODO
			// verifica su AAEP se utente loggato e' Legale Rappresentante
			List<Persona> listaPersone = azienda.getListaPersone();
			if (listaPersone != null) {
				log.debug(logprefix + "trovata listaPersone=" + listaPersone.size());

				for (Iterator itr = listaPersone.iterator(); itr.hasNext();) {
					Persona persona = (Persona) itr.next();
					log.debug(logprefix + "persona.getCodiceFiscale()=" + persona.getCodiceFiscale());

					if (persona != null && persona.getCodiceFiscale() != null
							&& persona.getCodiceFiscale().equals(codFisc)) {
						log.debug(logprefix + "trovata persona loggata in elenco listaPersone");
						List<Carica> listaCariche = persona.getListaCariche();
						if (listaCariche != null) {
							log.debug(logprefix + "trovata listaCariche=" + listaCariche.size());
							for (Iterator itr2 = listaCariche.iterator(); itr2.hasNext();) {
								Carica carica = (Carica) itr2.next();
								if (carica != null && "S".equals(carica.getFlagRappresentanteLegale())) {
									log.debug(logprefix + "FOUNDED carica.getFlagRappresentanteLegale()="
											+ carica.getFlagRappresentanteLegale());

									// TODO : utente loggato e' LR
									lr = "S";

									break;
								}
							}
						} else {
							log.debug(logprefix + "trovata listaCariche NULLA");
						}
						break;
					}
				}
			} else {
				log.debug(logprefix + "trovata listaPersone NULLA");
			}

		} else {
			log.debug(logprefix + "azienda NON trovata su AAEP");
		}
		log.debug(logprefix + " END");
		return lr;
	}
	
	private void getSessionParametersAppendedToUrl() {
				
		Gson gson = new Gson();
		
		getServletRequest().getSession().setAttribute("risultatoKO", getServletRequest().getParameter("risultatoKO"));
		getServletRequest().getSession().setAttribute("risultatoOK", getServletRequest().getParameter("risultatoOK"));

		String errori = getServletRequest().getParameter("errori");
		if (errori != null) {
			Collection errorCollection =  (Collection)gson.fromJson(errori, Collection.class);
			if ( errorCollection!=null && errorCollection.size() > 0) {
				while (errorCollection.iterator().hasNext()){	
					addActionError((String)errorCollection.iterator().next());
				}
			}
		}
		
		
		caricaDatiImpresa();
		

	}

	//GETTERS && SETTERS
	public ArrayList<ImpresaEnte> getListaImprese() {
		return listaImprese;
	}
	public void setListaImprese(ArrayList<ImpresaEnte> listaImprese) {
		this.listaImprese = listaImprese;
	}

	public AaepDAO getAaepDAO() {
		return aaepDAO;
	}


	public IpaDAO getIpaDAO() {
		return ipaDAO;
	}


	public void setAaepDAO(AaepDAO aaepDAO) {
		this.aaepDAO = aaepDAO;
	}


	public void setIpaDAO(IpaDAO ipaDAO) {
		this.ipaDAO = ipaDAO;
	}

}

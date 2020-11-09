/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.servizifindomweb;

import java.util.ArrayList;
import org.apache.log4j.Logger;
import it.csi.findom.findomrouter.business.servizifindomweb.dao.ServiziFindomWebDao;
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
import it.csi.findom.findomrouter.presentation.util.Constants;
import it.csi.findom.findomrouter.presentation.vo.Domanda;
import it.csi.findom.findomrouter.presentation.vo.ImpresaEnte;
import it.csi.findom.findomrouter.presentation.vo.StatoDomanda;
import it.csi.findom.findomrouter.util.TrasformaClassi;
import it.csi.findom.findomrules.dto.ParametriRegola;
import it.csi.findom.findomrules.dto.Regola;

public class ServiziFindomWeb {

	protected static final Logger LOGGER = Logger.getLogger(Constants.APPLICATION_CODE + ".DaoImpl");
	
	private static final String CLASS_NAME = "ServiziFindomWeb";
	private ServiziFindomWebDao findomWebDao;

	
	public ArrayList<StatoDomanda> getStatiDomanda() throws ServiziFindomWebException {
		final String methodName = "getStatiDomanda";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN-END");
		
		ArrayList<StatoDomandaDto> lista = findomWebDao.getStatiDomanda();
		return TrasformaClassi.statoDomandaDto2StatoDomanda(lista);
	}
	
	public int insertLogAudit(String codApplicativo, String ip, String utente ,String tipoOperazione ,String descrOperazione, String chiaveOperazione)
			throws ServiziFindomWebException {
		final String methodName = "insertLogAudit";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN-END");
		return findomWebDao.insertLogAudit(codApplicativo, ip, utente, tipoOperazione, descrOperazione, chiaveOperazione);
	}
	
	public ShellSoggettiDto getDatiSoggettoByCodiceFiscale(String utente) throws ServiziFindomWebException {
		final String methodName = "getDatiSoggettoByCodiceFiscale";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN-END");
		return findomWebDao.getDatiSoggettoByCodiceFiscale(utente);
	}
	
	public ArrayList<ShellSoggettiDto> getDatiSoggettoByIdSoggetto(ArrayList<String> listaId) throws ServiziFindomWebException {
		final String methodName = "getDatiSoggettoByIdSoggetto";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN-END");
		return findomWebDao.getDatiSoggettoByIdSoggetto(listaId);
	}

	public ArrayList<VistaDomandeDto> getVistaDomanda(Integer idSoggettoCreatore, Integer idSoggettoBeneficiario, Integer idNormativa, 
			Integer idBando, Integer idDomanda, Integer areaTematica 	) throws ServiziFindomWebException {
		final String methodName = "getVistaDomanda";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN-END");
		return findomWebDao.getVistaDomanda(idSoggettoCreatore, idSoggettoBeneficiario, idNormativa, idBando, idDomanda, areaTematica);
	}
	
	public ArrayList<FormeGiuridicheDto> getFormeGiuridiche() throws ServiziFindomWebException {
		final String methodName = "getFormeGiuridiche";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN-END");
		return findomWebDao.getFormeGiuridiche();
	}
	
	public AmministratoriDto getAmministratoreByCodiceFiscale(String codFisc) throws ServiziFindomWebException {
		final String methodName = "getAmministratoreByCodiceFiscale";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN-END");
		return findomWebDao.getAmministratoreByCodiceFiscale(codFisc);
	}

	public ArrayList<Domanda> getDomandeInserite(Integer idSoggBeneficiario,
			Integer idSoggCreatore, String ruolo, Integer idAreaTematica, String normativa,
			String descBreveBando, String bando, String sportello,
			String statoDomanda, String numDomanda) throws ServiziFindomWebException {
		final String methodName = "getDomandeInserite";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN-END");
		
		ArrayList<VistaDomandeDto> lista = findomWebDao.getDomandeInserite(idSoggBeneficiario, idSoggCreatore , ruolo,
				idAreaTematica, normativa, descBreveBando, bando,sportello,statoDomanda, numDomanda);
		
		return TrasformaClassi.vistaDomandaDto2Domanda(lista);
	}
	
	public void insertShellTSoggetto(ShellSoggettiDto newSoggetto) throws ServiziFindomWebException {
		final String methodName = "insertShellTSoggetto";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN");
		findomWebDao.insertShellTSoggetto(newSoggetto);
		LOGGER.debug(logprefix + " END");
	}
	
	public void updateShellTSoggetto(ShellSoggettiDto soggetto) throws ServiziFindomWebException {
		final String methodName = "updateShellTSoggetto";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN");
		findomWebDao.updateShellTSoggetto(soggetto);
		LOGGER.debug(logprefix + " END");		
	}
	
	public ProssimoSportelloAttivoDto getProssimoSportelloAttivo(Integer idAreaTematica) throws ServiziFindomWebException {
		final String methodName = "getProssimoSportelloAttivo";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN-END, idAreaTematica="+idAreaTematica);
		return findomWebDao.getProssimoSportelloAttivo(idAreaTematica);
	}
	
	public ArrayList<TipolBeneficiariDto> getListaTipolBeneficiari() throws ServiziFindomWebException {
		final String methodName = "getListaTipolBeneficiari";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN-END");
		return findomWebDao.getListaTipolBeneficiari();
	}

	public ArrayList<TipolBeneficiariDto> getListaTipolBeneficiariByIdBando(int idBando) throws ServiziFindomWebException {
		final String methodName = "getListaTipolBeneficiariByIdBando";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN-END");
		return findomWebDao.getListaTipolBeneficiariByIdBando(idBando);
	}
	
	public ArrayList<VistaDomandeBeneficiariDto> getVistaDomandeBeneficiari(int idBando, int idSoggettoBeneficiario) 
			throws ServiziFindomWebException {
		final String methodName = "getVistaDomandeBeneficiari";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN-END");
		return findomWebDao.getVistaDomandeBeneficiari(idBando, idSoggettoBeneficiario);
	}

	public NumberMaxDto getNumeroMassimoDomandeInviate(Integer idDomanda) throws ServiziFindomWebException {
		final String methodName = "getNumeroMassimoDomandeInviate";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN-END");
		return findomWebDao.getNumeroMassimoDomandeInviate(idDomanda);
	}

	public Integer updateShellDomande(Integer idDomanda, Integer idSoggettoCollegato,String statoDomanda, Integer idStatoIstruttoria)  throws ServiziFindomWebException {
		final String methodName = "updateShellDomande";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN-END");
		return findomWebDao.updateShellDomande(idDomanda, idSoggettoCollegato, statoDomanda,idStatoIstruttoria);
	}

	public byte[] getPdfDomanda(Integer idDomanda) throws ServiziFindomWebException {
		final String methodName = "getPdfDomanda";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN-END");
		return findomWebDao.getPdfDomanda(idDomanda);
	}

	public String getFlagIstruttoriaEsterna(Integer idBando) throws ServiziFindomWebException{
		final String methodName = "getFlagIstruttoriaEsterna";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN-END");
		return findomWebDao.getFlagIstruttoriaEsterna(idBando);
	}

	public Integer getIdStatoIstruttoriaByCodice(String codice) throws ServiziFindomWebException{
		final String methodName = "getIdStatoIstruttoriaByCodice";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN-END");
		return findomWebDao.getIdStatoIstruttoriaByCodice(codice);
	}

	public String getIstanzaRoutingByDomanda(Integer idDomanda, String fase) throws ServiziFindomWebException{
		final String methodName = "getIstanzaRoutingByDomanda";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN-END");
		return findomWebDao.getIstanzaRoutingByDomanda(idDomanda, fase);
	}

	public String getIstanzaRoutingBySportello(Integer idSportello, String fase) throws ServiziFindomWebException{
		final String methodName = "getIstanzaRoutingBySportello";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN-END");
		return findomWebDao.getIstanzaRoutingBySportello(idSportello, fase);
	}	

	public ArrayList<AggrDataDto> getAggrDataByIdDomanda(Integer idDomanda) throws ServiziFindomWebException {
		final String methodName = "getAggrDataByIdDomanda";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN-END");
		return findomWebDao.getAggrDataByIdDomanda(idDomanda);
	}
	
	public String getDenominazioneByCodiceFiscale(String cfBeneficiario) throws ServiziFindomWebException { // PWCMHF32H44H219H
		final String methodName = "getDenominazioneByCodiceFiscale";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.info(logprefix + " BEGIN-END");
		String denomBeneficiario = findomWebDao.getDenominazioneByCodiceFiscale(cfBeneficiario);
		if(denomBeneficiario != null){
			return denomBeneficiario;
		}else{
			return "";
		}
	}

	public int getDomandeConcluseByCodiceFiscale(Integer idSoggettoCreatore, Integer idSoggettoBeneficiario) throws ServiziFindomWebException {
		final String methodName = "getDomandeConcluseByCodiceFiscale";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN-END");
		return findomWebDao.cntDomandeDaInviareByCF(idSoggettoCreatore, idSoggettoBeneficiario);
	}
	
	public ArrayList<StatoEsteroDto> getStatoEsteroList(boolean esclusaItalia) throws ServiziFindomWebException {
		final String methodName = "getStatoEsteroList";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN-END");
		return findomWebDao.getStatoEsteroList(esclusaItalia);
	}
	public StatoEsteroDto getStatoEsteroByDescrizione(String descrizione) throws ServiziFindomWebException {
		final String methodName = "getStatoEsteroByDescrizione";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN-END");
		return findomWebDao.getStatoEsteroByDescrizione(descrizione);
	}
	public void updateNazioneSoggettoByIdSoggetto(Integer idSoggetto, String statoEstero) throws ServiziFindomWebException {
		final String methodName = "updateNazioneSoggettoByIdSoggetto";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN");
		findomWebDao.updateNazioneSoggettoByIdSoggetto(idSoggetto, statoEstero);
		LOGGER.debug(logprefix + " END");		
	}

	public ArrayList<VistaSportelliAttiviDto> getVistaSportelliAttiviByFilter(Integer idNormativa, Integer idBando, String statoEstero, Integer idAreaTematica) throws ServiziFindomWebException {
		final String methodName = "getVistaSportelliAttiviByFilter";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN-END");
		return findomWebDao.getVistaSportelliAttiviByFilter(idNormativa, idBando, statoEstero, idAreaTematica);
	}

	public ArrayList<ImpresaEnte> getListaImprese(int idSoggetto) throws ServiziFindomWebException {
		final String methodName = "getListaImprese";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN-END");
		return findomWebDao.getListaImprese(idSoggetto);
	}
	
	public ArrayList<Regola> getRegoleFromDB(int idTipoRegola, int idBando) throws ServiziFindomWebException{
		final String methodName = "getRegoleFromDB";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN-END");
		return findomWebDao.getRegoleFromDB(idTipoRegola, idBando);
	}

	public ArrayList<ParametriRegola> getParametriRegola(Integer idBando, int idRegola) throws ServiziFindomWebException{
		final String methodName = "getParametriRegola";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN-END");
		return findomWebDao.getParametriRegola(idBando, idRegola);
	}
	
	public Boolean getFlagUploadIndex(Long idBando) throws ServiziFindomWebException{
		final String methodName = "getFlagUploadIndex";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		LOGGER.debug(logprefix + " BEGIN-END");
		return findomWebDao.getFlagUploadIndex(idBando);
	}
	
	// GETTERS && SETTERS
	public ServiziFindomWebDao getFindomWebDao() {
		return findomWebDao;
	}

	public void setFindomWebDao(ServiziFindomWebDao findomWebDao) {
		this.findomWebDao = findomWebDao;
	}

	
	

}

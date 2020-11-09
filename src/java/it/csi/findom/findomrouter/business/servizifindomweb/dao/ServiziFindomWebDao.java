/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.servizifindomweb.dao;

import java.util.ArrayList;
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
import it.csi.findom.findomrouter.presentation.vo.ImpresaEnte;
import it.csi.findom.findomrules.dto.ParametriRegola;
import it.csi.findom.findomrules.dto.Regola;

public interface ServiziFindomWebDao {

	public ArrayList<StatoDomandaDto> getStatiDomanda() throws ServiziFindomWebException;

	
	public int insertLogAudit(String codApplicativo, String ip, String utente ,String tipoOperazione ,String descrOperazione, String chiaveOperazione) 
				throws ServiziFindomWebException;
	
	/**
	 * Estraggo un oggetto ShellSoggettiDto dato un cod_fiscale (tabella shell_t_soggetti)
	 * @param utente
	 * @return
	 * @throws ServiziFindomWebException
	 */
	public ShellSoggettiDto getDatiSoggettoByCodiceFiscale(String utente) throws ServiziFindomWebException;
	
	/**
	 * Estraggo tutti i dati dalla vista
	 * @param idSoggettoCreatore
	 * @param idSoggettoBeneficiario
	 * @param idNormativa
	 * @param idBando
	 * @param idDomanda
	 * @param areaTematica
	 * @return
	 * @throws ServiziFindomWebException
	 */
	public ArrayList<VistaDomandeDto> getVistaDomanda(Integer idSoggettoCreatore, Integer idSoggettoBeneficiario, Integer idNormativa, 
			Integer idBando, Integer idDomanda, Integer areaTematica) throws ServiziFindomWebException;
	
	/**
	 * Estraggo una lista di ShellSoggettiDto data una lista di id_soggetto (tabella shell_t_soggetti)
	 * @param listaId : lista id da estrarre
	 * @return lista di ShellSoggettiDto
	 * @throws ServiziFindomWebException
	 */
	public ArrayList<ShellSoggettiDto> getDatiSoggettoByIdSoggetto(ArrayList<String> listaId) throws ServiziFindomWebException;

	/**
	 * Estraggo tutte le forme giuridiche dalla ext_d_forme_giuridiche
	 * @return
	 * @throws ServiziFindomWebException
	 */
	public ArrayList<FormeGiuridicheDto> getFormeGiuridiche() throws ServiziFindomWebException;

	/**
	 * Recupero le informazioni dalla tabella findom_t_amministratori per il cf dato
	 * 
	 * @param codFisc
	 * @return
	 * @throws ServiziFindomWebException
	 */
	public AmministratoriDto getAmministratoreByCodiceFiscale(String codFisc) throws ServiziFindomWebException;

	/**
	 * Estraggo le domande inserite secondo i parametri di ingresso
	 * 
	 * @param idSoggBeneficiario :id_soggetto Impresa/ente per cui si sta operando
	 * @param idSoggCreatore : id_soggetto che ha creato la domanda == soggetto collegato
	 * @param ruolo
	 * @param idAreaTematica 
	 * @param normativa
	 * @param descBreveBando
	 * @param bando
	 * @param sportello
	 * @param statoDomanda
	 * @param numDomanda
	 * @return
	 */
	public ArrayList<VistaDomandeDto> getDomandeInserite(Integer idSoggBeneficiario,
			Integer idSoggCreatore, String ruolo, Integer idAreaTematica, String normativa,
			String descBreveBando, String bando, String sportello,
			String statoDomanda, String numDomanda) throws ServiziFindomWebException;

	/**
	 * Inserisco una entry nella tabella shell_t_soggetti
	 * @param newSoggetto
	 */
	public void insertShellTSoggetto(ShellSoggettiDto newSoggetto) throws ServiziFindomWebException;

	/**
	 * Aggiorno una entry nella tabella shell_t_soggetti
	 * @param soggetto
	 * @throws ServiziFindomWebException
	 */
	public void updateShellTSoggetto(ShellSoggettiDto soggetto)  throws ServiziFindomWebException;

	/**
	 * Estraggo i dati da findom_t_sportelli
	 * @param idAreaTematica 
	 * @return 
	 * @throws ServiziFindomWebException
	 */
	public ProssimoSportelloAttivoDto getProssimoSportelloAttivo(Integer idAreaTematica) throws ServiziFindomWebException;

	/**
	 * Estraggo i dati dalla tabella findom_d_tipol_beneficiari
	 * @return
	 */
	public ArrayList<TipolBeneficiariDto> getListaTipolBeneficiari() throws ServiziFindomWebException;

	/**
	 * 
	 * @param idBando
	 * @return
	 */
	public ArrayList<TipolBeneficiariDto> getListaTipolBeneficiariByIdBando(int idBando) throws ServiziFindomWebException;

	/**
	 * 
	 * @param idBando
	 * @param idSoggettoBeneficiario
	 * @return
	 * @throws ServiziFindomWebException
	 */
	public ArrayList<VistaDomandeBeneficiariDto> getVistaDomandeBeneficiari(int idBando, int idSoggettoBeneficiario)  throws ServiziFindomWebException;

	/**
	 * 
	 * @param idDomanda
	 * @return
	 * @throws ServiziFindomWebException
	 */
	public NumberMaxDto getNumeroMassimoDomandeInviate(Integer idDomanda) throws ServiziFindomWebException;

	/**
	 * 
	 * @param idDomanda
	 * @param idSoggettoCollegato
	 * @return
	 * @throws ServiziFindomWebException
	 */
	public Integer updateShellDomande(Integer idDomanda, Integer idSoggettoCollegato, String statoDomanda,Integer idStatoIstruttoria)throws ServiziFindomWebException;
	
	/**
	 * 
	 * @param idDomanda
	 * @return
	 * @throws ServiziFindomWebException
	 */
	public byte[] getPdfDomanda(Integer idDomanda) throws ServiziFindomWebException;
	
	/**
	 * legge il valore dell'attributo flag_istruttoria_esterna su findom_t_bandi per il bando avente l'id passato nel parametro
	 * @param idBando
	 * @return il valore del flag (stringa vuota se non valorizzato sul DB)
	 * @throws ServiziFindomWebException
	 */
	public String getFlagIstruttoriaEsterna(Integer idBando) throws ServiziFindomWebException;
	
	/**
	 * 
	 * @param codice
	 * @return il valore dell'id_stato istruttoria corrispondente al codice passato nel parametro, eventualmente null
	 * @throws ServiziFindomWebException
	 */
	public Integer getIdStatoIstruttoriaByCodice(String codice) throws ServiziFindomWebException;
	
	public String getIstanzaRoutingBySportello(Integer idSportello, String fase) throws ServiziFindomWebException;
	
	public String getIstanzaRoutingByDomanda(Integer idDomanda, String fase) throws ServiziFindomWebException;

	/**
	 * Estraggo i dati dalle tabelle AGGR_T_TEMPLATE, AGGR_T_MODEL, data una domanda
	 * 
	 * @param idDomanda
	 * @return
	 * @throws ServiziFindomWebException
	 */
	public ArrayList<AggrDataDto> getAggrDataByIdDomanda(Integer idDomanda) throws ServiziFindomWebException;
	
	/**
	 * Jira: 1332-step4/5 : 2R
	 * @param cfBeneficiario
	 * @return
	 * @throws ServiziFindomWebException
	 * - findomrouter
	 */
	public String getDenominazioneByCodiceFiscale(String cfBeneficiario) throws ServiziFindomWebException;	
	
	/**
	 * Jira: 1381: in elaborazione - 2R 
	 * - verifica domande concluse by cf beneficiario
	 * 
	 * @param cfBeneficiario
	 * @return
	 * @throws ServiziFindomWebException
	 */
	public int cntDomandeDaInviareByCF(Integer idSoggettoCreatore, Integer idSoggettoBeneficiario) throws ServiziFindomWebException;
	
	/**
	 * MB2019_04_18 gestione dei beneficiari aventi codice fiscale estero: metodo che restituisce la lista degli stati esteri
	 * @param esclusaItalia: se true, nella lista ritornata non compare l'Italia
	 * @return
	 * @throws ServiziFindomWebException
	 */
	public ArrayList<StatoEsteroDto> getStatoEsteroList(boolean esclusaItalia) throws ServiziFindomWebException;
	
	/**
	 * MB2019_04_18 gestione dei beneficiari aventi codice fiscale estero: metodo che restituisce i dati di uno stato estero data la sua descrzione
	 * @param descrizione
	 * @return
	 * @throws ServiziFindomWebException
	 */
	public StatoEsteroDto getStatoEsteroByDescrizione(String descrizione) throws ServiziFindomWebException;

	/**
	 *  MB2019_04_18 gestione dei beneficiari aventi codice fiscale estero: aggiorna il campo sigla nazione in shell_t_soggetti del record avente 
	 * l'idSoggetto passato nel parametro
	 * @param idSoggetto
	 * @param statoEstero
	 * @throws ServiziFindomWebException
	 */
	public void updateNazioneSoggettoByIdSoggetto(Integer idSoggetto, String statoEstero) throws ServiziFindomWebException;

	/**
	 * Estraggo i dati dalla vista findom_v_sportelli_attivi
	 * @param idNormativa
	 * @param idBando
	 * @param statoEstero , se null o '000' restituisce tutti gli sportelli (non solo quelli di ditte italiane)
	 * @param idAreaTematica 
	 * @return
	 * @throws ServiziFindomWebException
	 */
	public ArrayList<VistaSportelliAttiviDto> getVistaSportelliAttiviByFilter(Integer idNormativa, Integer idBando, String statoEstero, Integer idAreaTematica) throws ServiziFindomWebException;

	/**
	 * Estraggo la lista degli enti/imprese per cui il soggetto ha presentato almeno una domanda
	 * @param idSoggetto
	 * @return
	 * @throws ServiziFindomWebException
	 */
	public ArrayList<ImpresaEnte> getListaImprese(int idSoggetto) throws ServiziFindomWebException;

	/**
	 * Estraggo tutte le regole associate ad un dato sportello
	 * @param idSportello
	 * @return
	 * @throws ServiziFindomWebException
	 */
	public ArrayList<Regola> getRegoleFromDB(int idTipoRegola, int idBando) throws ServiziFindomWebException;


	public ArrayList<ParametriRegola> getParametriRegola(Integer idBando, int idRegola) throws ServiziFindomWebException;


	public Boolean getFlagUploadIndex(Long idBando) throws ServiziFindomWebException;
}

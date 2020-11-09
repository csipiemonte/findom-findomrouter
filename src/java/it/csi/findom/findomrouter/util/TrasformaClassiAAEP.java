/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.util;

import it.csi.aaep.aaeporch.business.ImpresaINFOC;
import it.csi.findom.findomwebnew.dto.aaep.AttivitaEconomica;
import it.csi.findom.findomwebnew.dto.aaep.Carica;
import it.csi.findom.findomwebnew.dto.aaep.Cessazione;
import it.csi.findom.findomwebnew.dto.aaep.Contatti;
import it.csi.findom.findomwebnew.dto.aaep.DatoCostitutivo;
import it.csi.findom.findomwebnew.dto.aaep.DettagliAlboArtigiano;
import it.csi.findom.findomwebnew.dto.aaep.DettagliCameraCommercio;
import it.csi.findom.findomwebnew.dto.aaep.Impresa;
import it.csi.findom.findomwebnew.dto.aaep.Persona;
import it.csi.findom.findomwebnew.dto.aaep.ProcConcors;
import it.csi.findom.findomwebnew.dto.aaep.Sede;
import it.csi.findom.findomwebnew.dto.aaep.SezioneSpeciale;
import it.csi.findom.findomwebnew.dto.aaep.Ubicazione;
import it.csi.findom.findomrouter.presentation.util.Constants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

public class TrasformaClassiAAEP {

	protected static final Logger log = Logger.getLogger(Constants.APPLICATION_CODE + ".util");
	private static final String CLASS_NAME = "TrasformaClassiAAEP";

	/**
	 * Trasformo l'oggetto impresaINFOC che arriva, dai WS di AAEP, in un
	 * oggetto serializzabile
	 * 
	 * @param impresa
	 * @return
	 */
	public static Impresa impresaINFOC2ImpresaI(ImpresaINFOC imprINFOC) {
		final String methodName = "impresaINFOC2ImpresaI";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		log.debug(logprefix + " BEGIN");

		Impresa impr = null;

		if (imprINFOC != null) {
			impr = new Impresa();
			impr.setCessazione(cessazioneAAEP2cessazione(imprINFOC.getCessazione()));
			impr.setCodiceFiscale(imprINFOC.getCodiceFiscale());
			impr.setCodiciATECO(attivitaEconomicaAAEP2attivitaEconomica(imprINFOC.getCodiciATECO()));
			impr.setCodNaturaGiuridica(imprINFOC.getCodNaturaGiuridica());
			impr.setDataAggiornamento(imprINFOC.getDataAggiornamento());
			impr.setDataInizioValidita(imprINFOC.getDataInizioValidita());
			impr.setDescrFonte(imprINFOC.getDescrFonte());
			impr.setDescrNaturaGiuridica(imprINFOC.getDescrNaturaGiuridica());
			impr.setDettagliAlboArtigiano(dettaglioAAAAEP2dettaglioAA(imprINFOC.getDettagliAlboArtigiano()));
			impr.setDettagliCameraCommercio(dettaglioCCAAEP2dettaglioCC(imprINFOC.getDettagliCameraCommercio()));
			impr.setIdAzienda(imprINFOC.getIdAzienda());
			impr.setIdFonte(imprINFOC.getIdFonte());
			impr.setIdNaturaGiuridica(imprINFOC.getIdNaturaGiuridica());
			impr.setIdSede(imprINFOC.getIdSede());
			impr.setPartitaIva(imprINFOC.getPartitaIva());
			impr.setPostaElettronicaCertificata(imprINFOC.getPostaElettronicaCertificata());
			impr.setRagioneSociale(imprINFOC.getRagioneSociale());
			impr.setSedi(listaSediAAEP2listaSedi(imprINFOC.getSedi()));
			impr.setAnnoDenunciaAddetti(imprINFOC.getAnnoDenunciaAddetti());
			impr.setCessazioneFunzioneSedeLegale(
					cessazioneAAEP2cessazione(imprINFOC.getCessazioneFunzioneSedeLegale()));
			impr.setCodFonte(imprINFOC.getCodFonte());
			impr.setDatoCostitutivo(datoCostitutivoAAEP2datoCostitutivo(imprINFOC.getDatoCostitutivo()));
			impr.setDescrIndicStatoAttiv(imprINFOC.getDescrIndicStatoAttiv());
			impr.setDescrIndicTrasfSede(imprINFOC.getDescrIndicTrasfSede());
			impr.setFlagLocalizzazionePiemonte(imprINFOC.getFlagLocalizzazionePiemonte());
			impr.setFlgAggiornamento(imprINFOC.getFlgAggiornamento());
			impr.setImpresaCessata(imprINFOC.getImpresaCessata());
			impr.setIndicStatoAttiv(imprINFOC.getIndicStatoAttiv());
			impr.setIndicTrasfSede(imprINFOC.getIndicTrasfSede());
			impr.setListaPersone(listaPersoneAAEP2listaPersone(imprINFOC.getListaPersone()));
			impr.setListaProcConcors(listaProcConcorsAAEP2listaProcConcors(imprINFOC.getListaProcConcors()));
			impr.setListaSezSpecInfoc(listaSezioneSpecialeAAEP2listaSezioneSpeciale(imprINFOC.getListaSezSpecInfoc()));
			impr.setNumAddettiFam(imprINFOC.getNumAddettiFam());
			impr.setNumAddettiSubord(imprINFOC.getNumAddettiSubord());
			impr.setTestoOggettoSociale(imprINFOC.getTestoOggettoSociale());
		}
		log.debug(logprefix + " END");
		return impr;
	}

	private static Cessazione cessazioneAAEP2cessazione(it.csi.aaep.aaeporch.business.Cessazione cessAAEP) {
		final String methodName = "cessazioneAAEP2cessazione";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		log.debug(logprefix + " BEGIN");

		Cessazione cess = null;
		if (cessAAEP != null) {
			cess = new Cessazione();
			cess.setCessazione(cessAAEP.isCessazione());
			cess.setCodCausaleCessazione(cessAAEP.getCodCausaleCessazione());
			cess.setDataCessazione(cessAAEP.getDataCessazione());
			cess.setDataDenunciaCessazione(cessAAEP.getDataDenunciaCessazione());
			cess.setDescrCausaleCessazione(cessAAEP.getDescrCausaleCessazione());
		}
		log.debug(logprefix + " END");
		return cess;
	}

	private static List<AttivitaEconomica> attivitaEconomicaAAEP2attivitaEconomica(
			List<it.csi.aaep.aaeporch.business.AttivitaEconomica> listAttEcoAAEP) {
		final String methodName = "attivitaEconomicaAAEP2attivitaEconomica";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		log.debug(logprefix + " BEGIN");

		List<AttivitaEconomica> listaAE = null;
		if (listAttEcoAAEP != null) {
			listaAE = new ArrayList<AttivitaEconomica>();
			for (Iterator itr = listAttEcoAAEP.iterator(); itr.hasNext();) {
				it.csi.aaep.aaeporch.business.AttivitaEconomica attEcoAAEP = (it.csi.aaep.aaeporch.business.AttivitaEconomica) itr
						.next();

				AttivitaEconomica attEc = new AttivitaEconomica();

				attEc.setAnnoDiRiferimento(attEcoAAEP.getAnnoDiRiferimento());
				attEc.setCodiceATECO(attEcoAAEP.getCodiceATECO());
				attEc.setCodImportanzaAA(attEcoAAEP.getCodImportanzaAA());
				attEc.setCodImportanzaRI(attEcoAAEP.getCodImportanzaRI());
				attEc.setDataCessazione(attEcoAAEP.getDataCessazione());
				attEc.setDataInizio(attEcoAAEP.getDataInizio());
				attEc.setDescrImportanzaAA(attEcoAAEP.getDescrImportanzaAA());
				attEc.setDescrImportanzaRI(attEcoAAEP.getDescrImportanzaRI());
				attEc.setDescrizione(attEcoAAEP.getDescrizione());

				listaAE.add(attEc);
			}
		}
		log.debug(logprefix + " END");
		return listaAE;
	}

	private static DettagliAlboArtigiano dettaglioAAAAEP2dettaglioAA(
			it.csi.aaep.aaeporch.business.DettagliAlboArtigiano dettaglioAAAAEP) {
		final String methodName = "dettaglioAAAAEP2dettaglioAA";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		log.debug(logprefix + " BEGIN");

		DettagliAlboArtigiano dett = null;
		if (dettaglioAAAAEP != null) {
			dett = new DettagliAlboArtigiano();
			dett.setDataDeliberaIscrizione(dettaglioAAAAEP.getDataDeliberaIscrizione());
			dett.setDescrIterIscrizione(dettaglioAAAAEP.getDescrIterIscrizione());
			dett.setFlgIterIscrizione(dettaglioAAAAEP.getFlgIterIscrizione());
			dett.setNumeroIscrizione(dettaglioAAAAEP.getNumeroIscrizione());
			dett.setProvinciaIscrizione(dettaglioAAAAEP.getProvinciaIscrizione());
		}

		log.debug(logprefix + " END");
		return dett;
	}

	private static DettagliCameraCommercio dettaglioCCAAEP2dettaglioCC(
			it.csi.aaep.aaeporch.business.DettagliCameraCommercio dettagliCCAAEP) {
		final String methodName = "dettaglioCCAAEP2dettaglioCC";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		log.debug(logprefix + " BEGIN");

		DettagliCameraCommercio dett = null;
		if (dettagliCCAAEP != null) {
			dett = new DettagliCameraCommercio();

			dett.setAnno(dettagliCCAAEP.getAnno());
			dett.setDataAggiornamento(dettagliCCAAEP.getDataAggiornamento());
			dett.setDataCancellazioneREA(dettagliCCAAEP.getDataCancellazioneREA());
			dett.setDataIscrizioneREA(dettagliCCAAEP.getDataIscrizioneREA());
			dett.setDataIscrizioneRegistroImprese(dettagliCCAAEP.getDataIscrizioneRegistroImprese());
			dett.setNumero(dettagliCCAAEP.getNumero());
			dett.setNumIscrizionePosizioneREA(dettagliCCAAEP.getNumIscrizionePosizioneREA());
			dett.setNumRegistroImprese(dettagliCCAAEP.getNumRegistroImprese());
			dett.setProvincia(dettagliCCAAEP.getProvincia());
			dett.setSiglaProvincia(dettagliCCAAEP.getSiglaProvincia());
			dett.setSiglaProvinciaIscrizioneREA(dettagliCCAAEP.getSiglaProvinciaIscrizioneREA());
			dett.setTribunale(dettagliCCAAEP.getTribunale());
		}
		log.debug(logprefix + " END");
		return dett;
	}

	private static List<Sede> listaSediAAEP2listaSedi(List<it.csi.aaep.aaeporch.business.Sede> sediList) {
		final String methodName = "listaSediAAEP2listaSedi";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		log.debug(logprefix + " BEGIN");

		List<Sede> lista = null;
		if (sediList != null) {
			lista = new ArrayList<Sede>();

			for (Iterator itr = sediList.iterator(); itr.hasNext();) {
				it.csi.aaep.aaeporch.business.Sede sedeAAEP = (it.csi.aaep.aaeporch.business.Sede) itr.next();
				lista.add(sedeINFOC2Sede(sedeAAEP));
			}
		}
		log.debug(logprefix + " END");
		return lista;
	}

	private static Contatti contattiAAEP2contatti(it.csi.aaep.aaeporch.business.Contatti contattiAAEP) {
		final String methodName = "contattiAAEP2contatti";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		log.debug(logprefix + " BEGIN");

		Contatti cont = null;
		if (contattiAAEP != null) {
			cont = new Contatti();

			cont.setEmail(contattiAAEP.getEmail());
			cont.setFax(contattiAAEP.getFax());
			cont.setNumeroVerde(contattiAAEP.getNumeroVerde());
			cont.setSitoWeb(contattiAAEP.getSitoWeb());
			cont.setTelefono(contattiAAEP.getTelefono());
		}
		log.debug(logprefix + " END");
		return cont;
	}

	private static Ubicazione ubicazioneAAEP2ubicazione(it.csi.aaep.aaeporch.business.Ubicazione ubicazioneAAEP) {
		final String methodName = "ubicazioneAAEP2ubicazione";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		log.debug(logprefix + " BEGIN");

		Ubicazione ubi = null;
		if (ubicazioneAAEP != null) {
			ubi = new Ubicazione();
			ubi.setAltreIndicazioni(ubicazioneAAEP.getAltreIndicazioni());
			ubi.setCap(ubicazioneAAEP.getCap());
			ubi.setCodCatastale(ubicazioneAAEP.getCodCatastale());
			ubi.setCodISTATComune(ubicazioneAAEP.getCodISTATComune());
			ubi.setCodNazione(ubicazioneAAEP.getCodNazione());
			ubi.setCodQuartiere(ubicazioneAAEP.getCodQuartiere());
			ubi.setCoordinataX(ubicazioneAAEP.getCoordinataX());
			ubi.setCoordinataY(ubicazioneAAEP.getCoordinataY());
			ubi.setDescrComune(ubicazioneAAEP.getDescrComune());
			ubi.setDescrizioneEstesa(ubicazioneAAEP.getDescrizioneEstesa());
			ubi.setGeometriaGJSON(ubicazioneAAEP.getGeometriaGJSON());
			ubi.setIndirizzo(ubicazioneAAEP.getIndirizzo());
			ubi.setNomeNazione(ubicazioneAAEP.getNomeNazione());
			ubi.setNumeroCivico(ubicazioneAAEP.getNumeroCivico());
			ubi.setSiglaProvincia(ubicazioneAAEP.getSiglaProvincia());
			ubi.setToponimo(ubicazioneAAEP.getToponimo());
		}
		log.debug(logprefix + " END");
		return ubi;
	}

	private static DatoCostitutivo datoCostitutivoAAEP2datoCostitutivo(
			it.csi.aaep.aaeporch.business.DatoCostitutivo datoAAEP) {
		final String methodName = "datoCostitutivoAAEP2datoCostitutivo";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		log.debug(logprefix + " BEGIN");

		DatoCostitutivo dato = null;
		if (datoAAEP != null) {
			dato = new DatoCostitutivo();

			dato.setCapitaleSocDelib(datoAAEP.getCapitaleSocDelib());
			dato.setCapitalesocVers(datoAAEP.getCapitalesocVers());
			dato.setCapitSocDeliberato(datoAAEP.getCapitSocDeliberato());
			dato.setCapitSocSottoscr(datoAAEP.getCapitSocSottoscr());
			dato.setCapitSocVersato(datoAAEP.getCapitSocVersato());
			dato.setCodDurataCS(datoAAEP.getCodDurataCS());
			dato.setCodFormaAmministr(datoAAEP.getCodFormaAmministr());
			dato.setCodTipoAtto(datoAAEP.getCodTipoAtto());
			dato.setCodTipoConferim(datoAAEP.getCodTipoConferim());
			dato.setDataCostituzione(datoAAEP.getDataCostituzione());
			dato.setDataFineEsAmmt(datoAAEP.getDataFineEsAmmt());
			dato.setDataFondazione(datoAAEP.getDataFondazione());
			dato.setDataIniEsAmmt(datoAAEP.getDataIniEsAmmt());
			dato.setDataRegAtto(datoAAEP.getDataRegAtto());
			dato.setDataScadenzaPrimoEsercizio(datoAAEP.getDataScadenzaPrimoEsercizio());
			dato.setDataTermineSocieta(datoAAEP.getDataTermineSocieta());
			dato.setDescrFormaAmministr(datoAAEP.getDescrFormaAmministr());
			dato.setDescrProvUffReg(datoAAEP.getDescrProvUffReg());
			dato.setDescrSiglaProvNotaio(datoAAEP.getDescrSiglaProvNotaio());
			dato.setDescrTipoAtto(datoAAEP.getDescrTipoAtto());
			dato.setDescrTipoConferim(datoAAEP.getDescrTipoConferim());
			dato.setFlagDurataIllimitata(datoAAEP.getFlagDurataIllimitata());
			dato.setLocalitaNotaio(datoAAEP.getLocalitaNotaio());
			dato.setNotaio(datoAAEP.getNotaio());
			dato.setNumAnniEsAmmt(datoAAEP.getNumAnniEsAmmt());
			dato.setNumAzioniCapitSociale(datoAAEP.getNumAzioniCapitSociale());
			dato.setNumMaxMembriCS(datoAAEP.getNumMaxMembriCS());
			dato.setNumMembriCSCarica(datoAAEP.getNumMembriCSCarica());
			dato.setNumMinMembriCS(datoAAEP.getNumMinMembriCS());
			dato.setNumRegAtto(datoAAEP.getNumRegAtto());
			dato.setNumRepertorio(datoAAEP.getNumRepertorio());
			dato.setNumSoci(datoAAEP.getNumSoci());
			dato.setNumSociCarica(datoAAEP.getNumSociCarica());
			dato.setScadenzaEsSucc(datoAAEP.getScadenzaEsSucc());
			dato.setSiglaProvNotaio(datoAAEP.getSiglaProvNotaio());
			dato.setSiglaProvUffRegistro(datoAAEP.getSiglaProvUffRegistro());
			dato.setTotFondoConsortE(datoAAEP.getTotFondoConsortE());
			dato.setTotFondoConsortile(datoAAEP.getTotFondoConsortile());
			dato.setTotQuoteCapitSoc(datoAAEP.getTotQuoteCapitSoc());
			dato.setTotQuoteCapitSocE(datoAAEP.getTotQuoteCapitSocE());
			dato.setUfficioRegistro(datoAAEP.getUfficioRegistro());
			dato.setValAzioniCapitSoc(datoAAEP.getValAzioniCapitSoc());
			dato.setValoreAzioniCapitSoc(datoAAEP.getValoreAzioniCapitSoc());
			dato.setValutaCapitale(datoAAEP.getValutaCapitale());
			dato.setValutaCapitSociale(datoAAEP.getValutaCapitSociale());
		}
		log.debug(logprefix + " END");
		return dato;
	}

	private static List<Persona> listaPersoneAAEP2listaPersone(
			List<it.csi.aaep.aaeporch.business.Persona> listaPersone) {
		final String methodName = "listaPersoneAAEP2listaPersone";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		log.debug(logprefix + " BEGIN");

		List<Persona> lista = null;
		if (listaPersone != null) {
			lista = new ArrayList<Persona>();

			for (Iterator itr = listaPersone.iterator(); itr.hasNext();) {
				it.csi.aaep.aaeporch.business.Persona persAAEP = (it.csi.aaep.aaeporch.business.Persona) itr.next();

				Persona persona = new Persona();
				persona.setCodiceFiscale(persAAEP.getCodiceFiscale());
				persona.setCognome(persAAEP.getCognome());
				persona.setDescrTipoPersona(persAAEP.getDescrTipoPersona());
				persona.setIdAzienda(persAAEP.getIdAzienda());
				persona.setIdFonteDato(persAAEP.getIdFonteDato());
				persona.setIdPersona(persAAEP.getIdPersona());
				persona.setListaCariche(listaCaricaAAEP2listaCarica(persAAEP.getListaCariche()));
				persona.setNome(persAAEP.getNome());
				persona.setTipoPersona(persAAEP.getTipoPersona());
				lista.add(persona);
			}
		}
		log.debug(logprefix + " END");
		return lista;
	}

	private static List<Carica> listaCaricaAAEP2listaCarica(List<it.csi.aaep.aaeporch.business.Carica> listaAAEP) {
		final String methodName = "listaCaricaAAEP2listaCarica";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		log.debug(logprefix + " BEGIN");

		List<Carica> lista = null;
		if (listaAAEP != null) {

			lista = new ArrayList<Carica>();
			for (Iterator itr = listaAAEP.iterator(); itr.hasNext();) {
				it.csi.aaep.aaeporch.business.Carica caricaAAEP = (it.csi.aaep.aaeporch.business.Carica) itr.next();

				Carica carica = new Carica();
				carica.setCodCarica(caricaAAEP.getCodCarica());
				carica.setCodDurataCarica(caricaAAEP.getCodDurataCarica());
				carica.setCodFiscaleAzienda(caricaAAEP.getCodFiscaleAzienda());
				carica.setCodFiscalePersona(caricaAAEP.getCodFiscalePersona());
				carica.setDataFineCarica(caricaAAEP.getDataFineCarica());
				carica.setDataInizioCarica(caricaAAEP.getDataInizioCarica());
				carica.setDataPresentazCarica(caricaAAEP.getDataPresentazCarica());
				carica.setDescrAzienda(caricaAAEP.getDescrAzienda());
				carica.setDescrCarica(caricaAAEP.getDescrCarica());
				carica.setDescrDurataCarica(caricaAAEP.getDescrDurataCarica());
				carica.setFlagRappresentanteLegale(caricaAAEP.getFlagRappresentanteLegale());
				carica.setIdAzienda(caricaAAEP.getIdAzienda());
				carica.setIdFonteDato(caricaAAEP.getIdFonteDato());
				carica.setNumAnniEsercCarica(caricaAAEP.getNumAnniEsercCarica());
				carica.setProgrCarica(caricaAAEP.getProgrCarica());
				carica.setProgrPersona(caricaAAEP.getProgrPersona());
				lista.add(carica);
			}
		}
		log.debug(logprefix + " END");
		return lista;
	}

	private static List<ProcConcors> listaProcConcorsAAEP2listaProcConcors(
			List<it.csi.aaep.aaeporch.business.ProcConcors> listaPCAAEP) {
		final String methodName = "listaProcConcorsAAEP2listaProcConcors";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		log.debug(logprefix + " BEGIN");

		List<ProcConcors> lista = null;
		if (listaPCAAEP != null) {
			lista = new ArrayList<ProcConcors>();

			for (Iterator itr = listaPCAAEP.iterator(); itr.hasNext();) {
				it.csi.aaep.aaeporch.business.ProcConcors procCAAEP = (it.csi.aaep.aaeporch.business.ProcConcors) itr
						.next();

				ProcConcors pr = new ProcConcors();
				pr.setCodAtto(procCAAEP.getCodAtto());
				pr.setCodLiquidazione(procCAAEP.getCodLiquidazione());
				pr.setDataAperturaProc(procCAAEP.getDataAperturaProc());
				pr.setDataChiusuraLiquidaz(procCAAEP.getDataChiusuraLiquidaz());
				pr.setDataEsecConcordPrevent(procCAAEP.getDataEsecConcordPrevent());
				pr.setDataFineLiquidaz(procCAAEP.getDataFineLiquidaz());
				pr.setDataRegistroAtto(procCAAEP.getDataRegistroAtto());
				pr.setDataRevocalLiquidaz(procCAAEP.getDataRevocalLiquidaz());
				pr.setDescIndicatEsecutAtto(procCAAEP.getDescIndicatEsecutAtto());
				pr.setDescrAltreIndicazioni(procCAAEP.getDescrAltreIndicazioni());
				pr.setDescrCodAtto(procCAAEP.getDescrCodAtto());
				pr.setDescrNotaio(procCAAEP.getDescrNotaio());
				pr.setDescrTribunale(procCAAEP.getDescrTribunale());
				pr.setIdAAEPAzienda(procCAAEP.getIdAAEPAzienda());
				pr.setIdAAEPFonteDato(procCAAEP.getIdAAEPFonteDato());
				pr.setLocalRegistroAtto(procCAAEP.getLocalRegistroAtto());
				pr.setNumRestistrAtto(procCAAEP.getNumRestistrAtto());
				pr.setProgrLiquidazione(procCAAEP.getProgrLiquidazione());
				pr.setSiglaProvRegAtto(procCAAEP.getSiglaProvRegAtto());
				lista.add(pr);
			}
		}
		log.debug(logprefix + " END");
		return lista;
	}

	private static List<SezioneSpeciale> listaSezioneSpecialeAAEP2listaSezioneSpeciale(
			List<it.csi.aaep.aaeporch.business.SezioneSpeciale> listaSSIAAEP) {
		final String methodName = "listaSezioneSpecialeAAEP2listaSezioneSpeciale";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		log.debug(logprefix + " BEGIN");

		List<SezioneSpeciale> lista = null;
		if (listaSSIAAEP != null) {
			lista = new ArrayList<SezioneSpeciale>();

			for (Iterator itr = listaSSIAAEP.iterator(); itr.hasNext();) {
				it.csi.aaep.aaeporch.business.SezioneSpeciale sezAAEP = (it.csi.aaep.aaeporch.business.SezioneSpeciale) itr
						.next();

				SezioneSpeciale sez = new SezioneSpeciale();
				sez.setCodAlbo(sezAAEP.getCodAlbo());
				sez.setCodiceSezSpec(sezAAEP.getCodiceSezSpec());
				sez.setCodSezione(sezAAEP.getCodSezione());
				sez.setDataFine(sezAAEP.getDataFine());
				sez.setDataInizio(sezAAEP.getDataInizio());
				sez.setFlColtDir(sezAAEP.getFlColtDir());
				sez.setIdAzienda(sezAAEP.getIdAzienda());
				sez.setIdFonteDato(sezAAEP.getIdFonteDato());
				sez.setIdSezioneSpeciale(sezAAEP.getIdSezioneSpeciale());
				lista.add(sez);
			}
		}

		log.debug(logprefix + " END");
		return lista;
	}

	public static Sede sedeINFOC2Sede(it.csi.aaep.aaeporch.business.Sede sedeAAEP) {
		Sede sede = new Sede();
		sede.setAteco(attivitaEconomicaAAEP2attivitaEconomica(sedeAAEP.getAteco()));
		for (int i = 0; i < sede.getAteco().size(); i++) {
			if (sede.getAteco().get(i).getAnnoDiRiferimento().equals("2007")
				&& sede.getAteco().get(i).getCodImportanzaRI().equals("P")) {
				sede.setCodiceAteco2007(sede.getAteco().get(i).getCodiceATECO());
				sede.setDescrizioneAteco2007(sede.getAteco().get(i).getDescrizione());
				break;
			}
		}
		sede.setCodCausaleCessazione(sedeAAEP.getCodCausaleCessazione());
		sede.setCodSettore(sedeAAEP.getCodSettore());
		sede.setContatti(contattiAAEP2contatti(sedeAAEP.getContatti()));
		sede.setDataAggiornam(sedeAAEP.getDataAggiornam());
		sede.setDataCessazione(sedeAAEP.getDataCessazione());
		sede.setDataInizioAttivita(sedeAAEP.getDataInizioAttivita());
		sede.setDataInizioValidita(sedeAAEP.getDataInizioValidita());
		sede.setDataNumeroDipendenti(sedeAAEP.getDataNumeroDipendenti());
		sede.setDenominazione(sedeAAEP.getDenominazione());
		sede.setDescrCausaleCessazione(sedeAAEP.getDescrCausaleCessazione());
		sede.setDescrSettore(sedeAAEP.getDescrSettore());
		sede.setDescrTipoSede(sedeAAEP.getDescrTipoSede());
		sede.setFonteDato(sedeAAEP.getFonteDato());
		sede.setIdAzienda(sedeAAEP.getIdAzienda());
		sede.setIdSede(sedeAAEP.getIdSede());
		sede.setNumeroDipendenti(sedeAAEP.getNumeroDipendenti());
		sede.setRiferimento(sedeAAEP.getRiferimento());
		sede.setTipoSede(sedeAAEP.getTipoSede());
		sede.setUbicazione(ubicazioneAAEP2ubicazione(sedeAAEP.getUbicazione()));
		return sede;
	}

}

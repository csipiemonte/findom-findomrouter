/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import it.csi.findom.findomrouter.dto.serviziFindomWeb.FormeGiuridicheDto;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.ShellSoggettiDto;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.StatoDomandaDto;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.VistaDomandeDto;
import it.csi.findom.findomrouter.presentation.util.Constants;
import it.csi.findom.findomrouter.presentation.vo.Domanda;
import it.csi.findom.findomrouter.presentation.vo.ImpresaEnte;
import it.csi.findom.findomrouter.presentation.vo.StatoDomanda;

public class TrasformaClassi {

	protected static final Logger log = Logger.getLogger(Constants.APPLICATION_CODE + ".util");
	private static final String CLASS_NAME = "TrasformaClassi";
	
	public static ArrayList<Domanda> vistaDomandaDto2Domanda(ArrayList<VistaDomandeDto> listaDto) {
		
		final String methodName = "vistaDomandaDto2Domanda";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";

		log.debug(logprefix + " BEGIN");
		
		ArrayList<Domanda> listaDomande = new ArrayList<Domanda>();
		
		for (VistaDomandeDto domandaDto : listaDto) {
			
			Domanda dd = new Domanda();
			dd.setIdDomanda(domandaDto.getIdDomanda());
			dd.setCodStatoDomanda(domandaDto.getCodStatoDomanda());
			dd.setStatoDomanda(domandaDto.getStatoDomanda());
			dd.setIdTipolBeneficiario(domandaDto.getIdTipolBeneficiario());
			dd.setDescrizioneTipolBeneficiario(domandaDto.getDescrizioneTipolBeneficiario());
			dd.setTipoEnteBeneficiario(domandaDto.getTipoEnteBeneficiario());
			dd.setCodStereotipo(domandaDto.getCodStereotipo());
			dd.setIdSoggettoCreatore(domandaDto.getIdSoggettoCreatore());
			dd.setCognomeSoggettoCreatore(domandaDto.getCognomeSoggettoCreatore());
			dd.setNomeSoggettoCreatore(domandaDto.getNomeSoggettoCreatore());
			dd.setDtCreazioneDomanda(domandaDto.getDtCreazioneDomanda());
			dd.setIdSoggettoInvio(domandaDto.getIdSoggettoInvio());
			dd.setCognomeSoggettoInvio(domandaDto.getCognomeSoggettoInvio());
			dd.setNomeSoggettoInvio(domandaDto.getNomeSoggettoInvio());
			dd.setDtInvioDomanda(domandaDto.getDtInvioDomanda());
			dd.setIdSoggettoBeneficiario(domandaDto.getIdSoggettoBeneficiario());
			dd.setDenominazioneSoggettoBeneficiario(domandaDto.getDenominazioneSoggettoBeneficiario());
			dd.setIdSportelloBando(domandaDto.getIdSportelloBando());
			dd.setNumAtto(domandaDto.getNumAtto());
			dd.setDtAtto(domandaDto.getDtAtto());
			dd.setDtAperturaSportello(domandaDto.getDtAperturaSportello());
			dd.setDtChiusuraSportello(domandaDto.getDtChiusuraSportello());
			dd.setIdBando(domandaDto.getIdBando());
			dd.setDescrBando(domandaDto.getDescrBando());
			dd.setDescrBreveBando(domandaDto.getDescrBreveBando());
			dd.setIdNormativa(domandaDto.getIdNormativa());
			dd.setNormativa(domandaDto.getNormativa());
			dd.setIdClassificazioneAssePrioritario(domandaDto.getIdClassificazioneAssePrioritario());
			dd.setCodiceAssePrioritario(domandaDto.getCodiceAssePrioritario());
			dd.setDescrizioneAssePrioritario(domandaDto.getDescrizioneAssePrioritario());
			dd.setIdClassificazionePrioritaInvestimento(domandaDto.getIdClassificazionePrioritaInvestimento());
			dd.setCodicePrioritaInvestimento(domandaDto.getCodicePrioritaInvestimento());
			dd.setDescrizionePrioritaInvestimento(domandaDto.getDescrizionePrioritaInvestimento());
			dd.setIdClassificazioneAzione(domandaDto.getIdClassificazioneAzione());
			dd.setCodiceAzione(domandaDto.getCodiceAzione());
			dd.setDescrizioneAzione(domandaDto.getDescrizioneAzione());
			dd.setFlagBandoDematerializzato(domandaDto.getFlagBandoDematerializzato());
			dd.setTipoFirma(domandaDto.getTipoFirma());

			String flgDemat = domandaDto.getFlagBandoDematerializzato();
			Date now = Calendar.getInstance().getTime();
			String statoRichiesta = null;
			Date dtTermineIntegrazione = domandaDto.getDtTermineIntegrazione();
			if (flgDemat != null && flgDemat.equals("S") && dtTermineIntegrazione != null)
				if (domandaDto.getFlagAbilitaIntegrazione() && dtTermineIntegrazione != null && (isEqualsDate(dtTermineIntegrazione, now) || dtTermineIntegrazione.after(now)))
					statoRichiesta = Constants.RICHIESTA_APERTA;
				else
					statoRichiesta = Constants.RICHIESTA_CHIUSA;

			dd.setStatoRichiesta(statoRichiesta);
			
			dd.setFlagProgettiComuni(domandaDto.getFlagProgettiComuni());
			dd.setRuoloCapofilaProgettoComune(domandaDto.getRuoloCapofilaProgettoComune());
			dd.setRuoloPartnerProgettoComune(domandaDto.getRuoloPartnerProgettoComune());
			
			
			listaDomande.add(dd);
		}
		
		log.debug(logprefix + " END");
		return listaDomande;
	}

	public static boolean isEqualsDate(Date date, Date date2) {
		if (date == null || date2 == null)
			return false;
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date);
		cal2.setTime(date2);
		return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
	}

	public static ArrayList<StatoDomanda> statoDomandaDto2StatoDomanda(ArrayList<StatoDomandaDto> listaStatiDomandeDto) {
		
		final String methodName = "vistaDomandaDto2Domanda";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";

		log.debug(logprefix + " BEGIN");
		
		ArrayList<StatoDomanda> listaStatiDomande = new ArrayList<StatoDomanda>();
		
		for (StatoDomandaDto statoDomandaDto : listaStatiDomandeDto) {
			
			StatoDomanda sd = new StatoDomanda();
			sd.setCodice(statoDomandaDto.getCodice());
			sd.setDataFineValidita(statoDomandaDto.getDataFineValidita());
			sd.setDataInizioValidita(statoDomandaDto.getDataInizioValidita());
			sd.setDescrizione(statoDomandaDto.getDescrizione());
			
			listaStatiDomande.add(sd);
		}
		
		log.debug(logprefix + " END");
		return listaStatiDomande;
	}

	public static ImpresaEnte soggettiDto2ImpresaEnte(ShellSoggettiDto shellSoggettiDto, ArrayList<FormeGiuridicheDto> listaFormeGiuridiche) {

		final String methodName = "soggettiDto2ImpresaEnte";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		log.debug(logprefix + " BEGIN");
		
		ImpresaEnte impEn = new ImpresaEnte();
		
		impEn.setCodiceFiscale(shellSoggettiDto.getCodiceFiscale());
		impEn.setIdFormaGiuridica(shellSoggettiDto.getIdFormaGiuridica());
		impEn.setCognome(shellSoggettiDto.getCognome());
		impEn.setNome(shellSoggettiDto.getNome());
		impEn.setDenominazione(shellSoggettiDto.getDenominazione());
		impEn.setIdSoggetto(shellSoggettiDto.getIdSoggetto());
		
		if(listaFormeGiuridiche!=null){
			for (FormeGiuridicheDto fg : listaFormeGiuridiche) {
				
				if(fg.getIdFormaGiuridica()==shellSoggettiDto.getIdFormaGiuridica()){
					impEn.setCodFormaGiuridica(fg.getCodFormaGiuridica());
					impEn.setDescrFormaGiuridica(fg.getDescrFormaGiuridica());
					impEn.setFlagPubblicoPrivato(fg.getFlagPubblicoPrivato());
					break;
				}
			}
		}
		log.debug(logprefix + " END");
		return impEn;
	}
	
	
}

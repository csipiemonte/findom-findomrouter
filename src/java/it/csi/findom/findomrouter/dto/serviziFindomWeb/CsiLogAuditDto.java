/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.dto.serviziFindomWeb;

import java.io.Serializable;
import java.util.Date;

/**
 * Oggetto di interfaccia per la tracciatura delle operazioni secondo lo statdard CSI
 *
 */
public class CsiLogAuditDto implements Serializable {

	static final long serialVersionUID = 1;
	
	// tabella CSI_LOG_AUDIT
	
	// Chiave della tabella
	private int idTraccia;
	
	// Data e ora dell'evento
	private Date dataOra;
	
	// Codice identificativo dell'applicazione utilizzata dall'utente; 
	// da comporre con i valori presenti in Anagrafica Prodotti: <codice prodotto>_<codice linea cliente>_<codice ambiente>_<codice Unita' di Installazione>
	private String idApplicazione;
	
	private String ipAddress;
	
	// Identificativo univoco dell'utente che ha effettuato l'operazione (es. login / codice fiscale / matricola / ecc.)';
	private String utente;
	
	// Questo campo dovra' contenere l'informazione circa l'operazione effettuata; 
	// utilizzare uno dei seguenti valori: login / logout / read / insert / update / delete / merge
	private String operazione;  
	
	// Questo campo dovra' contenere il nome della tabella (o dell'oggetto) su cui e' stata eseguita l'operazione; 
	// se la funzionalita' lo permette, inserire la combinazione tabella.colonna.
	// Se l'applicativo prevede accessi a schemi dati esterni, premettere il nome dello schema proprietario al nome dell'oggetto
	private String oggettoOperazione;
	
	// Questo campo dovra' contenere l'identificativo univoco dell'oggetto dell'operazione oppure, nel caso di aggiornamenti multipli, del valore che caratterizza 
	// l'insieme di oggetti (es: modifica di un dato in tutta una categoria merceologica)
	private String chiaveOperazione;

	
	// GETTERS && SETTERS
	
	public int getIdTraccia() {
		return idTraccia;
	}

	public void setIdTraccia(int idTraccia) {
		this.idTraccia = idTraccia;
	}

	public Date getDataOra() {
		return dataOra;
	}

	public void setDataOra(Date dataOra) {
		this.dataOra = dataOra;
	}

	public String getIdApplicazione() {
		return idApplicazione;
	}

	public void setIdApplicazione(String idApplicazione) {
		this.idApplicazione = idApplicazione;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getUtente() {
		return utente;
	}

	public void setUtente(String utente) {
		this.utente = utente;
	}

	public String getOperazione() {
		return operazione;
	}

	public void setOperazione(String operazione) {
		this.operazione = operazione;
	}

	public String getOggettoOperazione() {
		return oggettoOperazione;
	}

	public void setOggettoOperazione(String oggettoOperazione) {
		this.oggettoOperazione = oggettoOperazione;
	}

	public String getChiaveOperazione() {
		return chiaveOperazione;
	}

	public void setChiaveOperazione(String chiaveOperazione) {
		this.chiaveOperazione = chiaveOperazione;
	}
	
	
	
}

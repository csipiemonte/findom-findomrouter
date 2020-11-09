/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.util;


/**
 * <p>Classe delle costanti applicative.</p>
 *
 */
public class Constants {

	/**
	 * identificativo dell'applicativo.
	 */
	public static final String APPLICATION_CODE = "findomrouter";
	public static final String IRIDE2_APPLICATION = "FINDOMWEB";

	public static final String AUTH_ID_MARKER = "Shib-Iride-IdentitaDigitale";
	
	public static final String USERINFO_SESSIONATTR = "currentUser";
	public static final String IRIDE_ID_SESSIONATTR = "iride2_id";
	
	public static final String SESSION_KEY_INIT_CP = "S";
	public static final String FLAG_TRUE = "S";
	public static final String FLAG_FALSE = "N";
//	public final static String KEY_SERVICE = "KEY_SERVICE";

	public static final String CONTEXT_ATTR_NAME = "findomwebContext";
//	public static final String USER_INFO = "userInfo";
	public static final String STATUS_INFO = "statusInfo";
	public static final String INDEXTREE_NAME = "indexTree";
//	public static final String MESSAGES = "messages";  
	
	//public static final String IDENT_DIGITALE_MONITORAGGIO = "PRTMTR80A01L219F/OPERATORI/MONITORAGGIO";
	public static final String CF_MONITORAGGIO = "PRTMTR80A01L219F";
	public static final String CF_MONITORAGGIO_SYMON = "SYMGNT80A01L219B";
	
//	public static final String STATO_BOZZA = "BZ";
	public static final String STATO_INVIATA = "IN";
//	public static final String STATO_VALIDATAOK = "OK";
//	public static final String STATO_VALIDATAKO = "KO";
//	public static final String STATO_VALIDATAWO = "WO";
	public static final String STATO_CONCLUSA = "CO";
//	public static final String STATO_RICEVUTA = "RI"; // per l'istruttoria
	
//	public static final String ACCESS_FORM_RO = "ACCESS_RW_FORM";
//	public static final String ACCESS_FORM_RW = "ACCESS_RO_FORM";
//	public static final String ACCESS_FORM_PAGE = "ACCESS_PAGE";
	
	// valori per il logout/cambiaprofilo
	public static final String BUTTON_TXT_CONFERMA = "si, prosegui";
	public static final String ACTION_CONFIRMED = "confirmed";
	public static final String ACTION_NOTCONFIRMED = "notConfirmed";
	
	// feedback domanda
//	public static final String ELIMINA_DOMANDA = "La domanda &egrave; stata eliminata correttamente";
//	public static final String INVIO_DOMANDA = "La domanda &egrave; stata confermata. Ora &egrave; possibile premendo il tasto \"Stampa\", salvare il PDF, firmarlo e inviarlo tramite PEC (Posta Elettronica Certificata).";
//	
	public static final String ELIMINA_DOMANDA_FAILED = "ATTENZIONE!!!<br />si &egrave; verificato un errore, la domanda non &egrave; stata eliminata.";
//	public static final String ELIMINA_DOMANDA_ALLEGATI_INDEX_FAILED = "ATTENZIONE!!!<br />si &egrave; verificato un errore, la domanda &egrave; stata eliminata, ma non &egrave; stato possibile eliminare uno o pi&ugrave; allegati da Index ."; //MB2017_0424
//	public static final String ELIMINA_DOMANDA_FAILED_4STATE = "Impossibile eliminare una domanda in stato INVIATA";
//	public static final String ELIMINA_DOMANDA_NOTALLOWED = "L'utente non possiede i permessi per eliminare la domanda selezionata.";
//
	public static final String RIEPILOGO_DOMANDA_ERROR = "ATTENZIONE!!!<br />non &egrave; possibile visualizzare il dettaglio della domanda selezionata.";
//	
//	public static final String INVIO_DOMANDA_FAILED = "<p><span class='ui-icon ui-icon-alert' style='float: left; margin: 0 7px 20px 0;'></span> Invio domanda fallito. Contattare l'assistenza </p>";
//	public static final String CONCLUSIONE_DOMANDA_FAILED = "<p><span class='ui-icon ui-icon-alert' style='float: left; margin: 0 7px 20px 0;'></span> Conclusione domanda fallita. Contattare l'assistenza </p>";
//	public static final String INVIO_DOMANDA_NOTALLOWED = "<p><span class='ui-icon ui-icon-alert' style='float: left; margin: 0 7px 20px 0;'></span> Impossibile inviare la domanda.<br/>"
//														+ "L'utente non possiede i permessi per inviare la domanda selezionata.</p>";
//	public static final String INVIO_DOMANDA_NOTALLOWED_SPORT = "<p><span class='ui-icon ui-icon-alert' style='float: left; margin: 0 7px 20px 0;'></span>Impossibile inviare la domanda.<br/>"
//																+ "La data odierna non &egrave; compresa all'interno dello sportello valido per l'invio di questo bando.</p>";
//	
	public static final String CARICA_DOMANDA_FAILED = "Impossibile caricare la domanda selezionata.";
//	public static final String CARICA_DOMANDA_NOTALLOWED = "L'utente non possiede i permessi per visualizzare la domanda selezionata.";
	
	//public static final String MESSAGE_KEY_VALIDATION_INFO = "AGGR_messageValidationInfo";
//	public static final String MESSAGE_KEY_VALIDATION_WARN = "AGGR_messageValidationWarn";
//	public static final String MESSAGE_KEY_VALIDATION_ERROR = "AGGR_messageValidationError";
//	public static final String MESSAGE_SEPARATOR_FOR_SPLIT = ":";
	
	// messaggi errore
	public static final String ERR_MESSAGE_PARAM_ERROR = "<p>Attenzione!<br/> Verificare e correggere i campi evidenziati.</p>";
//	
	public static final String ERR_MESSAGE_FIELD_VOID = "valorizzare il campo";
	public static final String ERR_MESSAGE_FIELD_NOTALFA = "inserire solo caratteri alfanumerici";
	public static final String ERR_MESSAGE_FIELD_CFINVALID = "codice fiscale formalmente non corretto";
	public static final String ERR_MESSAGE_FIELD_NOTNUMERIC = "inserire solo caratteri numerici";
	public static final String ERR_MESSAGE_FIELD_OBBL = "il campo &egrave; obbligatorio";
//	
	public static final String ERR_MESSAGE_NUMDOMANDA_ERROR = "Il Numero Domanda inserito non risulta corretto.";
	public static final String ERR_MESSAGE_NUMDOMANDA_MSG = "inserire un numero";
//	
	public static final String ERR_MESSAGE_SELENTE_ERROR = "Selezionare almeno un'impresa o un ente";
	public static final String ERR_STRING_CFNEWENTE_ERROR = "cfNuovaImpresaError";
	public static final String ERR_MESSAGE_CF_ESTERO_MANCA_STATO = "selezionare lo stato estero di competenza";  //MB2019_04_18
	
	// messaggi errore creazione nuova Domanda
	public static final String ERR_MESSAGE_NEWDOMANDA_SOGGBEN = "<p>Attenzione!<br/> "+
			 "Impossibile creare la domanda, non &egrave; definito l'id del soggetto beneficiario.</p>";
	
//	public static final String ERR_MESSAGE_NEWDOMANDA_LISTDOMNULLA = "<p>Attenzione!<br/> "+
//			 "Impossibile creare la domanda, mancano alcuni parametri di configurazione sul database.</p>";
	
//	public static final String ERR_MESSAGE_NEWDOMANDA_TROPPEDOMANDE = "<p>Attenzione!<br/> "+
//			 "Impossibile creare una nuova domanda. Il numero delle domande presentate &egrave; pari al massimo consentito.</p>";
	
//	public static final String ERR_MESSAGE_NEWDOMANDA_DATIERROR = "<p>Attenzione!<br/> "+
//			 "Impossibile creare una nuova domanda. Si &egrave; verificato un errore nei dati di configurazione del Bando.</p>";

//	public static final String ERROR_CREAZIONE_DOMANDA = "<p>Attenzione!<br/> "+
//			"Impossibile creare una nuova domanda per il bando scelto. Errore nelle regole di creazione.</p>";

	// messaggi errore download pdf
	public static final String ERROR_LOAD_PDF = "Impossibile effettuare il download del file.";
	
	// messaggio sportello chiuso
//	public static final String ERR_MESSAGE_SPORTELLO_CHIUSO = "<p>Attenzione!<br/> Non &egrave; possibile aggiungere il documento firmato. Lo sportello del bando &egrave; chiuso.</p>";
	
	 
	/**
	 * Costanti CSI_LOG_AUDIT
	 */
	public static final String CSI_LOG_IDAPPL = "FINDOM_RP-01_PROD_FINDOMROUTER";
	public static final String CSI_LOG_OPER_LOGIN = "login";
	public static final String CSI_LOG_OPER_LOGOUT = "logout";
	public static final String CSI_LOG_WRONGPARAM = "parametri malevoli";
	public static final String CSI_LOG_OPER_CAMBIO_PROFILO = "cambio profilo";
	public static final String CSI_LOG_OPER_RELOAD_HOME = "ricaricamento home";
	public static final String CSI_LOG_OPER_SEL_IMPRESA = "selezione impresa";
	public static final String CSI_LOG_OPER_INS_IMPRESA = "inserimento impresa";
	public static final String CSI_LOG_OPER_INS_SOGGETTO = "inserimento soggetto";
//	public static final String CSI_LOG_OPER_SEL_DOMANDA = "selezione domanda";
//	public static final String CSI_LOG_OPER_INVIO_DOMANDA = "invio domanda";
	public static final String CSI_LOG_OPER_INSERT_DOMANDA = "creazione domanda";
//	public static final String CSI_LOG_OPER_DELETE_DOMANDA = "cancellazione domanda";
	

	// Ruoli
	public static final String RUOLO_AMM = "AMM"; //amministratore
	public static final String RUOLO_MON = "MON"; //monitoraggio
	public static final String RUOLO_LR = "LR"; //legale rappresentante
	public static final String RUOLO_NOR = "-"; //nessun ruolo specifico

	// oggetti in sessione : Map<> session
	public static final String SESSION_LISTAIMPRESE = "listaImpreseEntiToView";
	public static final String SESSION_ENTEIMPRESA = "enteImpresa";
	public static final String SESSION_SEDE_LEGALE = "sedeLegale";
	public static final String SESSION_DATIIPA = "datiIpa";
	
	// costanti per l'invio della domanda
//	public static final String XML_FIELD_DATATX = "_domanda.dataTrasmissione"; // nodo del  XML della domanda che contiene l'informazione "data d itrasmissione"
//	public static final String XML_FIELD_STATOSTAMPA = "_domanda.statoStampa"; // nodo del  XML della domanda che contiene l'informazione "stato della stampa"
//	public static final String XML_FIELD_VALUE_STATOSTAMPA = "DEFINITIVO"; // contenuto del nodo del  XML della domanda che contiene l'informazione "stato della stampa"
//
//	// costanti per la conclusione della domanda
//	public static final String XML_FIELD_DATACO = "_domanda.dataConclusione"; // nodo del  XML della domanda che contiene l'informazione "data di conclusione"	
	
	// PATH su INDEX
//	public static final String INDEX_REPOSITORY = "cm:findom";
//	public static final String INDEX_ROOTPATH = "/app:company_home/cm:findom";
//	public static final String INDEX_PREFIX = "/cm:";
	
	// Costanti per verifica firma digitale
//	public static final String VERIFICA_DEBOLE = "D";
//	public static final String VERIFICA_FORTE = "F";
//	public static final String VERIFICA_BLOCCANTE = "S";
//	public static final String VERIFICA_NON_BLOCCANTE = "N";
	
	// MESSAGGI ERRORE di INDEX
//	public static final String INDEX_ERROR = "index_error";
//	public static final String INDEX_ACTION_ERROR = "index_action_error";
//	public static final String INDEX_OK = "index_ok";
//	public static final String INDEX_ERROR_READ_MSG = "read_error";
//	public static final String INDEX_ERROR_PARAM_MSG = "param_error";
//	public static final String INDEX_ERROR_PARAM_NULL = "param_null_error";
//	public static final String INDEX_ERROR_DELETE_MSG = "delete_error";
//	public static final String INDEX_ERROR_INSERT_MSG = "insert_error";
//	public static final String INDEX_ERROR_FILE_NULL_MSG = "insert_error_file_null";
//	public static final String INDEX_ERROR_INSERT_EXSITING_MSG = "insert_exsisting_error";
//	public static final String INDEX_OK_INSERT_MSG = "insert_ok";
//	public static final String INDEX_OK_DELETE_MSG = "delete_ok";
//	public static final Integer ID_STATO_DOC_INDEX_GENERATO = 1;	
//	public static final Integer ID_STATO_DOC_INDEX_VALIDATO = 3;
//	public static final Integer ID_STATO_DOC_INDEX_DIFFERITO = 8;
	
	//
//	public static final String PARAMETRO_UPLOAD_FILE = "UPLOAD_FILE";
//	public static final String PARAMETRO_UPLOAD_FILE_FIRMA_DIGITALE = "FIRMA_DIGITALE";
//	public static final String ERROR_ESTENSIONE_FILE = "error_estensione_file";
//	
	public static final String FLAG_BANDO_DEMATERIALIZZATO = "S";
//	
//	public static final String FIRMA_DIGITALE = "D";
//	public static final String FIRMA_OLOGRAFA = "S"; //Olografa da scansione
//	
//	public static final String VERIFICA ="VERIFICA FIRMA";
//	public static final String VERIFICA_OK ="OK";
//	public static final String UPLOAD ="UPLOAD DOMANDA";
//	public static final String UPLOAD_OK ="OK";
//
//	public static final String UPLOAD_ERROR = "upload_error";
	
//	public static final String UPLOAD_DOMANDA_FAILED = "ATTENZIONE!!!<br />si &egrave; verificato un errore, non &egrave; possibile procedere.";
//	public static final String UPLOAD_DOMANDA_OK = "<b>Domanda inviata correttamente.<br />Entro le prossime 24 ore sar&agrave; disponibile, dalla pagina HOME selezionando l'icona \"Autore\" della domanda presentata, il numero di protocollo.</b>";
	
//	public static final String MAX_SIZE_SIGNED_FILE = "MAX_SIZE_SIGNED_FILE";
//	public static final String ERR_MSG_MAX_SIZE_SIGNED_FILE = "Il file eccede la dimensione massima consentita.";
//
//	public static final String SIGNEDFILE_EXTENSION = "SIGNEDFILE_EXTENSION";
//	
//	public static final String ERR_MSG_SIGNEDFILE_EXTENSION = "Caricare il file pdf della domanda, firmato digitalmente con estensione .p7m";
//	
//	public static final String ERR_MSG_NOME_FILE_MODIFICATO = "Il nome del file &egrave; stato modificato";
//	
//	public static final String ERR_MSG_MESSAGE_DIGEST = "Attenzione:<br/> la versione del documento &egrave; diversa da quella generata dal sistema. <br/>Cancella il pdf e scaricalo nuovamente. Non aprirlo e procedi a firmarlo direttamente per evitare involontarie manomissioni del file. ";
//	
//	public static final String ERR_MSG_VERIFICA_FIRMA = "Verifica firma digitale fallita. ";
//	
//	public static final String ERR_MSG_INDEX_INSERT = "Errore nel salvataggio del file su index. ";
//	public static final String ERR_MSG_INDEX_INSERT_DOMANDA = " Si è verificato un errore in fase di caricamento della domanda. Si consiglia di contattare l'assistenza.";
	
//	public static final String ERR_MSG_INDEX_INSERT_EXSITING_MSG = "File gi&agrave; presente su index. ";
//	public static final String ERR_MSG_INDEX_ERROR_FILE_NULL_MSG = "File vuoto";
//	
//	public static final String ERRORE_VERIFICA_BUSTA_CRITTOGRAFATA = "Conformit&agrave; e integrit&agrave; busta crittografica non valida. ";
//	public static final String ERRORE_VERIFICA_CONTENUTO_BUSTA_CRITTOGRAFATA = "Contenuto busta crittografica non valido. ";
//	public static final String ERRORE_VERIFICA_FIRMA_MANCANTE = "Firma non trovata. ";
//	public static final String ERRORE_VERIFICA_CONSISTENZA_FIRMA = "Consistenza firma non valida. ";
//	public static final String ERRORE_VERIFICA_VALIDITA_CERTIFICATO = "Certificato non valido. ";
//	public static final String ERRORE_VERIFICA_ENTE_CERTIFICATORE = "Ente certificatore non valido. ";
//	public static final String ERRORE_SERVIZI_VERIFICA_FIRMA = "Errore accesso ai servizi di verifica firma. ";
//	public static final String ERRORE_VERIFICA_LISTA_DI_REVOCA = "Errore in verifica della lista di revoca. ";
//	
//	public static final String ERRORE_VERIFICA_CODICE_FISCALE_FIRMATARIO = "Il codice fiscale del firmatario non corrisponde al codice fiscale del legale rappresentante, o del soggetto delegato, indicato in domanda.";

//	public static final String NODO_LEGALERAPPRESENTANTE = "_legaleRappresentante";
//	public static final String CAMPO_LEGALERAPPRESENTANTE_CF = "codiceFiscale";
//	
//	public static final String NODO_SOGGETTODELEGATO = "_soggettoDelegato";
//	public static final String CAMPO_SOGGETTODELEGATO_CF = "codiceFiscale";
//	
//	public static final String CODICE_STATO_ISTRUTTORIA_DA_PRENDERE_IN_CARICO = "RI";
//	
	public static final String COD_FASE_DOMANDA = "DOM_W";
	public static final String UTF8 = "UTF-8";

	public static final String RICHIESTA_CHIUSA = "C";
	public static final String RICHIESTA_APERTA = "A";
	
	public static final String PRESENTE_DOMANDA_DA_INVIARE = "<p><strong>Attenzione!</strong></p><p>E' presente almeno una domanda in stato 'da inviare'; per inviarla e' necessario scaricare il documento pdf della domanda tramite il pulsante 'stampa',<br/> firmarlo e ricaricarlo sul sistema tramite la funzionalita' di Upload.</p>";
	
}

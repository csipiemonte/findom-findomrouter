/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.action;

import it.csi.findom.findomrouter.exception.ServiziFindomWebException;
import it.csi.findom.findomrouter.presentation.util.Constants;
import it.csi.findom.findomrouter.presentation.vo.StatusInfo;
import it.csi.util.performance.StopWatch;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

 //TODO PK Eliminare

public class LoadPDFPropostaInviataAction extends BaseAction {
/*
	private static final long serialVersionUID = 1L;
	private static final String CLASS_NAME = "LoadPDFPropostaInviataAction";
	
	private InputStream pdfStream;
	private String contentDisposition;
	private Integer idDomanda;
*/
	@Override
	public String executeAction(){
		return SUCCESS;
	}
/*
		final String methodName = "executeAction";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";

		log.debug(logprefix + " BEGIN");
		StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
		
		stopWatch.start();
		log.debug(logprefix + "  idDomanda = [" + idDomanda + "]");

		byte[] filePdf;
		
		try {
			
			//filePdf non puo' essere nullo, e' un vincolo sul DB
			filePdf = getServiziFindomWeb().getPdfDomanda(idDomanda);
			log.debug(logprefix + "  filePdf letto");
			
			if(filePdf!=null && filePdf.length>0){
				
				StatusInfo state = getStatus();
				String nomeOggetto = "DomandaNumero["+idDomanda+"]-["+state.getCodFiscaleBeneficiario()+"]";
				contentDisposition = "attachment;filename=\"" + nomeOggetto + ".pdf\"";
				pdfStream = new ByteArrayInputStream(filePdf);
				
			}else{
				//file pdf nullo o vuoto
				log.error(logprefix + " file pdf nullo o vuoto");
				addActionError(Constants.ERROR_LOAD_PDF);
				return ERROR;
			}
			
		} catch (ServiziFindomWebException e) {
			log.error(logprefix + " ServiziFindomWebException:" + e.getMessage(), e);
			addActionError(Constants.ERROR_LOAD_PDF);
			return ERROR;
			
		} finally {
			stopWatch.stop();
			stopWatch.dumpElapsed("LoadPDFPropostaInviataAction", "executeAction()", "test", "test");
			
			log.debug(logprefix + "  END");
		}

		return SUCCESS;
	}
	
	
	// GETTERS && SETTERS

	public InputStream getPdfStream() {
		return pdfStream;
	}

	public void setPdfStream(InputStream pdfStream) {
		this.pdfStream = pdfStream;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}

	public Integer getIdDomanda() {
		return idDomanda;
	}

	public void setIdDomanda(Integer idDomanda) {
		this.idDomanda = idDomanda;
	}
	*/
}

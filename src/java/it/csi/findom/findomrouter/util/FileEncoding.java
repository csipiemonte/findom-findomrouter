/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.util;

import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;

import it.csi.findom.findomrouter.presentation.util.Constants;

public class FileEncoding {

	private static final String CLASS_NAME = "FileEncoding";
	
	protected static Logger log = Logger.getLogger(Constants.APPLICATION_CODE + ".util");
	
	public static String generateEncodedFile(String xsltTemplate, String xmlData) throws Exception{
		
		final String methodName = "generateEncodedFile";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		
		log.debug(logprefix + " BEGIN");
//		log.debug(logprefix + " xsltTemplate :" + xsltTemplate);
//		log.debug(logprefix + " xmlDomanda :" + xmlDomanda);
		
		String encodedFile = "";
		
		StringReader readerXML = new StringReader(xmlData);
	    StringReader readerXSLT = new StringReader(xsltTemplate);
	    
	    log.debug(logprefix + " letti StringReader di INPUT");
	    
	    StringWriter writer = new StringWriter();
	    
	    TransformerFactory tFactory = TransformerFactory.newInstance();
	    Transformer transformer;
	    
		try {
			transformer = tFactory.newTransformer(new StreamSource(readerXSLT));
			log.debug(logprefix + " valorizzato transformer");
			
			transformer.transform(new StreamSource(readerXML), new StreamResult(writer));
			log.debug(logprefix + " trasformata eseguita");
			
		} catch (TransformerConfigurationException tce) {
			log.error(logprefix + "TransformerConfigurationException: errore");
			tce.printStackTrace();
			throw new Exception(tce);
			
		} catch (TransformerException te) {
			log.error(logprefix + "TransformerException: errore");
			te.printStackTrace();
			throw new Exception(te);
		}
		
		encodedFile = writer.toString();
		
		//log.debug(logprefix + " encodedFile="+encodedFile);
		log.debug(logprefix + " END");
		return encodedFile;
	}
	
}

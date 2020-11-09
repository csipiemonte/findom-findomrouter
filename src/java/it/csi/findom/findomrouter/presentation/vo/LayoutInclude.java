/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.vo;

import static it.csi.findom.findomrouter.util.Utils.quote;

public class LayoutInclude implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	private String portalHead;
	private String portalHeader;
	private String applicationHeader;
	private String applicationLinkHelpContact;
	private String portalFooter;
	
	public String getPortalHeader() {
		return portalHeader;
	}

	public void setPortalHeader(String portalHeader) {
		this.portalHeader = portalHeader;
	}

	public String getApplicationHeader() {
		return applicationHeader;
	}

	public void setApplicationHeader(String applicationHeader) {
		this.applicationHeader = applicationHeader;
	}

	public String getPortalFooter() {
		return portalFooter;
	}

	public void setPortalFooter(String portalFooter) {
		this.portalFooter = portalFooter;
	}

	public String getPortalHead() {
		return portalHead;
	}

	public void setPortalHead(String portalHead) {
		this.portalHead = portalHead;
	}

	public String getApplicationLinkHelpContact() {
		return applicationLinkHelpContact;
	}

	public void setApplicationLinkHelpContact(String applicationLinkHelpContact) {
		this.applicationLinkHelpContact = applicationLinkHelpContact;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		//
		sb.append("\tportalHead:" + quote(portalHead) + "\n");
		sb.append("\tportalHeader:" + quote(portalHeader) + "\n");
		sb.append("\tapplicationHeader:" + quote(applicationHeader) + "\n");
		sb.append("\tapplicationLinkHelpContact:" + quote(applicationLinkHelpContact) + "\n");
		sb.append("\tportalFooter:" + quote(portalFooter) + "\n");

		return "LayoutInclude:[[\n" + sb.toString() + "]]";
	}
}

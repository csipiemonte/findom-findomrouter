/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

public class SessionSize {

	/**
	 * @param HttpSession httpsession
	 * @param JspWriter out
	 * @return int i
	 * @throws IOException
	 */
	public int getSize(HttpSession httpsession, JspWriter out) throws IOException {
		int i = 0;
		if (httpsession != null) {
			out.println("<table border=1 width=100%>");
			out.println("<tr><td colspan=2><font color=\"red\"><b>--- SESSION SIZE BYTE---</b></font></td></tr>");
			for (Enumeration<?> enumeration = httpsession.getAttributeNames(); enumeration.hasMoreElements();) {
				String s = (String) enumeration.nextElement();
				Object obj = httpsession.getAttribute(s);
				int temp = getObjectSize(s, obj, out);
				out.println("<tr><td>" + s + "</td><td>" + temp + "</td></tr>");
				i += temp;
			}
			out.println("</table>");
		}
		return i;
	}

	/**
	 * @param ServletContext context
	 * @param JspWriter out
	 * @return int i
	 * @throws IOException
	 */
	public int getSize(ServletContext context, JspWriter out) throws IOException {
		int i = 0;
		if (context != null) {
			out.println("<table border=1 width=100%>");
			out.println("<tr><td colspan=2><b>--- APPLICATION SIZE BYTE---</b></td></tr>");
			for (Enumeration<?> enumeration = context.getAttributeNames(); enumeration.hasMoreElements();) {
				String s = (String) enumeration.nextElement();
				Object obj = context.getAttribute(s);
				int temp = getObjectSize(s, obj, out);
				out.println("<tr><td>" + s + "</td><td>" + temp + "</td></tr>");
				i += temp;
			}
			out.println("</table>");
		}
		return i;
	}
	
	/**
	 * @param String s
	 * @param Object obj
	 * @param JspWriter out
	 * @return int toRet
	 */
	private int getObjectSize(String s, Object obj, JspWriter out) {
		int toRet = 0;
		if (obj != null) {
			ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
			ObjectOutputStream objectoutputstream = null;
			byte abyte0[];
			try {
				objectoutputstream = new ObjectOutputStream(bytearrayoutputstream);
				objectoutputstream.writeObject(obj);
				abyte0 = bytearrayoutputstream.toByteArray();
				toRet = abyte0.length;
			} catch (IOException ioe1) {
				try {
					out.println("Error on: " + s);
					ioe1.printStackTrace();
				} catch (IOException ioe2) {
					ioe2.printStackTrace();
				}
				toRet = 0;
			} finally {
				try {
					bytearrayoutputstream.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
				try {
					if (objectoutputstream != null)
						objectoutputstream.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
		return toRet;
	}

}

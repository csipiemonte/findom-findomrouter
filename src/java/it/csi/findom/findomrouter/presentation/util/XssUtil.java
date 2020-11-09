/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.util;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import it.csi.findom.findomrouter.util.Tracer;

public class XssUtil {

	protected static Logger LOG = Logger.getLogger(Constants.APPLICATION_CODE);
	
	public static String cleanXSS(String in) {
		final String method = "cleanXSS";
		if(in == null)
			return null;
		String out = in;
		out = StringUtils.replace(out, "\000", "");
		
		try{
		
			PatternCompiler compiler=new Perl5Compiler();
			PatternMatcher matcher=new Perl5Matcher();
		
			// ELENCO PATTERN VERIFICATI XSS
			
			List<String> patternList = new ArrayList<String>();
			patternList.add("<script>(.*?)</script>");
			patternList.add("src[\r\n]*=[\r\n]*\\'(.*?)\\'");
			patternList.add("</script>");
			patternList.add("<script(.*?)>");
			patternList.add("eval\\((.*?)\\)");
			patternList.add("expression\\((.*?)\\)");
			patternList.add("javascript:");
			patternList.add("jjavascript");
			patternList.add("script");
			patternList.add("avascript");
			patternList.add("vbscript:");
			patternList.add("onload(.*?)=");
			patternList.add("alert");
			patternList.add("iframe");
			patternList.add("/bin");
			patternList.add("/../");
			
			// Se siamo nelle cannne con SQL INJECTION...
			/*
			patternList.add("SELECT");
			patternList.add("UPDATE");
			patternList.add("INSERT");
			patternList.add("COMMIT");
			patternList.add("ORDER");
			// patternList.add("BY");
			patternList.add("GROUP");
			patternList.add("FROM");
			patternList.add("HAVING");
			patternList.add("CASE");
			patternList.add("UNION");
			patternList.add("DELETE");
			*/
				
			for(String pattern : patternList){
				Pattern localPattern = compiler.compile(pattern);
				if(matcher.matches(out, localPattern)){
					Tracer.info(LOG, XssUtil.class.getName(), method, "matching pattern " + pattern);
					out = StringUtils.replace(out, pattern, "");
				}
				if(matcher.contains(out, localPattern)){
					Tracer.info(LOG, XssUtil.class.getName(), method, "contains pattern " + pattern);
					out = StringUtils.replace(out, pattern, "");
				}	
				out = containsIgnoreCase(out, pattern) ? StringUtils.replace(out, pattern, "") : out;
				
			}
			out = StringUtils.replace(out, "\\(", "&#40;");
			out = StringUtils.replace(out, "\\)", "&#41;");
			out = StringUtils.replace(out, "<", "&lt;");
			out = StringUtils.replace(out, ">", "&gt;");
			
		}
		catch(MalformedPatternException e){
			Tracer.error(LOG, XssUtil.class.getName(), method, "MalformedPatternException: " + e);
		}
		finally{
			Tracer.debug(LOG, XssUtil.class.getName(), method, "IN [" + in + "] => OUT= [" + out + "]");
		}
		return out;
	}
	
	private static boolean containsIgnoreCase(String str, String searchStr)     {
	    if(str == null || searchStr == null) return false;
	    final int length = searchStr.length();
	    if (length == 0)
	        return true;
	    for (int i = str.length() - length; i >= 0; i--) {
	        if (str.regionMatches(true, i, searchStr, 0, length))
	            return true;
	    }
	    return false;
	}

}



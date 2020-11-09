<!--
Copyright Regione Piemonte - 2020
SPDX-License-Identifier: EUPL-1.2-or-later
-->
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://www.csi.it/taglibs/remincl-1.0" prefix="r"%>
<%@page import="java.util.TreeMap"%>
<%@page import="it.csi.findom.findomrouter.presentation.util.Constants"%>

	<r:include url="${layoutInclude.portalHead}" resourceProvider="sp" />
</head>
<body>	
	<!-- PAGE (OBBLIGATORIO) -->
	<div id="page">
		<r:include url="${layoutInclude.portalHeader}" resourceProvider="sp" />

		<!-- APPICATION AREA (OBBLIGATORIO) -->
		<div id="applicationArea">
 			<!--area userInfoPanel (dati utente + esci)-->
 			<r:include url="${layoutInclude.applicationHeader}" resourceProvider="sp" />

			<!-- FINE HEADER (parte comune a tutto l'applicativo) -->

			<!-- PANNELLO DEI CONTENUTI -->
			<div id="contentPanel">
				<!-- for m action=" #" method="post"-->

				<!--area menu verticale-->                
               	<div id="northPanel">
					<div class="wrapper">
						<!-- INIZIO CONTENUTO NORTH PANEL -->

						<!--inizio filo arianna + link help e contatti-->
						<div id="contextPanel">
							<div id="breadCrumbPanel">
								<span class="element">Sistemapiemonte</span>
								<span class="separator"> &raquo; </span>
								<span id="currentElement" class="active">gestione delle domande</span>
							</div>
							<r:include url="${layoutInclude.applicationLinkHelpContact}" resourceProvider="sp" />
						</div>
						<!--fine filo arianna + link help e contatti-->

						<!--area searchBox-->        

						<!--inizio barra utente di applicativo-->
				
			            <!--inizio userInfoPanel (dati utente + esci)-->
						<s:include value="include/userDataPanel.jsp" />
						<!--fine userInfoPanel -->
						
						<!--fine barra utente di applicativo-->                         
						<!-- FINE CONTENUTO NORTH PANEL -->
					</div>
				</div><!--/northPanel-->
				<div id="CenterWrapper" class="floatWrapper">
					<div id="centerPanel">
						<div class="wrapper">

						<s:if test="actionErrors != null && actionErrors.size > 0">
						 <%
							// <div class="feed_ko">      <s:actionerror />    </div>
							
							// e' stata sollevata una qualche eccezione, reimposto i dati della pagina chiamante
						 	// altrimenti si ottiene un NullPointerException
						 	// Nella S3_P3 (upload di file) controllo 
						
							//leggo le informazioni dal context
							TreeMap<String, Object> context = (TreeMap<String, Object>) request.getSession().getAttribute(Constants.CONTEXT_ATTR_NAME);
							if (context != null){
								
								String ucommId = (String)context.get("xcommId");
								String uformId = (String)context.get("xformId"); 
								String uformProg = (Integer)context.get("xformProg")+"";
								String uformState = (String)context.get("xformState");
								String uxsectid = (Integer)context.get("xsectId")+"";
								String uxsectxp = (String)context.get("xsectXp");
								String uxpageid = (Integer)context.get("xpageId")+"";
								String uxpagexp = (String)context.get("xpageXp");

								request.setAttribute("#xcommId",  ucommId); 
								request.setAttribute("#xformId", uformId);
								request.setAttribute("#xformProg", uformProg);
								request.setAttribute("#xformState", uformState);
								request.setAttribute("#xsectId", uxsectid);
								request.setAttribute("#xsectXp", uxsectxp);
								request.setAttribute("#xpageId", uxpageid);
								request.setAttribute("#xpageXp", uxpagexp);
							}
						%>
						</s:if>

							<!-- INZIO CONTENUTO CENTER PANEL -->
							<!-- INIZIO TAG DOMANDA -->
							<s:form action="exec" namespace="/aggregatoreUtil" method="post">
								<s:include value="include/aggregatoreUtilImpl.jsp" />
							</s:form>
							<!-- FINE TAG DOMANDA -->
							<!-- FINE CONTENUTO CENTER PANEL -->

						</div>
					</div>
				</div>
			</div><!--#contentPanel-->
            <!-- FINE PANNELLO DEI CONTENUTI -->

			<!-- footer (con remincl) (parte comune a tutto l'applicativo) -->
			<r:include url="${layoutInclude.portalFooter}" resourceProvider="sp" />
		</div>
		<!-- FINE APPICATION AREA (OBBLIGATORIO) -->
	</div>
	<!-- FINE PAGE (OBBLIGATORIO) -->
</body>
</html>

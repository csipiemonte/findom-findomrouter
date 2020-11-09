<!--
Copyright Regione Piemonte - 2020
SPDX-License-Identifier: EUPL-1.2-or-later
-->
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://www.csi.it/taglibs/remincl-1.0" prefix="r"%>

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
	            <!--area menu verticale-->                
		       	<div id="northPanel">
					<div class="wrapper">
						<!-- INIZIO CONTENUTO NORTH PANEL -->
	            		<!--inizio filo arianna + link help e contatti-->
						<div id="contextPanel">
							<div id="breadCrumbPanel">
								<!-- span class="element"><a href="/findomrouter">Findomrouter</a></span -->
								<span class="element">Sistemapiemonte</span>
								<span class="separator"> &raquo; </span>
								<span id="currentElement" class="active">gestione delle domande</span>
							</div>
						</div>		
					</div>
				</div>
				<div id="CenterWrapper" class="floatWrapper">
					<div id="centerPanel">
						<div class="wrapper">
							<!-- INZIO CONTENUTO CENTER PANEL -->
							<div class="pageContainer">
								<h3>Attenzione Failure </h3>
								<s:if test="hasActionErrors()">
									<div class="errors">
										<s:actionerror/>
									</div>
								</s:if>
								<br/>
								Uno o pi&ugrave; parametri di configurazione assenti.<br/>
								Impossibile accedere direttamente a questo applicativo.<br/>
								<br/>
								Per accedere utilizzare la seguente URL:<br/>
									<a href="http://www.sistemapiemonte.it/findomrouter">findomweb PROD</a><br/>
									<a href="http://dev-www.sistemapiemonte.it/findomrouter">findomweb DEV</a><br/>
									<a href="http://tst-www.sistemapiemonte.it/findomrouter">findomweb TEST</a>
						 		<!-- FINE CONTENUTO CENTER PANEL -->
							</div>
						</div>
					</div>
				</div><!--#contentPanel-->
	
	            <!-- FINE PANNELLO DEI CONTENUTI -->
	
				<!-- footer (con remincl) (parte comune a tutto l'applicativo)  -->
				<r:include url="${layoutInclude.portalFooter}" resourceProvider="sp" />
			</div>
			<!-- FINE APPICATION AREA (OBBLIGATORIO) -->
		</div>
		<!-- FINE PAGE (OBBLIGATORIO) -->
</body>
</html>

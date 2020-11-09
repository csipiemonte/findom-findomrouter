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
							<!-- INZIO CONTENUTO CENTER PANEL -->
							
							<div class="pageContainer">
                              
 								<!--INIZIO PARAMETRI 1 -->                             
								<h4 class="caption">ERROR</h4>
								<div class="content_caption toggle_target_01">
						           	<s:if test="hasActionErrors()">
										<div class="errors">
											<s:actionerror/>
										</div>
									</s:if>
					            </div><!--/content_caption-->  
								<!--FINE PARAMETRI 1 -->

								<s:form action="home">
									<div class="commandPanel">
										<div class="button right">
											<span class="highlighted">
												<s:submit  type="submit" name="Indietro" id="indietro" value="Indietro" />
											</span>
										</div>
									</div>
					            </s:form>
  							</div><!--/pageContainer-->
							
							
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

<!--
Copyright Regione Piemonte - 2020
SPDX-License-Identifier: EUPL-1.2-or-later
-->
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://www.csi.it/taglibs/remincl-1.0" prefix="r"%>

<r:include url="${layoutInclude.portalHead}" resourceProvider="sp"/>	

</head>

<body>
	
	<!-- PAGE (OBBLIGATORIO) -->
    <div id="page">
		<r:include url="${layoutInclude.portalHeader}" resourceProvider="sp" />
	
		<!-- APPICATION AREA (OBBLIGATORIO) -->
		<div id="applicationArea">
			
			<!--s:include value="include/applicationHeader.jsp" / -->
			<r:include url="${layoutInclude.applicationHeader}" resourceProvider="sp" />
		
			<!-- FINE HEADER (parte comune a tutto l'applicativo) -->
			
			<!-- PANNELLO DEI CONTENUTI -->
			<div id="contentPanel">
			
				<div id="northPanel">
	            <div class="wrapper">
	
	              <!-- INIZIO CONTENUTO NORTH PANEL -->
	
	              <!--inizio filo arianna + link help e contatti-->
	              <div id="contextPanel">
	                <div id="breadCrumbPanel">
	                  <!-- span class="element"><a href="/findomweb">Findomweb</a></span -->
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
	
	              <!--inizio menu dropdown-->
	              <div class="menuDropdown">
	                <ul class="sf-menu sf-navdrop sf-navdrop-simple">
	                  <li id="current"><span>Home</span></li>
	                  <li class="disattivo"><span>Indice</span></li>
	                  <li class="disattivo"><span>Anagrafica</span></li>
	                  <li class="disattivo"><span>Contenuti</span></li>
	                  <li class="disattivo"><span>Riepilogo</span></li>
	                </ul>
	              </div>
	              <!--fine menu dropdown-->
	
	              <!-- FINE CONTENUTO NORTH PANEL -->
	            </div>
	          </div><!--/northPanel-->
			
				<div id="centerWrapper" class="floatWrapper">
					<div id="centerPanel">
				
						<div class="wrapper">	
	
							<h4 class="caption">
			                  <span id="toggle_handle_01" class="toggle_handle collapse numTotTitle">
			                    <span class="descr">Gestione domande di finanziamento e/o riconoscimento</span>
			                  </span>
			                </h4>
	
							 <div class="content_caption toggle_target_01">
						            
						         <p>Benvenuto utente Monitoraggio</p>
							
								<table id="wpMonitoraggioTbl"  summary="">
									<col width="50%"/><col width="50%"/>
									<tr>
										<th scope="row"><label id="ptAccessoDBLbl">Database</label></th>
										<td> test del database OK; inserito 1 record nella tabella CSI_LOG_AUDIT
										</td>
									</tr>
									<tr>
										<th scope="row"><label id="ptAccessoServiziLbl">Servizi</label></th>
										<td> data letta tramite servizio dalla tabella dual</td>
									</tr>
								</table>
	
					   		 </div><!--/content_caption-->  

						</div>
					</div>
				</div>
			</div>   <!-- FINE  contentPanel-->			
			
			<r:include url="${layoutInclude.portalFooter}" resourceProvider="sp"/>	
		
		</div>    <!-- FINE APPICATION AREA (OBBLIGATORIO) -->		
	</div>   <!-- FINE PAGE (OBBLIGATORIO) -->	
</body>
</html>

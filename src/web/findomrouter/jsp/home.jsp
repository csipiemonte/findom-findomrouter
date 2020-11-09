<!--
Copyright Regione Piemonte - 2020
SPDX-License-Identifier: EUPL-1.2-or-later
-->
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://www.csi.it/taglibs/remincl-1.0" prefix="r"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@page import="it.csi.util.performance.StopWatch"%>

<%
StopWatch stopWatch = new StopWatch("findomrouter");
stopWatch.start();
%>
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

			<!-- stdMessagePanel CON MESSAGGI [- APPARE SOLO QUANDO NECESSARIO -] -->
			<div class="stdMessagePanel allPage">			
			    <div class="feedWarning">
			    <p><strong>Attenzione!</strong></p>
			    <p>Questo applicativo non gestisce le funzioni indietro e avanti del browser, si prega di non usarle.</p>
			    </div>
			</div>
			<!-- FINE stdMessagePanel -->
        
              <!--inizio menu dropdown-->
              <div class="menuDropdown">
                <ul class="sf-menu sf-navdrop sf-navdrop-simple">
                  <li id="current"><span>Home</span></li>
                  <li class="disattivo"><span>Indice</span></li>
                </ul>
              </div>
              <!--fine menu dropdown-->

              <!-- FINE CONTENUTO NORTH PANEL -->
            </div>
          </div><!--/northPanel-->

			<script type="text/javascript">
			    $(window).on('load',function (){
			    	abilDisabilStatiEsteri();
			    	if(!$('#id_nuovaImpresa').is(':hidden')){			    		
			    		$('#id_salvaNuovaImpresa').focus(); 
					 }		       
				});
			
				function visualizzaNuovaImpresa(){						
					$('#id_nuovaImpresa').show();						
					$('#id_statiEsteriSelect').val('');	
					$('#id_statiEsteriSelect').attr('disabled', true);			
				}
				function abilDisabilStatiEsteri(){
					if(document.getElementById("id_flagEstero").checked) {						
						$('#id_statiEsteriSelect').attr('disabled', false);	
					}else{						
						$('#id_statiEsteriSelect').attr('disabled', true);
						$('#id_statiEsteriSelect').attr('value', "");
						$('#id_statiEsteriSelect').val('');
						$('#id_statiEsteriSelect').removeClass("error");
						$('#id_cf_estero').find('.txtError').replaceWith("");
					}					
				}
				function submitForm(){
					if(document.getElementById("id_flagEstero").checked) {
					   $('#id_proponiDescrStatoEstero').click();
					}
				}
				
			</script>
			
          <div id="CenterWrapper" class="floatWrapper">
            <div id="centerPanel">
			<div id="contextPanel">
				
              <!-- INZIO CONTENUTO CENTER PANEL -->
              <div class="wrapper">
              
              <div class="stdMessagePanel" id="elencoZeroRisultati"> 
                 <s:if test="hasActionErrors()">
                   <div class="feedKo">
                     <s:actionerror/>
                     <!-- p>Attenzione!<br /> Verificare e correggere i campi evidenziati.</p-->
                   </div>
                 </s:if>
                <s:if test="hasActionMessages()">
                 	<div class="feedWarning">
				    	<s:actionmessage/>
				    </div>
				</s:if>
                 
                </div>
                
				<s:form action="cercaDomande" method="post">
				
                  <s:if test="listaImprese != null && listaImprese.size() > 0">
                 
                  <h4>Seleziona impresa/ente/persona fisica</h4>
					Indicare  l'impresa, l'ente o la persona fisica per cui si desidera presentare o visualizzare una domanda  <br/><br/>

				 	<div id="elencoRisultati">
                      <table summary="...." id="tblRisultati2" class="myovertable tablesorter">

                        <display:table name="listaImprese"
                                       export="false"
                                       uid="idlistaImprese" 
                                       pagesize="5"
                                       requestURI=""
                                       keepStatus="true"
                                       clearStatus="${param['trova'] != null}" 
                                       class="header"  >

	                     	<display:column title="Sel." headerScope="row">
	                     		<input type="radio" name="idImpresaEnte" value="<s:property value="%{#attr.idlistaImprese.idSoggetto}"/>" />
	                     	</display:column>
		                    <display:column title="Codice Fiscale" sortable="true" headerClass="sortable" property="codiceFiscale" headerScope="row"/>
		                    <display:column title="Denominazione" sortable="true" headerClass="sortable" property="denominazione" headerScope="row"/>
							<display:column title="Forma giuridica" sortable="true" headerClass="sortable" property="descrFormaGiuridica" headerScope="row"/>
	                    </display:table>
                      </table>
                   </div>

					<div class="commandPanel">
						<div class="button left">
							<span >
							<input type="button" value="Specifica altro soggetto" onclick="visualizzaNuovaImpresa()"/> 
							</span>
						</div>
						<div class="button right">
							<span class="save highlighted">
								<s:submit value="Conferma e prosegui" method="confermaImpresa"/> 
							</span>
						</div>
					</div>
					
					<br/>
                  </s:if>

                  <s:include value="include/formSpecificaEnte.jsp" />

				</s:form>
				
			
              </div>
              <!-- FINE CONTENUTO CENTER PANEL -->

 			</div> <!--contextPanel-->
 
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
<%
stopWatch.stop();
stopWatch.dumpElapsed("home.jsp", "main", "Renderizzazione jsp", "-");
%>
  </body>
</html>

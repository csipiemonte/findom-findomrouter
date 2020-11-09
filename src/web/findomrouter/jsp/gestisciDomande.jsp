<!--
Copyright Regione Piemonte - 2020
SPDX-License-Identifier: EUPL-1.2-or-later
-->
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://www.csi.it/taglibs/remincl-1.0" prefix="r"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@page import="it.csi.util.performance.StopWatch"%>
<%@page import="it.csi.findom.findomrouter.presentation.util.NumericDescCellComparator"%>

<%
StopWatch stopWatch = new StopWatch("findomrouter");
stopWatch.start();
%>
<r:include url="${layoutInclude.portalHead}" resourceProvider="sp" />

<script type="text/javascript" src="/ris/resources/application/finanziamenti/findomweb/js/ricercaDomande.js"></script>
<script type="text/javascript" src="/ris/resources/application/finanziamenti/findomweb/js/ricercaDomandeINS.js"></script>
<script>
navigator.browserSpecs = (function(){
    var ua = navigator.userAgent, tem, 
        M = ua.match(/(opera|chrome|safari|firefox|msie|trident(?=\/))\/?\s*(\d+)/i) || [];
    if(/trident/i.test(M[1])){
        tem = /\brv[ :]+(\d+)/g.exec(ua) || [];
        return {name:'IE',version:(tem[1] || '')};
    }
    if(M[1]=== 'Chrome'){
        tem = ua.match(/\b(OPR|Edge)\/(\d+)/);
        if(tem != null) return {name:tem[1].replace('OPR', 'Opera'),version:tem[2]};
    }
    M = M[2]? [M[1], M[2]]: [navigator.appName, navigator.appVersion, '-?'];
    if((tem = ua.match(/version\/(\d+)/i))!= null)
        M.splice(1, 1, tem[1]);
    return {name:M[0], version:M[1]};
})();

//console.log(navigator.browserSpecs); //Object { name: "Firefox", version: "42" }
   
   $(function(){
		if ((navigator.browserSpecs.name != "Edge")&&(navigator.browserSpecs.name != "IE")) {
        	history.replaceState("","",location.href.substring(0,location.href.indexOf("?")));
		}
    });

</script>
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

          <div id="CenterWrapper" class="floatWrapper">
            <div id="centerPanel">

              <!-- INZIO CONTENUTO CENTER PANEL -->
              <div class="wrapper">
                <!--INIZIO PARAMETRI 1 / elenco risultati -->
                <div class="stdMessagePanel" id="elencoZeroRisultati">
                	<s:if test="viewMsgDomandaConclusa != null">
					 	<div class="feedWarning">
					 		${viewMsgDomandaConclusa}
					 	</div>
					 </s:if>
	                 <s:if test="hasActionMessages()">
	                 	<s:if test="viewMsgDomandaConclusa != null">
					 	</s:if>
					 	<s:else>
		                 	<div class="feedCorrect">
		                 		<s:actionmessage />
		                 	</div>
					 	</s:else>
	                 </s:if>
	                 <s:if test="hasActionErrors()">
	                 	<div class="feedKo">
	                 		<s:actionerror />
	                 	</div>
	                 </s:if>
	 				 <s:if test="risultatoKO!= null">
	 				 	<div class="feedKo">${risultatoKO}</div>
	 				 </s:if>
					 <s:if test="risultatoOK!= null">
					 	<div class="feedCorrect">${risultatoOK}</div>
					 </s:if>
                </div>

				<h4 class="caption">
                  <span id="toggle_handle_01" class="toggle_handle collapse numTotTitle">
                    Ricerca e gestisci le domande <span class="descr">Cerca tra le domande di finanziamento gi&agrave; inserite nel sistema</span>
                  </span>
                  <span class="numTot">
                    <s:property value="domandeSalvateContate"/>
                  </span>
                </h4>

				<div class="content_caption toggle_target_01">
					<p>E' possibile selezionare uno o pi&ugrave;  parametri di ricerca  o in alternativa indicare il numero domanda</p>
				
					<s:form action="cercaDomande" method="post">  <!-- getCercaDomande -->
					
					<div class="boxAlter">
					
                      <table class="formTable" summary="Imposta i parametri per la ricerca">
                        <colgroup>
                          <col width="20%" />
                          <col width="80%" />
                        </colgroup>

                        <tr class="highlighted">
	                        <th><label for="id_areaTematica">Area Tematica</label></th>
	                        <td>
	                        <s:select id="id_areaTematica"
	                                      cssClass="long"
	                                      headerKey="-1"
                                      	  headerValue="Seleziona"
	                                      name="areaTematicaSRC"
	                                      list="listaAreeTematiche"
	                                      listKey="key"
	                                      listValue="value"
	                                      tabindex="1" />
	                        </td>
                      	</tr>			 

                        <tr class="highlighted">
                          <th><label for="id_normativa">Normativa</label></th>
                          <td>
                            <s:select id="id_normativa"
                                      cssClass="long"
                                      headerKey="-1"
                                      headerValue="Seleziona"
                                      name="normativa"
                                      list="listaNormative"
                                      listKey="key"
                                      listValue="value"
                                      tabindex="2" />
                          </td>
                        </tr>
                        <tr class="highlighted">
                          <th><label for="id_descBreveBando">Descrizione breve Bando</label></th>
                          <td>
                            <s:select id="id_descBreveBando"
                                      cssClass="long"
                                      headerKey="-1"
                                      headerValue="Seleziona"
                                      name="descBreveBando"
                                      list="listadescBreveBando"
                                      listKey="key"
                                      listValue="value"
                                      tabindex="3" />
                          </td>
                        </tr>
                        <tr class="highlighted">
                          <th><label for="id_bando">Bando</label></th>
                          <td>
                            <s:select id="id_bando"
                                      cssClass="long"
                                      headerKey="-1"
                                      headerValue="Seleziona"
                                      name="bando"
                                      list="listaBandi"
                                      listKey="key"
                                      listValue="value"
                                      tabindex="4" />
                          </td>
                        </tr>

						<tr class="highlighted">
                          <th><label for="id_sportello">Sportello</label><br/><p style="font-style:italic; font-weight:normal;">data inizio - data fine</p></th>
                          <td>
                            <s:select id="id_sportello"
                                      cssClass="long"
                                      headerKey="-1"
                                      headerValue="Seleziona"
                                      name="sportello"
                                      list="listaSportelli"
                                      listKey="key"
                                      listValue="value"
                                      tabindex="5" />
                          </td>
                        </tr>
                        
                        <tr>
                          <th><label for="id_statoDomanda">Stato della domanda</label></th>
                          <td>
                            <s:select id="id_statoDomanda"
                                      cssClass="long"
                                      headerKey="-1"
                                      headerValue="Seleziona"
                                      name="statoDomanda"
                                      list="listaStati"
                                      listKey="key"
                                      listValue="value"
                                      tabindex="6" />
                            <s:hidden name="cercaDomande" value="true"/>
                          </td>
                        </tr>
                      </table>
                    </div><!--/boxAlter-->
                    
                    <div class="boxAlter">
                      <table class="formTable" summary="Imposta i parametri per la ricerca">
                        <colgroup>
                          <col width="20%" />
                          <col width="80%" />
                        </colgroup>
                        <tr>
                          <th><label for="id_numDomanda">N&deg; domanda</label></th>
                          <td>
                          <s:if test="hasFieldErrors() && fieldErrors.id_numDomanda">
                          	<s:textfield id="id_numDomanda" name="numDomanda" cssClass="medium error" tabindex="6" />
                          	 <span class="txtError">
								<img src="/ris/resources/global/images/error.gif" alt="ci sono errori" title="dato errato" class="imError" />
								<span><s:fielderror><s:param>id_numDomanda</s:param></s:fielderror></span>
							</span>
			              </s:if>
			              <s:else><s:textfield id="id_numDomanda" name="numDomanda" cssClass="medium" tabindex="7" /></s:else>
                          </td>
                        </tr>
                      </table>
                    </div><!--/boxAlter-->
                    
                    <div class="commandPanel">
                      <div class="button left">
                        <span class="cancel">
                        	<s:submit type="submit" name="annulla_proposta" id="annulla_proposta" value="Pulisci i campi" action="annullaFiltri" tabindex="8"/>
                        </span>
                        <span class="highlighted"><s:submit type="submit" name="trova" id="cerca" value="Cerca" tabindex="9"/></span>
                      </div>
                    </div>

                  </s:form>
                    
                    
                  <!--INIZIO RISULTATI -->
                  <s:if test="listaDomande != null && listaDomande.size()==0">
                    <h4>Risultati della ricerca</h4>

                    <!--INIZIO NON CI SONO RISULTATI -->
                    <div class="stdMessagePanel" id="elencoZeroRisultati"> 
                      <div class="feedInfo">
                        <p>Non ci sono elementi da visualizzare in base ai criteri impostati</p>
                      </div>
                    </div>
                    <!--FINE NON CI SONO RISULTATI -->

                  </s:if>
                   <s:elseif test="listaDomande != null && listaDomande.size() > 0">
                  
                 <h4>Risultati della ricerca</h4>
                 <p style="font-style:italic; font-weight:normal;"> Per completare, eliminare, stampare o eseguire altre operazioni su una domanda gi&agrave; 
                 inserita, utilizzare i pulsanti visualizzati nella colonna 'Azioni'. Le diverse funzionalit&agrave; saranno disponibili per una domanda 
                 in base allo stato della stessa. </p>
                 
                <div id="elencoRisultati">

				<display:table name="listaDomande" uid="domanda" pagesize="5" requestURI="" keepStatus="true" 
					clearStatus="${param['trova'] != null}" class="myovertable tablesorter" defaultsort="2" defaultorder="ascending">
				
					<display:column title="Azioni" headerScope="col">

						<s:if test="%{#attr.domanda.codStatoDomanda=='IN' }">
						
							<s:url action="loadDomandaRouting" id="urlDettaglio">
								<s:param name="idDomanda" value="%{#attr.domanda.idDomanda}" />
							</s:url>
							<span class="detail">
							  <s:a href="%{urlDettaglio}" title="Vai alla domanda" id="vaiUrlDettaglio">
									<span class="nascosto">dettaglio</span>
								</s:a>
							</span>
							<!-- printPDFPropostaInviataRouting -->
							<s:url action="printPDFPropostaInviata.do" id="urlPrint">
								<s:param name="idDomanda"value="%{#attr.domanda.idDomanda}" />
							</s:url>
							<span class="print">
								<s:a href="%{urlPrint}" title="stampa">
									 <span class="nascosto">stampa</span>
								</s:a>
							</span>
							
							<s:if test='%{#attr.domanda.flagBandoDematerializzato=="S" }'>												
							<s:url action="vaiUploadDocFirmatoRouting.do" id="urlVaiUploadDocFirmato">
								<s:param name="idDomanda" value="%{#attr.domanda.idDomanda}" />
							</s:url>
							<span class="download"> 
								<s:a  href="%{urlVaiUploadDocFirmato}" title="download">
									<span class="nascosto">download documento firmato</span>
								</s:a>
							</span>							
							</s:if>
	                          					
						</s:if>
						
		                <s:if test="%{#attr.domanda.codStatoDomanda=='NV' }">						
							<s:url action="loadDomandaRouting" id="urlDettaglio">
								<s:param name="idDomanda" value="%{#attr.domanda.idDomanda}" />
							</s:url>
							<span class="detail">
							  <s:a href="%{urlDettaglio}" title="Vai alla domanda" id="vaiUrlDettaglio">
								<span class="nascosto">dettaglio</span>
							  </s:a>
							</span>
														
							<s:url action="printDomandaRouting.do" id="urlPrint2">
								<s:param name="idDomanda" value="%{#attr.domanda.idDomanda}" />
							</s:url>
							<span class="print">
								<s:a href="%{urlPrint2}" title="stampa">
									<span class="nascosto">stampa</span>
								</s:a>
							</span>				
			            </s:if>
						
						<s:if test="%{ #attr.domanda.codStatoDomanda=='BZ' || #attr.domanda.codStatoDomanda=='OK' 
								|| #attr.domanda.codStatoDomanda=='KO' || #attr.domanda.codStatoDomanda=='OW' }">
						
							<s:url action="loadDomandaRouting.do" id="urlModifica">
								<s:param name="idDomanda" value="%{#attr.domanda.idDomanda}" />
							</s:url>
							<span class="edit">
							  <s:a href="%{urlModifica}" title="Vai alla domanda" id="vaiUrlModifica">
									<span class="nascosto">Vai alla domanda</span>
								</s:a>
							</span>							
							
						<s:if test="%{(#attr.domanda.flagProgettiComuni=='false' || 
						                (#attr.domanda.ruoloCapofilaProgettoComune == 'false' &&  
						                 #attr.domanda.ruoloPartnerProgettoComune == 'false') 
								     )}">
								    
							<s:url action="eliminaProposta.do" id="urlElimina">
								<s:param name="idDomanda" value="%{#attr.domanda.idDomanda}" />
							</s:url>
							<span class="cancel">
								<s:a cssClass="dialog-elimina" href="%{urlElimina}" title="elimina domanda">
									<span class="nascosto">elimina</span>
								</s:a>
							</span>
						</s:if>
						
						<s:else>
						    <s:url action="invalidaProposta.do" id="urlInvalida">
								<s:param name="idDomanda" value="%{#attr.domanda.idDomanda}" />
								<s:param name="ruoloPartnerProgettoComune" value="%{#attr.domanda.ruoloPartnerProgettoComune}" />
								<s:param name="ruoloCapofilaProgettoComune" value="%{#attr.domanda.ruoloCapofilaProgettoComune}" />
							</s:url>
							<span class="invalidate">
								<s:a cssClass="dialog-elimina" href="%{urlInvalida}" title="invalida domanda">
									<span class="nascosto">invalida</span>
								</s:a>
							</span>
                        </s:else>
                        
							<s:url action="printDomandaRouting.do" id="urlPrint2">
								<s:param name="idDomanda" value="%{#attr.domanda.idDomanda}" />
							</s:url>
							<span class="print">
								<s:a href="%{urlPrint2}" title="stampa">
									<span class="nascosto">stampa</span>
								</s:a>
							</span>	
						</s:if>
						
						<s:if test="%{#attr.domanda.codStatoDomanda=='CO'}">

							<s:url action="loadDomandaRouting" id="urlDettaglio">
								<s:param name="idDomanda" value="%{#attr.domanda.idDomanda}" />
							</s:url>
							<span class="detail">
							  <s:a href="%{urlDettaglio}" title="Vai alla domanda" id="vaiUrlDettaglio">
									<span class="nascosto">dettaglio</span>
								</s:a>
							</span>
							<!-- printPDFPropostaInviataRouting -->
							<s:url action="printPDFPropostaInviata.do" id="urlPrint">
								<s:param name="idDomanda"value="%{#attr.domanda.idDomanda}" />
							</s:url>
							<span class="print">
								<s:a href="%{urlPrint}" title="stampa">
									 <span class="nascosto">stampa</span>
								</s:a>
							</span>										
												
							<s:url action="vaiUploadDocFirmatoRouting.do" id="urlVaiUploadDocFirmato">
								<s:param name="idDomanda" value="%{#attr.domanda.idDomanda}" />
							</s:url>
							<span class="upload"> 
								<s:a  href="%{urlVaiUploadDocFirmato}" title="upload">
									<span class="nascosto">upload documento firmato</span>
								</s:a>
							</span>
														
						</s:if>
						
						<s:if test="%{ #attr.domanda.codStatoDomanda=='OK' || #attr.domanda.codStatoDomanda=='OW'}">
							 
							<s:url action="inviaProposta.do" id="urlInvia">
								<s:param name="idDomanda" value="%{#attr.domanda.idDomanda}" />
							</s:url>
							<span class="send">
							<s:if test='%{#attr.domanda.flagBandoDematerializzato=="S" }'>	
								<s:a cssClass="dialog-elimina" href="%{urlInvia}" title="Concludi Domanda">
									<span class="nascosto">invia</span>
								</s:a>
							</s:if>
							<s:else>
								<s:a cssClass="dialog-elimina" href="%{urlInvia}" title="Invia Domanda">
									<span class="nascosto">invia</span>
								</s:a>
							</s:else>
							</span>

						</s:if>
						
						<s:url action="riepilogoDomanda.do" id="urlRiepilogoDomanda">
							<s:param name="idDomanda" value="%{#attr.domanda.idDomanda}" />
						</s:url>
						<span class="autore"> 
							<s:a  href="%{urlRiepilogoDomanda}" title="autore">
								<span class="nascosto">autore</span>
							</s:a>
						</span>
						
						<s:if test="%{ #attr.domanda.statoRichiesta=='A'.toString() }">
							<s:url action="inserisciDocumentiIntegrazioneRouter.do" id="urlInserisciDocIntegrazione">
								<s:param name="idDomanda" value="%{#attr.domanda.idDomanda}" />
								<s:param name="idBando" value="%{#attr.domanda.idBando}" />
								<s:param name="statoRichiesta" value="%{#attr.domanda.statoRichiesta}" />
							</s:url>
							<span class="richiestaattiva">
								<s:a href="%{urlInserisciDocIntegrazione}" title="Richiesta integrazione in corso" cssClass="inlineBlockLink">
									<span class="nascosto">Richiesta integrazione in corso</span>
								</s:a>
							</span>
						</s:if>
						<s:if test="%{ #attr.domanda.statoRichiesta=='C'.toString() }">
						<s:url action="inserisciDocumentiIntegrazioneRouter.do" id="urlInserisciDocIntegrazione">
								<s:param name="idDomanda" value="%{#attr.domanda.idDomanda}" />
								<s:param name="idBando" value="%{#attr.domanda.idBando}" />
								<s:param name="statoRichiesta" value="%{#attr.domanda.statoRichiesta}" />
							</s:url>
							<span class="richiestachiusa">
								<s:a href="%{urlInserisciDocIntegrazione}" title="Richiesta integrazione chiusa" cssClass="inlineBlockLink">
									<span class="nascosto">Richiesta integrazione chiusa</span>
								</s:a>
							</span>
						</s:if>
								                   	
					</display:column>
					
					<display:column title="N. domanda" sortable="true" headerClass="sortable" headerScope="col"
					comparator="it.csi.findom.findomrouter.presentation.util.NumericDescCellComparator" >
						 <s:property value="#attr.domanda.idDomanda"/>
					</display:column>
					
					<display:column title="Normativa" sortable="true" headerClass="sortable" headerScope="col" >
						 <s:property value="#attr.domanda.normativa" />
					</display:column>
					
					<display:column title="Descrizione breve bando" sortable="true" headerClass="sortable" headerScope="col" >
						 <s:property value="#attr.domanda.descrBreveBando" />
					</display:column>
					
					<display:column title="Bando" sortable="true" headerClass="sortable" headerScope="col" >
						 <s:property value="#attr.domanda.descrBando" />
					</display:column>
					
					<display:column title="Sportello" sortable="true" headerClass="sortable" headerScope="col" >
						 <s:property value="#attr.domanda.dtAperturaSportello" /> <br/> 
						 <s:if test="#attr.domanda.dtChiusuraSportello!=null">
						 <s:property value="#attr.domanda.dtChiusuraSportello" />
						 </s:if>
						 <s:else>
						 data fine non definita 
						 </s:else>
						 
					</display:column>	
					
					<display:column title="Stato" sortable="true" headerClass="sortable" headerScope="col" >
						 <s:property value="#attr.domanda.statoDomanda" />
					</display:column>
									
				</display:table>

                    
             </div> <!-- elencoRisultati-->
             
                    </s:elseif>
                    
                    
				</div> <!-- content caption -->
				
				 <!--INIZIO PARAMETRI 2 -->

				<script type="text/javascript">
					function apriFormIns(){
						 $("#id_formInserimento").show();
						 $("#id_nessunoSportello").hide();
					}
				</script>

                <h4 class="caption">
                  <span id="toggle_handle_02" class="toggle_handle collapse">Nuova Domanda <span class="descr">Compila una nuova domanda di finanziamento</span></span>
                </h4>
             
             	 <div class="content_caption toggle_target_02">
             	 
             <s:if test="showNuovaDomanda == 'false'">
                 
                 <div id="id_nessunoSportello"> 
                  <p>Nessuno sportello disponibile.
                  	<s:if test="prossimoSportelloAttivo != null">
                  		<br/>
                  		<p><span><s:property value="prossimoSportelloAttivo"/></span>
                  	</s:if>
                  </p>
                  <div class="commandPanel" id="id_bottoneApriFormIns">
					  <div class="button left">
						<span class="highlighted">
						  <input id="id_visualizzaFormInserimento" name="visualizzaFormInserimento" onclick="apriFormIns();"
								value="Visualizza form inserimento" tabindex="9" type="button">
						</span>
					  </div>
					</div>
                  </div>
                  <div id="id_formInserimento"  style="display: none;">
             </s:if>
             
             <s:else> 
				 <div id="id_formInserimento" >
			</s:else>
			
                <p>* Selezionare tutti i parametri</p>
                   
				<s:form action="createDomanda" id="nuovaPropostaForm"  method="post">

                    <table class="formTable" summary="Imposta i parametri per la ricerca dei bandi disponibili">
                      <colgroup>
                        <col width="20%" />
                        <col width="80%" />
                      </colgroup>

                      <tr>
                        <th><label for="id_areaTematicaINS">Area Tematica</label></th>
                        <td>
                     <s:if test="hasFieldErrors() && fieldErrors.areaTematicaINS">
                     		<s:select id="id_areaTematicaINS"
                                      cssClass="long error"
                                      headerKey="-1"
                                      headerValue="Seleziona"
                                      name="areaTematicaINS"
                                      list="listaAreeTematicheINS"
                                      listKey="key"
                                      listValue="value"
                                      tabindex="10" />
                     		<span class="txtError">
								<img src="/ris/resources/global/images/error.gif" alt="ci sono errori" title="dato obbligatorio" class="imError" />
								<span><s:fielderror><s:param>areaTematicaINS</s:param></s:fielderror></span>
							</span>
					  </s:if>
					  <s:else>
                      		<s:select id="id_areaTematicaINS"
                                      cssClass="long"
                                      headerKey="-1"
                                      headerValue="Seleziona"
                                      name="areaTematicaINS"
                                      list="listaAreeTematicheINS"
                                      listKey="key"
                                      listValue="value"
                                      tabindex="10" />
                     </s:else>
                        </td>
                      </tr>

                      <tr>
                        <th><label for="id_normativaINS">Normativa</label></th>
                        <td>
                          <s:if test="hasFieldErrors() && fieldErrors.normativaINS">
                        	<s:select id="id_normativaINS"
                                      cssClass="long error"
                                      headerKey="-1"
                                      headerValue="Seleziona"
                                      name="normativaINS"
                                      list="listaNormativeINS"
                                      listKey="key"
                                      listValue="value"
                                      tabindex="11" />  
                            <span class="txtError">
								<img src="/ris/resources/global/images/error.gif" alt="ci sono errori" title="dato obbligatorio" class="imError" />
								<span><s:fielderror><s:param>normativaINS</s:param></s:fielderror></span>
							</span>
							</s:if>
							<s:else>
								<s:select id="id_normativaINS"
                                      cssClass="long"
                                      headerKey="-1"
                                      headerValue="Seleziona"
                                      name="normativaINS"
                                      list="listaNormativeINS"
                                      listKey="key"
                                      listValue="value"
                                      tabindex="11" />
							</s:else>
                        </td>
                      </tr>
                      <tr>
                        <th><label for="id_descBreveBandoINS">Descrizione breve Bando</label></th>
                        <td>
                          <s:if test="hasFieldErrors() && fieldErrors.descBreveBandoINS">
                           <s:select id="id_descBreveBandoINS"
                                      cssClass="long error"
                                      headerKey="-1"
                                      headerValue="Seleziona"
                                      name="descBreveBandoINS"
                                      list="listadescBreveBandoINS"
                                      listKey="key"
                                      listValue="value"
                                      tabindex="12" />
                             <span class="txtError">
								<img src="/ris/resources/global/images/error.gif" alt="ci sono errori" title="dato obbligatorio" class="imError" />
								<span><s:fielderror><s:param>descBreveBandoINS</s:param></s:fielderror></span>
							</span>
                          </s:if>
                          <s:else>
                          	<s:select id="id_descBreveBandoINS"
                                      cssClass="long"
                                      headerKey="-1"
                                      headerValue="Seleziona"
                                      name="descBreveBandoINS"
                                      list="listadescBreveBandoINS"
                                      listKey="key"
                                      listValue="value"
                                      tabindex="12" />
                          </s:else>
                        </td>
                      </tr>
                      
                      <tr>
                        <th><label for="id_bandoINS">Bando</label></th>
                        <td>
                       
                          <s:if test="hasFieldErrors() && fieldErrors.bandoINS">
                           <s:select id="id_bandoINS"
                                      cssClass="long error"
                                      headerKey="-1"
                                      headerValue="Seleziona"
                                      name="bandoINS"
                                      list="listaBandiINS"
                                      listKey="key"
                                      listValue="value"
                                      tabindex="13" />
                            <span class="txtError">
								<img src="/ris/resources/global/images/error.gif" alt="ci sono errori" title="dato obbligatorio" class="imError" />
								<span><s:fielderror><s:param>bandoINS</s:param></s:fielderror></span>
							</span>
                          </s:if>
						  <s:else>
						  <s:select id="id_bandoINS"
                                      cssClass="long"
                                      headerKey="-1"
                                      headerValue="Seleziona"
                                      name="bandoINS"
                                      list="listaBandiINS"
                                      listKey="key"
                                      listValue="value"
                                      tabindex="13" />
                         </s:else>
                        </td>
                      </tr>
                                          
                      <tr>
                        <th><label for="id_sportelloINS">Sportello</label><br/><p style="font-style:italic; font-weight:normal;">data inizio - data fine</p></th>
                        <td>
	                          <s:if test="hasFieldErrors() && fieldErrors.sportelloINS">
	                          <s:select id="id_sportelloINS"
	                                    cssClass="long error"
	                                    headerKey="-1"
	                                    headerValue="Seleziona"
	                                    name="sportelloINS"
	                                    list="listaSportelliINS"
	                                    listKey="key"
	                                    listValue="value"
	                                    tabindex="14" />
	                                    
	                          	<span class="txtError">
								<img src="/ris/resources/global/images/error.gif" alt="ci sono errori" title="dato obbligatorio" class="imError" />
								<span><s:fielderror><s:param>sportelloINS</s:param></s:fielderror></span>
							</span>
	                          </s:if>
	                          <s:else>
	                          <s:select id="id_sportelloINS"
	                                    cssClass="long"
	                                    headerKey="-1"
	                                    headerValue="Seleziona"
	                                    name="sportelloINS"
	                                    list="listaSportelliINS"
	                                    listKey="key"
	                                    listValue="value"
	                                    tabindex="14" />
	                          </s:else>
                        </td>
                      </tr>
                                              
                      <tr>
                        <th><label for="id_tipolBeneficiarioINS">Tipologia Beneficiario</label></th>
                        <td>
                          <s:if test="hasFieldErrors() && fieldErrors.tipologiaBeneficiarioINS">
                          <s:select id="id_tipolBeneficiarioINS"
                                      cssClass="long error"
                                      headerKey="-1"
                                      headerValue="Seleziona"
                                      name="tipologiaBeneficiarioINS"
                                      list="listaTipologieBeneficiariINS"
                                      listKey="idTipolBeneficiario"
                                      listValue="descrizione"
                                      tabindex="15" />
                            <span class="txtError">
								<img src="/ris/resources/global/images/error.gif" alt="ci sono errori" title="dato obbligatorio" class="imError" />
								<span><s:fielderror><s:param>tipologiaBeneficiarioINS</s:param></s:fielderror></span>
							</span>
                          </s:if>
                          <s:else>
                          <s:select id="id_tipolBeneficiarioINS"
                                      cssClass="long"
                                      headerKey="-1"
                                      headerValue="Seleziona"
                                      name="tipologiaBeneficiarioINS"
                                      list="listaTipologieBeneficiariINS"
                                      listKey="idTipolBeneficiario"
                                      listValue="descrizione"
                                      tabindex="15" />
                          </s:else>
                        </td>
                      </tr>

                    </table>

                    <div class="commandPanel">
                      <div class="button left">
                        <span class="cancel">
                          <s:submit type="reset" name="annulla_nuova" id="annulla_nuova" value="Ripristina valori iniziali" action="annullaFiltriNuovaDomanda" tabindex="16"/>
                        </span>
                        <span class="highlighted"><s:submit type="submit" name="crea_nuova" id="id_creaNuovaDomanda" value="Inserisci nuova domanda" tabindex="17"/></span>
                      </div>
                    </div>
                    <s:include value="include/confermaInserisciDomanda.jsp" />
                  </s:form>
				</div>
                </div> <!--/content_caption-->
                
                <!--FINE PARAMETRI 2 -->
              </div>
              <!-- FINE CONTENUTO CENTER PANEL -->
            </div>
          </div>
        </div><!--#contentPanel-->
        <!-- FINE PANNELLO DEI CONTENUTI -->

        <r:include url="${layoutInclude.portalFooter}" resourceProvider="sp" />

      </div>
      <!-- FINE APPICATION AREA (OBBLIGATORIO) -->

    </div>
    <!-- FINE PAGE (OBBLIGATORIO) -->
<%
stopWatch.stop();
stopWatch.dumpElapsed("gestisciDOmande.jsp", "main", "Renderizzazione jsp", "-");
%>
<s:if test="showPopup == 'true'">
<script type="text/javascript">
  viewModaleConfermaInserisci( 'id_confermaInserisci', 'id_modaleConfermaInserisci');
</script>
</s:if>
  </body>
</html>

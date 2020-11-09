<!--
Copyright Regione Piemonte - 2020
SPDX-License-Identifier: EUPL-1.2-or-later
-->
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://www.csi.it/taglibs/remincl-1.0" prefix="r"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>

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
              <s:include value="include/userDataPanel.jsp" />
              <!--fine barra utente di applicativo-->

        
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

				<h4 class="caption">
                  <span id="toggle_handle_01" class="toggle_handle collapse numTotTitle">
                    Riepilogo Domanda
                  </span>
                </h4>
				
				<div class="content_caption toggle_target_01">
				
					<s:form action="cercaDomande" method="post">
					
					<div class="boxAlter">
					
                      <table class="formTable" summary="...">
                        <colgroup>
                          <col width="20%" />
                          <col width="40%" />
                          <col width="20%" />
                          <col width="40%" />
                        </colgroup>
                        
                        <tr >
                          <td colspan="2">Creazione Domanda</td>
                          <td colspan="2">Invio Domanda</td>
                        </tr>
                        
                        <tr >
                          <td>Codice fiscale</td>
                          <td>  <s:property value="soggCreatore.codiceFiscale" />   </td>
                          <td>Codice fiscale</td>
                         <td>  
                         	<s:if test="soggInviatore.codiceFiscale != null">
                         	<s:property value="soggInviatore.codiceFiscale" />
                         	</s:if>
                         	<s:else>
                         		-	
                         	</s:else>
                         </td>
                        </tr>
                        
                         <tr >
                          <td>Nome</td>
                          <td> <s:property value="soggCreatore.nome" />  </td>
                          <td>Nome</td>
                          <td>  
                         	<s:if test="soggInviatore.nome != null">
                         	<s:property value="soggInviatore.nome" />
                         	</s:if>
                         	<s:else>
                         		-	
                         	</s:else>
                         </td>
                        </tr>
                        
                         <tr >
                          <td>Cognome</td>
                            <td> <s:property value="soggCreatore.cognome" />  </td>
                          <td>Cognome</td>
                         <td>  
                         	<s:if test="soggInviatore.cognome != null">
                         	<s:property value="soggInviatore.cognome" />
                         	</s:if>
                         	<s:else>
                         		-	
                         	</s:else>
                         </td>
                        </tr>
                        
                         <tr >
                          <td>Data</td>
                           <td> <s:property value="domanda.dtCreazioneDomanda" />  </td>
                          <td>Data</td>
                           <td>  
                         	<s:if test="domanda.dtInvioDomanda != null">
                         	<s:property value="domanda.dtInvioDomanda" />
                         	</s:if>
                         	<s:else>
                         		-	
                         	</s:else>
                         </td>
                        </tr>
                        
                      </table>
                    </div><!--/boxAlter-->
                    
                    
                    <div class="commandPanel">
                      <div class="button left">
                        <span ><s:submit value="indietro"/></span>
                      </div>
                    </div>

                  </s:form>

				</div> <!-- content caption -->

              </div>
              <!-- FINE CONTENUTO CENTER PANEL -->

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

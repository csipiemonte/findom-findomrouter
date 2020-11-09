<!--
Copyright Regione Piemonte - 2020
SPDX-License-Identifier: EUPL-1.2-or-later
-->
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://www.csi.it/taglibs/remincl-1.0" prefix="r"%>
				      
</head>
<body>
  <div id="page">
    <div id="applicationArea">
      <!-- PANNELLO DEI CONTENUTI -->
      	
      <div id="contentPanel">
        <div id="dialog_target">

		<form action="inviaPropostaRouting.do" method="post" id="invioConAttesa">
            <div id="CenterWrapper" class="floatWrapper">
              <div id="centerPanel">
                <div class="wrapper">
                  <!-- INIZIO CONTENUTO CENTER PANEL -->
                  <h3>Invio domanda</h3>
                  <!-- DIALOG PANEL -->
                  <div class="dialogPanel">
                    <div class="stdMessagePanel">
                      <div class="feedWarning">
						 <s:if test="sportelloOpen == 'false'">
						    <s:if test="bandoDematerializzato  == 'true'">
						 	    <p><strong>Attenzione!<br />Non &egrave; possibile concludere la domanda, lo sportello non &egrave; attivo</strong></p>
						 	</s:if>
							<s:else>
							   <p><strong>Attenzione!<br />Non &egrave; possibile inviare la domanda, lo sportello non &egrave; attivo</strong></p>
							</s:else>
						 </s:if>
						 <s:else>         
						 	 <s:if test="numMaxDomandeBandoPresentate == 'true'">
								<p><strong>Attenzione!<br />E' gi&agrave; stato inviato il numero massimo di domande previsto dal bando.<br/>Si intende comunque proseguire?</strong></p>
							</s:if>
							<s:elseif test="numMaxDomandeSportelloPresentate == 'true'">
								<p><strong>Attenzione!<br />E' gi&agrave; stato inviato il numero massimo di domande previsto dallo sportello.<br/>Si intende comunque proseguire?</strong></p>
							</s:elseif>
							<s:else>
								<s:if test="bandoDematerializzato  == 'true'">
									<p>
										<strong>Attenzione!<br/>
										<%--  
											  Jira: 1375
											  	Sei sicuro di voler concludere la domanda? -->
											  	La domanda verr&agrave; conclusa e non potr&agrave; pi&ugrave; essere modificata. 
										--%>
											Sei sicuro di voler chiudere la domanda?<br/>
											Dopo la chiusura, la domanda non potr&agrave; pi&ugrave; essere modificata.
										</strong>
									</p>
								</s:if>
								<s:else>
									<s:if test="bandoMaterializzatoSenzaPEC == 'true'">
										<p><strong>Attenzione!<br />Sei sicuro di voler confermare la domanda?</strong></p>
										<p>La domanda non potr&agrave; pi&ugrave; essere modificata.</p>
									</s:if>
									<s:else>
										<p>
										<strong>Attenzione!<br/>Sei sicuro di voler confermare la domanda?<br/>La domanda non potr&agrave; pi&ugrave; essere modificata.<br/><br/>
										Proseguendo con l'invio sar&agrave; creato il PDF definitivo della domanda, da salvare, firmare e inviare tramite PEC (Posta Elettronica Certificata) entro i termini previsti dal bando.<br/>
										Solo con l'invio della PEC la domanda risulter&agrave; presentata.
										</strong></p>
									</s:else>
								</s:else>
							</s:else>
						</s:else>
                      </div>
                    </div>
                  </div>
                  <!-- FINE DIALOG PANEL -->
                  <div class="commandPanel noSpaceButton">
                    <div class="button left">
                       <span class="close_js"></span>
                    </div>
<s:if test="sportelloOpen == 'true'">
                    <div class="button right">
                      <span class="go highlighted">
                         <s:hidden name="idDomanda" value="%{idDomanda}"/>                   
                         <s:submit type="submit" name="prosegui" id="idInvia" value="si, prosegui" />                      
                      </span>
                    </div>
</s:if>
      				<div id="loading_box" hidden="true">
						<div id="dialog_target">
							<h3>Invio in corso..</h3>
							<div class="dialogPanel">
								<div class="stdMessagePanel">
									<div class="feedLoad"><p>Attendere prego: il sistema sta inviando la domanda...</p></div>
								</div>
							</div> 
						</div>
					  </div>
				    </div>
				    
                  </div>
                  <!-- FINE CONTENUTO CENTER PANEL -->
                </div>
              </div>
		</form>
              
        </div>   <!--/dialog_target-->
      </div>   <!--/contentPanel-->
      
     </div> <!-- applicationArea -->
  </div>
  <!-- FINE PAGE (OBBLIGATORIO) -->
</body>
</html>

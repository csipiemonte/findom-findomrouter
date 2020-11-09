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
		<form action="invalidaPropostaRouting.do" method="post">
            <div id="CenterWrapper" class="floatWrapper">
              <div id="centerPanel">
                <div class="wrapper">
                  <!-- INIZIO CONTENUTO CENTER PANEL -->
                  <h3>Conferma invalidazione domanda</h3>
                  <!-- DIALOG PANEL -->
                  <div class="dialogPanel">
                    <div class="stdMessagePanel">
                      <div class="feedWarning">
                      <s:if test="ruoloCapofilaProgettoComune == 'true'">
                         <p><strong>Attenzione!<br /> Sei sicuro di voler invalidare la domanda?<br /> La domanda selezionata e le domande di eventuali Partner non saranno pi&#249; modificabili e non si potranno presentare. </strong></p>
                      </s:if>
                      <s:else>
                         <p><strong>Attenzione!<br /> Sei sicuro di voler invalidare la domanda?<br /> La domanda non sar&#224; pi&#249; modificabile e non si potr&#224; presentare. </strong></p>
                      </s:else> 
                      </div>
                    </div>
                  </div>
                  <!-- FINE DIALOG PANEL -->
                  <div class="commandPanel noSpaceButton">
                    <div class="button left">
                       <span class="close_js"></span>
                    </div>
                    <div class="button right">
                      <s:hidden name="idDomanda" value="%{idDomanda}"/>
                      <span class="go highlighted"><input type="submit" name="prosegui" value="si, prosegui"  /></span>
                    </div>
                  </div>
                  <!-- FINE CONTENUTO CENTER PANEL -->
                </div>
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

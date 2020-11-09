<!--
Copyright Regione Piemonte - 2020
SPDX-License-Identifier: EUPL-1.2-or-later
-->
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://www.csi.it/taglibs/remincl-1.0" prefix="r"%>
<%@page import="it.csi.findom.findomrouter.presentation.util.Constants"%>
<r:include url="${layoutInclude.portalHead}" resourceProvider="sp" />
</head>
<body>
  <div id="page">
    <r:include url="${layoutInclude.portalHeader}" resourceProvider="sp" />
    <div id="applicationArea">
      <r:include url="${layoutInclude.applicationHeader}" resourceProvider="sp" />
      <!-- PANNELLO DEI CONTENUTI -->
      <div id="contentPanel">
        <div id="dialog_target">
        
          <s:form action="confermaCambiaProfilo" method="post">
            <div id="CenterWrapper" class="floatWrapper">
              <div id="centerPanel">
                <div class="wrapper">
                  <!-- INIZIO CONTENUTO CENTER PANEL -->
                  <h3>Logout dall'Applicativo</h3>
                  <!-- DIALOG PANEL -->
                  <div class="dialogPanel">
                    <div class="stdMessagePanel">
                      <div class="feedWarning">
                        <p>
                          <strong>Attenzione!<br /> Sei sicuro di voler cambiare impresa/ente/persona fisica?
                          </strong>
                        </p>
                        <p class="msgError">
                          <strong>Tutti i dati non ancora salvati andranno persi.</strong><br />
                          <br /> Sarai indirizzato alla home page dell'applicativo.<br />
                        </p>
                      </div>
                    </div>
                  </div>
                  <!-- FINE DIALOG PANEL -->
                  <div class="commandPanel noSpaceButton">
                    <div class="button left">
                      <span class="close_js"></span>
                    </div>
                    <div class="button right">
                      <span class="go highlighted">
                      <input type="submit" name="prosegui" value="<%=Constants.BUTTON_TXT_CONFERMA %>" />
                    </div>
                  </div>
                  <!-- FINE CONTENUTO CENTER PANEL -->
                </div>
              </div>
            </div>
          </s:form>
          
        </div> <!--/dialog_target-->
      </div>
      <!--/contentPanel-->
      <!-- FINE PANNELLO DEI CONTENUTI -->
      <r:include url="${layoutInclude.portalFooter}" resourceProvider="sp" />
    </div>
    <!-- FINE APPICATION AREA (OBBLIGATORIO) -->
  </div>
  <!-- FINE PAGE (OBBLIGATORIO) -->
</body>
</html>

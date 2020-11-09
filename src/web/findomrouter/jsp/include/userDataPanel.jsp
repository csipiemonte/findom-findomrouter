<!--
Copyright Regione Piemonte - 2020
SPDX-License-Identifier: EUPL-1.2-or-later
-->
<%@taglib uri="/struts-tags" prefix="s"%>

<div id="userAppPanel">
			                
	<div class="userAppData">
          <span class="user">
              Utente: 
              <span class="value">
              	<s:property value="userInfo.nome"/>&nbsp;<s:property value="userInfo.cognome"/>
              </span>
          </span> |
          <span class="role">
              CF: 
              <span class="value">
              	<s:property value="userInfo.codFisc"/>
              </span>
          </span> |
          <span class="organization">
              Ente/Impresa: 
              <span class="value">
              		<s:property value="status.descrImpresaEnte"/>
              	  - <s:property value="status.codFiscaleBeneficiario"/>
			  </span>
          </span>
    </div>

  <div class="commandPanel">
       <div class="button right">
       
	       <s:form action="apriModaliProfilo" method="post">
	           <span class="esci">
	           		<s:submit  name="esci" value="esci" id="esci" cssClass="dialog-feedback {href:'esci.do'}" title="Logout dall'Applicativo" />
	           </span>

	           <span class="highlighted">
	           		<s:submit name="cambiaProfilo" id="cambia_profilo" value="cambia soggetto" cssClass="dialog-feedback {href:'cambiaProfilo.do'}" title="Torna alla scelta dell'Impresa/Ente/Persona fisica" />
	           </span>
	       </s:form>
       
       </div>
   </div>

</div>

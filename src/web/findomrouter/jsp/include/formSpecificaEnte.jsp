<!--
Copyright Regione Piemonte - 2020
SPDX-License-Identifier: EUPL-1.2-or-later
-->
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="it.csi.findom.findomrouter.presentation.util.Constants"%>

<div id="id_nuovaImpresa" hidden="<s:property value="hideImpresa"/>">

	<h4>Seleziona impresa/ente/persona fisica</h4>
	Indicare il Codice Fiscale o la Partita IVA, italiani o esteri, dell'impresa, dell'ente o della persona fisica per cui si desidera presentare o visualizzare una domanda. 
	
	 <table summary="...." id="tblSpecificaImpresa" class="myovertable tablesorter">
	 	<col width="24%" />
		<col width="76%" />
	 	<tr>
	 		<td><label for="id_cfNuovaImpresa">Codice Fiscale </label></td>
	 		<td>
	 		<s:if test="hasFieldErrors() && fieldErrors.cfNuovaImpresaError">
	 		   <s:textfield name="cfNuovaImpresa" id="id_cfNuovaImpresa" cssClass="medium error" maxlength="16" />
                <span class="txtError">
					<img src="/ris/resources/global/images/error.gif" alt="ci sono errori" title="dato obbligatorio" class="imError" />
					<span><s:fielderror><s:param><%=Constants.ERR_STRING_CFNEWENTE_ERROR%></s:param></s:fielderror></span>
				</span><!-- cfNuovaImpresaError -->
             </s:if>
             <s:else>
                  	<s:textfield name="cfNuovaImpresa" id="id_cfNuovaImpresa" cssClass="medium"/>
             </s:else>
            </td>
	 	</tr>
	 	<tr>
	 	<td>
	 	<s:checkbox name="flagEstero" id="id_flagEstero" onclick="abilDisabilStatiEsteri(); submitForm();" />
	 	<label for="id_flagEstero">Non sono in possesso di un CF/P.Iva italiani</label>
	 	</td>
	 	<td id="id_cf_estero">
	 	<s:if test="hasActionErrors() && fieldErrors.statoEsteroObbligatorio">	 	
           <label for="id_statiEsteriSelect">Stato estero</label>
           <s:select id="id_statiEsteriSelect"
              cssClass="medium error"
              headerKey="-1"
              headerValue="Seleziona..."
              name="statoEstero"
              list="listaStatiEsteri"
              listKey="key"
              listValue="value"
              tabindex="2" />
          <span class="txtError">
              <img src="/ris/resources/global/images/error.gif" alt="ci sono errori" title="campo obbligatorio" class="imError" />	
                <span><s:fielderror><s:param>statoEsteroObbligatorio</s:param></s:fielderror></span>		
		  </span>
	    </s:if>
		<s:else>
		   <s:select id="id_statiEsteriSelect"
              cssClass="medium"
              headerKey="-1"
              headerValue="Seleziona..."
              name="statoEstero"
              list="listaStatiEsteri"
              listKey="key"
              listValue="value"
              tabindex="2" />
		</s:else>
	 	</td>
	 	</tr> 	 	
	 </table>
      
	<div class="commandPanel">
		<div class="button right">
			<span class="save highlighted">
				<s:submit id="id_salvaNuovaImpresa" value="Conferma e prosegui" method="salvaNuovaImpresa" />							  
			</span>			
		</div>
		<div hidden="true">
		    <s:submit id="id_proponiDescrStatoEstero" method="proponiStatoEstero" />
		</div>
	</div>	
</div>

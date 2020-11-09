<!--
Copyright Regione Piemonte - 2020
SPDX-License-Identifier: EUPL-1.2-or-later
-->
<%@taglib uri="/struts-tags" prefix="s"%>

<div class="stdMessagePanel allPage">			
	<div class="feedWarning">
		<p>
			Attenzione!
			<br /> L'impresa/ente inserita &egrave; gi&agrave; presente nella basedati con i seguenti valori: <br/>
		</p>
        <br/>
          
        <table summary="...." id="tblSpecificaImpresa" class="myovertable tablesorter">
		<col width="24%" />
		<col width="76%" />
		<tr>
			<td><label for="id_cfNuovaImpresa">Codice Fiscale </label></td>
			<td> <s:property value="impresaEnteTrovata.codiceFiscale"/></td>
		</tr>
		<tr>
			<td><label for="id_denNuovaImpresa">Denominazione </label></td>
			<td> <s:property value="impresaEnteTrovata.denominazione" /></td>
		</tr>
		<tr>
			<td><label for="id_FGNuovaImpresa">Forma giuridica </label></td>
			<td> <s:property value="impresaEnteTrovata.descrFormaGiuridica" /></td>
		</tr>
		</table>

		<s:hidden name="descrImpresaEnteTrovata" value="%{impresaEnteTrovata.denominazione}"/>
		<s:hidden name="idImpresaEnteTrovata" value="%{impresaEnteTrovata.idSoggetto}"/>
		<s:hidden name="cfImpresaEnteTrovata" value="%{impresaEnteTrovata.codiceFiscale}"/>

		<p>Vuoi proseguire con questa Impresa/Ente ? </p>
	
		<div class="commandPanel">
			<div class="button right">
				<span class="save highlighted">
					<s:submit value="Conferma e prosegui" method="confermaUtilizzoImpresa"/> 
				</span>
			</div>
		</div>
	
	</div>
</div>

<!--
Copyright Regione Piemonte - 2020
SPDX-License-Identifier: EUPL-1.2-or-later
-->
<%@taglib prefix="s" uri="/struts-tags"%>
				<!--  MODALE INIZIO  -->
				<script type="text/javascript">
					function viewModaleConfermaInserisci(viewModaleButton_id, modaleConfirm_id) {

						$(function() {
							$("#" + modaleConfirm_id)
								.dialog({
										title: $("#" + viewModaleButton_id).attr("title"),
										width: 600,
										modal: true,
										bgiframe: false,
										resizable: false,
										draggable: false,
										chiudi: false,
										load: function(dialog_target) {
											jQuery("span.close_js", dialog_target).jbutton({
												text : 'no, chiudi',
												callback : function (el) {
													$(el).parents(".ui-dialog-content:eq(0)").dialog("close");
												}
											});
										}
							});
						});
					}
					
					function showSendingBoxInserisci(){
						$("#id_modaleConfermaInserisci").dialog("close");
						$("#loading_box").dialog({
							title: "Inserimento in corso..",
							width: 600,
							modal: true,
							bgiframe: false,
							resizable: false,
							draggable: false,
							chiudi: false,
							load: function(dialog_target) {
								jQuery("span.close_js", dialog_target).jbutton({
									text : 'no, chiudi',
									callback : function (el) {
										$(el).parents(".ui-dialog-content:eq(0)").dialog("close");
									}
								});
							}
						});
					}
				</script>

				<div id="loading_box" hidden="true">
					<div id="dialog_target">
						<h3>Inserimento in corso..</h3>
						<div class="dialogPanel">
							<div class="stdMessagePanel">
								<div class="feedLoad"><p>Attendere prego: il sistema sta inserendo la domanda...</p></div>
							</div>
						</div> 
					</div>
				</div>
				
				<span>
					<a class="send" hidden="true" href="#" style="dialog-elimina" id="id_confermaInserisci" title="Inserisci Domanda" onclick="viewModaleConfermaInserisci( 'id_confermaInserisci', 'id_modaleConfermaInserisci')">
						Inserisci
					</a>
				</span>

				<div hidden="true" id="id_modaleConfermaInserisci">
					<div id="dialog_target">
						<h3>Inserisci Domanda</h3>
						<div class="dialogPanel">
							<div class="stdMessagePanel">

								<div class="feedWarning">
									 <s:if test="sportelloOpen == 'false'">
									 <p><strong>Attenzione!<br />Non &egrave; possibile inserire la domanda, lo sportello non &egrave; attivo</strong></p>
									</s:if>
									<s:else>
									 <s:if test="numMaxDomandeBandoPresentate == 'true'">
										<p><strong>Attenzione!<br />E' gi&agrave; stato inviato il numero massimo di domande previsto dal bando.<br/>Si intende inserire comunque una nuova domanda?</strong></p>
									</s:if>
									<s:elseif test="numMaxDomandeSportelloPresentate == 'true'">
										<p><strong>Attenzione!<br />E' gi&agrave; stato inviato il numero massimo di domande previsto dallo sportello.<br/>Si intende inserire comunque una nuova domanda?</strong></p>
									</s:elseif>
									</s:else>

								</div>
							</div>
						</div><!--/dialogPanel -->

						<div class="commandPanel noSpaceButton">
							<div class="button left">
								<span class="close_js"></span>
							</div>
 							<s:if test="sportelloOpen == 'true'">
							<div class="button right">
							
		                    <s:url action="createDomandaRouting.do" id="urlInserisci">
							  <s:param name="normativaINS" value="%{normativaINS}"/>
		                      <s:param name="descBreveBandoINS" value="%{descBreveBandoINS}"/>
		                      <s:param name="bandoINS" value="%{bandoINS}"/>
		                      <s:param name="sportelloINS" value="%{sportelloINS}"/>
		                      <s:param name="tipologiaBeneficiarioINS" value="%{tipologiaBeneficiarioINS}"/>
							</s:url>
							<span class="go highlighted">
							<s:a cssClass="send" id="inserisciLaDomanda" href="%{urlInserisci}" title="prosegui" onclick="showSendingBoxInserisci();">
								Inserisci
							</s:a>
							</span>
					
							</div>
						</s:if>
						</div>

					</div><!--/dialog_target -->
				</div><!--/id_modaleConfermaInserisci -->
				

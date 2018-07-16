<%@page import="com.unicef.pdf.form.input.domain.InputForm"%>
<%@page import="com.unicef.pdf.form.input.service.InputFormService"%>
<%
InputFormService inputFormService = (InputFormService)request.getAttribute("inputFormService");

String prim_participant = request.getParameter("prim_participant");
String sec_participant = request.getParameter("sec_participant");
String ter_participant = request.getParameter("ter_participant");
String prim_CurrentBehavior = request.getParameter("prim_CurrentBehavior");
String sec_CurrentBehavior = request.getParameter("sec_CurrentBehavior");
String ter_CurrentBehavior = request.getParameter("ter_CurrentBehavior");
String prim_RecomBehavior = request.getParameter("prim_RecomBehavior");
String sec_RecomBehavior = request.getParameter("sec_RecomBehavior");
String ter_RecomBehavior = request.getParameter("ter_RecomBehavior");
String prim_KeyBarrier = request.getParameter("prim_KeyBarrier");
String sec_KeyBarrier = request.getParameter("sec_KeyBarrier");
String ter_KeyBarrier = request.getParameter("ter_KeyBarrier");
String prim_OtherBarrier = request.getParameter("prim_OtherBarrier");
String sec_OtherBarrier = request.getParameter("sec_OtherBarrier");
String ter_OtherBarrier = request.getParameter("ter_OtherBarrier");
String prim_benefit = request.getParameter("prim_benefit");
String sec_benefit = request.getParameter("sec_benefit");
String ter_benefit = request.getParameter("ter_benefit");
String prim_practice = request.getParameter("prim_practice");
String sec_practice = request.getParameter("sec_practice");
String ter_practice = request.getParameter("ter_practice");
String prim_support = request.getParameter("prim_support");
String sec_support = request.getParameter("sec_support");
String ter_support = request.getParameter("ter_support");

InputForm inputForm = new InputForm();

inputForm.setPrim_participant(prim_participant);
inputForm.setSec_participant(sec_participant);
inputForm.setTer_participant(ter_participant);

inputForm.setPrim_CurrentBehavior(prim_CurrentBehavior);
inputForm.setSec_CurrentBehavior(sec_CurrentBehavior);
inputForm.setTer_CurrentBehavior(ter_CurrentBehavior);

inputForm.setPrim_RecomBehavior(prim_RecomBehavior);
inputForm.setSec_RecomBehavior(sec_RecomBehavior);
inputForm.setTer_RecomBehavior(ter_RecomBehavior);

inputForm.setPrim_KeyBarrier(prim_KeyBarrier);
inputForm.setSec_KeyBarrier(sec_KeyBarrier);
inputForm.setTer_KeyBarrier(ter_KeyBarrier);

inputForm.setPrim_OtherBarrier(prim_OtherBarrier);
inputForm.setSec_OtherBarrier(sec_OtherBarrier);
inputForm.setTer_OtherBarrier(ter_OtherBarrier);

inputForm.setPrim_benefit(prim_benefit);
inputForm.setSec_benefit(sec_benefit);
inputForm.setTer_benefit(ter_benefit);

inputForm.setPrim_practice(prim_practice);
inputForm.setSec_practice(sec_practice);
inputForm.setTer_practice(ter_practice);

inputForm.setPrim_support(prim_support);
inputForm.setSec_support(sec_support);
inputForm.setTer_support(ter_support);

boolean isUpdated = inputFormService.saveORUpdate(inputForm);
%>
<%if(isUpdated){ %>
	<div>Form data submitted successfully.</div>
<%}else{ %>
    <div>Failed to save submitted data.</div>
<%} %>

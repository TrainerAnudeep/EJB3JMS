<%@ page import ="com.example.jms.*,javax.naming.*,javax.jms.Queue,javax.jms.*,java.text.*,java.sql.Timestamp"%>

<%
	if("send".equals(request.getParameter("action")))
	{
		QueueConnection cnn=null;
		QueueSender sender=null;
		QueueSession sess=null;
		Queue queue=null;
		try
		{
			InitialContext ctx=new InitialContext();
			queue=(Queue)ctx.lookup("hq");
			out.println("Queue created");
			QueueConnectionFactory factory=(QueueConnectionFactory)ctx.lookup("hcf");
			cnn=factory.createQueueConnection();
			sess=cnn.createQueueSession(false,QueueSession.AUTO_ACKNOWLEDGE);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		TextMessage msg=sess.createTextMessage(request.getParameter("start")+","+
		request.getParameter("end")+","+
		request.getParameter("growthrate")+","+
		request.getParameter("saving"));

		long sent=System.currentTimeMillis();
		
		msg.setLongProperty("sent",sent);
		
		sender=sess.createSender(queue);
		sender.send(msg);
		sess.close();
%>
<html>
<head>
<meta http-equiv="REFRESH" content=" 3;URL=check.jsp?sent=<%=sent%>" >
</head>
<body>
 Please wait while I am checking whether the message has arrived .<br>
 <h3>Sent =<%=sent%></h3>
 <a href="calculator.jsp">Go Back to Calculator</a>
 </body>
</html>
<%
	return;
	}
	else
	{
	int start=25;
	int end=65;
	double growthrate=0.08;
	double saving=300.0;
	
%>
<html>
	<body>
	<p>Investment calculator<br/>
	<form action="calculator.jsp" method="post">
		<input type="hidden" name="action" value="send">
		Start age=<input type=text name="start" value="<%=start%>"><br/>
		End  age=<input type=text name="end" value="<%=end%>"><br/>
		Annual Growth Rate:=<input type=text name="growthrate" value="<%=growthrate%>"><br/>
		Montly Saving: =<input type=text name="saving" value="<%=saving%>"><br/>
		<input type=submit value="calculate">
		<input type="button" value="close Window" onClick="window.close()">
	</form>
	</p>
	</body>
</html>
<% return;}%>

<%@ page import="timetracker.ApplicationInfo"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<g:set var="entityName"
	value="${message(code: 'applicationInfo.label', default: 'ApplicationInfo')}" />
<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
	<a href="#list-applicationInfo" class="skip" tabindex="-1"><g:message
			code="default.link.skip.label" default="Skip to content&hellip;" /></a>
	<div class="nav" role="navigation">
		<ul>
			<li><a class="home" href="${createLink(uri: '/')}"><g:message
						code="default.home.label" /></a></li>
			<li><g:link class="create" action="create">
					<g:message code="default.new.label" args="[entityName]" />
				</g:link></li>
		</ul>
	</div>
	<div id="list-applicationInfo" class="content scaffold-list"
		role="main">
		<h1>
			<g:message code="default.list.label" args="[entityName]" />
		</h1>
		<g:if test="${flash.message}">
			<div class="message" role="status">
				${flash.message}
			</div>
		</g:if>
		<script type="text/javascript"
			src="https://www.google.com/jsapi?autoload={'modules':[{'name':'visualization',
       'version':'1.1','packages':['timeline']}]}"></script>
		<script type="text/javascript">

google.setOnLoadCallback(drawChart);
var wrapper;
function drawChart() {

 // var container = document.getElementById('example5.1');
 // var chart = new google.visualization.Timeline(container);
  
  var dataTable = new google.visualization.DataTable();
  dataTable.addColumn({ type: 'string', id: 'Type' });
  dataTable.addColumn({ type: 'string', id: 'Name' });
  dataTable.addColumn({ type: 'number', id: 'Start' });
  dataTable.addColumn({ type: 'number', id: 'End' });
  dataTable.addRows(${applicationInfoInstanceList});
  var options = {
			avoidOverlappingGridLines: false,
			timeline: { 
				  //rowLabelStyle: {fontName: 'Helvetica', fontSize: 24, color: '#603913' },
		      		//barLabelStyle: { fontName: 'Garamond', fontSize: 14 } 
			}
		  };
  wrapper = new google.visualization.ChartWrapper({
	    chartType: 'Timeline',
	    dataTable: dataTable,
	    options: options,
	    containerId: 'visualization'
	  });

 
  

 // chart.draw(dataTable, options);

  google.visualization.events.addListener(wrapper, 'ready', onReady);
  wrapper.draw();
  function onmouseoverHandler() {
	    alert('A table row was selected');
	    alert(e)
	  }
 
  function onReady() {
	  alert(1)
	  google.visualization.events.addListener(wrapper.getChart(), 'ready', onReadyChart);
  }
  function onReadyChart() {
	  alert(2)
	  google.visualization.events.addListener(wrapper.getChart(), 'onmouseover', onmouseoverHandler);
  }
  
  
 

}

</script>

		<div id="visualization" style="width: 900px; height: 200px;"></div>
	</div>
</body>
</html>

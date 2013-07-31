
<%@ page import="timetracker.AppTrackItem"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<g:set var="entityName" value="${message(code: 'appTrackItem.label', default: 'appTrackItem')}" />
<title><g:message code="default.list.label" args="[entityName]" /></title>
<r:require modules="timetracker" />


</head>
<body>
 <a href="#list-appTrackItem" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;" /></a>
 <div class="nav" role="navigation">
  <ul>
   <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label" /></a></li>
   <li><g:link class="create" action="create">
     <g:message code="default.new.label" args="[entityName]" />
    </g:link></li>
  </ul>
 </div>
 <div id="list-appTrackItem" class="content scaffold-list" role="main">
  <h1>
   <g:message code="default.list.label" args="[entityName]" />
  </h1>
  <g:if test="${flash.message}">
   <div class="message" role="status">
    ${flash.message}
   </div>
  </g:if>

  <script src="http://d3js.org/d3.v2.js"></script>
  
  
  <g:javascript>
     jQuery(document).ready(function($) {
       $('#timetrackerChart').timetrackerD3({
         trackItems: ${appTrackItemInstanceList},
         trackNames:["Application"]
       });
     });
	   
function changeTimeDomain(timeDomainString){
  $('#timetrackerChart').data('plugin_timetrackerD3').changeTimeDomain(timeDomainString);
}


function addTask() {

    var lastEndDate = getEndDate();
    var taskStatusKeys = Object.keys(taskStatus);
    var taskStatusName = taskStatusKeys[Math.floor(Math.random() * taskStatusKeys.length)];
    var taskName = taskNames[Math.floor(Math.random() * taskNames.length)];

    tasks.push({
	"startDate" : d3.time.hour.offset(lastEndDate, Math.ceil(1 * Math.random())),
	"endDate" : d3.time.hour.offset(lastEndDate, (Math.ceil(Math.random() * 3)) + 1),
	"taskName" : taskName,
	"status" : taskStatusName
    });

    changeTimeDomain(timeDomainString);
    gantt.redraw(tasks);
};

function removeTask() {
    tasks.pop();
    changeTimeDomain(timeDomainString);
    gantt.redraw(tasks);
};

	</g:javascript>


  <button type="button" onclick="addTask()">Add Task</button>
  <button type="button" onclick="removeTask()">Remove Task</button>
  <button type="button" onclick="changeTimeDomain('1min')">1 min</button>
  <button type="button" onclick="changeTimeDomain('1hr')">1 HR</button>
  <button type="button" onclick="changeTimeDomain('3hr')">3 HR</button>
  <button type="button" onclick="changeTimeDomain('6hr')">6 HR</button>
  <button type="button" onclick="changeTimeDomain('1day')">1 DAY</button>
  <button type="button" onclick="changeTimeDomain('1week')">1 WEEK</button>
  <div id="timetrackerChart"></div>
 </div>
</body>
</html>

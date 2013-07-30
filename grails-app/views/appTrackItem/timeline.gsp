
<%@ page import="timetracker.AppTrackItem"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<g:set var="entityName" value="${message(code: 'appTrackItem.label', default: 'appTrackItem')}" />
<title><g:message code="default.list.label" args="[entityName]" /></title>
<r:require modules="timelineD3" />

<style type="text/css">
.chart {
	font-family: Arial, sans-serif;
	font-size: 12px;
}

.axis path,.axis line {
	fill: none;
	stroke: #000;
	shape-rendering: crispEdges;
}

.bar {
	fill: #33b5e5;
}

.bar-failed {
	fill: #CC0000;
}

.bar-running {
	fill: #669900;
}

.bar-succeeded {
	fill: #33b5e5;
}

.bar-killed {
	fill: #ffbb33;
}
</style>
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
	    $(document).ready({

	    });
	   var tasks = ${appTrackItemInstanceList};

var taskStatus = {
    "SUCCEEDED" : "bar",
    "FAILED" : "bar-failed",
    "RUNNING" : "bar-running",
    "KILLED" : "bar-killed"
};

var taskNames = ["Application"];

tasks.sort(function(a, b) {
    return a.endDate - b.endDate;
});
var maxDate = tasks[tasks.length - 1].endDate;
tasks.sort(function(a, b) {
    return a.startDate - b.startDate;
});
var minDate = tasks[0].startDate;

var format = "%H:%M";
var timeDomainString = "1day";

var gantt = d3.gantt().taskTypes(taskNames).taskStatus(taskStatus).tickFormat(format);


gantt.timeDomainMode("fixed");
changeTimeDomain(timeDomainString);

gantt(tasks);

function changeTimeDomain(timeDomainString) {
    this.timeDomainString = timeDomainString;
    switch (timeDomainString) {
    case "1hr":
	format = "%H:%M:%S";
	gantt.timeDomain([ d3.time.hour.offset(getEndDate(), -1), getEndDate() ]);
	break;
    case "3hr":
	format = "%H:%M";
	gantt.timeDomain([ d3.time.hour.offset(getEndDate(), -3), getEndDate() ]);
	break;

    case "6hr":
	format = "%H:%M";
	gantt.timeDomain([ d3.time.hour.offset(getEndDate(), -6), getEndDate() ]);
	break;

    case "1day":
	format = "%H:%M";
	gantt.timeDomain([ d3.time.day.offset(getEndDate(), -1), getEndDate() ]);
	break;

    case "1week":
	format = "%a %H:%M";
	gantt.timeDomain([ d3.time.day.offset(getEndDate(), -7), getEndDate() ]);
	break;
    default:
	format = "%H:%M"

    }
    gantt.tickFormat(format);
    gantt.redraw(tasks);
}

function getEndDate() {
    var lastEndDate = Date.now();
    if (tasks.length > 0) {
	lastEndDate = tasks[tasks.length - 1].endDate;
    }

    return lastEndDate;
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
  <button type="button" onclick="changeTimeDomain('1hr')">1 HR</button>
  <button type="button" onclick="changeTimeDomain('3hr')">3 HR</button>
  <button type="button" onclick="changeTimeDomain('6hr')">6 HR</button>
  <button type="button" onclick="changeTimeDomain('1day')">1 DAY</button>
  <button type="button" onclick="changeTimeDomain('1week')">1 WEEK</button>
 </div>
</body>
</html>

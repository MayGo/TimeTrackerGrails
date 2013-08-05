
<%@ page import="timetracker.AppTrackItem"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="kickstart">
<title><g:message code="default.welcome.title" args="[meta(name:'app.name')]" /></title>
<g:set var="layout_nosecondarymenu" value="${true}" scope="request" />
<r:require modules="timetracker" />
<script src="http://d3js.org/d3.v2.js"></script>


<g:javascript>
     jQuery(document).ready(function($) {
       onBrushSelection = function (startDate, endDate){
         console.log("onBrushSelection")
         console.log(startDate)
         console.log(endDate)
       }
       
       $('#timetrackerChart').timetrackerD3({
         trackItems: ${appTrackItemInstanceList},
         trackNames:["Application"],
         onBrushSelection:onBrushSelection
       });
     });
    
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

</head>
<body>

 <section id="show-timeline" class="first">
  <ul id="Menu" class="nav nav-pills">
   <li><g:link action="create">
     <i class="icon-trash"></i>
     <g:message code="default.remove.label" args="['Tag']" />
    </g:link></li>
   <li><g:link action="create">
     <i class="icon-plus"></i>
     <g:message code="default.new.label" args="['Tag']" />
    </g:link></li>
  </ul>
  <div id="timetrackerChart"></div>
 </section>


</body>
</html>

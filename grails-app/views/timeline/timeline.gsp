
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

</head>
<body>

 <section id="show-timeline" class="first">
  <button type="button" onclick="addTask()" class="btn btn-medium">Add Task</button>
  <button type="button" onclick="removeTask()" class="btn btn-medium">Remove Task</button>
  <button type="button" onclick="changeTimeDomain('1min')" class="btn btn-medium">1 min</button>
  <button type="button" onclick="changeTimeDomain('1hr')" class="btn btn-medium">1 HR</button>
  <button type="button" onclick="changeTimeDomain('3hr')" class="btn btn-medium">3 HR</button>
  <button type="button" onclick="changeTimeDomain('6hr')" class="btn btn-medium">6 HR</button>
  <button type="button" onclick="changeTimeDomain('1day')" class="btn btn-medium">1 DAY</button>
  <button type="button" onclick="changeTimeDomain('1week')" class="btn btn-medium">1 WEEK</button>
  <div id="timetrackerChart"></div>
 </section>


</body>
</html>


<%@ page import="timetracker.AppTrackItem"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="timeline">
<title><g:message code="default.welcome.title" args="[meta(name:'app.name')]" /></title>
<g:set var="layout_nosecondarymenu" value="${true}" scope="request" />
<r:require modules="timetracker" />
<script src="http://d3js.org/d3.v2.js"></script>


<g:javascript>
     jQuery(document).ready(function($) {
       onBrushSelection = function (startDate, endDate){
         console.log("onBrushSelection")
         $('#beginDate').val(startDate.getTime())
         $('#endDate').val(endDate.getTime())
       }
       
       $('#timetrackerChart').timetrackerD3({
         trackItems: ${appTrackItemInstanceList},
         trackNames:["AppTrackItem", "LogTrackItem"],
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
  <r:script>
          
			$(document).ready(function() {
  			  $('#addTagSubmit').click(function(){
                  if ($('#tag').val()==="") {
                    // invalid
                    $('#tag').next('.help-inline').show();
                    return false;
                  }else if ($('#beginDate').val()==="" || $('#endDate').val()==="") {
                    alert("BeginDate or EndDate not specified!");
                    return false;
                  }
                  else {
                    // submit the form here
                    $.post($('#addTagForm').attr('action'),$("#addTagForm").serialize(), function(data) {
console.log(data);
var plugin = $("#timetrackerChart").timetrackerD3().data("plugin_timetrackerD3")
                        plugin.addItem(data)
                         $('#addTagDialog').modal('hide')  
                    			// $('.top-left').notify({
                                //    message: { text: data }
                               //   }).show();
                    });
                    return true;
                  }
                    
              });
			});
			
       </r:script>
       <div class='notifications top-left'></div>
  <div id="addTagDialog" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="addTagDialogLabel" aria-hidden="true"
    style="display: none;">
    <div class="modal-header">
      <a href="#" class="close" data-dismiss="modal" aria-hidden="true">×</a>
      <h3 id="addTagDialogLabel">Add new tag</h3>
    </div>
    <div class="modal-body">
      <form id="addTagForm" method="POST" action="${createLink(controller:'logTrackItem',action: 'save')}" >
        <g:render template="/logTrackItem/form" />
      </form>
    </div>
    <div class="modal-footer">
      <a href="#" class="btn" data-dismiss="modal" aria-hidden="true">Cancel</a>
      <a href="#" id="addTagSubmit" class="btn btn-primary">OK</a>
    </div>
  </div>

  <section id="show-timeline" class="first">
    <ul id="Menu" class="nav nav-pills">
      <li><g:link action="delete">
          <i class="icon-trash"></i>
          <g:message code="default.remove.label" args="['Tag']" />
        </g:link></li>
      <li><a data-toggle="modal" href="#addTagDialog"> <i class="icon-plus"></i> <g:message code="default.new.label" args="['Tag']" />
      </a></li>
    </ul>
    <div id="timetrackerChart"></div>
  </section>


</body>
</html>

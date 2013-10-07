
<%@ page import="timetracker.AppTrackItem"%>
<!DOCTYPE html>
<html>
<head>
<head>
    <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Open+Sans"/>

<meta name="layout" content="ng-app">
<title><g:message code="timeline.welcome.title"
		args="[meta(name:'app.name')]" /></title>
<r:require modules="timetracker" />
<script src="http://d3js.org/d3.v2.js"></script>


<g:javascript>
     jQuery(document).ready(function($) {
       onBrushSelection = function (d){
         $('#beginDate').val(d.beginDate.getTime())
         $('#endDate').val(d.endDate.getTime())
         $('#id').val(d.id)
       }
       onTrackItemChange = function (d){
            $.post($('#addTagForm').attr('action'),d, function(data) {
            console.log("updated");
                        console.log(data);
                      //  var plugin = $("#timetrackerChart").timetrackerD3().data("plugin_timetrackerD3")
                       
                    });
       }
       
       $('#timetrackerChart').timetrackerD3({
         trackItems: ${appTrackItemInstanceList},
         trackNames:["AppTrackItem", "LogTrackItem"],
         defaultBeginDate:${day},
         onBrushSelection:onBrushSelection,
         onTrackItemChange:onTrackItemChange
       });
     });
    
function addTask() {

    var lastEndDate = getEndDate();
    var taskStatusKeys = Object.keys(taskStatus);
    var taskStatusName = taskStatusKeys[Math.floor(Math.random() * taskStatusKeys.length)];
    var taskName = taskNames[Math.floor(Math.random() * taskNames.length)];

    tasks.push({
 "beginDate" : d3.time.hour.offset(lastEndDate, Math.ceil(1 * Math.random())),
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
<body  data-ng-app="scaffolding" data-base-url="${createLink(uri: '/')}">
	<r:script>
          
			$(document).ready(function() {
			  $("#index-tabs").ajaxTab();
			
			
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

	<div id="addTagDialog" class="modal hide fade" tabindex="-1"
		role="dialog" aria-labelledby="addTagDialogLabel" aria-hidden="true"
		style="display: none;">
		<div class="modal-header">
			<a href="#" class="close" data-dismiss="modal" aria-hidden="true">×</a>
			<h3 id="addTagDialogLabel">Add new tag</h3>
		</div>
		<div class="modal-body">
			<form id="addTagForm" method="POST"
				action="${createLink(controller:'logTrackItem',action: 'save')}">
				<g:render template="/timeline/logTrackForm" />
			</form>
		</div>
		<div class="modal-footer">
			<a href="#" class="btn" data-dismiss="modal" aria-hidden="true">Cancel</a>
			<a href="#" id="addTagSubmit" class="btn btn-primary">OK</a>
		</div>
	</div>

	<section id="timeline-time">
		<a
			href="${createLink(controller:'timeline',action: 'timeline', params: ['day': day-86400000])}"
			class="btn"><g:message code="timeline.yesterday" /></a> <a
			href="${createLink(controller:'timeline',action: 'timeline', params: ['day': today])}"
			class="btn ${(day==today)?"disabled":""}"><g:message
				code="timeline.today" /></a> <a
			href="${createLink(controller:'timeline',action: 'timeline', params: ['day': day+86400000])}"
			class="btn"><g:message code="timeline.tomorrow" /></a>
	</section>
  
  
	<section id="timeline-edit">
		<ul id="Menu" class="nav nav-pills">
			<li><g:link action="delete">
					<i class="icon-trash"></i>
					<g:message code="default.remove.label" args="['Tag']" />
				</g:link></li>
			<li><a data-toggle="modal" href="#addTagDialog"> <i
					class="icon-plus"></i> <g:message code="default.new.label"
						args="['Tag']" />
			</a></li>
		</ul>
		<div id="timetrackerChart"></div>
	</section>
  

    <div ng-controller="TabsDemoCtrl">
      <tabset>
        <tab select="renderTmpl('/appTrackItem/list')">
          <tab-heading>
            <i class="icon-justify"></i> <g:message code="timeline.all" args="['Tag']" />
          </tab-heading>
        </tab>
        <tab select="renderTmpl('/logTrackItem/list')">
          <tab-heading>
            <i class="icon-message-full"></i> <g:message code="timeline.top" args="['Tag']" />
          </tab-heading>
        
        </div>
        </tab>
      </tabset>
      <div class="content" role="main" data-ng-view>
    </div>

  </body>
</html>


<div class="page-header">
	<h1>Show LogTrackItem</h1>
</div>
<alert level="{{message.level}}" text="{{message.text}}"/>
<dl class="dl-horizontal">
	
	<dt>Begin Date</dt>
	<dd data-ng-bind="item.beginDate"></dd>
	
	<dt>Date Created</dt>
	<dd data-ng-bind="item.dateCreated"></dd>
	
	<dt>Desc</dt>
	<dd data-ng-bind="item.desc"></dd>
	
	<dt>End Date</dt>
	<dd data-ng-bind="item.endDate"></dd>
	
	<dt>Last Updated</dt>
	<dd data-ng-bind="item.lastUpdated"></dd>
	
	<dt>Tag</dt>
	<dd data-ng-bind="item.tag"></dd>
	
</dl>
<div class="form-actions">
	<a class="btn" data-ng-href="#/edit/{{item.id}}"><i class="icon-edit"></i> Edit</a>
	<button type="button" class="btn btn-danger" data-ng-click="delete(item)"><i class="icon-trash"></i> Delete</button>
</div>


<div class="page-header">
	<h1>Show AppTrackItem</h1>
</div>
<alert type="message.level">{{message.text}}</alert>
<dl class="dl-horizontal">
	
	<dt>Begin Date</dt>
	<dd data-ng-bind="item.beginDate"></dd>
	
	<dt>Date Created</dt>
	<dd data-ng-bind="item.dateCreated"></dd>
	
	<dt>End Date</dt>
	<dd data-ng-bind="item.endDate"></dd>
	
	<dt>Last Updated</dt>
	<dd data-ng-bind="item.lastUpdated"></dd>
	
	<dt>Tag</dt>
	<dd data-ng-bind="item.tag"></dd>
	
	<dt>Title</dt>
	<dd data-ng-bind="item.title"></dd>
	
</dl>
<div class="form-actions">
	<button type="button" class="btn" data-ng-click="edit(item)"><i class="icon-edit"></i> Edit</button>
	<button type="button" class="btn btn-danger" data-ng-click="delete(item)"><i class="icon-trash"></i> Delete</button>
</div>

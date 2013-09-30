
<div class="page-header">
	<h1>StateTrackItem List</h1>
</div>
<alert level="{{message.level}}" text="{{message.text}}"/>
<table class="table table-bordered table-striped">
    <thead>
        <tr>
		
			<th>Begin Date</th>
		
			<th>Date Created</th>
		
			<th>End Date</th>
		
			<th>Last Updated</th>
		
			<th>Tag</th>
		
        </tr>
    </thead>
    <tbody>
        <tr data-ng-repeat="item in list" data-ng-click="show(item)">
		
			<td>{{item.beginDate}}</td>
		
			<td>{{item.dateCreated}}</td>
		
			<td>{{item.endDate}}</td>
		
			<td>{{item.lastUpdated}}</td>
		
			<td>{{item.tag}}</td>
		
        </tr>
    </tbody>
</table>
<div class="pagination pagination-centered" data-pagination data-total="total"></div>
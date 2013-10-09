<alert type="message.level" >{{message.text}}</alert>
<table class="table table-bordered table-striped">
    <thead>
        <tr>
		
			<th>Begin Date</th>
		
			<th>Date Created</th>
		
			<th>End Date</th>
		
			<th>Last Updated</th>
		
			<th>Tag</th>
		
			<th>Title</th>
		
        </tr>
    </thead>
    <tbody>
        <tr data-ng-repeat="item in list" data-ng-click="show(item)">
		
			<td>{{item.beginDate}}</td>
		
			<td>{{item.dateCreated}}</td>
		
			<td>{{item.endDate}}</td>
		
			<td>{{item.lastUpdated}}</td>
		
			<td>{{item.tag}}</td>
		
			<td>{{item.title}}</td>
		
        </tr>
    </tbody>
</table>
<pagination boundary-links="true" total-items="total" page="currentPage" class="pagination-small" previous-text="&lsaquo;" next-text="&rsaquo;" first-text="&laquo;" last-text="&raquo;"></pagination>

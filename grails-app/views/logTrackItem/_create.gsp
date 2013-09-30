
<div class="page-header">
	<h1>Create LogTrackItem</h1>
</div>
<alert level="{{message.level}}" text="{{message.text}}"/>
<form name="form" data-ng-submit="save(item)" class="form-horizontal">

	<div class="control-group" data-ng-class="{error: errors.beginDate}">
		<label class="control-label" for="beginDate">Begin Date</label>
		<div class="controls">
			<input type="text" id="beginDate" name="beginDate" required data-ng-model="item.beginDate">
			<span class="help-inline" data-ng-show="errors.beginDate">{{errors.beginDate}}</span>
		</div>
	</div>
	
	<div class="control-group" data-ng-class="{error: errors.desc}">
		<label class="control-label" for="desc">Desc</label>
		<div class="controls">
			<input type="text" id="desc" name="desc" required data-ng-model="item.desc">
			<span class="help-inline" data-ng-show="errors.desc">{{errors.desc}}</span>
		</div>
	</div>
	
	<div class="control-group" data-ng-class="{error: errors.endDate}">
		<label class="control-label" for="endDate">End Date</label>
		<div class="controls">
			<input type="text" id="endDate" name="endDate" required data-ng-model="item.endDate">
			<span class="help-inline" data-ng-show="errors.endDate">{{errors.endDate}}</span>
		</div>
	</div>
	
	<div class="control-group" data-ng-class="{error: errors.tag}">
		<label class="control-label" for="tag">Tag</label>
		<div class="controls">
			<input type="text" id="tag" name="tag" required data-ng-model="item.tag">
			<span class="help-inline" data-ng-show="errors.tag">{{errors.tag}}</span>
		</div>
	</div>
	
	<div class="form-actions">
		<button type="submit" class="btn btn-primary" data-ng-disabled="form.$invalid"><i class="icon-ok"></i> Create</button>
	</div>
</form>

<% import grails.persistence.Event %>
<div class="page-header">
	<h1>Show ${className}</h1>
</div>
<alert type="message.level">{{message.text}}</alert>>
<dl class="dl-horizontal">
	<%  excludedProps = Event.allEvents.toList() << 'id' << 'version'
	allowedNames = domainClass.persistentProperties*.name << 'dateCreated' << 'lastUpdated'
	props = domainClass.properties.findAll { allowedNames.contains(it.name) && !excludedProps.contains(it.name) }
	Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))
	props.each { p -> %>
	<dt>${p.naturalName}</dt>
	<dd data-ng-bind="item.${p.name}"></dd>
	<%  } %>
</dl>
<div class="form-actions">
	<button type="button" class="btn" data-ng-click="edit(item)"><i class="icon-edit"></i> Edit</button>
	<button type="button" class="btn btn-danger" data-ng-click="delete(item)"><i class="icon-trash"></i> Delete</button>
</div>

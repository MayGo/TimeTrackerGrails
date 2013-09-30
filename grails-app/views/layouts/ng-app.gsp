<!doctype html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]>    <html class="no-js lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]>    <html class="no-js lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js" lang="en">
<!--<![endif]-->
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title><g:layoutTitle default="Grails" /></title>
  <meta name="viewport" content="width=device-width">
  <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
  <r:require modules="jquery" />
  <r:require modules="bootstrap" />
  <r:require modules="bootstrap_utils" />
  <r:require module="angular-scaffolding" />
  <g:layoutHead />
  <r:layoutResources />

</head>
<body data-ng-app="${pageProperty(name: 'body.data-ng-app')}"
  data-base-url="${pageProperty(name: 'body.data-base-url', default: createLink(action: 'index').replaceAll(/index$/, ''))}"
  data-template-url="${pageProperty(name: 'body.data-template-url', default: createLink(uri: '/template'))}"
  data-common-template-url="${pageProperty(name: 'body.data-common-template-url', default: createLink(uri: '/template'))}">
 <div id="header"><div id="logo" class="inset">TimeTracker</div></div>
	<div id="main-content">
		<g:layoutBody />
		<!-- Included Javascript files and other resources -->
		<r:layoutResources />
	</div>
</body>
</html>
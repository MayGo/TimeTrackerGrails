/**
 * A service for storing one-time messages to be displayed after redirecting to
 * another view.
 */
angular.module('flashService', []).factory('Flash', function() {
    var flash = {};

    flash.getMessage = function() {
    	
        var value = this.message;
        this.message = undefined;
        console.log("getMessage")
        console.log(value)
        return value;
    };

    flash.error = function(text) {
        this.message = {level: 'error', text: text};
    };
    flash.success = function(text) {
    	console.log("success...")
        this.message = {level: 'success', text: text};
    	console.log( this.message)
    };
    flash.info = function(text) {
        this.message = {level: 'info', text: text};
    };

    return flash;
});

/**
 * The main scaffolding module.
 */
var scaffoldingModule = angular.module('scaffolding', ['grailsService', 'flashService', 'ui.bootstrap']);

/**
 * Route definitions connecting URL fragments to views and controllers.
 */
scaffoldingModule.config(function($routeProvider) {
        var baseUrl = $('body').data('template-url');
        console.log(baseUrl);
        $routeProvider.
            when('/create', {templateUrl: baseUrl + '/create', controller: CreateCtrl}).
            when('/:contr/create', { templateUrl: function(params){ return baseUrl+'/'+params.contr+'/create' }, controller: CreateCtrl}).
           
            when('/edit/:id', {templateUrl: baseUrl + '/edit', controller: EditCtrl}).
            when('/:contr/edit/:id', { templateUrl: function(params){ return baseUrl+'/'+params.contr+'/edit' }, controller: EditCtrl}).
            
            when('/list', {templateUrl: baseUrl + '/list', controller: ListCtrl}).
            when('/:contr/list', { templateUrl: function(params){ return baseUrl+'/'+params.contr+'/list' }, controller: ListCtrl } ).
            
            when('/show/:id', {templateUrl: baseUrl + '/show', controller: ShowCtrl}).
            when('/:contr/show/:id', { templateUrl: function(params){ return baseUrl+'/'+params.contr+'/show' }, controller: ShowCtrl}).
            
            otherwise({redirectTo: '/list'});
    }
);

/**
 * A directive for including an alert message in the page.
 */
//scaffoldingModule.directive('alert', function() {
//	var baseUrl = $('body').data('common-template-url');
//	return {
//        restrict: 'E', // can only be used as an element
//        transclude: false, // the element should not contain any content so
//							// there's no need to transclude
//        scope: {
//			level: '@level',
//			text: '@text'
//        },
//        templateUrl: baseUrl + '/alert.html',
//        replace: true
//    }
//});

/**
 * A directive for including a standard pagination block in the page.
 */
/*
scaffoldingModule.directive('pagination', function() {
	var baseUrl = $('body').data('common-template-url');
	return {
        restrict: 'A', // can only be used as an attribute
        transclude: false, // the element should not contain any content so
							// there's no need to transclude
        scope: {
            total: '=total' // inherit the total property from the controller
							// scope
        },
        controller: function($scope, $routeParams) {
            $scope.max = parseInt($routeParams.max) || 10;
            $scope.offset = parseInt($routeParams.offset) || 0;
            $scope.currentPage = Math.ceil($scope.offset / $scope.max);
            $scope.contr=$routeParams.contr;
            console.log(1)
            $scope.pages = function() {
            	 console.log(2)
                var pages = [];
                for (var i = 0; i < Math.ceil($scope.total / $scope.max); i++)
                    pages.push(i);
                
                console.log(3)
                return pages;
            };
            console.log(4)
            $scope.lastPage = function() {
                return $scope.pages().slice(-1)[0];
            };
            console.log(5)
        },
        templateUrl: baseUrl + '/pagination.html',
        replace: false
    }
});
*/
function toArray(element) {
    return Array.prototype.slice.call(element);
}

Function.prototype.curry = function() {
    if (arguments.length < 1) {
        return this; // nothing to curry with - return function
    }
    var __method = this;
    var args = toArray(arguments);
    return function() {
        return __method.apply(this, args.concat(toArray(arguments)));
    }
}

/**
 * Generic $resource error handler used by all controllers.
 */
function errorHandler($scope, $location, Flash, response) {
    switch (response.status) {
        case 404: // resource not found - return to the list and display
					// message returned by the controller
            Flash.error(response.data.message);
            $location.path('/list');
            break;
        case 409: // optimistic locking failure - display error message on the
					// page
            $scope.message = {level: 'error', text: response.data.message};
            break;
        case 422: // validation error - display errors alongside form fields
            $scope.errors = response.data.errors;
            break;
        default: // TODO: general error handling
    }
}

function ListCtrl($scope, $routeParams, $location, Grails, Flash) {
    Grails.list($routeParams, function(list, headers) {
        $scope.list = list;
        $scope.max = parseInt($routeParams.max) || 10;
        $scope.offset = parseInt($routeParams.offset) || 0;
        $scope.currentPage = Math.ceil($scope.offset / $scope.max);

        $scope.total = parseInt(headers('X-Pagination-Total'));
        $scope.$watch('currentPage',  function(newValue, oldValue)  {
        	if(newValue!=oldValue){
    			$location.search({max:$scope.max,offset:($scope.currentPage) * $scope.max});
        	}
        });
        $scope.message = Flash.getMessage();
        $scope.message = {level:"info", text:"You have made to here!"}
        
    }, errorHandler.curry($scope, $location, Flash));

    $scope.show = function(item) {
    	console.log("show"+$routeParams.contr)
        $location.path('/'+$routeParams.contr+'/show/' + item.id);
    };
}

function ShowCtrl($scope, $routeParams, $location, Grails, Flash) {
	$scope.message = Flash.getMessage();
    console.log("nonii")
    console.log($scope.message);

    Grails.get({contr: $routeParams.contr, id: $routeParams.id}, function(item) {
        $scope.item = item;
    }, errorHandler.curry($scope, $location, Flash));

    $scope.edit = function(item) {
    	$location.path('/'+$routeParams.contr+'/edit/' + item.id);
    };
    
    $scope.delete = function(item) {
        item.$delete({contr: $routeParams.contr}, function(response) {
            Flash.success(response.message);
            $location.path('/'+$routeParams.contr+'/list');
        }, errorHandler.curry($scope, $location, Flash));
    };
}

function CreateCtrl($scope, $location, Grails, Flash) {
    $scope.item = new Grails;

    $scope.save = function(item) {
        item.$save({contr: $routeParams.contr}, function(response) {
            Flash.success(response.message);
            $location.path('/'+$routeParams.contr+'/show/' + response.id);
        }, errorHandler.curry($scope, $location, Flash));
    };
}

function EditCtrl($scope, $routeParams, $location, Grails, Flash) {
	console.log("EditCtrl:"+ $routeParams.contr)
    Grails.get({contr: $routeParams.contr, id: $routeParams.id}, function(item) {
        $scope.item = item;
    }, errorHandler.curry($scope, $location, Flash));

    $scope.update = function(item) {
        item.$update({contr: $routeParams.contr}, function(response) {
            Flash.success(response.message);
            $location.path('/'+$routeParams.contr+'/show/' + response.id);
        }, errorHandler.curry($scope, $location, Flash));
    };

    $scope.delete = function(item) {
        item.$delete({contr: $routeParams.contr}, function(response) {
            Flash.success(response.message);
            $location.path('/'+$routeParams.contr+'/list');
        }, errorHandler.curry($scope, $location, Flash));
    };
}
function TabsDemoCtrl($scope, $routeParams, $location, Grails, Flash) {
	
	  
	  $scope.renderTmpl = function(path) {
	    	console.log("renderTmpl")
	        $location.path(path);
	    };
	};

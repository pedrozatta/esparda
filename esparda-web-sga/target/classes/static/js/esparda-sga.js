var app = angular.module('esparda-sga', [ 'ngRoute' ]);

app.config([
		'$routeProvider',
		'$httpProvider',
		function($routeProvider, $httpProvider) {

			$routeProvider.when('/', {
				templateUrl : 'view/home.html',
				controller : 'home'
			}).otherwise('/');

			$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
			$httpProvider.defaults.headers.common["Accept"] = "application/json";
			$httpProvider.defaults.headers.common["Content-Type"] = "application/json";

			// configure $http to view a login whenever a 403
			// unauthorized response arrives
			// http://www.bennadel.com/blog/2777-monitoring-http-activity-with-http-interceptors-in-angularjs.htm
			$httpProvider.interceptors.push(function($rootScope, $q) {
				return ({
					responseError : responseError
				});

				function responseError(response) {
					console.log(response.config.method + ' ' + response.config.url + ' ' + response.status + ' '
							+ response.data);
					if (response.status == 403) {
						$rootScope.$broadcast('event:accessDenied');
					}
					return ($q.reject(response));
				}
			});

		} ]);

app.controller('navigation', function($rootScope, $scope, $http, $location, $route) {

	$scope.tab = function(route) {
		return $route.current && route === $route.current.controller;
	};

	$http.get('rest/usuario/autenticado').success(function(data) {
		if (data.nome) {
			$rootScope.authenticated = true;
		} else {
			$rootScope.authenticated = false;
		}
	}).error(function(reason) {
		$rootScope.authenticated = false;
	});

	$scope.credentials = {};

	$scope.logout = function() {
		$http.post('logout', {}).success(function() {
			$rootScope.authenticated = false;
			$location.path("/");
		}).error(function(data) {
			console.log("Logout failed")
			$rootScope.authenticated = false;
		});
	}

});

app.controller('RestClientCtrl', function($rootScope, $scope, $http, $q) {

	// $scope.templates = [];
	// localStorage.setItem("restClientTemplateList",
	// angular.toJson(templates));
	// $scope.templates =
	// angular.fromJson(localStorage.getItem("restClientTemplateList"));

	$scope.data = {
		url : "rest/usuario/status",
		json : "",
		retorno : "",
		tipo : "",
		metodo : "get"
	};

	var sucessFunction = function(data) {
		console.log("sucesso!")
		$scope.data.tipo = "sucesso";
		$scope.data.retorno = angular.toJson(data, true);
		$scope.$emit('esp-event-message:info', data);

	};

	var errorFunction = function(reason) {
		var data = $scope.data;
		data.tipo = "falha";
		data.retorno = angular.toJson(reason, true);
		$scope.$emit('esp-event-message:erro', reason);
	};

	$scope.enviar = function() {
		var data = $scope.data;

		switch (data.metodo) {
		case "post":
		case "put":
			$http[data.metodo](data.url, data.json).success(sucessFunction).error(errorFunction);
			break;

		default:
			$http[data.metodo](data.url).success(sucessFunction).error(errorFunction);
			break;
		}

	}

});

app.controller('home', function($rootScope, $scope, $http, $q) {

	$scope.fetchUserResource = function() {
		$rootScope.has403 = false;
		var d = $q.defer();
		$http.get('resource/userresource').success(function(response) {
			d.resolve(response);
		}).error(function() {
			d.reject();
		});
		d.promise.then(function success(response) {
			$scope.greeting = response;
		}, function error(error) {
			$scope.greeting = {
				"id" : "Default",
				"content" : "Error"
			};
		});
	}

	$scope.pesquisar_click = function(url) {
		$rootScope.has403 = false;
		$http.get(url).success(function(response) {
			console.log("sucesso!")
			$scope.retorno = response;
		}).error(function(reason) {
			console.warn("falha!")
			$scope.retorno = reason;
		});
	}

	$scope.fetchAdminResource = function() {
		$rootScope.has403 = false;
		var d = $q.defer();
		$http.get('resource/adminresource').success(function(response) {
			d.resolve(response);
		}).error(function() {
			d.reject();
		});
		d.promise.then(function success(response) {
			$scope.greeting = response;
		}, function error(error) {
			$scope.greeting = {
				"id" : "Default",
				"content" : "Error"
			};
		});
	}
});

app.run(function($rootScope, $log) {
	$rootScope.$on('event:accessDenied', function() {
		$rootScope.requests403 = [];
		console.log('Access denied');
		$rootScope.errorMsg = "You don't have the access to resource";
		$rootScope.has403 = true;
	});
	$rootScope.$on('esp-event-message:info', function(event, args) {
		$log.info(angular.toJson(args));
	});
	$rootScope.$on('esp-event-message:erro', function(event, args) {
		$log.error(angular.toJson(args));
	});
	$rootScope.$on('esp-event-message:warn', function(event, args) {
		$log.warn(angular.toJson(args));
	});
	$rootScope.$on('esp-event-message:debug', function(event, args) {
		$log.debug(angular.toJson(args));
	});
	$rootScope.$on('esp-event-message:log', function(event, args) {
		$log.log(angular.toJson(args));
	});

});
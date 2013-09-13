angular.module('puppetManagerService', [ 'ngResource' ]).factory('ServerType',
		function($resource) {
			return $resource('configmanager/servertypes', {}, {
				update : {
					url : 'configmanager/servertypes/:name',
					method : 'PUT',
					params : {
						name : '@name'
					}
				},
				list : {
					url : 'configmanager/servertypes',
					method : 'GET',
					params : {
						name : '@name'
					}
				},
				get : {
					url : 'configmanager/servertypes/:name',
					method : 'GET',
					params : {
						name : '@name'
					}
				}
			});
		}).factory('DITail', function($resource) {
	return $resource('configmanager/servertypes/:serverTypeName/ditails', {}, {
		list : {
			url : 'configmanager/servertypes/:serverTypeName/ditails',
			method : 'GET',
			params : {
				serverTypeName : '@serverTypeName'
			}
		},
		update : {
			url : 'configmanager/servertypes/:serverTypeName/ditails/:id',
			method : 'PUT',
			params : {
				serverTypeName : '@serverTypeName',
				id : '@id'
			}
		}
	});
});

angular
		.module(
				'ditailManager',
				[ 'puppetManagerService', '$userServices' ],
				function($routeProvider, $httpProvider) {
					// $routeProvider.when('/posts/:id', {
					// templateUrl : '/post/view.html',
					// controller : ViewCtl
					// });
					// register the interceptor via an anonymous factory
					$httpProvider.responseInterceptors
							.push(function($q) {
								return function(promise) {
									return promise
											.then(
													function(response) {
														return response;
													},
													function(response) {
														if (response.status == "403") {
															window.location = "/login.htm";
															return;
														}
														var reason = response.data.reason ? response.data.reason
																: "Server can't be connected";
														alert(reason);
														return $q
																.reject(response);
													});
								};
							});
				});

function ServerTypeCtrl($scope, $routeParams, $rootScope, ServerType, DITail) {
	$scope.selectedServerType = '';
	$scope.editing = false;
	$scope.serverTypes = null;
	$scope.serverType = null;
	$scope.focusedDITail = {};
	$scope.ditail = null;
	$scope.ditails = null;
	$scope.focusedRawData = null;
	$scope.rawDatas = null;

	$scope.selectServerType = function(serverType) {
		$scope.selectedServerType = serverType.name;
		$scope.getServerType(serverType.name);
		$scope.listDITails(serverType.name);
	};

	$scope.setFocusedDITail = function(ditail) {
		$scope.ditail = ditail;
		$scope.focusedDITail = {};
		angular.extend($scope.focusedDITail, ditail);
	};

	$scope.setEditing = function(editing) {
		$scope.editing = editing;
	};

	$scope.addLogGroup = function() {
		$scope.serverType.groups.push({
			enabled : true
		});
	};

	$scope.addLogDir = function(groupIndex) {
		if (!$scope.serverType.groups[groupIndex].logs) {
			$scope.serverType.groups[groupIndex].logs = [];
		}
		$scope.serverType.groups[groupIndex].logs.push({});
	};

	$scope.removeLogGroup = function(index) {
		$scope.serverType.groups.splice(index, 1);
	};

	$scope.removeLogDir = function(groupIndex, index) {
		$scope.serverType.groups[groupIndex].logs.splice(index, 1);
	};

	$scope.listServerTypes = function() {
		$scope.serverTypes = ServerType.list({}, function(data) {
		});
	};

	$scope.saveConfiguration = function(serverTypeId) {
		$scope.serverType.speedLimit = parseInt($scope.serverType.speedLimit);
		$scope.serverType = ServerType.update($scope.serverType,
				function(data) {
					$scope.editing = false;
				});
	};

	$scope.getServerType = function(serverTypeName) {
		$scope.editing = false;
		$scope.serverType = ServerType.get({
			name : serverTypeName
		}, function(data) {
		});
	};

	$scope.setSpeedLimit = function() {
		$scope.focusedDITail.speedLimit = parseInt($scope.focusedDITail.speedLimit);
		DITail.update($scope.focusedDITail, function(data) {
			for ( var i = 0; i < $scope.ditails.entries.length; i++) {
				if ($scope.ditails.entries[i].id == data.id) {
					$scope.ditails.entries[i].speedLimit = data.speedLimit;
					break;
				}
			}
			$('#settingSpeedModal').modal('hide');
		});
	};

	$scope.enableDITail = function(ditail) {
		ditail.speedLimit = parseInt(ditail.speedLimit);
		var tmp = {};
		angular.extend(tmp, ditail);
		tmp.enabled = true;
		DITail.update(tmp, function(data) {
			for ( var i = 0; i < $scope.ditails.entries.length; i++) {
				if ($scope.ditails.entries[i].id == data.id) {
					$scope.ditails.entries[i].enabled = true;
					break;
				}
			}
		});
	};

	$scope.disableDITail = function(ditail) {
		ditail.speedLimit = parseInt(ditail.speedLimit);
		var tmp = {};
		angular.extend(tmp, ditail);
		tmp.enabled = false;
		DITail.update(tmp, function(data) {
			for ( var i = 0; i < $scope.ditails.entries.length; i++) {
				if ($scope.ditails.entries[i].id == data.id) {
					$scope.ditails.entries[i].enabled = false;
					break;
				}
			}
		});
	};

	$scope.listDITails = function(serverTypeName) {
		$scope.ditails = DITail.list({
			serverTypeName : serverTypeName
		}, function(data) {
		});
	};

	$scope.openRawDataSelectModle = function() {
		$scope.rawDatas = [ {
			"id" : "RawDataID001",
			"name" : "RawDataName001"
		}, {
			"id" : "RawDataID002",
			"name" : "RawDataName002"
		}, {
			"id" : "RawDataID003",
			"name" : "RawDataName003"
		}, {
			"id" : "RawDataID004",
			"name" : "RawDataName004"
		} ];
		if ($scope.rawDatas && $scope.rawDatas.length > 0) {
			$scope.focusedRawData = {};
			angular.extend($scope.focusedRawData, $scope.rawDatas[0]);
		}
	};
	$scope.setFocusedRawData = function(rawData) {
		$scope.focusedRawData = {};
		angular.extend($scope.focusedRawData, rawData);
	};
}
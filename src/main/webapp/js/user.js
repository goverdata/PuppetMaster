//if (window.location.href.toLowerCase().slice(0, 7) == "http://"
//		&& window.location.href.toLowerCase().slice(0, 16) != "http://localhost") {
//	window.location.href = "https://" + window.location.href.substring(7);
//}

angular.module('$userServices', ['ngResource']).
factory('User', function($resource){
	return $resource('configmanager/user/login', {}, {
		login: {method:'Post', headers: {'Content-Type': 'application/x-www-form-urlencoded'}},
		logout: {url: 'configmanager/user/logout', method:'Post'},
		info: {url: 'configmanager/user/info', method:'Get'}
	});
});

angular.module('issueboard', ['$userServices']).directive('autoFillSync', function() {
	return {
        restrict: "A",
        require: "?ngModel",
        link: function(scope, element, attrs, ngModel) {
            setInterval(function() {
                if (!(element.val()=='' && ngModel.$pristine)) {
                    scope.$apply(function() {
                        ngModel.$setViewValue(element.val());
                    });
                }
            }, 300);
        }
    };
});

function LoginCtl($scope, $http, $rootScope, User) {
	$scope.login = function() {
		var $data = {
			userMail : $scope.userMail,
			password: $scope.password,
			session: $scope.session
		};
		if (!$data.userMail || !$data.password) {
			return;
		}
		User.login($.param($data), function(data) {
			if (window.location.href.indexOf("logout") != -1) {
				window.location = 'index.html';
			} else if (document.referrer && document.referrer.indexOf("login.htm") == -1) {
				window.location = document.referrer;
			} else {
				window.location = 'index.html';
			}
		}, function() {
			$scope.failed = true;
		});
	};

	$scope.logout = function() {
		User.logout(function() {
			$rootScope.user = null;
			window.location.href = "login.htm?logout";
		});
	};

	$scope.info = function() {
		$rootScope.user = User.info();
	};
}

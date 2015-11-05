angular.module('App', [])
    .controller('MainController', ['$scope', '$q', '$http', function ($scope, $q, $http) {
        var defered= $q.defer();
        var promise = defered.promise;
        setTimeout(function () {
            defered.notify('haha');
            defered.resolve('haha');
        },100);
    

        promise.then(function () {
            console.log('resolve');
        }, function () {
            console.log('reject');
        }, function () {
            console.log('notify');
        })
        .then(function() {console.log(2)})
        .then(function() {console.log('3')});

        $http({
            method: 'GET', 
            url:'index.html'
        })
        .then(function(a,b,c,d){
            console.log('---------------');
            console.log(a);
            console.log(b);
            console.log(c);
            console.log(d);
        });

    }])

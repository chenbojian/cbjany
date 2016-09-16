// Code goes here

angular.module('App', [])
    .controller('ListController', ListController)
    .directive('lazyLoad', lazyLoad);

function ListController() {
    this.veryLongList = Array(10000).fill().map((_, i) => i);
    this.numbers = this.veryLongList.map(x => Math.random().toString() + x)
}

ListController.prototype.calc = function (value) {
    return this.numbers[value];
}


function lazyLoad($window, $timeout) {
    return {
        restrict: 'A',
        transclude: 'element',
        link: function($scope, $element, $attr, $ctrl, $transclude) {
            var scrollHelper = angular.element('<div></div>');
            var height = 3000;
            var renderCount = 50;
            scrollHelper.css({
                height: height + 'px'
            });
            angular.element($window.document.body).append(scrollHelper); 
            $scope.lazyList = [];
            var inside;
            $transclude($scope, function(clone) {
                inside = clone;
                clone.css({
                    position: 'fixed',
                    top: '80px'
                })
                $element.after(clone);
            });
            var originList = $scope.$eval($attr.lazyLoad);
            $scope.lazyList = originList.filter((_, i) => i < renderCount);
            $timeout(function() {
                height = inside.find('tr')[0].clientHeight * originList.length;
                scrollHelper.css({height: height + 'px'});
            },0,false);

            angular.element($window).on('scroll resize', function() {
                var start = parseInt((originList.length) * ($window.scrollY / (height - $window.innerHeight)));
                $scope.lazyList = originList.filter((_, i) => i >= start && i < start + renderCount);
                $scope.$apply();
            });

        }
    }
}
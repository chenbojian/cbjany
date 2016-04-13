angular.module('MyApp', [])
    .directive('testTrans', transFn2)
    .directive('testTrans', transFn);

function transFn() {
    return {
        restrict: 'ECA',
        terminal: true,
        priority: 400,
        transclude: 'element',
        link: function ($scope, $element, $attr, $ctrl, $transclude) {
            console.log($element);
            console.log('--------> transFn');
            $transclude(function(clone) {
                console.log('--->',clone);
            });
        }
    }
}

function transFn2() {
    return {
        restrict: 'ECA',
        priority: -400,
        link: function() {
            console.log('----> transFn2');
        }
    }
}

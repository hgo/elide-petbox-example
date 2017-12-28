angular.module('spa', ['schemaForm', 'auth', 'user'])
    .constant('highlight',function (code) {
        hljs.highlightBlock(code[0]);
    })
    .directive('hljsCode', function (highlight) {
            return {
                restrict: 'E',
                scope: {code: '=', type: '@'},
                transclude : true,
                template: '<pre><code ng-bind="code"></code></pre>',
                compile: function ($scope, $element) {
                    return {
                        postLink : function () {
                            var code_el = $element.children('code');
                            code_el.addClass($scope.type);
                            highlight(code_el);
                            $scope.$watch('code', function () {
                                highlight(code_el);
                            });
                        }
                    }
                }
            }
        }
    );
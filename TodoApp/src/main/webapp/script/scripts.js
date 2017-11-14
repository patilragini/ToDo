//module
var myApp=angular.module("myModule",[]);
//controller

var controllers=function($scope){
$scope.message="angular...";
console.log("heree"+$scope.message);
}

myApp.controller("myController",controllers);
var WifiInfo= function() {
};
var cordova = require('cordova'),
    exec = require('cordova/exec');
WifiInfo.prototype.get = function(success, fail) {
		exec(success, success, 'WifiInfoPlugin', null, [] );
};

cordova.addConstructor(function() {

	if (!window.plugins) {
		window.plugins = {};
	}
	window.plugins.WifiInfo = new WifiInfo();
});

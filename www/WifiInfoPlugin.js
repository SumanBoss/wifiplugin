var WifiInfo= function() {
};
var exec = require('cordova/exec');
WifiInfo.prototype.get = function(success, fail) {
		execsuccess, success, 'WifiInfoPlugin', null, [] );
};

cordova.addConstructor(function() {

	if (!window.plugins) {
		window.plugins = {};
	}
	window.plugins.WifiInfo = new WifiInfo();
});

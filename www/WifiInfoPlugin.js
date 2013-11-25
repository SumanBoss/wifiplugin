
cordova.define('cordova/WifiInfoPlugin', function(require, exports, module) {
    var exec = require('cordova/exec');

    var MyOwnPlugin = function() {};

    MyOwnPlugin.prototype.managewifi = function(onSuccess, onFailed) {
       exec(success, fail, 'WifiInfoPlugin', 'managewifi', []);
    alert('here');
    }

    module.exports = new MyOwnPlugin();
});

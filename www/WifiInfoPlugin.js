    var exec = require('cordova/exec');

    var MyOwnPlugin = function() {};

 MyOwnPlugin.prototype.managewifi = function(successCallback, errorCallback) {
       exec(successCallback, errorCallback, 'wifi', 'managewifi', []);
    };
    
      MyOwnPlugin.prototype.getdevice = function(successCallback, errorCallback) {
       exec(successCallback, errorCallback, 'wifi', 'getdevice', []);
    };
      MyOwnPlugin.prototype.info = function(successCallback, errorCallback) {
       exec(successCallback, errorCallback, 'wifi', 'wifinfo', []);
    };
    module.exports = new MyOwnPlugin();


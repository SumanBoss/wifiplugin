var utils = require('cordova/utils'),
        exec = require('cordova/exec'),
        cordova = require('cordova');

WifiInfoPlugin.prototype.managewifi = function(success, fail) {
    exec(success, fail, 'WifiInfoPlugin', 'managewifi', []);
    alert('here');
};

    var exec = require('cordova/exec');

    var MyOwnPlugin = function() {};

    MyOwnPlugin.prototype.managewifi = function(suc,fa) {
       exec(function(winParam) {
            alert('sucess plug');
       }, function(error) {
             alert(error);
       }, 'WifiInfoPlugin', 'managewifi', []);
    }
    module.exports = new MyOwnPlugin();


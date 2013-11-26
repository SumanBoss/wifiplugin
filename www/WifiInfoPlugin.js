    var exec = require('cordova/exec');

    var MyOwnPlugin = function() {};

    MyOwnPlugin.prototype.managewifi = function(suc,fa) {
       exec(function(winParam) {
            // alert('sucess plug');
       }, function(error) {
             alert(error);
       }, 'wifi', 'managewifi', []);
    }
    
      MyOwnPlugin.prototype.getdevice = function(suc,fa) {
       exec(function(winParam) {
            // alert(winParam);
       }, function(error) {
             alert(error);
       }, 'wifi', 'getdevice', []);
    }
    module.exports = new MyOwnPlugin();


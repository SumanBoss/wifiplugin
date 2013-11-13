var calendar = {
managewifi : function(success, fail) {
		cordova.exec(success, fail, 'WifiInfoPlugin', 'managewifi', [] );
}
}
module.exports = calendar;

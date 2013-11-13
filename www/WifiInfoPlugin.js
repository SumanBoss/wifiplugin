
managewifi : function(success, fail) {
		PhoneGap.exec(success, fail, 'WifiInfoPlugin', 'managewifi', [] );
}


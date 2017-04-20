/*Java Script*/
/*Funzioni per la gestione del cookie Utente tra Client e Server*/

	function setCookie(cname, cvalue, exdays) {
	    var d = new Date();
	    d.setTime(d.getTime() + (exdays*24*60*60*1000));
	    var expires = "expires="+ d.toUTCString();
	    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
	    alert("Creazione cookie riuscita!");
	}
    
	function getCookie(cname) {
	    var name = cname + "=";
	    var decodedCookie = decodeURIComponent(document.cookie);
	    var ca = decodedCookie.split(';');
	    for(var i = 0; i < ca.length; i++) {
	        var c = ca[i];
	        while (c.charAt(0) == ' ') {
	            c = c.substring(1);
	        }
	        if (c.indexOf(name) == 0) {
	            return c.substring(name.length, c.length);
	        }
	    }
	    return "";
	}
	
	function checkCookie() {
	    var user=getCookie("pippo");
	    if (user != "" && user == document.getElementById("user").value) {
	        alert("Welcome again " + user);
	    } else {
	       user = document.getElementById("user").value;
	       if (user != "" && user != null) {
	           setCookie("pippo", user, 30);
	       }
	    } 
	} 
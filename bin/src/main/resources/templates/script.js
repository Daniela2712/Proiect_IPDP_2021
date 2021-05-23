	
	


	function savePass(){
   		var pass = $("#pass").val();
	    var valid = pass == $("#passConfirm").val();
	    if(!valid) {
	      $("#error").show();
	      return;

	    }
	}
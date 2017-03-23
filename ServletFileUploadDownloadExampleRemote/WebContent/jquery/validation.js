$(document).ready(function () { 
		$("form[name='form_jquery']").validate({
	 rules: {
            username: "required",
            password: {
                required: true,
                minlength: 5
               },
            email: {
                required: true,
                email: true
            }
         },
     messages: {
            username: "Inserisci nome!",
            password: {
                required:"Inserisci password!",
                minlength:"La password deve essere lunga almeno 5 caratteri!" 
               },
  
            email: {
            	required:"Inserisci email!",
            	email:"Inserire un indirizzo email valido!"
            }
         },
    /*submitHandler: function(form) {
         alert("Inserimento campi obbligatori riuscito!");
         form.submit(); 
        }*/
    }); 
});
	
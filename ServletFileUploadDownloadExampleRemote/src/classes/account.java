package classes;

public class account{
	//attributi
	private String username;
	private String password;
	private String email;

	//costruttore zero argomenti
	public account(){
	}
	
	//costruttore con argomenti per inizializzazione attributi
	public account(String username, String password, String email){
		this.username=username;
		this.password=password;
		this.email=email;
	}
	
	//metodi per la lettura
	public String leggiUsername(){
		return this.username;
	}
	
	public String leggiPassword(){
		return this.password;
	}
	
	public String leggiEmail(){
		return this.email;
	}
	
	//metodi per la scrittura
	public void impostaUsername(String username){
		this.username=username;
	}
	
	public void impostaPassword(String password){
		this.password=password;
	}
	
	//metodo per impostare il proprietario dell'account
	public void impostaEmail(String email){
		this.email=email;
	}
	
	

	public static void main(String[] args){
	
	}

}

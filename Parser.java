import java.util.HashMap;
import java.io.StringReader;
import java.lang.Exception;

public class Parser {
	
	private HashMap<String, Boolean> map = new HashMap<String, Boolean>();
	private String str;
	private TokenType currentToken;
	
	private enum TokenType {
		EXPR, QUERY, TRUE, FALSE, PROP, IMP, DIS, CON, NEG, L_PAR, R_PAR, ASSIGN, DELIM, TERMINATOR, VAR, EOL
	}
	
	public static boolean parse(String str){
		
		return true;
	}
	
	// Caleb McHenry
	private void lex(){
		TokenType temp;
		
		currentTokenType = temp;
	}
	
	// Caleb McHenry
	private boolean accept(TokenType param){
		if(currentToken == param){
			lex();
			return true;
		}
		else{
			return false
		}
	}
	
	//Caleb McHenry
	private void expect(TokenType param) {
		if(currentToken == param){
			lex();
			return;
		}
		else{
			throw new exception();
			return;
		}
		
	}
	
	// Alex Colon
	private void program(){
		
	}
	
	// Alex Colon
	private void assignment(){
		
	}
	
	// Alex Colon
	private void query() {
		
	}
	
	// Andrew Suggs
	private void proposition() {
		
	}
	
	//Andrew Suggs
	private void implication() {
		
	}
	
	//Andrew Suggs
	private void disjunction() {
		
	}
	
	//Trace Boso
	private static boolean conjunction() throws LexemeNotValidException, IOException {
		boolean result = negation();
		while (accept(TokenType.CON)){
			result = result && negation();
		}
		
		return result;
	}
	
	//Trace Boso
	private static boolean negation() throws LexemeNotValidException, IOException {
		boolean result = expression();
		
		if (accept(TokenType.NEG)){
			result= !result;
		}
		return result;
	}
	
	//Trace Boso
	private static boolean expression() throws LexemeNotValidException, IOException {
		boolean result = proposition();
		
		if (accept(TokenType.VAR)){
			result = bool ();
		}
		return result;
	}
	
	//Gus Shaw
	private void boo() {
		
	}
	
	//Gus Shaw
	private void variable() {
		
	}
	
	//Gus Shaw
	private void literal() {
		
	}
}
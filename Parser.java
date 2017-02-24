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
	
	private void lex(){
		TokenType temp;
		
		currentTokenType = temp;
	}
	
	private boolean accept(TokenType param){
		if(currentToken == param){
			lex();
			return true;
		}
		else{
			return false
		}
	}
	
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
	
	private void program(){
		
	}
	
	private void assignment(){
		
	}
	
	private void query() {
		
	}
	
	private void proposition() {
		
	}
	
	private void implication() {
		
	}
	
	private void disjunction() {
		
	}
	
	private void conjunction() {
		
	}
	
	private void negation() {
		
	}
	
	private void expression(){
		
	}
	
	private void boo() {
		
	}
	
	private void variable() {
		
	}
	
	private void literal() {
		
	}
}
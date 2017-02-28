import java.util.HashMap;
import java.io.IOException;
import java.io.StringReader;
import java.lang.Exception;

public class Parser {
	
	private HashMap<String, Boolean> map = new HashMap<String, Boolean>();
	private static String LEXEME;
	private static TokenType TOKEN;
	private static StringReader stringReader;
	
	private enum TokenType {
		LET, QUERY, LIT, PROP, IMP, DIS, CON, NEG, L_PAR, R_PAR, ASSIGN, DELIM, DOT, VAR, EOL
	}
	
	public static boolean parse(String str) throws IOException{
		lex();
		return true;
	}
	
	// Caleb McHenry
	private static void lex() throws IOException{
		LEXEME = "";
		int c = stringReader.read();
		
		//End Of Line
		if(c == -1){
			TOKEN = TokenType.EOL;
			return;
		}
		
		//Clear white space
		while((char)c == ' '){
			 c = stringReader.read();
		}
		
		//Variable or Keyword
		if(isLetter((char)c) == true){
			while(isLetter((char)c)){
				LEXEME += (char)c;
				stringReader.mark(1);
				c = stringReader.read();
			}
			stringReader.reset();
			//LET
			if(LEXEME.toUpperCase() == "LET"){
				TOKEN = TokenType.LET;
				return;
			}
			//QUERY
			else if(LEXEME.toUpperCase() == "QUERY"){
				TOKEN = TokenType.QUERY;
				return;
			}
			//TRUE or FALSE
			else if(LEXEME.toUpperCase() == "TRUE" || LEXEME.toUpperCase() == "FALSE"){
				TOKEN = TokenType.LIT;
				return;
			}
			//Variable
			else{
				TOKEN = TokenType.VAR;
				return;
			}
		}
		else{
			LEXEME += (char)c;
			//Proposition <=>
			if(c == '<'){
				c = stringReader.read();
				if(c == '='){
					LEXEME += (char)c;
					c = stringReader.read();
					if(c == '>'){
						LEXEME += (char)c;
						TOKEN = TokenType.PROP;
						return;
					}
				}
				//TODO: throw error
				return;
			}
			//Implication ->
			else if(c == '-'){
				c = stringReader.read();
				if(c == '>') {
					LEXEME += (char)c;
					TOKEN =TokenType.IMP;
					return;
				}
				else{
					//TODO: throw error
				}
			}
			//Disjunction |
			else if(c == '|'){
				TOKEN = TokenType.DIS;
				return;
			}
			//Conjunction &
			else if(c == '&'){
				TOKEN = TokenType.CON;
				return;
			}
			//Negation ~
			else if(c == '~'){
				TOKEN = TokenType.NEG;
				return;
			}
			//Left Parenthesis (
			else if(c == '('){
				TOKEN = TokenType.L_PAR;
				return;
			}
			//Right Parenthesis )
			else if(c == ')'){
				TOKEN = TokenType.R_PAR;
				return;
			}
			//Assign =
			else if(c == '='){
				TOKEN = TokenType.ASSIGN;
			}
			//Delimeter ;
			else if(c == ';'){
				TOKEN = TokenType.DELIM;
			}
			//Terminator .
			else if(c == '.'){
				TOKEN = TokenType.DOT;
			}
		}
	}
	
	// Caleb McHenry
	private boolean accept(TokenType param) throws IOException{
		if(TOKEN == param){
			lex();
			return true;
		}
		else{
			return false;
		}
	}
	
	//Caleb McHenry
	private void expect(TokenType param) throws IOException {
		if(TOKEN == param){
			lex();
			return;
		}
		else{
			//throw new exception();
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
	private void conjunction() {
		
	}
	
	//Trace Boso
	private void negation() {
		
	}
	
	//Trace Boso
	private void expression(){
		
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
	
	private static boolean isLetter(char c){
		boolean boo = false;
		if((int)c >= 65 && (int)c <= 90 || (int)c >= 97 && (int)c <= 122){
			boo = true;
		}
		return boo;
	}
}
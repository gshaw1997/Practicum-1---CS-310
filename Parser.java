import java.util.HashMap;

import Parser.TokenType;

import java.io.IOException;
import java.io.StringReader;
import java.lang.Exception;

public class Parser {
	
	private HashMap<String, Boolean> lookUpTable = new HashMap<String, Boolean>();
	private static String LEXEME;
	private static TokenType TOKEN;
	private static StringReader stringReader;
	
	private enum TokenType {
		LET, QUERY, LIT, PROP, IMP, DIS, CON, NEG, L_PAR, R_PAR, ASSIGN, DELIM, DOT, VAR, EOL
	}
	
	public static boolean parse(String str) throws Exception{
		lex();
		return true;
	}
	
	// Caleb McHenry
	private static void lex() throws Exception{
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
				throw new Exception();
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
					throw new Exception();
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
	private boolean accept(TokenType param) throws Exception{
		if(TOKEN == param){
			lex();
			return true;
		}
		else{
			return false;
		}
	}
	
	//Caleb McHenry
	private void expect(TokenType param) throws Exception {
		if(TOKEN == param){
			lex();
			return;
		}
		else{
			throw new Exception();
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
	
	///Gus Shaw
	private boolean bool() throws NotInLookupTableException {
		//if token is variable
		if(accept(TokenType.VAR)){
			//Check if variable is in the lookup table
			if(lookUpTable.keySet().contains(variable())){
				//If the variable is a key in the look up table, return the value that if points to. (TRUE|FALSE)
				return lookUpTable.get(variable());
			}
			//If the variable is not a key in the look up table, throw an exception
			else{
				throw new NotInLookupTableException("This value is not in the lookup table.");
			}
		}
		//Else if not a variable, check if it is a literal
		else if(accept(TokenType.LIT)){
			//If is a literal return the value of the literal
			return literal();
		}
		return false;
	}
	
	//Gus Shaw
	//Returns alphabetic lexeme as the name of the variable
	private String variable() {
		return null;
	}
	
	//Gus Shaw
	//Returns true or false
	private boolean literal() {
		return false;
	}
	
	private static boolean isLetter(char c){
		boolean boo = false;
		if((int)c >= 65 && (int)c <= 90 || (int)c >= 97 && (int)c <= 122){
			boo = true;
		}
		return boo;
	}
}
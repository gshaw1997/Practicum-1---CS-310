import java.util.HashMap;

import Parser.TokenType;

import java.io.IOException;
import java.io.StringReader;
import java.lang.Exception;

public class Parser {
	
	private HashMap<String, Boolean> lookUpTable = new HashMap<String, Boolean>();
	private static StringBuilder CURRENT_LEXEME;
	private static StringBuilder NEXT_LEXEME;
	private static TokenType TOKEN;
	private static StringReader stringReader;
	
	private enum TokenType {
		LET, QUERY, LIT, PROP, IMP, DIS, CON, NEG, L_PAR, R_PAR, ASSIGN, DELIM, DOT, VAR, EOL
	}
	
	public static boolean parse(String str) throws LexemeNotValidException, IOException{
		stringReader = new StringReader(str);
		lex();
		return true;
	}
	
	// Caleb McHenry
	private static void lex() throws LexemeNotValidException, IOException{
		if(NEXT_LEXEME != null){
			CURRENT_LEXEME = NEXT_LEXEME;
		}
		NEXT_LEXEME = new StringBuilder();
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
				NEXT_LEXEME.append((char)c);
				stringReader.mark(1);
				c = stringReader.read();
			}
			stringReader.reset();
			//LET
			if(NEXT_LEXEME.toString().toUpperCase() == "LET"){
				TOKEN = TokenType.LET;
				return;
			}
			//QUERY
			else if(NEXT_LEXEME.toString().toUpperCase() == "QUERY"){
				TOKEN = TokenType.QUERY;
				return;
			}
			//TRUE or FALSE
			else if(NEXT_LEXEME.toString().toUpperCase() == "TRUE" || NEXT_LEXEME.toString().toUpperCase() == "FALSE"){
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
			NEXT_LEXEME.append((char)c);
			//Proposition <=>
			if(c == '<'){
				c = stringReader.read();
				if(c == '='){
					NEXT_LEXEME.append((char)c);
					c = stringReader.read();
					if(c == '>'){
						NEXT_LEXEME.append((char)c);
						TOKEN = TokenType.PROP;
						return;
					}
				}
				throw new LexemeNotValidException("Lexeme: " + NEXT_LEXEME + "is not a valid lexeme");
			}
			//Implication ->
			else if(c == '-'){
				c = stringReader.read();
				if(c == '>') {
					NEXT_LEXEME.append((char)c);
					TOKEN =TokenType.IMP;
					return;
				}
				else{
					throw new LexemeNotValidException("Lexeme: " + NEXT_LEXEME.toString() + "is not a valid lexeme");
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
			//Delimiter ;
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
	private boolean accept(TokenType param) throws LexemeNotValidException, IOException{
		if(TOKEN == param){
			lex();
			return true;
		}
		else{
			return false;
		}
	}
	
	//Caleb McHenry
	private void expect(TokenType param) throws NotExpectedTokenTypeException, LexemeNotValidException, IOException{
		if(TOKEN == param){
			lex();
			return;
		}
		else{
			throw new NotExpectedTokenTypeException("Expected TokenType: " + TOKEN + ", but received TokenType: " + param);
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
	private void proposition() throws Exception {
		implication();
		while(accept(TokenType.PROP)){
			implication();
		}
	}
	
	//Andrew Suggs
	private void implication() throws Exception {
		disjunction();
		while(accept(TokenType.IMP)){
			disjunction();
		}
	}
	
	//Andrew Suggs
	private void disjunction() throws Exception {
		conjunction();
		while(accept(TokenType.DIS)){
			conjunction();
		}
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
	//	The first form returns the value of the variable whose name is
	//	<variable> (VAR in enum) in the lookup table as a boolean or else throws an
	//	exception if the name <variable> (VAR in enum) is not in the lookup table
	//  The second form returns the value of <literal> (LIT in enum) as a boolean
	private boolean bool() throws Exception {
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
		//Return the current token which should be a variable name
		return LEXEME;
	}
	
	//Gus Shaw
	//Returns true or false
	private boolean literal() {
		//Take the current token check if it equals the literal string true, If so return the boolen value true
		if(LEXEME.toLowerCase().equals("true")) return true;
		//Else the current token must equal false; Return the boolean value false
		else
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
import java.util.HashMap;
import java.io.IOException;
import java.io.StringReader;

public class Parser {
	
	private static HashMap<String, Boolean> lookUpTable = new HashMap<String, Boolean>();
	private static StringBuilder CURRENT_LEXEME;
	private static StringBuilder NEXT_LEXEME;
	private static TokenType TOKEN;
	private static StringReader stringReader;
	
	private enum TokenType {
		LET, QUERY, LIT, PROP, IMP, DIS, CON, NEG, L_PAR, R_PAR, ASSIGN, DELIM, DOT, VAR, EOL
	}
	
	public static boolean parse(String str) throws RuntimeException, IOException{
		stringReader = new StringReader(str);
		lex();
		return program();
	}
	
	// Caleb McHenry
	private static void lex() throws RuntimeException, IOException{
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
		if(Character.isLetter((char)c) == true){
			while(Character.isLetter((char)c)){
				NEXT_LEXEME.append((char)c);
				stringReader.mark(1);
				c = stringReader.read();
			}
			stringReader.reset();
			//LET
			if(NEXT_LEXEME.toString().toUpperCase().equals("LET")){
				TOKEN = TokenType.LET;
				return;
			}
			//QUERY
			else if(NEXT_LEXEME.toString().toUpperCase().equals("QUERY")){
				TOKEN = TokenType.QUERY;
				return;
			}
			//TRUE or FALSE
			else if(NEXT_LEXEME.toString().toUpperCase().equals("TRUE") || NEXT_LEXEME.toString().toUpperCase().equals("FALSE")){
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
				throw new RuntimeException("Lexeme: " + NEXT_LEXEME + "is not a valid lexeme");
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
					throw new RuntimeException("Lexeme: " + NEXT_LEXEME.toString() + "is not a valid lexeme");
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
	private static boolean accept(TokenType param) throws RuntimeException, IOException{
		if(TOKEN == param){
			lex();
			return true;
		}
		else{
			return false;
		}
	}
	
	//Caleb McHenry
	private static void expect(TokenType param) throws RuntimeException, IOException{
		if(TOKEN == param){
			lex();
			return;
		}
		else{
			throw new RuntimeException("Lexed TokenType: " + TOKEN + ", but expected TokenType: " + param);
		}
		
	}
	
	// Alex Colon
	private static boolean program() throws RuntimeException, IOException{ // { <assignment> }* <query>
		while(NEXT_LEXEME.toString().toUpperCase().equals("LET")){
			assignment();
		}
		return query();
	}
	
	// Alex Colon
	private static void assignment() throws RuntimeException, IOException{ // LET <variable> = <proposition>;
		expect(TokenType.LET);
		String name =variable();
		expect(TokenType.ASSIGN);
		boolean value = proposition();
		lookUpTable.put(name, value);
		expect(TokenType.DELIM);
	}
	
	// Alex Colon
	private static boolean query() throws RuntimeException, IOException{ // QUERY <proposition>
		expect(TokenType.QUERY);
		boolean value = proposition();
		expect(TokenType.DOT);
		expect(TokenType.EOL);
		return value;
	}
	
	// Andrew Suggs
	private static boolean proposition() throws RuntimeException, IOException {
		boolean result = implication();
		while(accept(TokenType.PROP)){
			result = (result == implication());
		}
		return result;
	}
	
	//Andrew Suggs
	private static boolean implication() throws RuntimeException, IOException {
		 boolean result = disjunction();
		 if(NEXT_LEXEME.toString().toUpperCase().equals("->")){
			 expect(TokenType.IMP);
			 result = implication() || !result;
		 }
		return result;
	}
	
	//Andrew Suggs
	private static boolean disjunction() throws RuntimeException, IOException {
		boolean result = conjunction();
		while(accept(TokenType.DIS)){
			result = conjunction() || result;
		}
		return result;
	}
	
	//Trace Boso
	private static boolean conjunction() throws RuntimeException, IOException {
		boolean result = negation();
		while (accept(TokenType.CON)){
			result = negation() && result;
		}
		
		return result;
	}
	
	//Trace Boso
	private static boolean negation() throws RuntimeException, IOException {
		boolean result;
		
		if (accept(TokenType.NEG)){
			result= !expression();
		}
		else {
			result=expression();
		}
		return result;
	}
	
	//Trace Boso
	private static boolean expression() throws RuntimeException, IOException {
		boolean result;
		
		if (NEXT_LEXEME.toString().toUpperCase().equals("(")){
			expect(TokenType.L_PAR);
			result = proposition();
			expect(TokenType.R_PAR);
		}
		else {
			result = bool();
		}
		return result;
	}
	
	///Gus Shaw
	private static boolean bool() throws RuntimeException, IOException  {
		//if token is variable
		if(NEXT_LEXEME.toString().toUpperCase().equals("TRUE") || NEXT_LEXEME.toString().toUpperCase().equals("FALSE")){
			return literal();
		}
		else{
			return lookUpTable.get(variable());
		}
	}
		
	//Gus Shaw
	//Returns alphabetic lexeme as the name of the variable
	private static String variable() throws RuntimeException, IOException {
		//Return the current token which should be a variable name
		expect(TokenType.VAR);
		return CURRENT_LEXEME.toString();
	}
	
	//Gus Shaw
	//Returns true or false
	private static boolean literal() throws RuntimeException, IOException {
		//Take the current token check if it equals the literal string true, If so return the boolen value true
		expect(TokenType.LIT);
		if(CURRENT_LEXEME.toString().toLowerCase().equals("true")) return true;
		//Else the current token must equal false; Return the boolean value false
		else
		return false;
	}
}
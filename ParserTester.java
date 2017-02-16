
public class ParserTester {
	
	static Object[][] tests = {{ true,  "QUERY TRUE." },
			{ false, "QUERY FALSE." },
			{ false, "QUERY ~TRUE." },
			{ true,  "QUERY ~FALSE." },
			
			{ true,  "QUERY TRUE -> TRUE." },
			{ false, "QUERY TRUE -> FALSE." },
			{ true,  "QUERY FALSE -> TRUE." },
			{ true,  "QUERY FALSE -> FALSE." },
			
			{ true,  "QUERY TRUE <=> TRUE." },
			{ false, "QUERY TRUE <=> FALSE." },
			{ false, "QUERY FALSE <=> TRUE." },
			{ true,  "QUERY FALSE <=> FALSE." },
			
			{ true,  "LET p = TRUE;  LET q = TRUE;  QUERY p & q." },
			{ false, "LET p = TRUE;  LET q = FALSE; QUERY p & q." },
			{ false, "LET p = FALSE; LET q = TRUE;  QUERY p & q." },
			{ false, "LET p = FALSE; LET q = FALSE; QUERY p & q." },
			
			{ true,  "LET p = TRUE;  LET q = TRUE;  LET r = TRUE;   QUERY p & q & r." },
			{ false, "LET p = TRUE;  LET q = TRUE;  LET r = FALSE;  QUERY p & q & r." },
			{ false, "LET p = TRUE;  LET q = FALSE; LET r = TRUE;   QUERY p & q & r." },
			{ false, "LET p = TRUE;  LET q = FALSE; LET r = FALSE;  QUERY p & q & r." },
			{ false, "LET p = FALSE; LET q = TRUE;  LET r = TRUE;   QUERY p & q & r." },
			{ false, "LET p = FALSE; LET q = TRUE;  LET r = FALSE;  QUERY p & q & r." },
			{ false, "LET p = FALSE; LET q = FALSE; LET r = TRUE;   QUERY p & q & r." },
			{ false, "LET p = FALSE; LET q = FALSE; LET r = FALSE;  QUERY p & q & r." },
			
			{ true,  "LET p = TRUE;  LET q = TRUE;  QUERY p | q." },
			{ true,  "LET p = TRUE;  LET q = FALSE; QUERY p | q." },
			{ true,  "LET p = FALSE; LET q = TRUE;  QUERY p | q." },
			{ false, "LET p = FALSE; LET q = FALSE; QUERY p | q." },
			
			{ true,  "LET p = TRUE;  LET q = TRUE;  LET r = TRUE;   QUERY p | q | r." },
			{ true,  "LET p = TRUE;  LET q = TRUE;  LET r = FALSE;  QUERY p | q | r." },
			{ true,  "LET p = TRUE;  LET q = FALSE; LET r = TRUE;   QUERY p | q | r." },
			{ true,  "LET p = TRUE;  LET q = FALSE; LET r = FALSE;  QUERY p | q | r." },
			{ true,  "LET p = FALSE; LET q = TRUE;  LET r = TRUE;   QUERY p | q | r." },
			{ true,  "LET p = FALSE; LET q = TRUE;  LET r = FALSE;  QUERY p | q | r." },
			{ true,  "LET p = FALSE; LET q = FALSE; LET r = TRUE;   QUERY p | q | r." },
			{ false, "LET p = FALSE; LET q = FALSE; LET r = FALSE;  QUERY p | q | r." },

			{ true,  "LET p = TRUE;  LET q = TRUE;  QUERY p -> q." },
			{ false, "LET p = TRUE;  LET q = FALSE; QUERY p -> q." },
			{ true,  "LET p = FALSE; LET q = TRUE;  QUERY p -> q." },
			{ true,  "LET p = FALSE; LET q = FALSE; QUERY p -> q." },
			
			{ true,  "LET p = TRUE;  LET q = TRUE;  LET r = TRUE;   QUERY p -> q -> r." },
			{ false, "LET p = TRUE;  LET q = TRUE;  LET r = FALSE;  QUERY p -> q -> r." },
			{ true,  "LET p = TRUE;  LET q = FALSE; LET r = TRUE;   QUERY p -> q -> r." },
			{ true,  "LET p = TRUE;  LET q = FALSE; LET r = FALSE;  QUERY p -> q -> r." },
			{ true,  "LET p = FALSE; LET q = TRUE;  LET r = TRUE;   QUERY p -> q -> r." },
			{ true,  "LET p = FALSE; LET q = TRUE;  LET r = FALSE;  QUERY p -> q -> r." },
			{ true,  "LET p = FALSE; LET q = FALSE; LET r = TRUE;   QUERY p -> q -> r." },
			{ true,  "LET p = FALSE; LET q = FALSE; LET r = FALSE;  QUERY p -> q -> r." },
			
			{ true,  "LET p = TRUE;  LET q = TRUE;  QUERY p <=> q." },
			{ false, "LET p = TRUE;  LET q = FALSE; QUERY p <=> q." },
			{ false, "LET p = FALSE; LET q = TRUE;  QUERY p <=> q." },
			{ true,  "LET p = FALSE; LET q = FALSE; QUERY p <=> q." },
			
			{ true,  "LET p = TRUE;  LET q = TRUE;  LET r = TRUE;   QUERY p <=> q <=> r." },
			{ false, "LET p = TRUE;  LET q = TRUE;  LET r = FALSE;  QUERY p <=> q <=> r." },
			{ false, "LET p = TRUE;  LET q = FALSE; LET r = TRUE;   QUERY p <=> q <=> r." },
			{ true,  "LET p = TRUE;  LET q = FALSE; LET r = FALSE;  QUERY p <=> q <=> r." },
			{ false, "LET p = FALSE; LET q = TRUE;  LET r = TRUE;   QUERY p <=> q <=> r." },
			{ true,  "LET p = FALSE; LET q = TRUE;  LET r = FALSE;  QUERY p <=> q <=> r." },
			{ true,  "LET p = FALSE; LET q = FALSE; LET r = TRUE;   QUERY p <=> q <=> r." },
			{ false, "LET p = FALSE; LET q = FALSE; LET r = FALSE;  QUERY p <=> q <=> r." },
			
			{ true,  "LET p = TRUE;  QUERY p." },
			{ false, "LET p = FALSE; QUERY p." },
			{ false, "LET p = TRUE;  QUERY ~p." },
			{ true,  "LET p = FALSE; QUERY ~p." },
			{ true,  "LET p = TRUE;  QUERY ~(~p)." },
			{ false, "LET p = FALSE; QUERY ~(~p)." },
			{ true,  "LET p = TRUE;  QUERY (p)." },
			{ false, "LET p = FALSE; QUERY (p)." },
			{ true,  "LET p = TRUE;  QUERY ((p))." },
			{ false, "LET p = FALSE; QUERY ((p))." },
			
			{ true,  "LET p = TRUE;  LET q = TRUE;  QUERY ~(p & q) <=> (~p | ~q)."},
			{ true,  "LET p = TRUE;  LET q = FALSE; QUERY ~(p & q) <=> (~p | ~q)."},
			{ true,  "LET p = FALSE; LET q = TRUE;  QUERY ~(p & q) <=> (~p | ~q)."},
			{ true,  "LET p = FALSE; LET q = FALSE; QUERY ~(p & q) <=> (~p | ~q)."},
			
			{ true,  "LET p = TRUE;  LET q = TRUE;  QUERY (p -> q) <=> (~p | q)."},
			{ true,  "LET p = TRUE;  LET q = FALSE; QUERY (p -> q) <=> (~p | q)."},
			{ true,  "LET p = FALSE; LET q = TRUE;  QUERY (p -> q) <=> (~p | q)."},
			{ true,  "LET p = FALSE; LET q = FALSE; QUERY (p -> q) <=> (~p | q)."},
			
			{ true,  "LET p = TRUE;  LET q = TRUE;  QUERY p -> q <=> ~p | q."},
			{ true,  "LET p = TRUE;  LET q = FALSE; QUERY p -> q <=> ~p | q."},
			{ true,  "LET p = FALSE; LET q = TRUE;  QUERY p -> q <=> ~p | q."},
			{ true,  "LET p = FALSE; LET q = FALSE; QUERY p -> q <=> ~p | q."},
		
			{ true,  "LET p = TRUE;  LET q = p;  QUERY q." },
			{ false, "LET p = FALSE; LET q = p;  QUERY q." },
			{ false, "LET p = TRUE;  LET q = ~p; QUERY q." },
			{ true,  "LET p = FALSE; LET q = ~p; QUERY q." },
			
			{ false, "LET p = TRUE;  LET q = TRUE;  LET r = ~p & ~q; QUERY r." },
			{ false, "LET p = TRUE;  LET q = FALSE; LET r = ~p & ~q; QUERY r." },
			{ false, "LET p = FALSE; LET q = TRUE;  LET r = ~p & ~q; QUERY r." },
			{ true,  "LET p = FALSE; LET q = FALSE; LET r = ~p & ~q; QUERY r." },
			
			{ true,  "LET red = TRUE;     QUERY red." },
			{ false, "LET orange = FALSE; QUERY orange." },
			{ false, "LET yellow = TRUE;  QUERY ~yellow." },
			{ true,  "LET green = FALSE;  QUERY ~green." },
			{ true,  "LET blue = TRUE;    QUERY ~(~blue)." },
			{ false, "LET purple = FALSE; QUERY ~(~purple)." },
			
			{ true,  "let p = true;     query p." },
			{ true,  "let P = true;     query P." },
			{ true,  "let p = true;     query P." },
			{ true,  "let P = true;     query p." }};

	public static void main(String[] args) {
		System.out.println("Hello");
		for(Object[] test: tests){
			boolean result = Parser.parse((String)test[1]);
			System.out.printf("Success: %6b| Expected:%6b| Actual:%6b|  %s\n",(boolean)test[0] == result, test[0], result, test[1]);
		}
	}

}

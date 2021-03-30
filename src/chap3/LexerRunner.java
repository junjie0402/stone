package chap3;
import stone.*;

//整个源代码其实就是一个很长很长的字符串。语言处理器首先会进行词法分析。
public class LexerRunner {
    public static void main(String[] args) throws ParseException {
        Lexer l = new Lexer(new CodeDialog());
        for (Token t; (t = l.read()) != Token.EOF; )
            System.out.println("=> " + t.getText());
    }
}

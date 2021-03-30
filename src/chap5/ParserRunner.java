package chap5;
import stone.ast.ASTree;
import stone.*;

//经过词法分析，程序已经被分解为一个个单词。
//语法分析的主要任务是分析单词之间的关系，如判断哪些单词属于同一个表达式语句。
//语法分析的结果能够通过抽象语法树来表示。这一阶段还会检查程序中是否含有语法错误。
public class ParserRunner {
    public static void main(String[] args) throws ParseException {
        Lexer l = new Lexer(new CodeDialog());
        BasicParser bp = new BasicParser();
        while (l.peek(0) != Token.EOF) {
            ASTree ast = bp.parse(l);
            System.out.println("=> " + ast.toString());
        }
    }
}

package stone;
import static stone.Parser.rule;
import java.util.HashSet;
import stone.Parser.Operators;
import stone.ast.*;

public class BasicParser {
    HashSet<String> reserved = new HashSet<String>();
    Operators operators = new Operators();
    
    /*
     * 表 4.2 BNF中用到的元符号
     * { pat } 		模式pat至少重复0次
     * [ pat ] 		与重复出现0次或1次的模式pat匹配
     * pat1 | pat2 	与pat1或pat2匹配
     * ()			括号内视为一个完整的模式
     */
    
    //------------------------------------------------------------
    
    /*
     代码清单5.1 Stone 语言的语法定义：（BNF 语法规则）
     
     primary : "(" expr ")" | NUMBER | IDENTIFIER | STRING 
     factor  : "-" primary | primary
     expr    : factor { OP factor }
     block   : "{" [ statement ] {(";" | EOL) [ statement ]} "}"
     simple  : expr
     statement : "if" expr block [ "else" block ]
                 | "while" expr block 
                 | simple
     program : [ statement ] (";" | EOL)
     */
    
    //------------------------------------------------------------
    
    /*
     * 左右括号不仅是终结符，也是分割字符，通过 sep 方法添加
     * 非终结符由 ast 方法添加，参数是一个需要添加的非终结符对应的Parser对象
     * repeat 方法对应 { pat } 模式
     * option 方法对应 [ pat ] 模式
     */
    
    
    // 以下 Parser 类型字段，是将 BNF 语法规则转换成 Java 语言的结果
    Parser expr0 = rule();
    Parser primary = rule(PrimaryExpr.class).or(
        	rule().sep("(").ast(expr0).sep(")"),
            rule().number(NumberLiteral.class),
            rule().identifier(Name.class, reserved),  //identifier:标识符。reserved中的字符无法作为标识符的变量名使用
            rule().string(StringLiteral.class)
          );
    Parser factor = rule().or(
    		rule(NegativeExpr.class).sep("-").ast(primary),
            primary
          );                               
    Parser expr = expr0.expression(BinaryExpr.class, factor, operators);

    Parser statement0 = rule();
    Parser block = rule(BlockStmnt.class)
        .sep("{").option(statement0)
        .repeat(rule().sep(";", Token.EOL).option(statement0))
        .sep("}");
    Parser simple = rule(PrimaryExpr.class).ast(expr);
    Parser statement = statement0.or(
            rule(IfStmnt.class).sep("if").ast(expr).ast(block).option(rule().sep("else").ast(block)),
            rule(WhileStmnt.class).sep("while").ast(expr).ast(block),
            simple
          );

    Parser program = rule().or(statement, rule(NullStmnt.class)).sep(";", Token.EOL);

    
    
    public BasicParser() {
        reserved.add(";");
        reserved.add("}");
        reserved.add(Token.EOL);

        //1、2、3、4表示优先级，数值越大，优先级越高
        //Operators.LEFT 表示左结合，左结合是指两个相同运算符出现时左侧的那个优先级较高，例如连续两个加号+，3 + 4 + 5
        //Operators.RIGHT 表示右结合，优先计算右侧的运算，例如连续两个等号=，x = y = 3
        operators.add("=", 1, Operators.RIGHT);
        operators.add("==", 2, Operators.LEFT);
        operators.add(">", 2, Operators.LEFT);
        operators.add("<", 2, Operators.LEFT);
        operators.add("+", 3, Operators.LEFT);
        operators.add("-", 3, Operators.LEFT);
        operators.add("*", 4, Operators.LEFT);
        operators.add("/", 4, Operators.LEFT);
        operators.add("%", 4, Operators.LEFT);
    }
    public ASTree parse(Lexer lexer) throws ParseException {
        return program.parse(lexer);
    }
}

package stone.ast;
import stone.Token;

//ASTLeaf是叶节点（不含树枝的节点）的父类
public class StringLiteral extends ASTLeaf {
    public StringLiteral(Token t) { super(t); }
    public String value() { return token().getText(); }
}

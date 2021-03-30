package stone.ast;
import stone.Token;

//ASTLeaf是叶节点（不含树枝的节点）的父类
public class NumberLiteral extends ASTLeaf {
    public NumberLiteral(Token t) { super(t); }
    public int value() { return token().getNumber(); }
}

package stone.ast;
import stone.Token;

//ASTLeaf是叶节点（不含树枝的节点）的父类
public class Name extends ASTLeaf {
    public Name(Token t) { super(t); }
    public String name() { return token().getText(); }
}

package stone.ast;
import java.util.List;

//ASTList 是含有树枝的节点对象的父类
public class NegativeExpr extends ASTList {
    public NegativeExpr(List<ASTree> c) { super(c); }
    public ASTree operand() { return child(0); }
    public String toString() {
        return "-" + operand();
    }
}

package org.jruby.ir.instructions;

import org.jruby.ir.IRFlags;
import org.jruby.ir.IRScope;
import org.jruby.ir.IRVisitor;
import org.jruby.ir.Operation;
import org.jruby.ir.operands.Variable;
import org.jruby.ir.persistence.IRReaderDecoder;
import org.jruby.ir.persistence.IRWriterEncoder;
import org.jruby.ir.runtime.IRRuntimeHelpers;
import org.jruby.ir.transformations.inlining.CloneInfo;
import org.jruby.runtime.ThreadContext;
import org.jruby.runtime.builtin.IRubyObject;

import java.util.EnumSet;

public class ReceiveKeywordRestArgInstr extends ReceiveArgBase implements FixedArityInstr {

    public ReceiveKeywordRestArgInstr(Variable result) {
        super(Operation.RECV_KW_REST_ARG, result, -1);
    }

    @Override
    public boolean computeScopeFlags(IRScope scope, EnumSet<IRFlags> flags) {
        scope.setReceivesKeywordArgs();
        return true;
    }

    @Override
    public Instr clone(CloneInfo ii) {
        return new ReceiveKeywordRestArgInstr(ii.getRenamedVariable(result));
    }

    @Override
    public void encode(IRWriterEncoder e) {
        super.encode(e);
    }

    public static ReceiveKeywordRestArgInstr decode(IRReaderDecoder d) {
        return new ReceiveKeywordRestArgInstr(d.decodeVariable());
    }

    @Override
    public IRubyObject receiveArg(ThreadContext context, IRubyObject[] args, boolean usesKeywords) {
        return IRRuntimeHelpers.receiveKeywordRestArg(context, args, usesKeywords);
    }

    @Override
    public void visit(IRVisitor visitor) {
        visitor.ReceiveKeywordRestArgInstr(this);
    }

}

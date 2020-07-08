package com.aidriveall.cms.anltr;

import com.aidriveall.cms.service.CommonQueryQueryService;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.springframework.data.jpa.domain.Specification;

public class CriteriaLogicExprListener extends LogicExprBaseListener {
    public ParseTreeProperty<Specification> specifications = new ParseTreeProperty<>();

    @Override
    public void exitOr(LogicExprParser.OrContext ctx) {
        Specification left = specifications.get(ctx.expr(0));
        Specification right = specifications.get(ctx.expr(1));
        if (left != null && right != null) {
            specifications.put(ctx, left.or(right));
        }
        super.exitOr(ctx);
    }

    @Override
    public void exitStat(LogicExprParser.StatContext ctx) {
        specifications.put(ctx, specifications.get(ctx.expr()));
        super.exitStat(ctx);
    }

    @Override
    public void exitVar(LogicExprParser.VarContext ctx) {
        // 获得表达式指定的specification
        specifications.put(ctx, (Specification) CommonQueryQueryService.specificationMap.get(ctx.VAR().getText()));
        super.exitVar(ctx);
    }

    @Override
    public void exitAnd(LogicExprParser.AndContext ctx) {
        Specification left = specifications.get(ctx.expr(0));
        Specification right = specifications.get(ctx.expr(1));
        if (left != null && right != null) {
            specifications.put(ctx, left.and(right));
        }
        super.exitAnd(ctx);
    }

    @Override
    public void exitGroup(LogicExprParser.GroupContext ctx) {
        specifications.put(ctx, specifications.get(ctx.expr()));
        super.exitGroup(ctx);
    }
}

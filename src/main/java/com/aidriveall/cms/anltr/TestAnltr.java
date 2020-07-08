package com.aidriveall.cms.anltr;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class TestAnltr {
    public static void main(String[] args) {
        ANTLRInputStream input = new ANTLRInputStream(" 2_1");
        LogicExprLexer lexer = new LogicExprLexer(input);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        LogicExprParser parser = new LogicExprParser(tokenStream);
        LogicExprParser.StatContext parseTree = parser.stat();
        CriteriaLogicExprListener visitor = new CriteriaLogicExprListener();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(visitor, parseTree);
        System.out.println(parseTree.toStringTree(parser)); //打印规则数
        System.out.println(visitor.specifications.get(parseTree));
    }
}

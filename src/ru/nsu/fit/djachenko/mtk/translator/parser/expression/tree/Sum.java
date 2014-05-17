package ru.nsu.fit.djachenko.mtk.translator.parser.expression.tree;

import ru.nsu.fit.djachenko.mtk.translator.parser.Type;

public class Sum implements Expression
{
	private final Expression left;
	private final Expression right;

	public Sum(Expression left, Expression right)
	{
		this.left = left;
		this.right = right;
	}

	@Override
	public String toString()
	{
		return left.toString() + " + " + right.toString();
	}

	@Override
	public double count()
	{
		return left.count() + right.count();
	}

	@Override
	public String toCode()
	{
		return left.toCode() + right.toCode() + "dadd\n";
	}

	@Override
	public Type getType()
	{
		return Type.common(left.getType(), right.getType());
	}
}

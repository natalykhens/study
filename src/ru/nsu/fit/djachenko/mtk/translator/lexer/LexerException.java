package ru.nsu.fit.djachenko.mtk.translator.lexer;

class LexerException extends Exception
{
	LexerException(String message, int line, int column)
	{
		super(message + " at line " + line + ", column " + column);
	}
}

package tp1.exceptions;

public class GameParseException extends GameModelException
{
	private static final long serialVersionUID = 1L;
	
	public GameParseException() { super(); }
	public GameParseException(String message){ super(message); }
	public GameParseException(String message, Throwable cause){ super(message, cause); }
	public GameParseException(Throwable cause){ super(cause); }
	
	protected GameParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
    }
}

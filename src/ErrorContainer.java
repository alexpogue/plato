
public class ErrorContainer implements IErrorContainer
{
	private ErrorCode errorCode;
	
	@Override
	public void setError(ErrorCode e) {
		errorCode = e;
		return;
	}

	@Override
	public ErrorCode getError() {
		return errorCode;
	}
}

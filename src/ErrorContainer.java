
public class ErrorContainer implements IErrorContainer
{
	ErrorCode errorCode;
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

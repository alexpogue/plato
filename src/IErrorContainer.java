
public interface IErrorContainer {
		void setError(ErrorCode e);
		ErrorCode getError();
		
		public static enum ErrorCode {
			Success,
			MediaNotFound,
			MediaNotCheckedOut,
			LatePolicyNotFound,
			BadAmount
		}
}


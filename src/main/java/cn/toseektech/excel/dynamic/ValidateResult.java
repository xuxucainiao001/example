package cn.toseektech.excel.dynamic;

public class ValidateResult {
	
	 private boolean success = true;
	 	 
	 private String errorMessage;
	 
	 public ValidateResult() {}
	 
	 public ValidateResult(String errorMessage) {
		 this.success =false;
		 this.errorMessage= errorMessage;
	 }
	 
	 public static ValidateResult success() {
		 return new  ValidateResult();
	 }
	 
	 public static ValidateResult error(String errorMessage) {
		 return new  ValidateResult(errorMessage);
	 }
	 
	 public String getErrorMessage() {
		return errorMessage;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
		
}

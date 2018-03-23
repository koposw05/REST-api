package resultVO;


public class resultVO {
	private String process;
	private int success;
	private int fail;
	private int total;
	
	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public int getFail() {
		return fail;
	}

	public void setFail(int fail) {
		this.fail = fail;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "resultVO [process=" + process + ", success=" + success + ", fail=" + fail +"total="+total+"]";
	}
}

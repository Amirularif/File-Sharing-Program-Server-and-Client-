package ServerClient;

public class MyFile {
	
	private int id;
	private String name;
	private byte[] data;
	private String fileextension;
	
	
	public MyFile(int id, String name, byte[] data, String fileextension) {
		super();
		this.id = id;
		this.name = name;
		this.data = data;
		this.fileextension = fileextension;
	}


	public int getId() {
		return id;
	}


	public String getName() {
		return name;
	}


	public byte[] getData() {
		return data;
	}


	public String getFileextension() {
		return fileextension;
	}


	public void setId(int id) {
		this.id = id;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setData(byte[] data) {
		this.data = data;
	}


	public void setFileextension(String fileextension) {
		this.fileextension = fileextension;
	}
	
	
	

}

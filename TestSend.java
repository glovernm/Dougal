
public class TestSend {
	public static void main(String [] args) {
		SendMail mail = new SendMail("gleasowa@plu.edu","Test","Testing without attachments");
		mail.send();
		
		mail.setParams("gleasowa@plu.edu", "Test2", "Testing with Params edited", "C:\\Users\\gleasowa\\Desktop\\TestText.txt");
		mail.send();
	}
}
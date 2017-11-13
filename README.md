# Falconide Java SDK

This is a JavaSDK/Client for Falconide's API.

## Installation

```shell
git clone https://github.com/falconide/falconide-sdk-java.git .
cd falconide-sdk-java
```

Now create the Example.java (put the values of API key, email, etc as per your requirements)

```java
package falconidetest;
import com.netcore.falconide.Email;
import com.netcore.falconide.Falconide;
import com.netcore.falconide.Falconide.Response;
import com.netcore.falconide.MetaData;
import java.util.concurrent.TimeUnit;

public class FalconideTest {
	public static void main(String[] args) {

		Falconide falcon = new Falconide("API KEY");  //Unique API key 
		//falcon.enableConsoleLogging();
		falcon.disableConsoleLogging();
		Email email = new Email();

		email.setEmailContent("EMAIL_CONTENT");  //Content of the email
		email.setFrom("FROM_ADDRESS");  //FROM ADDRESS like 'Test USER'
		email.setFromName("From Name"); //From name
		//email.setReplyToId("REPLY_ID"); //Reply to Address in the mail
		email.setSubject("SUBJECT");    //set subject of the email

		MetaData data = new MetaData();
		data.addCC("abc@xyz.com", "ccAPI"); //CC address
		data.addSubstitue("NAME", "ABS"); //Replaces [%NAME%] attibute in n the html content with ABS  
		
		data.setXAPIHeader("API123");//Mentions X-API header mentoned ny the use
		email.addRecipient("RECIPIENT_EMAIL_ADDRESS", data);

                //Repeat the steps from line 25 to 44 for another email  
	
		email.enableClickTrack();
		email.enableOpenTrack();;
		email.addFooter();
		email.setTemplateId("2928"); // Mention template id created in the falconide panel
                //Below code sets the timeout 
                falcon.setConnectTimeout(1,TimeUnit.MINUTES );
                falcon.setReadTimeout(1,TimeUnit.MINUTES );
                falcon.setWriteTimeout(1,TimeUnit.MINUTES ); 
		
		try {
			email.addAttachment(new File("test.pdf")); //specify the path for attachemnt
			
			Response response = falcon.send(email);
			if (response.getStatus()) {

				System.out.println("Success-Falcon");
				System.out.println(response.getMessage());
			} else {
				System.out.println(response.getCode() + "::::"
						+ response.getMessage());
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	
	
	}
}

```
Compile the Example.java and Run the code
## Documentation for API Endpoints

For API documentation, please visit the below url:

[**docs.falconide.com**](https://docs.falconide.com/send.web.json/) 




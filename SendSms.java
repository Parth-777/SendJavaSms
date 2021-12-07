import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class SendSms {
    public static void main(String[] args) {
        System.out.println("Started program");



       
        String message="This sms is sent using Java";
        String number="Enter the mobile number to which you want to send the message"

        smsSend(message,number);



    }

    public static void smsSend(String message, String number){
        //using fast2Sms messaging service in our program for sending the messages

        try {
            //api key and senderId for fast2sms
            String apiKey="Your Api Key";
            String senderId ="FastSM";
            //important step , encode the message
            message=  URLEncoder.encode(message, "UTF-8");
            // language of the message
            String language="english";

            // route for the msg, write p for promotional message
            String route="p";

            // fire a get reqeust to this url and include all the details
           String url ="https://www.fast2sms.com/dev/bulkV2?authorization="+apiKey+"&sender_id="+senderId+"&message="+message+"&language="+language+"&route="+route+"&numbers="+number;
           System.out.println(url);
            System.out.println("Passed stage1");
             URL myurl = new URL(url);
            HttpsURLConnection connection = (HttpsURLConnection)myurl.openConnection();// fired a get request

            connection.setRequestMethod("GET");
           //optinal
            connection.setRequestProperty("User-Agent","Mozilla/5.0");
            //optional
            connection.setRequestProperty("cache-control","no-cache");
            System.out.println("Passed stage2");
            // when we fire a request , we get a code in return , if that code is 200, it is successful , otherwise some error has occured according to the code received
         int code=   connection.getResponseCode();
         System.out.println(code);
         //pausing  the program for 5seconds
         Thread.sleep(5000);

         System.out.println("Passed stage3");
         //Service Route Success Response: we store the success response using stringbuffer
         /*here is how it looks
             {
             "return": true,
             "request_id": "lwdtp7cjyqxvfe9", (request id may be  different,  doesnt matter)
             "message": [
             "Message sent successfully"
             ]
            }
            We should get exactly this output if our request is successful which means we have connected to fast2sms service
          */
         StringBuffer response  = new StringBuffer();
         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
         while(true){
             String line = bufferedReader.readLine();
             if(line==null)
                 break;

             response.append(line);
         }

         System.out.println(response);

        } catch (UnsupportedEncodingException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

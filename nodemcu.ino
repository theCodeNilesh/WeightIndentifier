  // Wire Master Reader

#include <Wire.h>
#include <ESP8266WiFi.h>





String apiKey = "0DUT0RZT82O6IEQA";     //  Enter your Write API key from ThingSpeak

const char *ssid =  "realme";     // replace with your wifi ssid and wpa2 key
const char *pass =  "sneha9701";
const char* server = "api.thingspeak.com";

int table[]={0};

WiFiClient client;

void setup()
{
  Wire.begin();        // join i2c bus (address optional for master)
  Serial.begin(9600);  // start serial for output
  Serial.println("Connecting to ");
  Serial.println(ssid);
 
  WiFi.begin(ssid, pass);
 
  while (WiFi.status() != WL_CONNECTED) 
  {
         delay(100);
         Serial.print(".");
  }
  Serial.println("");
  Serial.println("WiFi connected");
}

void loop()
{
    Wire.requestFrom(2, 2);    // request 3 bytes from slave device #2

    for(int i=0;i<1;i++)
    {
    int c = Wire.read(); // receive a byte as character

    Serial.print(c);
    table[i]=c;
    Serial.print('\t');   
   
    }
     Serial.print('\n');
     Serial.print(table[0]);
     Serial.print('\n');
     if (client.connect(server,80))   //   "184.106.153.149" or api.thingspeak.com
                      {  
                            
                             String postStr = apiKey;
                             postStr +="&field1=";
                             postStr += String(table[0]);
                             postStr += "\r\n\r\n";
 
                             client.print("POST /update HTTP/1.1\n");
                             client.print("Host: api.thingspeak.com\n");
                             client.print("Connection: close\n");
                             client.print("X-THINGSPEAKAPIKEY: "+apiKey+"\n");
                             client.print("Content-Type: application/x-www-form-urlencoded\n");
                             client.print("Content-Length: ");
                             client.print(postStr.length());
                             client.print("\n\n");
                             client.print(postStr);
 
                      }
          client.stop();
 
          Serial.println("Waiting...");
  
  // thingspeak needs minimum 15 sec delay between updates, i've set it to 30 seconds
  delay(10000);
}

#include <MQ135.h>

float voltage;
String A = "GET /update?api_key=UHQ8UGU5ESR3YZUT&field1=0";
String Z = " HTTP/1.1 \nHOST: api.thingspeak.com \r\n\r\n";   //web link
MQ135 gasSensor = MQ135(A0) ;
void setup() 
{                
    Serial.begin(115200); 
    Serial.println("AT\r\n");
    delay(2000);
  
}
void loop() 
{
/* VOLTAGE */
   

  float voltage = analogRead(A0);

  char voltage_buff[16];
  String voltageX = dtostrf(voltage, 4, 1, voltage_buff);
  String postStr = A + voltageX + Z;
/*********   Uploading to ThinkSpeak Cloud    ***********/
/*********  Sending AT Commands to ESP8266    ***********/

  Serial.print("AT+CIPSTART=\"TCP\",\"api.thingspeak.com\",80\r\n");
  delay(3000);
  String ciplength = "AT+CIPSEND=" + String(postStr.length()) + "\r\n";
  Serial.print(ciplength);
  delay(3000);
  Serial.print(postStr);
  delay(3000);

}

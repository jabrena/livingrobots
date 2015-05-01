#include <Wire.h>
#define SLAVE_ADDRESS 0x04

long randNumber;

void setup()
{
    Serial.begin(115200); // start serial for output
    Wire.begin(SLAVE_ADDRESS);
    Wire.onReceive(receiveData);
    Wire.onRequest(sendData);
    Serial.println("Ready!");
    
  // if analog input pin 0 is unconnected, random analog
  // noise will cause the call to randomSeed() to generate
  // different seed numbers each time the sketch runs.
  // randomSeed() will then shuffle the random function.
  randomSeed(analogRead(0));
}
int val,flag=0;
void loop()
{
  if(flag==1)
   {
     Serial.println(val);
     flag=0;
   }
}
void receiveData(int byteCount)
{
    while(Wire.available()>0){
      val=Wire.read();
      flag=1;
    }
}
// callback for sending data
void sendData()
{
  randNumber = random(0, 30);
  Wire.write(randNumber);
  //Wire.write(2);
  //Serial.println(1);
}

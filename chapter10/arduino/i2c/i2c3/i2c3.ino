#include <Wire.h>
#define SLAVE_ADDRESS 0x04

void setup()
{
  Wire.begin(); // join i2c bus
  Serial.begin(9600);
  
  // if analog input pin 0 is unconnected, random analog
  // noise will cause the call to randomSeed() to generate
  // different seed numbers each time the sketch runs.
  // randomSeed() will then shuffle the random function.
  randomSeed(analogRead(0));
}

void loop(){

  Wire.beginTransmission(SLAVE_ADDRESS);
  
  long randNumber;
  randNumber = random(0, 127);
  Wire.write(randNumber);
  
  delay(500);
  
  Wire.endTransmission();       // stop transmitting

}

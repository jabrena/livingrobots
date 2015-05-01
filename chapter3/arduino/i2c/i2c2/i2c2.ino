#include <Wire.h>
#define SLAVE_ADDRESS 0x04

long randNumber;

void setup(){
  Serial.begin(115200); // start serial for output
  Wire.begin(SLAVE_ADDRESS);                // join i2c bus with address #2
  Wire.onRequest(requestEvent); // register event

  // if analog input pin 0 is unconnected, random analog
  // noise will cause the call to randomSeed() to generate
  // different seed numbers each time the sketch runs.
  // randomSeed() will then shuffle the random function.
  randomSeed(analogRead(0));

}

void loop(){
  delay(100);
}

// function that executes whenever data is requested by master
// this function is registered as an event, see setup()
void requestEvent(){
  randNumber = random(0, 127);
  Wire.write(127);
}

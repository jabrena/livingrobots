#include <Wire.h>
#define SLAVE_ADDRESS 0x04

void setup() {
  //Wire.begin();
  Serial.begin(115200); // start serial for output
  Wire.begin(SLAVE_ADDRESS);                // join i2c bus
  Wire.onRequest(requestEvent); // register event
  Wire.onReceive(receiveData);
  
  // if analog input pin 0 is unconnected, random analog
  // noise will cause the call to randomSeed() to generate
  // different seed numbers each time the sketch runs.
  // randomSeed() will then shuffle the random function.
  randomSeed(analogRead(0));
}

void loop() {

  //Wire.requestFrom(SLAVE_ADDRESS, 1, true);
  delay(100);
}

void receiveData(int byteCount){
    while(Wire.available()>0){
      int val=Wire.read();
    }
}

// function that executes whenever data is requested by master
// this function is registered as an event, see setup()
void requestEvent() {

  long randNumber;
  randNumber = random(0, 127);
  //Wire.beginTransmission(SLAVE_ADDRESS);
  Wire.write(randNumber);
  //Wire.endTransmission();

}

#include <Wire.h>

//I2C Slave Address
#define SLAVE_ADDRESS 0x04

const int maxDistances = 255; //TODO: How to define a constant
int distances[maxDistances];
const int maxDistance = 255;
long randomDistance;
long randomAngle;
int value;
int reg = 0;

void setup(){
  Serial.begin(9600);         // start serial for output
  Wire.begin(SLAVE_ADDRESS);
  Wire.onReceive(receiveData);
  Wire.onRequest(sendData);
  
  //Initialization
  for (int i = 0; i < maxDistances; i++)  {
    distances[i] = 0;      
  }
}

//Simulating RPLIDAR
void loop(){
  randomDistance = random(maxDistance);
  randomAngle = random(maxDistances);
  distances[randomAngle] = randomDistance;
}

void receiveData(int byteCount){
    while(Wire.available()>0){
      reg=Wire.read();
    }
}

byte b[2];

// callback for sending data
void sendData(){
  //Serial.println(reg);
  value = distances[reg];

  b[0]=value;
  b[1]=value%256;

  Wire.write(b,2);

}


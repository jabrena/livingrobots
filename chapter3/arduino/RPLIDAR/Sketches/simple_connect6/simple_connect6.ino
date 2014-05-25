

#include <Wire.h>

//I2C Slave Address
#define SLAVE_ADDRESS 0x04

const int maxAngle = 360; //TODO: How to define a constant
const int maxDistance = 255;
const int limitByteValue = 255;
int distanceCM = 0;
int value = 0;
int value2 = 0;
int reg = 0;
byte distances[maxAngle];
byte distances2[maxAngle];
const int saMax = 30;
byte subArray[saMax];
int resto = 0;
 
// This sketch code is based on the RPLIDAR driver library provided by RoboPeak
#include <RPLidar.h>

// You need to create an driver instance 
RPLidar lidar;

#define RPLIDAR_MOTOR 3 // The PWM pin for control the speed of RPLIDAR's motor.
                        // This pin should connected with the RPLIDAR's MOTOCTRL signal 
                       
                        
void setup() {

  Wire.begin(SLAVE_ADDRESS);
  Wire.onReceive(receiveData);
  Wire.onRequest(sendData);
  
  //Initialization
  for (int i = 0; i < maxAngle; i++)  {
    distances[i] = 0;   
    distances2[i] = 0;    
  }
  
  Serial.begin(9600);
  
  // bind the RPLIDAR driver to the arduino hardware serial
  lidar.begin(Serial);
  
  // set pin modes
  pinMode(RPLIDAR_MOTOR, OUTPUT);
}

void loop() {
  if (IS_OK(lidar.waitPoint())) {
    float distance = lidar.getCurrentPoint().distance; //distance value in mm unit
    float angle    = lidar.getCurrentPoint().angle; //anglue value in degree
    bool  startBit = lidar.getCurrentPoint().startBit; //whether this point is belong to a new scan
    byte  quality  = lidar.getCurrentPoint().quality; //quality of the current measurement
    
    distanceCM = int(distance/10);
    
    if(distanceCM <= 255){
      distances[int(angle)] = distanceCM; //mm to cm    
      distances2[int(angle)] = 0; //mm to cm    
    }else{
      value2 = distanceCM - limitByteValue;
      distances[int(angle)] = limitByteValue;
      if(value2 <= 255){
        distances2[int(angle)] = value2;
      }else{
        distances2[int(angle)] = limitByteValue;  
      }
    }
    
  } else {
    analogWrite(RPLIDAR_MOTOR, 0); //stop the rplidar motor
    
    // try to detect RPLIDAR... 
    rplidar_response_device_info_t info;
    if (IS_OK(lidar.getDeviceInfo(info, 100))) {
       // detected...
       lidar.startScan();
       
       // start motor rotating at max allowed speed
       analogWrite(RPLIDAR_MOTOR, 255);
       delay(1000);
    }
  }
  
}

//Function to handle a request
void receiveData(int byteCount){
    while(Wire.available()>0){
      reg=Wire.read();
    }
}

// callback for sending data
void sendData(){

  //First byte: 0-255 cm
  if (reg == 0x01){ //0-29
    for (int i = 0; i < saMax; i++)  {
      subArray[i] = distances[i];      
    }
  }else if (reg == 0x02){ //30-59
    for (int i = 0; i < saMax; i++)  {
      subArray[i] = distances[i+30];      
    }
  }else if (reg == 0x03){ //60-89
    for (int i = 0; i < saMax; i++)  {
      subArray[i] = distances[i+60];      
    }
  }else if (reg == 0x04){ //90-119
    for (int i = 0; i < saMax; i++)  {
      subArray[i] = distances[i+90];      
    }
  }else if (reg == 0x05){ //120-149
    for (int i = 0; i < saMax; i++)  {
      subArray[i] = distances[i+120];      
    }
  }else if (reg == 0x06){ //150-179
    for (int i = 0; i < saMax; i++)  {
      subArray[i] = distances[i+150];      
    }
  }else if (reg == 0x07){ //180-209
    for (int i = 0; i < saMax; i++)  {
      subArray[i] = distances[i+180];      
    }
  }else if (reg == 0x08){ //210-239
    for (int i = 0; i < saMax; i++)  {
      subArray[i] = distances[i+210];      
    }
  }else if (reg == 0x09){ //240-269
    for (int i = 0; i < saMax; i++)  {
      subArray[i] = distances[i+240];      
    }
  }else if (reg == 0x10){ //270-299
    for (int i = 0; i < saMax; i++)  {
      subArray[i] = distances[i+270];      
    }
  }else if (reg == 0x11){ //300-329
    for (int i = 0; i < saMax; i++)  {
      subArray[i] = distances[i+300];      
    }
  }else if (reg == 0x12){ //330-359
    for (int i = 0; i < saMax; i++)  {
      subArray[i] = distances[i+330];      
    }
    
  //Second byte: 256-510 cm
  }else if (reg == 0x21){ //0-29
    for (int i = 0; i < saMax; i++)  {
      subArray[i] = distances2[i];      
    }
  }else if (reg == 0x22){ //30-59
    for (int i = 0; i < saMax; i++)  {
      subArray[i] = distances2[i+30];      
    }
  }else if (reg == 0x23){ //60-89
    for (int i = 0; i < saMax; i++)  {
      subArray[i] = distances2[i+60];      
    }
  }else if (reg == 0x24){ //90-119
    for (int i = 0; i < saMax; i++)  {
      subArray[i] = distances2[i+90];      
    }
  }else if (reg == 0x25){ //120-149
    for (int i = 0; i < saMax; i++)  {
      subArray[i] = distances2[i+120];      
    }
  }else if (reg == 0x26){ //150-179
    for (int i = 0; i < saMax; i++)  {
      subArray[i] = distances2[i+150];      
    }
  }else if (reg == 0x27){ //180-209
    for (int i = 0; i < saMax; i++)  {
      subArray[i] = distances2[i+180];      
    }
  }else if (reg == 0x28){ //210-239
    for (int i = 0; i < saMax; i++)  {
      subArray[i] = distances2[i+210];      
    }
  }else if (reg == 0x29){ //240-269
    for (int i = 0; i < saMax; i++)  {
      subArray[i] = distances2[i+240];      
    }
  }else if (reg == 0x30){ //270-299
    for (int i = 0; i < saMax; i++)  {
      subArray[i] = distances2[i+270];      
    }
  }else if (reg == 0x31){ //300-329
    for (int i = 0; i < saMax; i++)  {
      subArray[i] = distances2[i+300];      
    }
  }else if (reg == 0x32){ //330-359
    for (int i = 0; i < saMax; i++)  {
      subArray[i] = distances2[i+330];      
    }
  }else{
    for (int i = 0; i < saMax; i++)  {
      subArray[i] = 4;      
    }
  }
  
  Wire.write(subArray,saMax);
}

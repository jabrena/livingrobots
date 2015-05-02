

#include <Wire.h>
//#include <RunningMedian.h>

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
boolean distances3[maxAngle];
const int saMax = 30;
byte subArray[saMax];
int resto = 0;

//EV3G support
int distanceThreshold = 40;
//0-100
int c1 = 0;
int c1s = 0;
int c2 = 0;
int c2s = 0;
int c3 = 0;
int c3s = 0;
int c4 = 0;
int c4s = 0;

//280-360
int c15 = 0;
int c15s = 0;
int c16 = 0;
int c16s = 0;
int c17 = 0;
int c17s = 0;
int c18 = 0;
int c18s = 0;

int PROTOCOL_RESPONSE_VALUE = 0;
boolean calibratedSensor = false;
long sensorIteration = 0;

unsigned long start;// = micros();

unsigned long end;// = micros();
boolean flagTime = true;
 
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
  
  resetDistances();
  
  Serial.begin(115200);
  
  // bind the RPLIDAR driver to the arduino hardware serial
  lidar.begin(Serial);
  
  // set pin modes
  pinMode(RPLIDAR_MOTOR, OUTPUT);
  

}

void loop() {
  
  if(flagTime){
    start = micros();
    flagTime = false;
  }
  
  if (IS_OK(lidar.waitPoint())) {

    //Read values from Sensor
    float distance = lidar.getCurrentPoint().distance; //distance value in mm unit
    float angle    = lidar.getCurrentPoint().angle; //anglue value in degree
    bool  startBit = lidar.getCurrentPoint().startBit; //whether this point is belong to a new scan
    byte  quality  = lidar.getCurrentPoint().quality; //quality of the current measurement
    
    //Store distances in arrays to send by I2C (1byte = 255 cm)
    storeDistances(distance,angle);
    
    //Calculate averages for EV3G
    calculateAverages();
    
    sensorIteration++;
    
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

void storeDistances(float distance, float angle){

    //Store distance in a 2 arrays
    distanceCM = int(distance/10);
    distances3[int(angle)] = true;
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
}

void calculateAverages(){

  
  //0-80
  
  int sumS = 0;
  int sum = 0;
  
  //Read distances
  for(int i = 0; i < 20; i++){
    if(distances3[i]){
      sumS++;
      sum += distances[i];  
      if(distances[i] == 255){
         sum += distances2[i];  
      }
    }
  }
  c1s = sumS;
  c1 = (int) sum/ 20;
  sumS = 0;
  sum = 0;
      
  for(int i = 21; i < 40; i++){
    if(distances3[i]){
      sumS++;
      sum += distances[i];
      if(distances[i] == 255){
         sum += distances2[i];  
      } 
    }
  }
  c2s = sumS;
  c2 = (int) sum/ 20;
  sumS = 0;
  sum = 0;
  
  for(int i = 41; i < 60; i++){
    if(distances3[i]){
      sumS++;
      sum += distances[i];
      if(distances[i] == 255){
         sum += distances2[i];  
      }
    }
  }
  c3s = sumS;
  c3 = (int) sum/ 20;
  sumS = 0;
  sum = 0;
  
  for(int i = 61; i < 80; i++){
    if(distances3[i]){
      sumS++;
      sum += distances[i];
      if(distances[i] == 255){
         sum += distances2[i];  
      } 
    }
  }
  c4s = sumS;
  c4 = (int) sum/ 20;
  sumS = 0;
  sum = 0;

  //280-360
  
  //Read distances
  for(int i = 281; i < 300; i++){
    if(distances3[i]){
      sumS++;
      sum += distances[i];  
      if(distances[i] == 255){
         sum += distances2[i];  
      }
    }
  }
  c15s = sumS;
  c15 = (int) sum/ 20;
  sumS = 0;
  sum = 0;
      
  for(int i = 301; i < 320; i++){
    if(distances3[i]){
      sumS++;
      sum += distances[i];
      if(distances[i] == 255){
         sum += distances2[i];  
      } 
    }
  }
  c16s = sumS;
  c16 = (int) sum/ 20;
  sumS = 0;
  sum = 0;
  
  for(int i = 321; i < 340; i++){
    if(distances3[i]){
      sumS++;
      sum += distances[i];
      if(distances[i] == 255){
         sum += distances2[i];  
      }
    }
  }
  c17s = sumS;
  c17 = (int) sum/ 20;
  sumS = 0;
  sum = 0;
  
  for(int i = 341; i < 359; i++){
    if(distances3[i]){
      sumS++;
      sum += distances[i];
      if(distances[i] == 255){
         sum += distances2[i];  
      } 
    }
  }
  c18s = sumS;
  c18 = (int) sum/ 19; //DETAIL
  sumS = 0;
  sum = 0;


  if(sensorIteration > 900){
    /*
    Serial.print(c3s);
    Serial.print(" ");
    Serial.print(c4s);
    Serial.print(" ");
    Serial.print(c1s);
    Serial.print(" ");
    Serial.print(c2s);
    Serial.print(" ");
    */

    Serial.print(c15);
    Serial.print(" ");
    Serial.print(c16);
    Serial.print(" ");
    Serial.print(c17);
    Serial.print(" ");
    Serial.print(c18);
    Serial.print(" ");
    
    Serial.print(c1);
    Serial.print(" ");
    Serial.print(c2);
    Serial.print(" ");
    Serial.print(c3);
    Serial.print(" ");
    Serial.print(c4);
    Serial.print(" ");
    //Serial.println(sensorIteration);
    
    end = micros();
    flagTime = true;
    
    unsigned long delta = end - start;
    Serial.println(delta);// /1000000
    
    resetDistances();
    sensorIteration = 0;
  }
  
}

//Initialization (360 distances)
void resetDistances(){

  for (int i = 0; i < maxAngle; i++)  {
    distances[i] = 0;   
    distances2[i] = 0;
    distances3[i] = false;    
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

  //EV3G Support
  

  //Read distances
  for(int i = 0; i < 90; i++){
    if(distances[i] > c1){
      c1 = distances[i];
    }
  }
  
  for(int i = 91; i < 180; i++){
    if(distances[i] > c2){
      c2 = distances[i];
    }
  }
  
  for(int i = 181; i < 270; i++){
    if(distances[i] > c3){
      c3 = distances[i];
    }
  }
  
  for(int i = 271; i < 359; i++){
    if(distances[i] > c4){
      c4 = distances[i];
    }
  }
  
  /*  
  if(distances[10] <= distanceThreshold){
    PROTOCOL_RESPONSE_VALUE = 1;
  }else{
    PROTOCOL_RESPONSE_VALUE = 0;
  }

  
  d10 = false;
  d20 = false;
  d30 = false;
  d40 = false;
  */
  Wire.write(PROTOCOL_RESPONSE_VALUE);
  
/*
  Serial.print(c1);
  Serial.print(" ");
  Serial.print(c2);
  Serial.print(" ");
  Serial.print(c3);
  Serial.print(" ");
  Serial.println(c4);
  //Serial.println("DEMO");
  */
}



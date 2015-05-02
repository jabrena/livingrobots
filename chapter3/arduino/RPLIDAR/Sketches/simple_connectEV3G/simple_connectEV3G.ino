

#include <Wire.h>
#include <RPLidar.h>

RPLidar lidar;

#define RPLIDAR_MOTOR 3 // The PWM pin for control the speed of RPLIDAR's motor.
                        // This pin should connected with the RPLIDAR's MOTOCTRL signal 
         
//I2C Slave Address
#define SLAVE_ADDRESS 0x04

int REQUEST_COMMAND = 0;
int RESPONSE_VALUE = 0;

const int maxAngle = 360; //TODO: How to define a constant
const int maxDistance = 127;//For EV3G, it is not possible to send more than 7bits
const int limitByteValue = 255;//255

byte distances[maxAngle];
byte distances2[maxAngle];
byte distances3[maxAngle];

const int angleDivision = 8;
int angleDivisions[angleDivision];

long sensorIteration = 0;

unsigned long start;

boolean flagTime = true;
  
void setup() {

  Wire.begin(SLAVE_ADDRESS);
  Wire.onReceive(receiveData);
  Wire.onRequest(requestEvent);
  
  // if analog input pin 0 is unconnected, random analog
  // noise will cause the call to randomSeed() to generate
  // different seed numbers each time the sketch runs.
  // randomSeed() will then shuffle the random function.
  randomSeed(analogRead(0));
  
  
  resetDistances();
  
  Serial.begin(9600);
  
  // bind the RPLIDAR driver to the arduino hardware serial
  lidar.begin(Serial);
  
  // set pin modes
  pinMode(RPLIDAR_MOTOR, OUTPUT);

}

void loop() {
  
  readLIDARSensor();
  Wire.requestFrom(SLAVE_ADDRESS, 1, true);
  delay(10);
}

void readLIDARSensor(){
  
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
    
    //Store distances in arrays to send by I2C (1byte (7bits) = 127 cm)
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

    int value2 = 0;
  
    //Store distance in a 2 arrays
    int distanceCM = int(distance/10);//In CM
    distances3[int(angle)] = 1;
    if(distanceCM <= limitByteValue){
      distances[int(angle)] = distanceCM; //mm to cm    
      distances2[int(angle)] = 0; //mm to cm    
    }else{
      value2 = distanceCM - limitByteValue;
      distances[int(angle)] = limitByteValue;
     
      //LEJOS 8bits to send by I2C
      if(value2 <= 255){
        distances2[int(angle)] = value2;
      }else{
        distances2[int(angle)] = limitByteValue;  
      }
      
    }  
}

void calculateAverages(){

  
  //0-80
  
  angleDivisions[0] = getAverageDistance(0, 20,20);
  angleDivisions[1] = getAverageDistance(21, 40,20);
  angleDivisions[2] = getAverageDistance(41, 60, 20);
  angleDivisions[3] = getAverageDistance(61, 80, 20);
  
  angleDivisions[4] = getAverageDistance(281, 300, 20);
  angleDivisions[5] = getAverageDistance(301, 320, 20);
  angleDivisions[6] = getAverageDistance(321, 340, 20);
  angleDivisions[7] = getAverageDistance(341, 359, 19);

  if(sensorIteration > 900){

    unsigned long end = micros();
    unsigned long delta = end - start;
    //showStats(delta);
    
    resetDistances();
    sensorIteration = 0;
    flagTime = true;

  }
  
}

void showStats(unsigned long delta){
    Serial.print(angleDivisions[4]);
    Serial.print(" ");
    Serial.print(angleDivisions[5]);
    Serial.print(" ");
    Serial.print(angleDivisions[6]);
    Serial.print(" ");
    Serial.print(angleDivisions[7]);
    Serial.print(" ");
    
    Serial.print(angleDivisions[0]);
    Serial.print(" ");
    Serial.print(angleDivisions[1]);
    Serial.print(" ");
    Serial.print(angleDivisions[2]);
    Serial.print(" ");
    Serial.print(angleDivisions[3]);
    Serial.print(" ");
    Serial.println(delta);// /1000000
}

int getAverageDistance (int from, int to, int nSamples){
  
  //int sumS = 0;
  int sum = 0;
  int average = 0;
  //int anglesMeasured = 0;
  
  //Read distances
  for(int i = from; i < to; i++){
    
    int angleDistance  = 0;
    if(distances3[i]){
      //sumS++;
      angleDistance += (int) distances[i]; 
      if(distances[i] == limitByteValue){
         angleDistance += (int) distances2[i];  
      }
      
      sum += angleDistance;
      
    }
    
  }
  //anglesMeasured = sumS;
  average = (int) sum / nSamples;
  return average / 10; // Decimenters
}

//Initialization (360 distances)
void resetDistances(){

  for (int i = 0; i < maxAngle; i++)  {
    distances[i] = (byte) 0;  
    distances2[i] = (byte) 0;
    distances3[i] = (byte) 0;  
  }

  for (int i = 0; i < angleDivision; i++)  {
    angleDivisions[i] = (byte) 0;       
  }

}

//Function to handle a request
void receiveData(int byteCount){
    while(1 < Wire.available()){
      REQUEST_COMMAND = Wire.read();
    }
}

// callback for sending data
void requestEvent(){

  //EV3G Support
  RESPONSE_VALUE = (int) angleDivisions[0];

  long randNumber;
  randNumber = random(0, 127);
  
  RESPONSE_VALUE = randNumber;

  Wire.write(RESPONSE_VALUE);
  
  delay(100);
}



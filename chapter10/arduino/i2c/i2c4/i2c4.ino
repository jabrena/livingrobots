
// TEST_i2c_Master
// Arduino 1.0.5

#include <Wire.h>

int good = 0;

void setup()
{
  Serial.begin( 9600);
  Serial.println( "Start");
  
  Wire.begin();
}  

void loop() 
{
  Wire.beginTransmission( 0000001);
  Wire.write( 0x11); // for the (simulated) register address.
  byte error = Wire.endTransmission(false); // hold bus
  if( error != 0)
  {
    Serial.print("endTransmission error = ");
    Serial.println( error);
  }
  else
  {
    Wire.requestFrom( 0x2B, 4, true);  // release bus after this
    byte avail = Wire.available();
    if( avail != 4)
    {
      Serial.print( "avail = ");
      Serial.println( avail);
      Serial.print( "good = ");
      Serial.println( good);
      good = 0;
    }
    else
    {
      char buffer[10];
      buffer[0] = Wire.read();
      buffer[1] = Wire.read();
      buffer[2] = Wire.read();
      buffer[3] = Wire.read();
      if( buffer[0] != 'A' || buffer[1] != 'B' || buffer[2] != 'C' || buffer[3] != 'D')
      {
        Serial.print( "data : ");
        buffer[4] = '\0';
        Serial.println( buffer);
      }
      else
      {
        good++;
      }
    }
  }
  delay( random( 2, 1500));
}

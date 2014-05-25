#include <iostream>
#include <fstream>
#include <vector>
#include <libplayerc++/playerc++.h>
using namespace std;
using namespace PlayerCc;

//this function takes a 2d vector of integers, normalizes it to have values between 0-255 and then writes 
//it as a image in PGM format. (See http://netpbm.sourceforge.net/doc/pgm.html)
void write_pgm(const std::string& filename, std::vector<std::vector<int> > &img){
  std::ofstream outf(filename.c_str());
  int Sx=img.size();
  int Sy=img[0].size();
  outf<<"P2"<<endl
      <<Sx<<" "<<Sy<<endl
      <<"255"<<endl;//max value

  int maxval=0;
  for(int  i=0;i<Sx;i++){ 
    for(int j=0;j<Sy;j++){ 
      if(maxval < img[i][j])
	maxval = img[i][j];
    }
  }
  for(int j=0;j<Sy;j++){ 
    for(int i=0;i<Sx;i++){ 
      outf<<(int)(img[i][Sy-j-1]/(float)maxval*255.0)<<" ";
    }
    outf<<endl;
  }
}

//update the map 
void process_data(vector<vector<int> >& map,  SonarProxy& sp,double rx, double ry, double ra){
  int mx,my;
  //just mark the trail of the robot in the map.
  mx=(rx+15.0)*10 ; 
  my=(ry+15.0)*10;
  map[mx][my]++;


}

int main(int argc, char *argv[])
{

  int runtime = 100;
  if(argc>1) runtime = atoi(argv[1]); //first argument specifies the running time in seconds

  PlayerClient    robot("localhost");
  SonarProxy      sp(&robot,0);
  Position2dProxy pp(&robot,0);
  SimulationProxy simp(&robot,0); //used to get real position of the robot

  double x,y,a,t,t0; //robot position

  const int Sx=400,Sy=400; //size of the map 
  std::vector<std::vector<int> >map(Sx,std::vector<int>(Sy,0));

  

  robot.Read();
  t0=sp.GetDataTime();//starting time;
  cerr<<"t0="<<t0<<endl;
  for(;;)
  {
    double turnrate, speed;

    // read from the proxies
    robot.Read();


    simp.GetPose2d ("robot1", x, y, a); //position and orientation of the robot
    t=sp.GetDataTime();//current simulation time in seconds;
    cerr<<"Elapsed Time: "<<t-t0<<endl; 
    cerr<<"Robot Pose (x,y,theta): "<<x<<","<<y<<","<<a<<endl;
    cerr<<"Sonar Data: ";
    for(unsigned int i=0; i<sp.GetCount(); i++){
      cerr<<sp[i]<<" ";
    }
    cerr<<endl;
    //you can get the post of the sonars in robot's local coordinate system using:
    player_pose3d_t  pose;
    sp.RequestGeom();
    cerr<<"Sonar pose (x,y,theta) in local coordinates:";
    for(unsigned int i=0; i<sp.GetPoseCount(); i++){
      pose=sp.GetPose(i);
      cerr<<"("<<pose.px<<","<<pose.py<<","<<pose.pyaw<<") ";
    }
    cerr<<endl;

    

    process_data(map,sp,x,y,a);
    // do simple collision avoidance
    if((sp[0] + sp[1]) < (sp[6] + sp[7]))
      turnrate = dtor(-20); // turn 20 degrees per second
    else
      turnrate = dtor(20);

    if(sp[3] < 0.500)
      speed = 0;
    else
      speed = 0.100;
        
    // command the motors
    pp.SetSpeed(speed, turnrate);

    if(t-t0 > runtime){
      write_pgm("map.pgm",map);
      return 0;
    }
      
  }
}


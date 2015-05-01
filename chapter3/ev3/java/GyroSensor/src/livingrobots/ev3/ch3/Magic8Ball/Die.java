package livingrobots.ev3.ch3.Magic8Ball;

public class Die {

    private int sides;  // number of sides, > 0
    private int top;    // side facing up,  1 to sides inclusive
    
    public Die () {
        sides = 6;
        top   = 1;
    }
    
    public Die (int specifiedSides) {
        sides = Math.abs(specifiedSides);
        top   = 1;
    }
    
    public Die (int specifiedSides, int specifiedTop) {
        sides = Math.abs(specifiedSides);
        top   = ((Math.abs(specifiedTop) - 1) % sides) + 1;
    }
    
    public void setTop (int specifiedTop) {
        top = ((Math.abs(specifiedTop) - 1) % sides) + 1;
    }
    
    public int getTop () {
        return top;
    }
    
    public void roll () {
        top = (int) Math.round(sides * Math.random());
    }
    
}
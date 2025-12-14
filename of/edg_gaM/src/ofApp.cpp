#include "ofApp.h"

//--------------------------------------------------------------
void ofApp::setup()
{
    ofSetWindowTitle("edg_gaM");
    
    fbw=800;
    fbh=450;
    fb.allocate(fbw,fbh, GL_RGBA);
    
    r=c=0;
    for(int i=0;i<NR;i++)
        for(int j=0;j<NC;j++)
            buf[i]+=" ";
}

//--------------------------------------------------------------
void ofApp::update(){}

//--------------------------------------------------------------
void ofApp::draw()
{
    fb.begin();
        ofBackground(0);
        ofSetColor(0,255,255);
        
        for(int i=0;i<NR;i++)
            ofDrawBitmapString(buf[i], 5,i*10+18);
    fb.end();
    fb.draw(0,0, WW,HH);
}

//--------------------------------------------------------------
void ofApp::exit(){}

//--------------------------------------------------------------
void ofApp::keyPressed(int key)
{
    if(key==13)
    {
        r=(r+1)%NR;
        c=0;
    }
    else
    {
        buf[r][c]=(char)key;
        c=(c+1)%NC;
    }
}

//--------------------------------------------------------------
void ofApp::keyReleased(int key){}

//--------------------------------------------------------------
void ofApp::mouseMoved(int x, int y ){}

//--------------------------------------------------------------
void ofApp::mouseDragged(int x, int y, int button){}

//--------------------------------------------------------------
void ofApp::mousePressed(int x, int y, int button){}

//--------------------------------------------------------------
void ofApp::mouseReleased(int x, int y, int button){}

//--------------------------------------------------------------
void ofApp::mouseScrolled(int x, int y, float scrollX, float scrollY){}

//--------------------------------------------------------------
void ofApp::mouseEntered(int x, int y){}

//--------------------------------------------------------------
void ofApp::mouseExited(int x, int y){}

//--------------------------------------------------------------
void ofApp::windowResized(int w, int h){}

//--------------------------------------------------------------
void ofApp::gotMessage(ofMessage msg){}

//--------------------------------------------------------------
void ofApp::dragEvent(ofDragInfo dragInfo){}


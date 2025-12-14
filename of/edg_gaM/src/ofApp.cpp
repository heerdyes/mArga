#include "ofApp.h"

//--------------------------------------------------------------
void ofApp::setup()
{
    ofSetWindowTitle("edg_gaM");
    
    fbw=800;
    fbh=450;
    fb.allocate(fbw,fbh, GL_RGBA);
}

//--------------------------------------------------------------
void ofApp::update(){}

//--------------------------------------------------------------
void ofApp::draw()
{
    fb.begin();
        ofBackground(0);
        ofSetColor(0,255,255);
        ofDrawBitmapString("hello from c++", 20,20);
    fb.end();
    fb.draw(0,0, WW,HH);
}

//--------------------------------------------------------------
void ofApp::exit(){}

//--------------------------------------------------------------
void ofApp::keyPressed(int key){}

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


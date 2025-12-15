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

void ofApp::fbrndr()
{
    ofBackground(192);
    ofSetColor(0);
    
    float xx=6,yy=18,ygap=16;
    float curw=7;
    int r=0;
    
    ofDrawBitmapStringHighlight(ofToString(r,2,'0'), xx,yy);
    xx+=22;
    
    for(cnode *i=buf.hd;i!=nullptr;i=i->n)
    {
        // cursor
        if(i==buf.mh)
        {
            float ycur=yy+3;
            ofDrawLine(xx,ycur,xx+curw,ycur);
        }
        // char rndr
        if(i->c=='\n')
        {
            xx=6;
            yy+=ygap;
            r++;
            ofDrawBitmapStringHighlight(ofToString(r,2,'0'), xx,yy);
            xx+=22;
        }
        else
        {
            ofDrawBitmapString(ofToString(i->c), xx,yy);
            xx+=9;
        }
    }
}

//--------------------------------------------------------------
void ofApp::draw()
{
    fb.begin();
    fbrndr();
    fb.end();
    fb.draw(0,0, WW,HH);
}

//--------------------------------------------------------------
void ofApp::exit(){}

//--------------------------------------------------------------
void ofApp::keyPressed(int key)
{
    cout<<key<<endl;
    if(key==3680) return;
    if(key==1) return;
    if(key==3682) return;
    if(key==2) return;
    
    if(key==57358) // -> arrow
    {
        buf.mhr();
    }
    else if(key==57356) // <- arrow
    {
        buf.mhl();
    }
    else if(key==57359) // v arrow
    {
        buf.mhd();
    }
    else if(key==57357) // ^ arrow
    {
        buf.mhu();
    }
    else if(key==127) // del
    {
        buf.delr();
    }
    else if(key==13) // NL is special as always
    {
        buf.insr('\n');
        buf.mhr();
    }
    else
    {
        buf.insr((char)key);
        buf.mhr();
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


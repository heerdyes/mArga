#pragma once

#include "ofMain.h"

#define WW ofGetWidth()
#define HH ofGetHeight()
#define NR 20
#define NC 80

class cnode
{
public:
    char c;
    cnode *p;
    cnode *n;
    
    cnode(char cc)
    {
        c=cc;
        p=nullptr;
        n=nullptr;
    }
};

class clist
{
public:
    cnode *hd;
    cnode *tl;
    cnode *mh;
    
    clist()
    {
        hd=tl=mh=nullptr;
    }
    
    bool mt() { return hd==nullptr || tl==nullptr || mh==nullptr; }
    
    void append(char c)
    {
        cnode *tmp=new cnode(c);
        
        if(hd==nullptr || tl==nullptr)
        {
            mh=hd=tl=tmp;
        }
        else
        {
            tmp->p=tl;
            tl->n=tmp;
            tl=tmp;
        }
    }
    
    void mhl()
    {
        if(mh==nullptr) return;
        if(mh==hd) return;
        mh=mh->p;
    }
    
    void mhr()
    {
        if(mh==nullptr) return;
        if(mh==tl) return;
        mh=mh->n;
    }
    
    void mhd()
    {
        if(mh==nullptr) return;
        if(mh==tl) return;
        while(mh->c!='\n' && mh!=tl) mh=mh->n;
        mhr();
    }
    
    void mhu()
    {
        if(mh==nullptr) return;
        if(mh==hd) return;
        while(mh->c!='\n' && mh!=hd) mh=mh->p;
        mhl();
    }
    
    void insl(char c)
    {
        cnode *tmp=new cnode(c);
        if(mh==nullptr || hd==nullptr || tl==nullptr)
        {
            mh=hd=tl=tmp;
        }
        else
        {
            if(mh->p==nullptr) // means mh at hd
            {
                tmp->n=mh;
                mh->p=tmp;
                hd=tmp;
            }
            else
            {
                tmp->n=mh;
                tmp->p=mh->p;
                mh->p->n=tmp;
                mh->p=tmp;
            }
        }
    }
    
    void insr(char c)
    {
        cnode *tmp=new cnode(c);
        if(mh==nullptr || hd==nullptr || tl==nullptr)
        {
            mh=hd=tl=tmp;
        }
        else
        {
            if(mh->n==nullptr) // means mh at tl
            {
                tmp->p=mh;
                mh->n=tmp;
                tl=tmp;
            }
            else
            {
                tmp->n=mh->n;
                tmp->p=mh;
                mh->n->p=tmp;
                mh->n=tmp;
            }
        }
    }
    
    void delr()
    {
        if(mh==nullptr) return;
        if(mh==hd && mh==tl) mh=hd=tl=nullptr;
        if(mh==hd)
        {
            mh=hd->n;
            hd->n=mh->p=nullptr;
            hd=mh;
        }
        else if(mh==tl)
        {
            mh=tl->p;
            mh->n=tl->p=nullptr;
            tl=mh;
        }
        else
        {
            mh->p->n=mh->n;
            mh->n->p=mh->p;
            cnode *tmp=mh->n;
            mh->n=mh->p=nullptr;
            mh=tmp;
        }
    }
    
    // debug purposes
    void dump()
    {
        if(hd!=nullptr)
        {
            for(cnode *i=hd;i!=nullptr;i=i->n)
                cout<<i->c<<endl;
        }
    }
};

class ofApp : public ofBaseApp{

	public:
		void setup() override;
		void update() override;
		void draw() override;
		void exit() override;

		void keyPressed(int key) override;
		void keyReleased(int key) override;
		void mouseMoved(int x, int y ) override;
		void mouseDragged(int x, int y, int button) override;
		void mousePressed(int x, int y, int button) override;
		void mouseReleased(int x, int y, int button) override;
		void mouseScrolled(int x, int y, float scrollX, float scrollY) override;
		void mouseEntered(int x, int y) override;
		void mouseExited(int x, int y) override;
		void windowResized(int w, int h) override;
		void dragEvent(ofDragInfo dragInfo) override;
		void gotMessage(ofMessage msg) override;
		
		void fbrndr();
		
		ofFbo fb;
		int fbw,fbh;
		clist buf;
};


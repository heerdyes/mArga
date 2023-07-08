let tp = {
    r: 0,
    c: 0,
    dir: '>'
};
let state = 0;
let mxg, trb;
let r0 = '/';

function setup() {
    createCanvas(1280, 720);
    background(0);
    textSize(18);
    textFont("OCRA");
    mxg = color(0, 255, 0);
    trb = color(23, 202, 232);
    fill(mxg);
    noStroke();
    frameRate(3);
}

function rndr() {
    let xpos = 20,
        ypos = 30;
    for (let i = 0; i < tape.length; i++) {
        xpos = 20;
        for (let j = 0; j < tape[i].length; j++) {
            if (i == tp.r && j == tp.c) {
                fill(mxg);
            } else {
                fill(trb);
            }
            xpos += 20;
            text(tape[i][j], xpos, ypos);
        }
        ypos += 24;
    }
    // registers
    stroke(0, 112, 0);
    line(0, height - 55, width, height - 55);
    noStroke();
    text('r0=' + r0, 30, height - 30);
    text('; state=' + state, 90, height - 30);
}

function nxtpos() {
    let cols = tape[0].length;
    let rows = tape.length;
    if (tp.dir == '>') {
        return [tp.r, (tp.c + 1) % cols];
    }
    if (tp.dir == '<') {
        return [tp.r, tp.c == 0 ? cols - 1 : tp.c - 1];
    }
    if (tp.dir == '^') {
        return [tp.r == 0 ? rows - 1 : tp.r - 1, tp.c];
    }
    if (tp.dir == 'v') {
        return [(tp.r + 1) % rows, tp.c];
    }
    return [tp.r, tp.c];
}

function nxt() {
    let rc = nxtpos();
    tp.r = rc[0];
    tp.c = rc[1];
}

function fde() {
    let inst = tape[tp.r][tp.c];
    // fsm to parse register assignment
    if ('<>^v'.indexOf(inst) != -1) {
        tp.dir = inst;
    } else if (state == 0) {
        if (inst == 'r') {
            state = 1;
        } else {
            state = 0;
        }
    } else if (state == 1) {
        if (inst == '0') {
            state = 2;
        } else if (inst == 'r') {
            state = 1;
        } else {
            state = 0;
        }
    } else if (state == 2) {
        if (inst == '=') {
            nxt();
            r0 = tape[tp.r][tp.c];
        }
        state = 0;
    }
    nxt();
}

function draw() {
    background(0);
    fde();
    rndr();
}
var tapedata = [
    '>1234r0=89vbcdef',
    '0123456789rbcdef',
    '01234567890bcdef',
    's1234d6789=bcdef',
    '0123456789abcdef',
    '0123456789abcdef',
    '^1234=0r89<bcdef',
    '0123456789abcdef',
    '0123456789abcdef',
    '0123456789abcdef',
    '0123456789abcdef',
    '0123456789abcdef',
    '0123456789abcdef',
    '0123456789abcdef',
    '0123456789abcdef',
    '0123456789abcdef'
];
var tape = [];

for (let i = 0; i < tapedata.length; i++) {
    let row = tapedata[i].split('');
    tape.push(row);
}

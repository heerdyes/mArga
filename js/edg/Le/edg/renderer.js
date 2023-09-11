const information = document.getElementById('info')
information.innerText = `This app is using Chrome (v${versions.chrome()}), Node.js (v${versions.node()}), and Electron (v${versions.electron()})`

const fntf = document.getElementById('filename')

const loadfile = () => {
  let sfile = fntf.value
  if(sfile === '') {
    console.log('empty file name not allowed!')
    return
  }
  edg.loadfile(sfile).then((r) => {
    console.log('returned: ' + r)
    information.innerHTML = r
  })
}

const bfl = document.getElementById('bfileloader')
bfl.onclick = loadfile

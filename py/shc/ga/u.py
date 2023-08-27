def cadr(s):
  arg0 = ''
  ctr = 0
  for c in s:
    if c == ' ':
      break
    ctr += 1
  if ctr == len(s):
    return s, ''
  return s[0:ctr], s[ctr + 1:]


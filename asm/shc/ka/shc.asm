section .text
  global _start

_start:
  ; prompt prm
  mov eax,4      ; syscall sys_write
  mov ebx,1      ; stdout
  mov ecx,prm    ; shell prompt
  mov edx,len    ; msg len
  int 0x80       ; interrupt
  
  ; scan n0
  mov eax,3      ; sys_read
  mov ebx,2      ; stdin
  mov ecx,n0     ; &n0
  mov edx,5      ; len(n0)
  int 0x80       ; interrupt
  
  ; printout
  mov eax,4      ; sys_write
  mov ebx,1      ; stdout
  mov ecx,n0     ; n0
  mov edx,5      ; len(n0)
  int 0x80       ; interrupt

endprog:
  mov eax,1      ; sys_exit
  mov ebx,0      ; exitcode
  int 0x80       ; interrupt

section .data
  prm db '-> '   ; message string
  len equ $-prm  ; string length

section .bss
  n0 resb 5

LOOP: LDX 63
      IN 2,2
      If (2 == -1) JMP GO
      STR 2,1,0
      STX 62
      LDR 1,6,2
      AIR 1,1
      STR 63
      JMP LOOP
GO:   JMP 1000 begin execution

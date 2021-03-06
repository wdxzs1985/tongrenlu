-- 无法呈现对象 APPLICATION 的 TABLE DDL。带有 DBMS_METADATA 的 R_WIKI 正在尝试内部生成器。
CREATE TABLE R_WIKI 
(
  WIKI_ID VARCHAR2(15 BYTE) 
, TAG_ID VARCHAR2(15 BYTE) 
, USER_ID VARCHAR2(15 BYTE) 
, CONTENT NCLOB 
, ADD_DATE DATE 
, UPD_DATE DATE 
, DEL_FLG CHAR(1 BYTE) DEFAULT 0 
) 
LOGGING 
TABLESPACE USERS 
PCTFREE 10 
INITRANS 1 
STORAGE 
( 
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1 
  MAXEXTENTS UNLIMITED 
  BUFFER_POOL DEFAULT 
) 
NOCOMPRESS 
LOB (CONTENT) STORE AS SYS_LOB0000037549C00004$$ 
( 
  ENABLE STORAGE IN ROW 
  CHUNK 8192 
  RETENTION 
  NOCACHE 
  LOGGING 
  TABLESPACE USERS 
  STORAGE 
  ( 
    INITIAL 65536 
    NEXT 1048576 
    MINEXTENTS 1 
    MAXEXTENTS UNLIMITED 
    BUFFER_POOL DEFAULT 
  )  
)

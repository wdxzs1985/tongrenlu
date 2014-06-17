--------------------------------------------------------
--  DDL for Table R_COMIC
--------------------------------------------------------

  CREATE TABLE "APPLICATION"."R_COMIC" 
   (	"ARTICLE_ID" VARCHAR2(15 BYTE), 
	"ADD_DATE" DATE, 
	"UPD_DATE" DATE, 
	"DEL_FLG" CHAR(1 BYTE) DEFAULT 0, 
	"RED_FLG" CHAR(1 BYTE) DEFAULT 0, 
	"TRANSLATE_FLG" CHAR(1 BYTE) DEFAULT 0
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;

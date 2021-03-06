--------------------------------------------------------
--  DDL for Table M_TRACK
--------------------------------------------------------

  CREATE TABLE "APPLICATION"."M_TRACK" 
   (	"SONG_TITLE" NVARCHAR2(255), 
	"LEAD_ARTIST" NVARCHAR2(255), 
	"TRACK_NUMBER" NUMBER DEFAULT 0, 
	"FILE_ID" VARCHAR2(15 BYTE), 
	"ADD_DATE" DATE, 
	"UPD_DATE" DATE, 
	"DEL_FLG" CHAR(1 BYTE) DEFAULT 0, 
	"ORIGINAL_TITLE" NVARCHAR2(1024)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;

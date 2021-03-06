--------------------------------------------------------
--  DDL for Table R_CIRCLE_MAP
--------------------------------------------------------

  CREATE TABLE "APPLICATION"."R_CIRCLE_MAP" 
   (	"CIRCLE_ID" VARCHAR2(15 BYTE), 
	"AREA1" NVARCHAR2(1), 
	"AREA2" VARCHAR2(2 BYTE), 
	"AREA3" VARCHAR2(1 BYTE), 
	"ADD_DATE" DATE, 
	"UPD_DATE" DATE, 
	"DEL_FLG" CHAR(1 BYTE) DEFAULT 0, 
	"EVENT_CODE" VARCHAR2(20 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;

drop table T_ACCOUNT if exists;

create table T_ACCOUNT (ID bigint identity primary key, NUMBER varchar(9),
                        NAME varchar(50) not null, BALANCE decimal(8,2), unique(NUMBER));
                        
ALTER TABLE T_ACCOUNT ALTER COLUMN BALANCE SET DEFAULT 0.0;

drop table T_WXACCOUNT if exists;

create table T_WXACCOUNT (ID bigint identity primary key
                        ,AVATAR_URL varchar(255)
                        ,NICK_NAME varchar(50) not null
                        ,CITY varchar(10)
                        );
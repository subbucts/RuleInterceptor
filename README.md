# RuleInterceptor

Rule Interceptor is a library.

The environment variable settings needed for the parent microservice are: 
 "ruledb": [{"credentials": {"uri": "<<URI Details>>","max_conns": "<<no.of connections>>"},"dbname": "<<name of the product>>"}]
 
 The sample details for postgres DB are: 
 
 "ruledb": [{"credentials": {"uri": "postgres://iwztgpsa:eaZgb_yvxXV0JpXc_XLhMAblunGwRIDz@jumbo.db.elephantsql.com:5432/iwztgpsa","max_conns": "5"},"dbname": "elephantsql"}]
 
 
 DB table and sequence scripts are below:
 
 create table rule_metadata (
RULE_ID INT PRIMARY KEY,
RULE_NAME TEXT NOT NULL UNIQUE,
RULE_DESCRIPTION TEXT, 
MICRO_SERVICE_NAME TEXT NOT NULL,
ENABLED TEXT NOT NULL ,
PRE_POST_HOOK TEXT NOT NULL ,
EXEC_SEQ INT NOT NULL,
RULE_TYPE TEXT NOT NULL,
PROCEED_IND TEXT NOT NULL,
CREATED_BY TEXT,
CREATED_TIME DATE,
MODIFIED_BY TEXT,
MODIFIED_TIME DATE
);


CREATE SEQUENCE SEQ_RM_RULE_ID START 1;

create table rule_data (
RULE_ID INT NOT NULL UNIQUE,
RULE_NAME TEXT NOT NULL UNIQUE,
RULE_CONTENT TEXT, 
RULE_DEPENDENCY TEXT,
ENABLED TEXT NOT NULL ,
IS_COMPILED TEXT NOT NULL 
);


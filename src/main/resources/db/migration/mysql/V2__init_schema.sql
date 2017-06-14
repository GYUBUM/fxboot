create table t_authorities (authorities_id bigint not null auto_increment, role_id bigint, username varchar(50), primary key (authorities_id)) ENGINE=InnoDB;
create table t_resources (resources_id bigint not null auto_increment, url varchar(255), primary key (resources_id)) ENGINE=InnoDB;
create table t_role (role_id bigint not null auto_increment, role_name varchar(100), primary key (role_id)) ENGINE=InnoDB;
create table t_role_resource (role_resource_id bigint not null auto_increment, resources_id bigint, role_id bigint, primary key (role_resource_id)) ENGINE=InnoDB;
create table t_user (username varchar(20) not null, accountnonexpired varchar(255), accountnonlocked varchar(255), address varchar(120), credentialsnonexpired varchar(255), email varchar(100), enabled varchar(255), password varchar(200) not null, phone varchar(20), primary key (username)) ENGINE=InnoDB;
alter table t_authorities add constraint FKieyvwqd4ja74o5vpnfr4ivbxn foreign key (role_id) references t_role (role_id);
alter table t_authorities add constraint FK2vsvnhavwx6f5qei6qpg6lf4 foreign key (username) references t_user (username);
alter table t_role_resource add constraint FKe9end1043jxgke9d6troai43h foreign key (resources_id) references t_resources (resources_id);
alter table t_role_resource add constraint FKjoc0b6anonklrmwalu5g6udfl foreign key (role_id) references t_role (role_id);
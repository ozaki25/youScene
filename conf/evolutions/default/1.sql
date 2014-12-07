# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table contents (
  id                        bigint not null,
  title                     varchar(255),
  article                   varchar(255),
  user_id                   bigint,
  created_date              timestamp not null,
  updated_date              timestamp not null,
  constraint pk_contents primary key (id))
;

create table users (
  id                        bigint not null,
  user_id                   varchar(255),
  name                      varchar(255),
  section_name              varchar(255),
  constraint pk_users primary key (id))
;

create sequence contents_seq;

create sequence users_seq;

alter table contents add constraint fk_contents_user_1 foreign key (user_id) references users (id) on delete restrict on update restrict;
create index ix_contents_user_1 on contents (user_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists contents;

drop table if exists users;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists contents_seq;

drop sequence if exists users_seq;


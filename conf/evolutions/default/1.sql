# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table accesses (
  id                        bigint not null,
  content_id                bigint,
  user_id                   bigint,
  created_date              timestamp not null,
  constraint pk_accesses primary key (id))
;

create table contents (
  id                        bigint not null,
  title                     varchar(255),
  article                   varchar(255),
  author_id                 bigint,
  created_date              timestamp not null,
  updated_date              timestamp not null,
  constraint pk_contents primary key (id))
;

create table likes (
  id                        bigint not null,
  content_id                bigint,
  user_id                   bigint,
  created_date              timestamp not null,
  constraint pk_likes primary key (id))
;

create table users (
  id                        bigint not null,
  user_id                   varchar(255),
  name                      varchar(255),
  section_name              varchar(255),
  constraint pk_users primary key (id))
;

create sequence accesses_seq;

create sequence contents_seq;

create sequence likes_seq;

create sequence users_seq;

alter table accesses add constraint fk_accesses_content_1 foreign key (content_id) references contents (id) on delete restrict on update restrict;
create index ix_accesses_content_1 on accesses (content_id);
alter table accesses add constraint fk_accesses_user_2 foreign key (user_id) references users (id) on delete restrict on update restrict;
create index ix_accesses_user_2 on accesses (user_id);
alter table contents add constraint fk_contents_author_3 foreign key (author_id) references users (id) on delete restrict on update restrict;
create index ix_contents_author_3 on contents (author_id);
alter table likes add constraint fk_likes_content_4 foreign key (content_id) references contents (id) on delete restrict on update restrict;
create index ix_likes_content_4 on likes (content_id);
alter table likes add constraint fk_likes_user_5 foreign key (user_id) references users (id) on delete restrict on update restrict;
create index ix_likes_user_5 on likes (user_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists accesses;

drop table if exists contents;

drop table if exists likes;

drop table if exists users;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists accesses_seq;

drop sequence if exists contents_seq;

drop sequence if exists likes_seq;

drop sequence if exists users_seq;


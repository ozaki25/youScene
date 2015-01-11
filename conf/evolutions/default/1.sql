# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table accesses (
  id                        bigint not null,
  blog_id                   bigint,
  user_id                   bigint,
  created_date              timestamp not null,
  constraint pk_accesses primary key (id))
;

create table blogs (
  id                        bigint not null,
  title                     varchar(255),
  article                   varchar(255),
  author_id                 bigint,
  created_date              timestamp not null,
  updated_date              timestamp not null,
  constraint pk_blogs primary key (id))
;

create table images (
  id                        bigint not null,
  image_name                varchar(255),
  user_id                   bigint,
  created_date              timestamp not null,
  constraint pk_images primary key (id))
;

create table likes (
  id                        bigint not null,
  blog_id                   bigint,
  user_id                   bigint,
  created_date              timestamp not null,
  constraint pk_likes primary key (id))
;

create table tags (
  id                        bigint not null,
  tag_name                  varchar(255),
  created_date              timestamp not null,
  constraint pk_tags primary key (id))
;

create table users (
  id                        bigint not null,
  user_id                   varchar(255),
  name                      varchar(255),
  section_name              varchar(255),
  constraint pk_users primary key (id))
;


create table tags_blogs (
  tags_id                        bigint not null,
  blogs_id                       bigint not null,
  constraint pk_tags_blogs primary key (tags_id, blogs_id))
;
create sequence accesses_seq;

create sequence blogs_seq;

create sequence images_seq;

create sequence likes_seq;

create sequence tags_seq;

create sequence users_seq;

alter table accesses add constraint fk_accesses_blog_1 foreign key (blog_id) references blogs (id) on delete restrict on update restrict;
create index ix_accesses_blog_1 on accesses (blog_id);
alter table accesses add constraint fk_accesses_user_2 foreign key (user_id) references users (id) on delete restrict on update restrict;
create index ix_accesses_user_2 on accesses (user_id);
alter table blogs add constraint fk_blogs_author_3 foreign key (author_id) references users (id) on delete restrict on update restrict;
create index ix_blogs_author_3 on blogs (author_id);
alter table images add constraint fk_images_user_4 foreign key (user_id) references users (id) on delete restrict on update restrict;
create index ix_images_user_4 on images (user_id);
alter table likes add constraint fk_likes_blog_5 foreign key (blog_id) references blogs (id) on delete restrict on update restrict;
create index ix_likes_blog_5 on likes (blog_id);
alter table likes add constraint fk_likes_user_6 foreign key (user_id) references users (id) on delete restrict on update restrict;
create index ix_likes_user_6 on likes (user_id);



alter table tags_blogs add constraint fk_tags_blogs_tags_01 foreign key (tags_id) references tags (id) on delete restrict on update restrict;

alter table tags_blogs add constraint fk_tags_blogs_blogs_02 foreign key (blogs_id) references blogs (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists accesses;

drop table if exists blogs;

drop table if exists tags_blogs;

drop table if exists images;

drop table if exists likes;

drop table if exists tags;

drop table if exists users;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists accesses_seq;

drop sequence if exists blogs_seq;

drop sequence if exists images_seq;

drop sequence if exists likes_seq;

drop sequence if exists tags_seq;

drop sequence if exists users_seq;


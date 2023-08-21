create table annotation (priority integer, annotation_id bigint not null, creation_date timestamp, due_date timestamp, modification_date timestamp, md_text varchar(10000), primary key (annotation_id));
create table annotation_tags (annotation_annotation_id bigint not null, tags_tag_id bigint not null, primary key (annotation_annotation_id, tags_tag_id));
create table tag (tag_id bigint not null, abbreviation varchar(255), color varchar(255), tag_name varchar(255), primary key (tag_id));
